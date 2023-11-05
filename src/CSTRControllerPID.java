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

        double[][] simulation = new double[12][(int)super.getNumberOfSteps()];
        double[] RKResults; //temp array to hold double return value
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
        //[11] is the ideal step size calculation from RK45

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

                if (simulation[8][0]<0) simulation[8][0] = 0;

                currentConcentrations[0] = simulation[1][0];
                currentConcentrations[1] = simulation[2][0];

                //simulation[9][0] = eulersMethod(super.getTimeStep(),simulation[8][0], currentConcentrations, 0, CSTR);
                //simulation[10][0] = eulersMethod(super.getTimeStep(),simulation[8][0], currentConcentrations, 1, CSTR);

                RKResults = RK45(super.getTimeStep(), simulation[8][0], currentConcentrations, CSTR);
                simulation[9][0] = RKResults[0];
                if (simulation[9][0]<0) simulation[9][0] = 0;
                simulation[10][0] = RKResults[1];
                if (simulation[10][0]<0) simulation[10][0] = 0;
                simulation[11][0] = RKResults[2];

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

                if (simulation[8][i]<0) simulation[8][i] = 0;

                currentConcentrations[0] = simulation[1][i];
                currentConcentrations[1] = simulation[2][i];

                //simulation[9][i] = eulersMethod(super.getTimeStep(),simulation[8][i], currentConcentrations, 0, CSTR);
                //simulation[10][i] = eulersMethod(super.getTimeStep(),simulation[8][i], currentConcentrations, 1, CSTR);

                RKResults = RK45(super.getTimeStep(), simulation[8][i], currentConcentrations, CSTR);
                simulation[9][i] = RKResults[0];
                if (simulation[9][i]<0) simulation[9][i] = 0;
                simulation[10][i] = RKResults[1];
                if (simulation[10][i]<0) simulation[10][i] = 0;
                simulation[11][i] = RKResults[2];

            } //end of i>0 loop
        }
        return simulation;
    }


    public double eulersMethod (double wholeTimeStep, double flowRate, double[] currentConcentrations, int currentSpeciesNumber, CSTRReactor CSTR) {

        double answer;

       answer = currentConcentrations[currentSpeciesNumber] + wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, currentConcentrations, currentSpeciesNumber));

       return answer;

    }

    //TODO validate RK45 method using an Excel file
    public double[] RK45 (double wholeTimeStep, double flowRate, double[] currentConcentrations, CSTRReactor CSTR) {

        double[] answerRK4 = new double[2];
        double[] answerRK5 = new double[2];
        double[] answer = new double[3];
        double K1CA, K2CA, K3CA, K4CA, K5CA, K6CA;
        double K1CB, K2CB, K3CB, K4CB, K5CB, K6CB;
        double[] concentrationsForK2 = new double[2];
        double[] concentrationsForK3 = new double[2];
        double[] concentrationsForK4 = new double[2];
        double[] concentrationsForK5 = new double[2];
        double[] concentrationsForK6 = new double[2];
        double optimalStepSizeCA;
        double optimalStepSizeCB;

        K1CA = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, currentConcentrations, 0));
        K1CB = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, currentConcentrations, 1));

        concentrationsForK2[0] = currentConcentrations[0] + (K1CA/4);
        concentrationsForK2[1] = currentConcentrations[1] + (K1CB/4);

        K2CA = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, concentrationsForK2, 0));
        K2CB = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, concentrationsForK2, 1));

        concentrationsForK3[0] = currentConcentrations[0] + (3*K1CA/32) + (9*K2CA/32);
        concentrationsForK3[1] = currentConcentrations[1] + (3*K1CB/32) + (9*K2CB/32);

        K3CA = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, concentrationsForK3, 0));
        K3CB = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, concentrationsForK3, 1));

        concentrationsForK4[0] = currentConcentrations[0] + (1932*K1CA/2197) - (7200*K2CA/2197) + (7296*K3CA/2197);
        concentrationsForK4[1] = currentConcentrations[1] + (1932*K1CB/2197) - (7200*K2CB/2197) + (7296*K3CB/2197);

        K4CA = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, concentrationsForK4, 0));
        K4CB = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, concentrationsForK4, 1));

        concentrationsForK5[0] = currentConcentrations[0] + (439*K1CA/216)-(8*K2CA)+(3680*K3CA/513)-(845*K4CA/4104);
        concentrationsForK5[1] = currentConcentrations[1] + (439*K1CB/216)-(8*K2CB)+(3680*K3CB/513)-(845*K4CB/4104);

        K5CA = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, concentrationsForK5, 0));
        K5CB = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, concentrationsForK5, 1));

        concentrationsForK6[0] = currentConcentrations[0] - (8*K1CA/27)+(2*K2CA)-(3544*K3CA/2565)+1859*K4CA/4104-(11*K5CA/40);
        concentrationsForK6[1] = currentConcentrations[1] - (8*K1CB/27)+(2*K2CB)-(3544*K3CB/2565)+1859*K4CB/4104-(11*K5CB/40);

        K6CA = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, concentrationsForK6, 0));
        K6CB = wholeTimeStep*(CSTR.simulateStep(wholeTimeStep, flowRate, concentrationsForK6, 1));

        answerRK4[0] = currentConcentrations[0] + (25*K1CA/216) + (1408*K3CA/2565) + (2197*K4CA/4101) - (K5CA/5);
        answerRK4[1] = currentConcentrations[1] + (25*K1CB/216) + (1408*K3CB/2565) + (2197*K4CB/4101) - (K5CB/5);

        answerRK5[0] = currentConcentrations[0] + (16*K1CA/135) + (6656*K3CA/12825) + (28561*K4CA/56430) - (9*K5CA/50)+(2*K6CA/55);
        answerRK5[1] = currentConcentrations[1] + (16*K1CB/135) + (6656*K3CB/12825) + (28561*K4CB/56430) - (9*K5CB/50)+(2*K6CB/55);

        //TODO fix issues with optimal step size

        optimalStepSizeCA = Math.pow((wholeTimeStep/(2*Math.abs(answerRK5[0]-answerRK4[0]))), 0.25);
        optimalStepSizeCB = Math.pow((wholeTimeStep/(2*Math.abs(answerRK5[1]-answerRK4[1]))), 0.25);

        if (optimalStepSizeCA<optimalStepSizeCB) answer[2] = optimalStepSizeCA;
        else answer[2] = optimalStepSizeCB;

        answer[0] = answerRK5[0];
        answer[1] = answerRK5[1];

        return answer;

    }

} // end of class
