public class TestingDriver {

    public static void main(String args[]) {

        //Comment and uncomment to test
        testReactorClass();


    }

        public static void testReactorClass() {

            double volume = 1;
            double initialFlow = 0.05;
            double k = 0.2;
            String reactionString = "A->B";
            Reaction reaction = new Reaction(reactionString, k);
            double[] initialConcentrations = {0.04, 0.16};
            double[] inletConcentrations = {1.2, 0};
            int controlled = 0;
            boolean isControlled = false;

            //valid reactor test
            CSTRReactor CSTR = new CSTRReactor(volume, initialFlow, reaction, initialConcentrations, inletConcentrations, controlled, isControlled);

            System.out.println("Volume 1->2 test");
            CSTR.setVolume(2);
            System.out.println(CSTR.getVolume());

            System.out.println("Initial flow 0.05->0.1 test");
            CSTR.setInitialFlow(0.1);
            System.out.println(CSTR.getInitialFlow());

            double[] initialConcentrations_2 = {0.08, 0.32};
            System.out.println("Initial concentrations 0.04, 0.16->0.08, 0.32 test");
            CSTR.setInitialConcentrations(initialConcentrations_2);
            double[] out = CSTR.getInitialConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);

            double[] inletConcentrations_2 = {2.4, 0};
            System.out.println("Inlet concentrations 1.2, 0->2.4, 0 test");
            CSTR.setInletConcentrations(inletConcentrations_2);
            out = CSTR.getInletConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);

            System.out.println("Controlled 0->1 test");
            CSTR.setControlled(1);
            System.out.println(CSTR.getControlled());

            System.out.println("isControlled false->true test");
            CSTR.setIsControlled(true);
            System.out.println(CSTR.getIsControlled());

            System.out.println("Test of invalid volume");
            try {
                CSTRReactor CSTR_2 = new CSTRReactor(-1, initialFlow, reaction, initialConcentrations, inletConcentrations, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid inital flow");
            try {
                CSTRReactor CSTR_2 = new CSTRReactor(volume, -0.5, reaction, initialConcentrations, inletConcentrations, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid reaction");
            try {
                CSTRReactor CSTR_2 = new CSTRReactor(volume, initialFlow, null, initialConcentrations, inletConcentrations, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid initalConcentrations for null array");
            try {
                CSTRReactor CSTR_2 = new CSTRReactor(volume, initialFlow, reaction, null, inletConcentrations, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid initalConcentrations for negative concentration array");
            try {
                double[] initialConcentrations_3 = {-0.04, 0.16};
                CSTRReactor CSTR_2 = new CSTRReactor(volume, initialFlow, reaction, initialConcentrations_3, inletConcentrations, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid inletConcentations for null array");
            try {
                CSTRReactor CSTR_2 = new CSTRReactor(volume, initialFlow, reaction, initialConcentrations, null, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Test of invalid inletConcentations for negative concentration array");
            try {
                double[] inletConcentrations_3 = {-1.2, 0};
                CSTRReactor CSTR_2 = new CSTRReactor(volume, initialFlow, reaction, initialConcentrations, inletConcentrations_3, controlled, isControlled);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Test of invalid rector copy");
            try {
                CSTRReactor CSTR_3 = new CSTRReactor(null);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Test of equal reactors");

            CSTRReactor CSTR_4 = new CSTRReactor(CSTR);
            System.out.println(CSTR.equals(CSTR_4));

        } //end of reactor tests




    }

