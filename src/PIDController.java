/** Abstract parent class for PID control loops for transient processes
 * @author Dylan
 * @version 1.3
 */
public class PIDController extends Controller{

    public enum CONTROLLER_TYPE {P, PI, PD, I, ID, D, PID}; //type of controller to use

    protected double startTime; //normally 0
    protected double endTime; //greater than startTime
    protected double timeStep; //needs to be big enough to make at least one time step between start and end time
    protected double controllerGain; //K_C
    protected double integratingTimeConstant; //𝛕_I
    protected double derivativeTimeConstant;//𝛕_D
    protected double numberOfSteps; //calculated from above not given directly

    protected CONTROLLER_TYPE controllerType; //controller type in use

    /** Constructor for the abstract PID controller class
     *
     * @param startTime start time for simulation, normally 0
     * @param endTime end time for simulation (must be after start time)
     * @param timeStep time step for simulation (must be small enough for at least 1 step)
     * @param controllerGain controller gain
     * @param integratingTimeConstant controller integrating time constant
     * @param derivativeTimeConstant controller derivative time constant
     * @param controllerType controller type which must be a type of PID or uncontrolled
     * @throws IllegalArgumentException if end time is before start time or time step is too large for the range given (<1 step) or if controller type is null
     * @author Dylan
     */
    public PIDController(double startTime, double endTime, double timeStep, double controllerGain, double integratingTimeConstant, double derivativeTimeConstant, CONTROLLER_TYPE controllerType) throws IllegalArgumentException {

        if (controllerType==null) throw new IllegalArgumentException("Controller type cannot be null");
        if (endTime<startTime) throw new IllegalArgumentException("Error end time must be larger than start time");
        if (timeStep>(endTime-startTime)) throw new IllegalArgumentException("Error time step is too large for time range");

        this.startTime = startTime;
        this.endTime = endTime;
        this.timeStep = timeStep;
        this.controllerGain = controllerGain;
        this.integratingTimeConstant = integratingTimeConstant;
        this.derivativeTimeConstant = derivativeTimeConstant;
        this.numberOfSteps = (int)Math.ceil((this.endTime-this.startTime)/this.timeStep)+1;
        this.controllerType = controllerType;
    }

    /** Copy constructor for the abstract PID controller class
     *
     * @param source an object of the abstract PID controller class
     * @throws IllegalArgumentException if o object to copy is null
     * @author Dylan
     */
    public PIDController(PIDController source) throws IllegalArgumentException {
        if (source==null) throw new IllegalArgumentException("Error, copy of null PIDController object");

        this.startTime=source.startTime;
        this.endTime=source.endTime;
        this.timeStep=source.timeStep;
        this.controllerGain=source.controllerGain;
        this.integratingTimeConstant=source.integratingTimeConstant;
        this.derivativeTimeConstant=source.derivativeTimeConstant;
        this.numberOfSteps=source.numberOfSteps;
        this.controllerType=source.controllerType;
    }

    /** Clone method to call the copy constructor
     *
     * @return a copy of the cloned object
     * @throws IllegalArgumentException if o object to copy is null
     * @author Dylan
     */
    public PIDController clone() throws IllegalArgumentException{
        throw new UnsupportedOperationException();
    };

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

        return true;
    }

    /** Simulation for proportional part of PID controller function for all steps
     *
     * @param error error between real value and set point
     * @author Dylan
     * @author Ogechi
     */
    public void simulateProportionalStep(double error) {
        if(controllerType == CONTROLLER_TYPE.P || controllerType == CONTROLLER_TYPE.PD || controllerType == CONTROLLER_TYPE.PID) {
            P =this.controllerGain*error;
        } else{ P = 0;}
    }

    /** Simulation for integral part of PID controller function for steps i>0 steps
     *
     * @param error error between real value and set point
     * @author Dylan
     * @author Ogechi
     */
    public void simulateIntegralStep(double error) {
        if(controllerType == CONTROLLER_TYPE.I || controllerType == CONTROLLER_TYPE.PI || controllerType ==CONTROLLER_TYPE.ID || controllerType == CONTROLLER_TYPE.PID){
            I = pastI+((this.controllerGain/this.integratingTimeConstant)*error*this.timeStep);
        }else{ I = 0;}
    }

    /** Simulation for derivative part of PID controller function for steps i>0 steps
     *
     * @param currentValue current value of the controller variable
     * @author Dylan
     * @author Ogechi
     */
    public void simulateDerivativeStep(double currentValue) {
        if(controllerType == CONTROLLER_TYPE.D || controllerType == CONTROLLER_TYPE.PD || controllerType == CONTROLLER_TYPE.ID || controllerType == CONTROLLER_TYPE.PID){
            D = -this.controllerGain*this.derivativeTimeConstant*((currentValue-pastD)/this.timeStep);
            pastD = currentValue;
        }else{D = 0;}
    }


    /** Calculates the controller output for a given object
     *
     * @param object Object that implements Controllable
     * @return value of the controlled variable
     * @author Ogechi
     */
    public double compute(Controllable object){
        double error = setPoint - processVariable;
        simulateProportionalStep(error);
        simulateIntegralStep(error);
        simulateDerivativeStep(error);
        pastI = I;
        pastD = processVariable;
        return P + I + D > 0 ? P + I + D: 0;
    }

} //end of class
