public abstract class PIDController {

    //TODO still needs a interface to implement, not exactly sure how to do this since it will want all functions in the child class too then
    //TODO still needs to be changed to throw exceptions and add javadoc to the class
    private double startTime; //normally 0
    private double endTime; //greater than startTime
    private double timeStep; //needs to be big enough to make at least one time step between start and end time
    private double controllerGain; //K_C

    private double integratingTimeConstant; //𝛕_I

    private double derivativeTimeConstant;//𝛕_D
    private double numberOfSteps; //calculated from above not given directly

    //Constructor
    public PIDController(double startTime, double endTime, double timeStep, double controllerGain, double integratingTimeConstant, double derivativeTimeConstant) {

        if (endTime<startTime) {
            System.out.println("Error end time must be larger than start time");
            System.exit(0);
        }
        if (timeStep>(endTime-startTime)) {
            System.out.println("Error time step is too large for time range");
            System.exit(0);
        }

        this.startTime = startTime;
        this.endTime = endTime;
        this.timeStep = timeStep;
        this.controllerGain = controllerGain;
        this.integratingTimeConstant = integratingTimeConstant;
        this.derivativeTimeConstant = derivativeTimeConstant;
        this.numberOfSteps = (int)Math.ceil((this.endTime-this.startTime)/this.timeStep)+1;
    }

    //Copy constructor
    public PIDController(PIDController source) {
        if (source==null) {
            System.out.println("Error copy of null PIDController object");
            System.exit(0);
        }
        this.startTime=source.startTime;
        this.endTime=source.endTime;
        this.timeStep=source.timeStep;
        this.controllerGain=source.controllerGain;
        this.integratingTimeConstant=source.integratingTimeConstant;
        this.derivativeTimeConstant=source.derivativeTimeConstant;
        this.numberOfSteps=source.numberOfSteps;
    }

    //Clone method
    public abstract PIDController clone();

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public double getNumberOfSteps() {
        return numberOfSteps;
    }

    public boolean setStartTime(double startTime) {
        if (startTime < this.endTime) return false;
        this.startTime= startTime;
        this.numberOfSteps = (int)Math.ceil((this.endTime-this.startTime)/this.timeStep)+1;
        return true;
    }

    public boolean setEndTime(double endTime) {
        if (this.startTime < endTime) return false;
        this.endTime= endTime;
        this.numberOfSteps = (int)Math.ceil((this.endTime-this.startTime)/this.timeStep)+1;
        return true;
    }

    public boolean setTimeStep (double timeStep) {
        if (((this.endTime-this.startTime)/timeStep)<1) return false;
        this.timeStep = timeStep;
        this.numberOfSteps = (int)Math.ceil((this.endTime-this.startTime)/this.timeStep)+1;
        return true;
    }

    public double getControllerGain() {
        return this.controllerGain;
    }

    public boolean setControllerGain(double controllerGain) {
        this.controllerGain = controllerGain;
        return true;
    }

    public double getIntegratingTimeConstant() {
        return this.integratingTimeConstant;
    }

    public boolean setIntegratingTimeConstant(double integratingTimeConstant) {
        this.integratingTimeConstant = integratingTimeConstant;
        return true;
    }

    public double getDerivativeTimeConstant() {
        return this.derivativeTimeConstant;
    }

    public boolean setDerivativeTimeConstant(double derivativeTimeConstant) {
        this.derivativeTimeConstant = derivativeTimeConstant;
        return true;
    }

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

    //used for all i steps
    public double simulateProportionalStep(double error) {
        return this.controllerGain*error;
    }

    //used for i>0 steps
    public double simulateIntegralStep(double error, double pastIntegralPart) {
        return (pastIntegralPart+((this.controllerGain/this.integratingTimeConstant)*error*this.timeStep));
    }
    //used for i=0 steps
    public double simulateIntegralStep(double error) {
        return ((this.controllerGain/this.integratingTimeConstant)*error*this.timeStep);
    }
    //use for i>0
    public double simulateDerivativeStep(double currentValue, double pastValue) {
        return (-this.controllerGain*this.derivativeTimeConstant*((currentValue-pastValue)/this.timeStep));
    }
    //use for i=0
    public double simulateDerivativeStep(double currentValue) {
        return (-this.controllerGain*this.derivativeTimeConstant*((currentValue)/this.timeStep));
    }


} //end of class
