/** Class for PID control loops for transient processes
 * @author Dylan
 * @author Ogechi
 * @version 2.0
 */
public class PIDController {

    public enum CONTROLLER_TYPE {P, PI, PD, I, ID, D, PID}; //type of controller to use

    private double startTime; //normally 0
    private double endTime; //greater than startTime
    private double timeStep; //needs to be big enough to make at least one time step between start and end time
    private double controllerGain; //K_C
    private double integratingTimeConstant; //𝛕_I
    private double derivativeTimeConstant;//𝛕_D
    private double numberOfSteps; //calculated from above not given directly
    private CONTROLLER_TYPE controllerType; //controller type in use
    private double tolerance; //tolerance for error between RK4 and RK5 method
    private double g_P; //proportional part of the controller
    private double g_I; //integrating part of the controller
    private double g_pastI; //past integrating part of the controller
    private double g_D; //derivative part of the controller
    private double g_pastD; //past derivative part of the controller
    private double g_processVariable;
    private double g_setPoint;
    private double g_output;
    private double deadTime; //dead time

    /*Array holding Simulation results each row contains the simulation values at the given time
     * simulation[0] : Time
     * simulation[1 to n-5]: controllable output values
     * simulation[n-4] : manipulated variable
     * simulation [n-3] : P
     * simulation [n-2] : I
     * simulation [n-1] : D
     *
      */
    private double[][] g_simulation;
    private double g_previousTime;

    private Controllable controllable;

    /** Constructor for the abstract PID controller class
     *
     * @param startTime start time for simulation, normally 0
     * @param endTime end time for simulation (must be after start time)
     * @param timeStep time step for simulation (must be small enough for at least 1 step)
     * @param controllerGain controller gain
     * @param integratingTimeConstant controller integrating time constant
     * @param derivativeTimeConstant controller derivative time constant
     * @param controllerType controller type which must be a type of PID or uncontrolled
     * @param deadTime dead time for PID controller
     * @param tolerance tolerance for error between RK4 and RK5 method
     * @throws IllegalArgumentException if end time is before start time or time step is too large for the range given (<1 step) or if controller type is null or if dead time is negative or if tolerance not greater than 0
     * @author Dylan
     */
    public PIDController(double startTime, double endTime, double timeStep, double controllerGain, double integratingTimeConstant, double derivativeTimeConstant, CONTROLLER_TYPE controllerType, double deadTime, Controllable controllable, double tolerance) {

        if (controllerType==null) throw new IllegalArgumentException("Controller type cannot be null");
        if (endTime<startTime) throw new IllegalArgumentException("Error end time must be larger than start time");
        if (timeStep>(endTime-startTime)) throw new IllegalArgumentException("Error time step is too large for time range");
        if (deadTime<0) throw new IllegalArgumentException("Dead time must be greater than or equal to 0");
        if (tolerance<0 || tolerance==0) throw new IllegalArgumentException("Tolerance must be greater than 0");

        this.startTime = startTime;
        this.endTime = endTime;
        this.timeStep = timeStep;
        this.controllerGain = controllerGain;
        this.integratingTimeConstant = integratingTimeConstant;
        this.derivativeTimeConstant = derivativeTimeConstant;
        this.numberOfSteps = (int)Math.ceil((this.endTime-this.startTime)/this.timeStep)+1;
        this.controllerType = controllerType;
        this.deadTime = deadTime;
        this.controllable = controllable;
        this.tolerance=tolerance;
        resetGlobalVariables();
    }

    /** Copy constructor for the abstract PID controller class
     *
     * @param source an object of the abstract PID controller class
     * @throws IllegalArgumentException if o object to copy is null
     * @author Dylan
     */
    public PIDController(PIDController source) {
        if (source==null) throw new IllegalArgumentException("Error, copy of null PIDController object");

        this.startTime=source.startTime;
        this.endTime=source.endTime;
        this.timeStep=source.timeStep;
        this.controllerGain=source.controllerGain;
        this.integratingTimeConstant=source.integratingTimeConstant;
        this.derivativeTimeConstant=source.derivativeTimeConstant;
        this.numberOfSteps=source.numberOfSteps;
        this.controllerType=source.controllerType;
        this.deadTime=source.deadTime;
        this.tolerance=source.tolerance;
        this.g_P=source.g_P;
        this.g_I=source.g_I;
        this.g_pastI=source.g_pastI;
        this.g_D=source.g_D;
        this.g_pastD=source.g_pastD;
        this.g_processVariable=source.g_processVariable;
        this.g_setPoint=source.g_setPoint;
        this.g_output=source.g_output;
        //TODO add simulation and previous time
    }

