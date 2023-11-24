//Complete
//Should we add some additional comments especially where code extends beyond this course?

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.PatternSyntaxException;

/**
 * The `Driver` class represents the main entry point for running a simulation of a chemical reactor
 * with a PID controller. It reads input parameters from a file, initializes the reactor, controller,
 * and reaction, and performs a simulation using the PID controller.
 *
 * The input file should contain key-value pairs separated by a colon (":"). The keys are used to
 * identify and retrieve specific parameters for initializing the simulation.
 *
 * The output of the simulation is written to a CSV file, and the file name for output is also specified
 * in the input file.
 *
 * @author Ogechi
 */
public class Driver {

    /**
     * The main method of the `Driver` class. It reads input parameters from a file, initializes the
     * reactor, controller, and reaction, and performs a simulation using the PID controller. The output
     * of the simulation is written to a CSV file.
     *
     * @param args Command line arguments (not used).
     * @throws IOException If an error occurs during file reading or writing.
     * @author Ogechi
     */
    public static void main(String[] args) throws IOException {
        Reaction reaction;
        String[] header = null;
        CSTRReactor cstrReactor;
        boolean isControlled;
        PIDController pidController;
        String SEPARATOR = ",";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter file name: ");
        String fileName = scanner.nextLine();
        scanner.close();
        String fileOutName = null;
        Map<String, String> dict = populateFromFile(fileName);


        //attempt to initialize file population parameters from the populated dictionary
        try {
            fileOutName = dict.get("populate to file");
            header = dict.get("header").split(",");
        } catch(NullPointerException e){
            throw new IllegalArgumentException("Element of File population parameter information value is null");
        } catch (PatternSyntaxException e){
            System.out.println("Ensure the list of headers are delimited by commas");
        }

        //attempt to initialize Reaction parameters from the populated dictionary
        try{
            reaction = new Reaction(dict.get("reaction equation"), Double.parseDouble(dict.get("k")));

        } catch(NullPointerException e){
            throw new IllegalArgumentException("Element of Reaction parameter information value is null");
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("k must be a double");
        }


        //attempt to initialize Reactor parameters from the populated dictionary
        try {
            double V = Double.parseDouble(dict.get("volume"));
            double initialFlow = Double.parseDouble(dict.get("initial flow"));
            String[] str = dict.get("initial concentrations").split(SEPARATOR);
            int n = str.length;
            double[] initialConcentrations = new double[n];
            for (int i = 0; i < n; i++){
                initialConcentrations[i] = Double.parseDouble(str[i]);
            }
            str = dict.get("inlet concentrations").split(SEPARATOR);
            n = str.length;
            double[] inletConcentrations = new double[n];
            for (int i = 0; i < n; i++){
                inletConcentrations[i] = Double.parseDouble(str[i]);
            }
            isControlled = Boolean.parseBoolean(dict.get("is controlled"));
            if (!isControlled){
                cstrReactor = new CSTRReactor(V, initialFlow, reaction, initialConcentrations, inletConcentrations);
            } else {
                cstrReactor = new CSTRReactor(V, initialFlow, reaction, initialConcentrations, inletConcentrations, Integer.parseInt(dict.get("controlled")), true);
            }

        } catch (NullPointerException e){
            throw new IllegalArgumentException("Element of Reactor information value is null");
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("inlet Concentrations and initial concentrations must consist of double values");
        }

        try {

            double startTime = Double.parseDouble(dict.get("start time"));
            double endTime = Double.parseDouble(dict.get("end time"));
            double timeStep = Double.parseDouble(dict.get("time step"));
            double tolerance = Double.parseDouble(dict.get("tolerance"));
            Queue<double[]> disturbances = new Queue<>();
            if(!(dict.get("disturbances")).contains(";")){
                String[] s = dict.get("disturbances").trim().split(",");
                double[] d = new double[2];
                for(int i = 0; i < 2; i++){
                    d[i] = Double.parseDouble(s[i].trim());
                }
                disturbances.enqueue(d);
            }else{
                String[] str = dict.get("disturbances").trim().split(";");
                for (String string : str){
                    String[] s = string.trim().split(",");
                    double[] d = new double[2];
                    for(int i = 0; i < 2; i++){
                        d[i] = Double.parseDouble(s[i].trim());
                    }
                    disturbances.enqueue(d);
                }
            }
            if(!isControlled){
                pidController = new PIDController(startTime, endTime, timeStep, 0, 0, 0, PIDController.CONTROLLER_TYPE.UNCONTROLLED, 0, cstrReactor, tolerance, disturbances, 0);
            } else {
                pidController = new PIDController(startTime, endTime, timeStep,
                        Double.parseDouble(dict.get("controller gain")),
                        Double.parseDouble(dict.get("integrating time constant")),
                        Double.parseDouble(dict.get("derivative time constant")),
                        Util.getControllerTypeByLabel(dict.get("controller type")),
                        Double.parseDouble(dict.get("dead time")),
                        cstrReactor, tolerance,disturbances,
                        Double.parseDouble(dict.get("set point")));
                int i = cstrReactor.getInitialConcentrations().length;
                cstrReactor.setCurrentConcentrations(new double[i]);
            }
        }catch(NullPointerException e){
            throw new IllegalArgumentException("Element of PIDController information value is null");
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Ensure start time, end time, time step, tolerance, controller gain, integrating time constant derivative time constant, and dead time consist of double values");
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }

        double[][] simulation = pidController.simulate();

        populateToFile(fileOutName, simulation, header);

    }

