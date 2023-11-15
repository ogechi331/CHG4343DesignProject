/**
 * Class to simulate Controller actions
 *
 * @author Ogechi
 */
public class ControllerSimulator {
    protected double startTime; //normally 0
    protected double prevTime;
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

    public ControllerSimulator(double startTime, double endTime, double timeStep, int numberOfSteps, Controllable controllable, double deadTime) {
        this.startTime = startTime;
        this.prevTime = startTime;
        this.endTime = endTime;
        this.timeStep = timeStep;
        this.numberOfSteps = numberOfSteps;
        this.controllable = controllable;
        if(controllable.isControlled()) this.controller = controllable.getController();
        this.deadTime = deadTime;
    }

    public void simulate(){

        if(controllable.isControlled()){
            //number of variables to store
            int n = controllable.getInitialValues().length + 5;
            Queue<double[]> queue = new Queue<>();

            simulation = new double[numberOfSteps][n];
            double error;
            double[] temp;

            //initialize the first row with initial values
            simulation[0][0] = startTime;
            for(int i = 0; i < controllable.getInitialValues().length; i++){
                simulation[0][i+1] = controllable.getInitialValues()[i];
            }
            simulation[0][n-4] = controller.processVariable;
            simulation[0][n-3] = controller.P;
            simulation[0][n-2] = controller.I;
            simulation[0][n-1] = controller.D;

            controller.processVariable = controllable.getControlledVar();
            controller.output = controller.compute(controllable);
            queue.enqueue(new double[]{prevTime + deadTime,controller.output});
            int step = 1;
            while (step < numberOfSteps){
                error = controller.processVariable - controller.setPoint;
                simulation[step][0] = prevTime + timeStep;
                temp = controllable.getSystemOutput(prevTime, prevTime +timeStep);
                for (int i = 0; i < n-5; i++) {
                    simulation[step][i+1] = temp[i];
                }

                //check if controlled action takes place, if not then equal to prev
                   if(queue.peek()[0]>= prevTime) {
                       simulation[step][n - 4] = queue.peek()[1];
                       controllable.setManipulatedVariable(queue.peek()[1]);
                       queue.dequeue();
                   }else{
                       simulation[step][n-4] =  simulation[n-4][step-1];
                   }

                   controller.simulateProportionalStep(error);
                   controller.simulateIntegralStep(error);
                   controller.simulateDerivativeStep(controller.processVariable);

                   //store PID values in simulation
                   simulation[step][n-3] = controller.P;
                   simulation[step][n-2] = controller.I;
                   simulation[step][n-1] = controller.D;

                   controller.processVariable = controllable.getControlledVar();
                   controller.output = controller.compute(controllable);

                   //enqueue the controller action
                   queue.enqueue(new double[]{prevTime + deadTime,controller.output});
                   prevTime += timeStep;
                   step++;
            }
        }else{
            int n = controllable.getInitialValues().length+ 1;
            simulation = new double[n][numberOfSteps];

            //store initial values in simulation
            simulation[0][0] = prevTime;
            for(int i = 0; i < controllable.getInitialValues().length; i++){
                simulation[0][i+1] = controllable.getInitialValues()[i];
            }
            int step = 1;
            double[] temp;

            while(step <numberOfSteps){

                simulation[step][0] = prevTime;
                temp = controllable.getSystemOutput(prevTime, prevTime +timeStep);
                for (int i = 0; i < n-5; i++) {
                    simulation[step][i+1] = temp[i];
                }
                prevTime += timeStep;

                step++;
            }
        }
    }

}
