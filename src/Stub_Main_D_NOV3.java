public class Stub_Main_D_NOV3 {

        //stub code driver class for Dylan to test integrated classes created first November 3rd, 2023
        public static void main(String[] args) {

                double startTime = 0;
                double endTime = 20;
                double timeStep = 0.1;
                double controllerGain = 2.5;
                double integratingTimeConstant = 0.05;
                double derivativeTimeConstant = 2;


                CSTRControllerPID controlCSTR = new CSTRControllerPID(startTime, endTime, timeStep, controllerGain, integratingTimeConstant, derivativeTimeConstant);
                //passing reaction equation and rate constant
                Reaction reaction = new Reaction("A->B", 0.2);
                double[] initialConcentrations = {0.04, 0.16}; //steady state before step change
                double[] inletConcentrations = {1.2, 0}; //step change
                //giving reactor volume, initial flow rate, reaction, initial concentrations, and new inlet concentration
                CSTRReactor CSTR = new CSTRReactor(1, 0.05, reaction, initialConcentrations, inletConcentrations);
                double numberOfSteps = (int)Math.ceil((endTime-startTime)/timeStep)+1;
                double[][] results;
                results = controlCSTR.stimulateProcess(0.040, 0.160, 0.16, 0.05, CSTR);

                System.out.println("The reactor results are as follows");

                for (int i =0; i< (int)numberOfSteps; i++) {
                        System.out.printf("Time %1.1f CA %1.3f CB %1.3f \n", results[0][i], results[1][i], results[2][i]);
                        //troubleshooting version
                        //System.out.printf("Time %1.1f CA %1.3f CB %1.3f P_Part %1.3f I_Part %1.3f D_Part %1.3f controller %1.3f flow rate %1.3f new CA %1.3f new CB %1.3f ideal step size is %1.3f\n", results[0][i], results[1][i], results[2][i], results[4][i], results[5][i], results[6][i], results[7][i], results[8][i], results[9][i], results[10][i], results[11][i]);
                }

        }
}
