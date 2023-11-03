public class CSTRControllerPID extends PIDController {

    //TODO still needs to be changed to throw exceptions and add javadoc to the class

    //Constructor
    public CSTRControllerPID(double startTime, double endTime, double timeStep, double controllerGain, double integratingTimeConstant, double derivativeTimeConstant) {
        super(startTime, endTime, timeStep, controllerGain, integratingTimeConstant, derivativeTimeConstant);
    }

    //Copy constructor
    public CSTRControllerPID (CSTRControllerPID source) {
        super(source);
    }

    public CSTRControllerPID clone() {
        return new CSTRControllerPID(this);
    }

    //PID loop simulator
    public double[][] stimulateProcess (double initialConcentrationA, double initialConcentrationB, double setPoint, double initialFlowRate, CSTRReactor CSTR) { //for test case 2 initial is 0.2 and setPoint is 1.2

        double[][] simulation = new double[11][(int)super.getNumberOfSteps()];
        //[0] is time
        //[1] is CA
        //[2] is CB
        //[3] is Error
        //[4] is P part
        //[5] is I part
        //[6] is D part
        //[7] is controller
        //[8] is flow rate
        //[9] is new CA
        //[10] is new CB

        //loop for process simulation
        for (int i = 0; i<(int)super.getNumberOfSteps(); i++) {

            double[] currentConcentrations = new double[2]; //temp variable to pass information

            if (i==0) {
                simulation[0][0] = super.getStartTime();
                simulation[1][0] = initialConcentrationA;
                simulation[2][0] = initialConcentrationB;
                simulation[3][0] = setPoint-simulation[2][0];
                simulation[4][0] = super.simulateProportionalStep(simulation[3][0]);
                simulation[5][0] = super.simulateIntegralStep(simulation[3][0]);
                simulation[6][0] = super.simulateDerivativeStep(simulation[2][0]);
                simulation[7][0] = simulation[4][0] + simulation[5][0] + simulation[6][0];
                simulation[8][0] = initialFlowRate-simulation[7][0];

                currentConcentrations[0] = simulation[1][0];
                currentConcentrations[1] = simulation[2][0];
                //time step, current concentration, flow rate, reactant to simulate
                simulation[9][0] = CSTR.simulateStep(super.getTimeStep(),simulation[8][0], currentConcentrations, 0);
                //simulation[9][0] = simulation[1][0]+(super.getTimeStep()/1)*(simulation[8][0]*inletConcentration-simulation[8][0]*simulation[1][0]-0.2*1*simulation[1][0]);
                //simulation[10][0] = simulation[2][0]+(super.getTimeStep()/1)*(0.2*1*simulation[1][0]-simulation[8][0]*simulation[2][0]);
                simulation[10][0] = CSTR.simulateStep(super.getTimeStep(), simulation[8][0], currentConcentrations, 1);
            } //end of i=0 loop
            if (i>0) {
                simulation[0][i] = simulation[0][i-1]+super.getTimeStep();
                simulation[1][i] = simulation[9][i-1];
                simulation[2][i] = simulation[10][i-1];
                simulation[3][i] = setPoint-simulation[2][i];
                simulation[4][i] = super.simulateProportionalStep(simulation[3][i]);
                simulation[5][i] = super.simulateIntegralStep(simulation[3][i], simulation[5][i-1]);
                simulation[6][i] = super.simulateDerivativeStep(simulation[2][i], simulation[2][i-1]);
                simulation[7][i] = simulation[4][i] + simulation[5][i] + simulation[6][i];
                simulation[8][i] = initialFlowRate-simulation[7][i];

                currentConcentrations[0] = simulation[1][i];
                currentConcentrations[1] = simulation[2][i];
                //time step, current concentration, flow rate, reactant to simulate
                //simulation[9][i] = simulation[1][i]+(super.getTimeStep()/1)*(simulation[8][i]*inletConcentration-simulation[8][i]*simulation[1][i]-0.2*1*simulation[1][i]);
                simulation[9][i] = CSTR.simulateStep(super.getTimeStep(),simulation[8][i], currentConcentrations, 0);
                //simulation[10][i] = simulation[2][i]+(super.getTimeStep()/1)*(0.2*1*simulation[1][i]-simulation[8][i]*simulation[2][i]);
                simulation[10][i] = CSTR.simulateStep(super.getTimeStep(),simulation[8][i],currentConcentrations, 1);
            } //end of i>0 loop
        }
        return simulation;
    }

} // end of class
