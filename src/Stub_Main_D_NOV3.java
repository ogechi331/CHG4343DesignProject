public class Stub_Main_D_NOV3 {

        //stub code driver class for Dylan to test integrated classes created first November 3rd, 2023
        public static void main(String[] args) {


        CSTRControllerPID controlCSTR = new CSTRControllerPID(0, 10, 0.1, 2.5, 0.05, 2);
        //passing reaction equation and rate constant
        Reaction reaction = new Reaction("A->B", 0.2);
        double[] initialConcentrations = {0.04, 0.16}; //steady state before step change
        //giving reactor volume, initial flow rate, reaction, initial concentrations, and new inlet concentration
        CSTRReactor CSTR = new CSTRReactor(1, 0.05, reaction, initialConcentrations, 1.2);
        double numberOfSteps = (int)Math.ceil((10-0)/0.1)+1;
        double[][] results = new double[11][(int)numberOfSteps];
        results = controlCSTR.stimulateProcess(0.040, 0.160, 0.16, 0.05, 1.2, CSTR);

        System.out.println("The reactor results are as follows");

        for (int i =0; i< (int)numberOfSteps; i++) {
            System.out.printf("Time %1.1f CA %1.3f CB %1.3f \n", results[0][i], results[1][i], results[2][i]);
        }


        }
    }
}
