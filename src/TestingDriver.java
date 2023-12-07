/** This class is for validation of individual methods
 * Lines calling static testing methods must be uncommented to print test results
 * This class does nothing by default and is presented to show how validation of individual methods was performed
 * @author Dylan
 */
public class TestingDriver {

    /** Main method which does nothing by default
     * Can uncomment lines to run the methods used in the validation procedure
     * @param args
     * @author Dylan
     */
    public static void main(String args[]) {

        //Comment and un-comment to test

        //un-comment next line to test reactor and CSTR reactor classes
        //testReactorClass();
        //un-comment next line to test reaction class
        //testReaction();
        //un-comment next line to test species class
        //testSpecies();
        //un-comment next line to test the PID controller class
        //testPIDController();



        //RK45 has its own validation driver using an IVP with an analytical solution
        //interfaces are implemented and not testable on their own
        //Queue and SinglyLinkedList are tested when in use with controller and generic


    }

    /** Static method to test methods in the Reactor and CSTRReactor Classes
     * @author Dylan
     */
        public static void testReactorClass() {

            double volume = 1;
            double initialFlow = 0.05;
            double k = 0.2;
            String reactionString = "A->B";
            Reaction reaction = new Reaction(reactionString, k);
            double[] initialConcentrations = {0.04, 0.16};
            double[] inletConcentrations = {1.2, 0};
            int controlled = 0;
            boolean isControlled = false;
            double[] out;

            //valid reactor test
            CSTRReactor CSTR = new CSTRReactor(volume, initialFlow, reaction, initialConcentrations, inletConcentrations, controlled, isControlled);

            System.out.println("Volume 1->2 test");
            System.out.println(CSTR.getVolume());
            CSTR.setVolume(2);
            System.out.println(CSTR.getVolume());

            System.out.println("Invalid volume 2->-1 test");
            System.out.println(CSTR.getVolume());
            CSTR.setVolume(-1);
            System.out.println(CSTR.getVolume());

            System.out.println("Initial flow 0.05->0.1 test");
            System.out.println(CSTR.getVolume());
            CSTR.setInitialFlow(0.1);
            System.out.println(CSTR.getInitialFlow());

            System.out.println("Invalid initial flow 0.1->-1 test");
            System.out.println(CSTR.getVolume());
            CSTR.setInitialFlow(-1);
            System.out.println(CSTR.getInitialFlow());

            double[] initialConcentrations_2 = {0.08, 0.32};
            System.out.println("Initial concentrations 0.04, 0.16->0.08, 0.32 test");
            out = CSTR.getInitialConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);
            CSTR.setInitialConcentrations(initialConcentrations_2);
            out = CSTR.getInitialConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);

            double[] initialConcentrations_B = {-0.08, 0.32};
            System.out.println("Invalid initial concentrations 0.08, 0.32->-0.08, 0.32 test");
            out = CSTR.getInitialConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);
            CSTR.setInitialConcentrations(initialConcentrations_B);
            out = CSTR.getInitialConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);

            System.out.println("Invalid initial concentrations 0.08, 0.32->null test");
            out = CSTR.getInitialConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);
            CSTR.setInitialConcentrations(null);
            out = CSTR.getInitialConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);

            double[] inletConcentrations_2 = {2.4, 0};
            System.out.println("Inlet concentrations 1.2, 0->2.4, 0 test");
            out = CSTR.getInletConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);
            CSTR.setInletConcentrations(inletConcentrations_2);
            out = CSTR.getInletConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);

            double[] inletConcentrations_B = {-2.4, 0};
            System.out.println("Invalid inlet concentrations 2.4, 0->-2.4, 0 test");
            out = CSTR.getInletConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);
            CSTR.setInletConcentrations(inletConcentrations_B);
            out = CSTR.getInletConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);

            System.out.println("Invalid inlet concentrations 2.4, 0->null test");
            out = CSTR.getInletConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);
            CSTR.setInletConcentrations(null);
            out = CSTR.getInletConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);

            System.out.println("Controlled 0->1 test");
            System.out.println(CSTR.getControlled());
            CSTR.setControlled(1);
            System.out.println(CSTR.getControlled());

            System.out.println("isControlled false->true test");
            System.out.println(CSTR.getIsControlled());
            CSTR.setIsControlled(true);
            System.out.println(CSTR.getIsControlled());

            System.out.println("Current species number 0->1 test");
            System.out.println(CSTR.getCurrentSpeciesNumber());
            CSTR.setCurrentSpeciesNumber(1);
            System.out.println(CSTR.getCurrentSpeciesNumber());

            System.out.println("Test of constructor, invalid volume");
            try {
                CSTRReactor CSTR_2 = new CSTRReactor(-1, initialFlow, reaction, initialConcentrations, inletConcentrations, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of constructor, invalid inital flow");
            try {
                CSTRReactor CSTR_2 = new CSTRReactor(volume, -0.5, reaction, initialConcentrations, inletConcentrations, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of constructor, invalid reaction");
            try {
                CSTRReactor CSTR_2 = new CSTRReactor(volume, initialFlow, null, initialConcentrations, inletConcentrations, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of constructor, invalid initalConcentrations for null array");
            try {
                CSTRReactor CSTR_2 = new CSTRReactor(volume, initialFlow, reaction, null, inletConcentrations, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of constructor, invalid initalConcentrations for negative concentration array");
            try {
                double[] initialConcentrations_3 = {-0.04, 0.16};
                CSTRReactor CSTR_2 = new CSTRReactor(volume, initialFlow, reaction, initialConcentrations_3, inletConcentrations, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of constructor, invalid inletConcentations for null array");
            try {
                CSTRReactor CSTR_2 = new CSTRReactor(volume, initialFlow, reaction, initialConcentrations, null, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of constructor, invalid inletConcentations for negative concentration array");
            try {
                double[] inletConcentrations_3 = {-1.2, 0};
                CSTRReactor CSTR_2 = new CSTRReactor(volume, initialFlow, reaction, initialConcentrations, inletConcentrations_3, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Test of invalid rector copy");
            try {
                CSTRReactor CSTR_3 = new CSTRReactor(null);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Test of equal reactors and clone method");

            CSTRReactor CSTR_4 = CSTR.clone();
            System.out.println(CSTR.equals(CSTR_4));

            System.out.println("Test of not equal reactors");

            CSTRReactor CSTR_5 = new CSTRReactor(CSTR);
            CSTR_5.setVolume(4);
            System.out.println(CSTR.equals(CSTR_5));

            //other methods are tested whenever the controller is used

        } //end of reactor tests

    /** Static method to test methods in the reaction Class
     * @author Dylan
     */
    public static void testReaction () {
        String reactionString = "A->B";
        double k = 0.2;

        System.out.println("Test of valid reaction");

        Reaction reaction = new Reaction(reactionString, k);

        System.out.println("Test of in-valid reaction due to bad string");
        String reactionString_2 = "A->";

        try {
            Reaction reaction_2 = new Reaction(reactionString_2, k);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Test of in-valid reaction due to bad string");
        String reactionString_3 = "";

        try {
            Reaction reaction_3 = new Reaction(reactionString_3, k);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("k 0.2->0.4 test");
        System.out.println(reaction.getK());
        reaction.setK(0.4);
        System.out.println(reaction.getK());

        System.out.println("Reactants A to C test");
        Species[] output;
        output = reaction.getReactants();
        System.out.println(output[0].getName());
        Species C = new Species("C", 0);
        Species[] reactants = new Species[1];
        reactants[0] = C;
        reaction.setReactants(reactants);
        output = reaction.getReactants();
        System.out.println(output[0].getName());

        System.out.println("Invalid reactants A to null test");
        output = reaction.getReactants();
        System.out.println(output[0].getName());
        Species[] reactants_B = new Species[1];
        reactants_B = null;
        reaction.setReactants(reactants);
        output = reaction.getReactants();
        System.out.println(output[0].getName());

        System.out.println("Invalid reactants A to {null} test");
        output = reaction.getReactants();
        System.out.println(output[0].getName());
        Species[] reactants_C = new Species[1];
        reactants_C[0] = null;
        reaction.setReactants(reactants);
        output = reaction.getReactants();
        System.out.println(output[0].getName());


        System.out.println("Products B to C test");
        output = reaction.getProducts();
        System.out.println(output[0].getName());
        Species[] products = new Species[1];
        products[0] = C;
        reaction.setProducts(products);
        output = reaction.getProducts();
        System.out.println(output[0].getName());

        System.out.println("Invalid products B to null test");
        output = reaction.getProducts();
        System.out.println(output[0].getName());
        Species[] products_B = new Species[1];
        products_B = null;
        reaction.setProducts(products_B);
        output = reaction.getProducts();
        System.out.println(output[0].getName());

        System.out.println("Invalid products B to {null} test");
        output = reaction.getProducts();
        System.out.println(output[0].getName());
        Species[] products_C = new Species[1];
        products_C[0] = null;
        reaction.setProducts(products_C);
        output = reaction.getProducts();
        System.out.println(output[0].getName());

        System.out.println("Test of equal reactions and clone method");

        Reaction reaction_2 = reaction.clone();
        System.out.println(reaction.equals(reaction_2));

        System.out.println("Test of non-equal reactions");

        Reaction reaction_3 = new Reaction(reaction);
        reaction_3.setK(0.8);
        System.out.println(reaction.equals(reaction_3));

        System.out.println("Test of invalid reaction copy");
        try {
            Reaction reaction_4 = new Reaction(null);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //other methods are tested whenever the controller is used

    } //end of reaction tests

    /** Static method to test methods in the species Class
     * @author Dylan
     */
    public static void testSpecies() {

        System.out.println("Test of valid species");
        Species A = new Species("A", 1);

        System.out.println("Test of equal species and clone method");
        Species A_2 = A.clone();
        System.out.println(A.equals(A_2));

        System.out.println("Test of equal species and clone method");
        Species A_3 = A.clone();
        A_3.setName("B");
        System.out.println(A.equals(3_2));

        System.out.println("Name A->B test");
        System.out.println(A.getName());
        A.setName("B");
        System.out.println(A.getName());

        System.out.println("coefficent 1->2 test");
        System.out.println(A.getCoefficient());
        A.setCoefficient(2);
        System.out.println(A.getCoefficient());

        try {
            Species D = new Species(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        //other methods are tested whenever the controller is used

    } //end of species checks

    /** Static method to test methods in the PIDController Class
     * @author Dylan
     */
    public static void testPIDController () {

        double volume = 1;
        double initialFlow = 0.05;
        double k = 0.2;
        String reactionString = "A->B";
        Reaction reaction = new Reaction(reactionString, k);
        double[] initialConcentrations = {0.04, 0.16};
        double[] inletConcentrations = {1.2, 0};
        int controlled = 0;
        boolean isControlled = false;
        double[] out;

        //valid reactor
        CSTRReactor CSTR = new CSTRReactor(volume, initialFlow, reaction, initialConcentrations, inletConcentrations, controlled, isControlled);

        double startTime = 0;
        double endTime = 10;
        double timeStep = 1;
        double controllerGain = 1;
        double integratingTimeConstant = 1;
        double derivativeTimeConstant = 1;
        PIDController.CONTROLLER_TYPE controllerType = PIDController.CONTROLLER_TYPE.PID;
        double deadTime = 0;
        Controllable controllable = CSTR;
        double tolerance = 0.1;
        Queue<double[]> disturbances = new Queue<>();
        String string = "1.2, 0";
        String[] s = string.trim().split(",");
        double[] d = new double[2];
        for(int i = 0; i < 2; i++){
            d[i] = Double.parseDouble(s[i].trim());
        }
        disturbances.enqueue(d);
        double setPoint = 0.04;

        //valid controller test

        try { //checked exception so this is needed

            PIDController PID = new PIDController(startTime, endTime, timeStep, controllerGain, integratingTimeConstant, derivativeTimeConstant, controllerType, deadTime, controllable, tolerance, disturbances, setPoint);

            System.out.println("start time 0->0.1 test");
            System.out.println(PID.getStartTime());
            PID.setStartTime(0.1);
            System.out.println(PID.getStartTime());

            System.out.println("invalid start time 0.1->100 test");
            System.out.println(PID.getStartTime());
            PID.setStartTime(100);
            System.out.println(PID.getStartTime());

            System.out.println("end time 10->10.1 test");
            System.out.println(PID.getEndTime());
            PID.setEndTime(10.1);
            System.out.println(PID.getEndTime());

            System.out.println("invalid end time 10.1->0 test");
            System.out.println(PID.getEndTime());
            PID.setEndTime(0);
            System.out.println(PID.getEndTime());


            System.out.println("timestep 1->0.1 test");
            System.out.println(PID.getTimeStep());
            PID.setTimeStep(0.1);
            System.out.println(PID.getTimeStep());

            System.out.println("invalid timestep 0.1->100 test");
            System.out.println(PID.getTimeStep());
            PID.setTimeStep(100);
            System.out.println(PID.getTimeStep());

            System.out.println("controller gain 1->1.1 test");
            System.out.println(PID.getControllerGain());
            PID.setControllerGain(1.1);
            System.out.println(PID.getControllerGain());

            System.out.println("invalid controller gain 1.1->-1 test");
            System.out.println(PID.getControllerGain());
            PID.setControllerGain(-1);
            System.out.println(PID.getControllerGain());

            System.out.println("controller tau I 1->1.1 test");
            System.out.println(PID.getIntegratingTimeConstant());
            PID.setIntegratingTimeConstant(1.1);
            System.out.println(PID.getIntegratingTimeConstant());

            System.out.println("invalid tau I 1.1->-1 test");
            System.out.println(PID.getIntegratingTimeConstant());
            PID.setIntegratingTimeConstant(-1);
            System.out.println(PID.getIntegratingTimeConstant());

            System.out.println("tau D 1->1.1 test");
            System.out.println(PID.getDerivativeTimeConstant());
            PID.setDerivativeTimeConstant(1.1);
            System.out.println(PID.getDerivativeTimeConstant());

            System.out.println("invalid controller gain 1.1->-1 test");
            System.out.println(PID.getDerivativeTimeConstant());
            PID.setDerivativeTimeConstant(-1);
            System.out.println(PID.getDerivativeTimeConstant());

            System.out.println("controller type PID->PI test");
            System.out.println(PID.getControllerType());
            PID.setControllerType(PIDController.CONTROLLER_TYPE.PI);
            System.out.println(PID.getControllerType());

            System.out.println("invalid controller type PID->null test");
            System.out.println(PID.getControllerType());
            PID.setControllerType(null);
            System.out.println(PID.getControllerType());

            System.out.println("dead time 0->1 test");
            System.out.println(PID.getDeadTime());
            PID.setDeadTime(1);
            System.out.println(PID.getDeadTime());

            System.out.println("invalid dead time 1->-1 test");
            System.out.println(PID.getDeadTime());
            PID.setDeadTime(-1);
            System.out.println(PID.getDeadTime());

            System.out.println("tolerance 0.1->0.05 test");
            System.out.println(PID.getTolerance());
            PID.setTolerance(0.05);
            System.out.println(PID.getTolerance());

            System.out.println("invalid tolerance 0.05->-1 test");
            System.out.println(PID.getTolerance());
            PID.setTolerance(-1);
            System.out.println(PID.getTolerance());

            System.out.println("set point 0.04->0.08 test");
            System.out.println(PID.getSetPoint());
            PID.setSetPoint(0.08);
            System.out.println(PID.getSetPoint());

            PIDController PID_2 = new PIDController(startTime, endTime, timeStep, controllerGain, integratingTimeConstant, derivativeTimeConstant, controllerType, deadTime, controllable, tolerance, disturbances, setPoint);

            System.out.println("Test of true equals method and clone method");

            PIDController PID_3 = PID_2.clone();

            System.out.println(PID_2.equals(PID_3));

            System.out.println("Test of false equals method");
            PIDController PID_4 = new PIDController(PID_2);
            PID_4.setSetPoint(100);
            System.out.println(PID_2.equals(PID_4));

            System.out.println("Test of invalid copy of PID Controller");
            try {
                PIDController PID_5 = new PIDController(null);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Test of invalid constructor, start time");
            try {
                PIDController PID_5 = new PIDController(11, endTime, timeStep, controllerGain, integratingTimeConstant, derivativeTimeConstant, controllerType, deadTime, controllable, tolerance, disturbances, setPoint);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid constructor, end time");
            try {
                PIDController PID_5 = new PIDController(startTime, 0, timeStep, controllerGain, integratingTimeConstant, derivativeTimeConstant, controllerType, deadTime, controllable, tolerance, disturbances, setPoint);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid constructor, time step");
            try {
                PIDController PID_5 = new PIDController(startTime, endTime, 100, controllerGain, integratingTimeConstant, derivativeTimeConstant, controllerType, deadTime, controllable, tolerance, disturbances, setPoint);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid constructor, KC");
            try {
                PIDController PID_5 = new PIDController(startTime, endTime, timeStep, -1, integratingTimeConstant, derivativeTimeConstant, controllerType, deadTime, controllable, tolerance, disturbances, setPoint);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid constructor, Tau I");
            try {
                PIDController PID_5 = new PIDController(startTime, endTime, timeStep, controllerGain, -1, derivativeTimeConstant, controllerType, deadTime, controllable, tolerance, disturbances, setPoint);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid constructor, Tau D");
            try {
                PIDController PID_5 = new PIDController(startTime, endTime, timeStep, controllerGain, integratingTimeConstant, -1, controllerType, deadTime, controllable, tolerance, disturbances, setPoint);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid constructor, controller type");
            try {
                PIDController PID_5 = new PIDController(startTime, endTime, timeStep, controllerGain, integratingTimeConstant, derivativeTimeConstant, null, deadTime, controllable, tolerance, disturbances, setPoint);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid constructor, dead time");
            try {
                PIDController PID_5 = new PIDController(startTime, endTime, timeStep, controllerGain, integratingTimeConstant, derivativeTimeConstant, controllerType, -1, controllable, tolerance, disturbances, setPoint);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid constructor, tolerance");
            try {
                PIDController PID_5 = new PIDController(startTime, endTime, timeStep, controllerGain, integratingTimeConstant, derivativeTimeConstant, controllerType, deadTime, controllable, -1, disturbances, setPoint);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            //other methods tested in test cases to get valid controller outputs

    }
        catch (CloneNotSupportedException e) {
        System.out.println(e.getMessage());
    }

    } //end of PID controller checks



    }

