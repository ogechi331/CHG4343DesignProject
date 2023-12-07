/** This class is for validation of individual methods
 * Lines calling static testing methods must be uncommented to print test results
 * This class does nothing by default and is presented to show how validation of individual methods was performed
 * @author Dylan
 */
public class TestingDriver {

    public static void main(String args[]) {

        //Comment and uncomment to test
        //uncomment next line to test reactor and CSTR reactor classes
        //testReactorClass();
        //uncomment next line to test reaction class
        //testReaction();
        //uncomment next line to test species class
        //testSpecies();



        //interfaces are implemented and not testable on their own
        //Queue and SinglyLinkedList are tested when in use with controller and generic


    }

    /** Static method to test methods in the Reactor and CSTRReactor Classes
     * @author Dyla
     */
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
            double[] out;

            //valid reactor test
            CSTRReactor CSTR = new CSTRReactor(volume, initialFlow, reaction, initialConcentrations, inletConcentrations, controlled, isControlled);

            System.out.println("Volume 1->2 test");
            System.out.println(CSTR.getVolume());
            CSTR.setVolume(2);
            System.out.println(CSTR.getVolume());

            System.out.println("Initial flow 0.05->0.1 test");
            System.out.println(CSTR.getVolume());
            CSTR.setInitialFlow(0.1);
            System.out.println(CSTR.getInitialFlow());

            double[] initialConcentrations_2 = {0.08, 0.32};
            System.out.println("Initial concentrations 0.04, 0.16->0.08, 0.32 test");
            out = CSTR.getInitialConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);
            CSTR.setInitialConcentrations(initialConcentrations_2);
            out = CSTR.getInitialConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);

            double[] inletConcentrations_2 = {2.4, 0};
            System.out.println("Inlet concentrations 1.2, 0->2.4, 0 test");
            out = CSTR.getInletConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);
            CSTR.setInletConcentrations(inletConcentrations_2);
            out = CSTR.getInletConcentrations();
            System.out.println(out[0]);
            System.out.println(out[1]);

            System.out.println("Controlled 0->1 test");
            System.out.println(CSTR.getControlled());
            CSTR.setControlled(1);
            System.out.println(CSTR.getControlled());

            System.out.println("isControlled false->true test");
            System.out.println(CSTR.getIsControlled());
            CSTR.setIsControlled(true);
            System.out.println(CSTR.getIsControlled());

            System.out.println("Current species number 0->1 test");
            System.out.println(CSTR.getCurrentSpeciesNumber());
            CSTR.setCurrentSpeciesNumber(1);
            System.out.println(CSTR.getCurrentSpeciesNumber());

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

            System.out.println("Test of equal reactors and clone method");

            CSTRReactor CSTR_4 = CSTR.clone();
            System.out.println(CSTR.equals(CSTR_4));

            System.out.println("Test of not equal reactors");

            CSTRReactor CSTR_5 = new CSTRReactor(CSTR);
            CSTR_5.setVolume(4);
            System.out.println(CSTR.equals(CSTR_5));

            //other methods are tested whenever the controller is used

        } //end of reactor tests

    /** Static method to test methods in the reaction Class
     * @author Dylan
     */
    public static void testReaction () {
        String reactionString = "A->B";
        double k = 0.2;

        System.out.println("Test of valid reaction");

        Reaction reaction = new Reaction(reactionString, k);

        System.out.println("Test of in-valid reaction due to bad string");
        String reactionString_2 = "A->";

        try {
            Reaction reaction_2 = new Reaction(reactionString_2, k);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Test of in-valid reaction due to bad string");
        String reactionString_3 = "";

        try {
            Reaction reaction_3 = new Reaction(reactionString_3, k);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("k 0.2->0.4 test");
        System.out.println(reaction.getK());
        reaction.setK(0.4);
        System.out.println(reaction.getK());

        System.out.println("Reactants A to C test");
        Species[] output;
        output = reaction.getReactants();
        System.out.println(output[0].getName());
        Species C = new Species("C", 0);
        Species[] reactants = new Species[1];
        reactants[0] = C;
        reaction.setReactants(reactants);
        output = reaction.getReactants();
        System.out.println(output[0].getName());

        System.out.println("Products B to C test");
        output = reaction.getProducts();
        System.out.println(output[0].getName());
        Species[] products = new Species[1];
        products[0] = C;
        reaction.setProducts(reactants);
        output = reaction.getProducts();
        System.out.println(output[0].getName());

        System.out.println("Test of equal reactions and clone method");

        Reaction reaction_2 = reaction.clone();
        System.out.println(reaction.equals(reaction_2));

        System.out.println("Test of non-equal reactions");

        Reaction reaction_3 = new Reaction(reaction);
        reaction_3.setK(0.8);
        System.out.println(reaction.equals(reaction_3));

        System.out.println("Test of invalid reaction copy");
        try {
            Reaction reaction_4 = new Reaction(null);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //other methods are tested whenever the controller is used

    } //end of reaction tests

    /** Static method to test methods in the species Class
     * @author Dylan
     */
    public static void testSpecies() {

        System.out.println("Test of valid species");
        Species A = new Species("A", 1);

        System.out.println("Test of equal species and clone method");
        Species A_2 = A.clone();
        System.out.println(A.equals(A_2));

        System.out.println("Test of equal species and clone method");
        Species A_3 = A.clone();
        A_3.setName("B");
        System.out.println(A.equals(3_2));

        System.out.println("Name A->B test");
        System.out.println(A.getName());
        A.setName("B");
        System.out.println(A.getName());

        System.out.println("coefficent 1->2 test");
        System.out.println(A.getCoefficient());
        A.setCoefficient(2);
        System.out.println(A.getCoefficient());

        //other methods are tested whenever the controller is used

    } //end of species checks



    }