    /** Clone method to call the copy constructor
     *
     * @return a copy of the cloned object
     * @throws IllegalArgumentException if o object to copy is null
     * @author Dylan
     */
    public PIDController clone() {
        try{
            return new PIDController(this);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Failed to clone CSTRControllerPID: "+ e.getMessage());
        }
    }

    /** Accessor method for start time
     *
     * @return start time
     * @author Dylan
     */
    public double getStartTime() {
        return startTime;
    }

    /** Accessor method for end time
     *
     * @return end time
     * @author Dylan
     */
    public double getEndTime() {
        return endTime;
    }

    /** Accessor method for time step
     *
     * @return time step
     * @author Dylan
     */
    public double getTimeStep() {
        return timeStep;
    }

    /** Accessor method for the number of steps
     *
     * @return the number of steps
     * @author Dylan
     */
    public double getNumberOfSteps() {
        return numberOfSteps;
    }

    /** Mutator method for start time, updates number of steps.
     *
     * @param startTime start time which must be before end time
     * @return true if updated and false if not
     * @author Dylan
     */

    public boolean setStartTime(double startTime) {
        if (startTime < this.endTime) return false;
        this.startTime= startTime;
        this.numberOfSteps = (int)Math.ceil((this.endTime-this.startTime)/this.timeStep)+1;
        return true;
    }

    /** Mutator method for end time, updates number of steps
     *
     * @param endTime end time which must be after start time
     * @return true if updated and false if not
     * @author Dylan
     */
    public boolean setEndTime(double endTime) {
        if (this.startTime < endTime) return false;
        this.endTime= endTime;
        this.numberOfSteps = (int)Math.ceil((this.endTime-this.startTime)/this.timeStep)+1;
        return true;
    }

    /** Mutator method for time step, updates number of steps
     *
     * @param timeStep time step which must be small enough for at least one step
     * @return true if updated and false if not
     * @author Dylan
     */
    public boolean setTimeStep (double timeStep) {
        if (((this.endTime-this.startTime)/timeStep)<1) return false;
        this.timeStep = timeStep;
        this.numberOfSteps = (int)Math.ceil((this.endTime-this.startTime)/this.timeStep)+1;
        return true;
    }

    /** Accessor method for controller gain KC
     *
     * @return controller gain KC
     * @author Dylan
     */
    public double getControllerGain() {
        return this.controllerGain;
    }

    /** Mutator method for controller gain KC
     *
     * @param controllerGain controller gain KC
     * @return true if updated and false if not
     * @author Dylan
     */
    public boolean setControllerGain(double controllerGain) {
        this.controllerGain = controllerGain;
        return true;
    }

    /** Accessor method for integrating time constant tau I
     *
     * @return integrating time constant tau I
     * @author Dylan
     */
    public double getIntegratingTimeConstant() {
        return this.integratingTimeConstant;
    }

    /** Mutator method for integrating time constant tau I
     *
     * @param integratingTimeConstant takes integrating time constant tau I
     * @return true if updated and false if not
     * @author Dylan
     */
    public boolean setIntegratingTimeConstant(double integratingTimeConstant) {
        this.integratingTimeConstant = integratingTimeConstant;
        return true;
    }

    /** Accessor method for derivative time constant tau D
     *
     * @return derivative time constant tau D
     * @author Dylan
     */
    public double getDerivativeTimeConstant() {
        return this.derivativeTimeConstant;
    }

    /** Mutator method for derivative time constant tau D
     *
     * @param derivativeTimeConstant derivative time constant tau D
     * @return true if updated and false if not
     * @author Dylan
     */
    public boolean setDerivativeTimeConstant(double derivativeTimeConstant) {
        this.derivativeTimeConstant = derivativeTimeConstant;
        return true;
    }
    /** Accessor method for controller type
     *
     * @return controller type
     * @author Dylan
     */
    public CONTROLLER_TYPE getControllerType() {
        return controllerType;
    }

