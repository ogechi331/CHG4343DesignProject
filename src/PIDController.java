public abstract class PIDController {

    private double startTime;
    private double endTime;
    private double timeStep;
    private double controllerGain; //K_C

    private double integratingTimeConstant; //𝛕_I

    private double derivativeTimeConstant;//𝛕_D
    private double numberOfSteps;

    //Constructor
    public PIDController(double startTime, double endTime, double timeStep, double controllerGain, double integratingTimeConstant, double derivativeTimeConstant) {

        if (endTime<startTime) System.exit(0);
        if (timeStep> (startTime-endTime)) System.exit(0);

        this.startTime = startTime;
        this.endTime = endTime;
        this.timeStep = timeStep;
        this.controllerGain = controllerGain;
        this.integratingTimeConstant = integratingTimeConstant;
        this.derivativeTimeConstant = derivativeTimeConstant;
        this.numberOfSteps = (int)Math.ceil((this.endTime-this.startTime)/this.timeStep);
    }

    //Copy constructor
    public PIDController(PIDController source) {
        if (source==null) System.exit(0);
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

    /*public double[] getTimeInformation() {

        double[] timeInformation = new double[4];
        timeInformation[0] = this.startTime;
        timeInformation[1] = this.endTime;
        timeInformation[2] = this.timeStep;
        timeInformation[3] = this.numberOfSteps;

        return timeInformation;
    } */

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

    public boolean setTimeInformation(double[] timeInformation) {
        if (timeInformation==null) return false;
        if (timeInformation.length!=3) return false;

        this.startTime = timeInformation[0];
        this.endTime = timeInformation[1];
        this.timeStep = timeInformation[2];
        this.numberOfSteps = (int)Math.ceil((timeInformation[0]-timeInformation[1])/timeInformation[2]);


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

    //TODO Note to self for Dylan, plan to split this into P, I, and D parts
    public double simulateTimeStep(double time, double setPoint, double pastError, double currentValue, double pastIntegralPart, double pastValue, double pastControlState) {

        double nextControlState;
        double proportionalPart, integralPart, derivativePart;
        double error;

        error = setPoint-currentValue;

        proportionalPart = this.controllerGain*(error);
        integralPart = pastIntegralPart + ((this.controllerGain/this.integratingTimeConstant)*error*this.timeStep);
        derivativePart = -this.controllerGain*this.derivativeTimeConstant*((currentValue-pastValue)/(this.timeStep));

        nextControlState = pastControlState + proportionalPart + integralPart + derivativePart;
        return nextControlState;
    }

} //end of class
