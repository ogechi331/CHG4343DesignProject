public class CSTRControllerPID extends PIDController {



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

    //TODO Note to self for Dylan this section is not finished yet
    public void stimulateProcess (double initialConcentration, double setPoint) { //for test case 2 initial is 0.2 and setPoint is 1.2



        double[][] simulation = new double[5][(int)super.getNumberOfSteps()];
        //[0] is times
        //[1] is set point concentration at each time
        //[2] is exit concentration at each time
        //[3] is error in concentration at each time
        //[5] is for the resulting control state at each time
        //[5] is new concentration for i+1 time

        //fill in times for process simulation
        for (int i = 0; i<(int)super.getNumberOfSteps(); i++) {

            double pastIntegralPart = 0;

            if (i==0) {
                simulation[0][0] = super.getStartTime();
                simulation[1][0] = setPoint;
                simulation[2][0] = initialConcentration;
                simulation[3][0] = setPoint-initialConcentration;
            }
            if (i>0) {
                simulation[0][i] = simulation[0][i-1] + super.getTimeStep();
                simulation[1][i] = setPoint;
                simulation[2][i] = simulation[5][i-1];
                simulation[3][i] = setPoint - simulation[5][i-1];
                simulation[4][i] = super.simulateTimeStep(simulation[0][i], simulation[1][i], simulation[3][i-1], simulation[5][i-1], pastIntegralPart, simulation[2][i-1],simulation[4][i-1]);
                double flowRate = simulation[4][i];
                if (flowRate < 0) flowRate = 0;
                simulation[5][i] = (flowRate*simulation[2][i-1])/(flowRate+(0.2*1)); //hard coded currently k and V for reactor
            }
        }



    }


}