    /** Mutator method for controller type
     *
     * @param controllerType controller type which must be a type of PID or uncontrolled
     * @return true if updated and false if not
     * @author Dylan
     */
    public boolean setControllerType(CONTROLLER_TYPE controllerType) {
        if (controllerType==null) return false;
        this.controllerType = controllerType;
        return true;
    }

    /** Accessor method for dead time, theta
     *
     * @return dead time, theta
     * @author Dylan
     */
    public double getDeadTime() {
        return this.deadTime;
    }

    /** Mutator method for dead time, theta
     *
     * @param deadTime dead time, must be greater than or equal to 0
     * @return true if updated and false if not
     * @author Dylan
     */
    public boolean setDeadTime (double deadTime) {
        if (deadTime<0) return false;
        this.deadTime=deadTime;
        return true;
    }

    /** Reset method for global variables
     * @author Dylan
     */
    protected void resetGlobalVariables() {
        this.g_P=0;
        this.g_I=0;
        this.g_pastI=0;
        this.g_D=0;
        this.g_pastD=0;
        this.g_processVariable=0;
        this.g_setPoint=0;
        this.g_output=0;
        this.g_previousTime=0;
        //TODO add simulation
    }

    /** Equals method
     *
     * @param comparator object to compare to current object
     * @return true if all instant variables and class type are equal, otherwise returns false
     * @author Dylan
     */
    public boolean equals (Object comparator) {
        if (comparator==null) return false;
        if (comparator.getClass()!=this.getClass()) return false;
        PIDController specificComparator = (PIDController)comparator;

        if (specificComparator.startTime!=this.startTime) return false;
        if (specificComparator.endTime!=this.endTime) return false;
        if (specificComparator.timeStep!=this.timeStep) return false;
        if (specificComparator.controllerGain!=this.controllerGain) return false;
        if (specificComparator.integratingTimeConstant!=this.integratingTimeConstant) return false;
        if (specificComparator.derivativeTimeConstant!=this.derivativeTimeConstant) return false;
        if (specificComparator.deadTime!=this.deadTime) return false;
        if (specificComparator.tolerance!=this.tolerance) return false;
        if (specificComparator.g_P!=this.g_P) return false;
        if (specificComparator.g_I!=this.g_I) return false;
        if (specificComparator.g_pastI!=this.g_pastI) return false;
        if (specificComparator.g_D!=this.g_D) return false;
        if (specificComparator.g_pastD!=this.g_pastD) return false;
        if (specificComparator.g_processVariable!=this.g_processVariable) return false;
        if (specificComparator.g_setPoint!=this.g_setPoint) return false;
        if (specificComparator.g_output!=this.g_output) return false;
        if (specificComparator.g_previousTime!=this.g_previousTime) return false;
        //TODO add simulation

        return true;
    }

    /** Simulation for proportional part of PID controller function for all steps
     *
     * @param error error between real value and set point
     * @author Dylan
     * @author Ogechi
     */
    public void simulateProportionalStep(double error) {
        if(controllerType == CONTROLLER_TYPE.P || controllerType == CONTROLLER_TYPE.PD || controllerType == CONTROLLER_TYPE.PI || controllerType == CONTROLLER_TYPE.PID) {
            g_P =this.controllerGain*error;
        } else {
            g_P = 0;
        }
    }

    /** Simulation for integral part of PID controller function for steps i>0 steps
     *
     * @param error error between real value and set point
     * @author Dylan
     * @author Ogechi
     */
    public void simulateIntegralStep(double error) {
        if(controllerType == CONTROLLER_TYPE.I || controllerType == CONTROLLER_TYPE.PI || controllerType == CONTROLLER_TYPE.ID || controllerType == CONTROLLER_TYPE.PID){
            g_I = g_pastI+((this.controllerGain/this.integratingTimeConstant)*error*this.timeStep);
        } else {
            g_I = 0;
        }
    }

    /** Simulation for derivative part of PID controller function for steps i>0 steps
     *
     * @param currentValue current value of the controller variable
     * @author Dylan
     * @author Ogechi
     */
    public void simulateDerivativeStep(double currentValue) {
        if(controllerType == CONTROLLER_TYPE.D || controllerType == CONTROLLER_TYPE.PD || controllerType == CONTROLLER_TYPE.ID || controllerType == CONTROLLER_TYPE.PID){
            g_D = -this.controllerGain*this.derivativeTimeConstant*((currentValue-g_pastD)/this.timeStep);
            g_pastD = currentValue;
        } else {
            g_D = 0;
        }
    }

