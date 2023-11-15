/**
 * Class to simulate Controller actions
 *
 * @author Ogechi
 */
public class SimulateController {
    protected double startTime; //normally 0
    protected double currentTime;
    protected double endTime; //greater than startTime
    protected double timeStep; //needs to be big enough to make at least one time step between start and end time
    protected int numberOfSteps; //calculated from above not given directly'
    protected Controllable controllable;
    protected PIDController controller;
    private double deadTime;
    /**Array holding Simulation results each row contains the simulation values at the given time
     * simulation[0] : Time
     * simulation[1 to n-5]: controllable output values
     * simulation[n-4] : manipulated variable
     * simulation [n-3] : P
     * simulation [n-2] : I
     * simulation [n-1] : D
     *
     * */
    protected double[][] simulation;

    public SimulateController(double startTime, double endTime, double timeStep, int numberOfSteps, Controllable controllable, double deadTime) {
        this.startTime = startTime;
        this.currentTime = startTime;
        this.endTime = endTime;
        this.timeStep = timeStep;
        this.numberOfSteps = numberOfSteps;
        this.controllable = controllable;
        this.controller = controllable.getController();
        this.deadTime = deadTime;
    }

    public void simulate(){
        //number of variables to store
        int n = controllable.getInitialValues().length + 5;

        simulation = new double[n][numberOfSteps];
        double error;
        double[] temp;
        if(controllable.isControlled()){
            //initialize the first row with initial values
            simulation[0][0] = startTime;
           for(int i = 0; i < controllable.getInitialValues().length; i++){
               simulation[i+1][0] = controllable.getInitialValues()[i];
           }
           simulation[n-4][0] = controller.processVariable;
           simulation[n-3][0] = controller.P;
           simulation[n-2][0] = controller.I;
           simulation[n-1][0] = controller.D;

           controller.processVariable = controllable.getControlledVar();
           controller.output = controller.compute(controllable);
           int step = 1;
           while (step < numberOfSteps){
               error = controller.processVariable - controller.setPoint;
               simulation[0][step] = currentTime + timeStep;
               temp = controllable.getSystemOutput(currentTime, currentTime+timeStep);
               for (int i = 0; i < n-5; i++) {
                   simulation[i+1][step] = temp[i];
               }

               //check if controlled action takes place, if not then equal to prev
               simulation[n-4][step] = controller.output;
               controllable.setManipulatedVariable(controller.output);

               controller.simulateProportionalStep(error);
               controller.simulateIntegralStep(error);
               controller.simulateDerivativeStep(controller.processVariable);

               //store PID values in simulation
               simulation[n-3][step] = controller.P;
               simulation[n-2][step] = controller.I;
               simulation[n-1][step] = controller.D;

               controller.processVariable = controllable.getControlledVar();
               controller.output = controller.compute(controllable);

               step++;
           }


        }
    }

}