    /**
     * Populates a Map with String key-value pairs from a colon-separated file. The keys in the file must
     * match those used; otherwise, the construction of Object will throw an IllegalArgumentException.
     * <p>
     * The parameters to be populated have specific keys:
     * </p>
     * <ul>
     * <li>"header": Header for file output (Time, controllable output variable values, manipulated variable, P, I, D)</li>
     * <li>"populate to file": File name to which the simulation results should be populated.</li>
     * <li>"volume": Volume of the reactor.</li>
     * <li>"initial flow": Initial flow rate of the reactor.</li>
     * <li>"initial concentrations": Initial concentrations of the reactants in the reactor (comma-separated).</li>
     * <li>"inlet concentrations": Inlet concentrations of the reactants (comma-separated).</li>
     * <li>"controlled": The index of the controllable variable in the reactor.</li>
     * <li>"is controlled": Boolean indicating whether the reactor is controlled.</li>
     * <li>"reaction equation": Equation representing the chemical reaction.</li>
     * <li>"k": Reaction rate constant.</li>
     * <li>"controller type": Type of the PID controller.</li>
     * <li>"start time": Start time of the simulation.</li>
     * <li>"end time": End time of the simulation.</li>
     * <li>"time step": Time step for the simulation.</li>
     * <li>"controller gain": Gain parameter of the PID controller.</li>
     * <li>"integrating time constant": Integrating time constant of the PID controller.</li>
     * <li>"derivative time constant": Derivative time constant of the PID controller.</li>
     * <li>"tolerance": Tolerance for the simulation.</li>
     * <li>"set point": Set point for the controlled variable.</li>
     * <li>"dead time": Dead time in the system.</li>
     * <li>"disturbances": Disturbances applied during the simulation (semicolon-separated pairs of time and magnitude).</li>
     * </ul>
     *
     * @param strFileName Path of the file.
     * @return Map<String, String> Map containing the data from the file.
     * @throws FileNotFoundException If the specified file is not found.
     * @author Ogechi
     */
    private static Map<String,String> populateFromFile(String strFileName) throws FileNotFoundException {
        String SEPARATOR = ":";
        Scanner scanner = new Scanner(new File(strFileName));

        Map<String, String> dictionary = new HashMap<>();

        //file Population info
        dictionary.put("header", null);
        dictionary.put("populate to file", null);

        //reactor  info
        dictionary.put("volume", null);
        dictionary.put("initial flow", null);
        dictionary.put("initial concentrations", null);
        dictionary.put("inlet concentrations", null);
        dictionary.put("controlled", null);
        dictionary.put("is controlled", null);

        // Reaction info
        dictionary.put("reaction equation", null);
        dictionary.put("k", null);

        //controller info

        dictionary.put("controller type", null);
        dictionary.put("start time", null);
        dictionary.put("end time", null);
        dictionary.put("time step", null);
        dictionary.put("controller gain", null);
        dictionary.put("integrating time constant", null);
        dictionary.put("derivative time constant", null);
        dictionary.put("tolerance", null);
        dictionary.put("set point", null);
        dictionary.put("dead time", null);
        dictionary.put("disturbances", null);


        while (scanner.hasNext()){
            String str  = scanner.nextLine();

            if(!str.isEmpty() && !str.trim().equals("") && !str.trim().equals(System.lineSeparator())){
                String[] currentStr = str.split(SEPARATOR,2);
                if (currentStr.length == 2){
                    if (dictionary.containsKey(currentStr[0].trim())){
                        dictionary.put(currentStr[0].toLowerCase().trim(),currentStr[1].trim());
                    }else{
                        throw new IllegalArgumentException("incorrect name format of variable name in .csv file: " + currentStr[0]);
                    }
                } else{
                    throw new IllegalArgumentException("Incorrect file format. should be in form: 'Variable name : value' " + currentStr[0]);
                }
            }
        }
        return dictionary;
    }

    /**
     * Populates a CSV file with the simulation results.
     *
     * @param filePath Path of the output file.
     * @param data Simulation data to be written to the file.
     * @param header Header for the CSV file.
     * @author Ogechi
     */
    private static void populateToFile(String filePath, double[][] data, String[] header){
        if (!filePath.toLowerCase().endsWith(".csv")) {
            filePath += ".csv";
        }
        File file = new File(filePath);

        try{
            FileWriter outputFile = new FileWriter(file);

            for (String s: header) {
                outputFile.append(s);
                outputFile.append(",");
            }
            outputFile.append('\n');
            for (double[] d: data){
                for (double value : d) {
                    outputFile.append(Double.toString(value));
                    outputFile.append(",");
                }
                outputFile.append("\n");
            }
            outputFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