    /** Calculates the controller output for a given object
     *
     * @param object Object that implements Controllable
     * @return value of the controlled variable
     * @author Ogechi
     * @author Dylan
     */
    public double compute(Controllable object){
        double error = this.g_setPoint - this.g_processVariable;
        simulateProportionalStep(error);
        simulateIntegralStep(error);
        simulateDerivativeStep(error);
        this.g_pastI = this.g_I;
        this.g_pastD = this.g_processVariable;
        return this.g_P + this.g_I + this.g_D > 0 ? this.g_P + this.g_I + this.g_D: 0;
    }

    /** Method to simulate PID controller
     * @author Ogechi
     * @author Dylan
     */
    public void simulate(){

        if(this.controllable.isControlled()){
            //number of variables to store
            int n = this.controllable.getInitialValues().length + 5;
            Queue<double[]> queue = new Queue<>();

            this.g_simulation = new double[(int)this.numberOfSteps][n];
            double error;
            double[] temp;

            //initialize the first row with initial values
            this.g_simulation[0][0] = this.startTime;
            for(int i = 0; i < this.controllable.getInitialValues().length; i++){
                this.g_simulation[0][i+1] = this.controllable.getInitialValues()[i];
            }
            this.g_simulation[0][n-4] = this.g_processVariable;
            this.g_simulation[0][n-3] = this.g_P;
            this.g_simulation[0][n-2] = this.g_I;
            this.g_simulation[0][n-1] = this.g_D;

            this.g_processVariable = this.controllable.getControlledVar();
            this.g_output = this.compute(this.controllable);
            queue.enqueue(new double[]{this.g_previousTime + this.deadTime,this.g_output});

            int step = 1;

            while (step < numberOfSteps){
                error = this.g_processVariable - this.g_setPoint;
                this.g_simulation[step][0] = this.g_previousTime + this.timeStep;
                temp = this.controllable.getSystemOutput(this.g_previousTime, this.g_previousTime + this.timeStep, this.tolerance);
                for (int i = 0; i < n-5; i++) {
                    this.g_simulation[step][i+1] = temp[i];
                }

                //check if controlled action takes place, if not then equal to prev
                if(queue.peek()[0]>= this.g_previousTime) {
                    this.g_simulation[step][n - 4] = queue.peek()[1];
                    this.controllable.setManipulatedVariable(queue.peek()[1]);
                    queue.dequeue();
                }else{
                    this.g_simulation[step][n-4] =  this.g_simulation[n-4][step-1];
                }

                this.simulateProportionalStep(error);
                this.simulateIntegralStep(error);
                this.simulateDerivativeStep(this.g_processVariable);

                //store PID values in simulation
                this.g_simulation[step][n-3] = this.g_P;
                this.g_simulation[step][n-2] = this.g_I;
                this.g_simulation[step][n-1] = this.g_D;

                this.g_processVariable = controllable.getControlledVar();
                this.g_output = this.compute(controllable);

                //enqueue the controller action
                queue.enqueue(new double[]{this.g_previousTime + this.deadTime, this.g_output});
                this.g_previousTime += timeStep;
                step++;
            }
        } else {
            int n = this.controllable.getInitialValues().length+ 1;
            this.g_simulation = new double[n][(int)this.numberOfSteps];

            //store initial values in simulation
            this.g_simulation[0][0] = this.g_previousTime;
            for(int i = 0; i < controllable.getInitialValues().length; i++){
                this.g_simulation[0][i+1] = controllable.getInitialValues()[i];
            }
            int step = 1;
            double[] temp;

            while(step <numberOfSteps){

                this.g_simulation[step][0] = this.g_previousTime;
                temp = this.controllable.getSystemOutput(this.g_previousTime, this.g_previousTime + this.timeStep, this.tolerance);
                for (int i = 0; i < n-5; i++) {
                    this.g_simulation[step][i+1] = temp[i];
                }
                this.g_previousTime += this.timeStep;

                step++;
            }
        }
    }


} //end of class
