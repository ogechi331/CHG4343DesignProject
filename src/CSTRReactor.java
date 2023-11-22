/** Concrete class for the CSTR reactor
 * @author Alex
 * @author Dylan
 * @author Ogechi
 * @version 2.2
 */
public class CSTRReactor extends Reactor implements DifferentialEquation, Controllable{


    /** Constructor for CSTR reactor
     *
     * @param V reactor volume
     * @param initialFlow initial volumetric flow rate into the reactor
     * @param reaction reaction object representing the reactor in the reactor
     * @param initialConcentrations initial concentrations of species in the reactor
     * @param inletConcentrations inlet concentrations to the reactor after step change
     * @throws IllegalArgumentException if volume<0, initial flow<0, concentrations<0, inlet concentrations<0, and if reaction, initial concentrations or initial concentrations are null
     * @author Alex
     * @author Dylan
     */
    public CSTRReactor(double V, double initialFlow, Reaction reaction, double[] initialConcentrations, double[] inletConcentrations) {
        super(V, initialFlow, reaction, initialConcentrations, inletConcentrations, 0, false);
    }
    public CSTRReactor(double V, double initialFlow, Reaction reaction, double[] initialConcentrations, double[] inletConcentrations, int controlled, boolean isControlled) {
        super(V, initialFlow, reaction, initialConcentrations, inletConcentrations, controlled, isControlled);
    }

    /** Copy constructor for the CSTR reactor
     *
     * @param source source object to copy
     * @throws IllegalArgumentException if o object to copy is null
     * @author Alex
     * @author Dylan
     */
    public CSTRReactor(CSTRReactor source) {
        super(source);
    }

    /** Clone method for the CSTR reactor
     *
     * @return a copy of the object
     * @throws IllegalArgumentException if o object to copy is null
     * @author Alex
     * @author Dylan
     */
    public CSTRReactor clone() {
        try{
            return new CSTRReactor(this);
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException("Failed to clone CSTRReactor: "+ e.getMessage());
        }
    }

    /** Equals method
     *
     * @param comparator object to compare to current object
     * @return true if all instant variables and class type are equal, otherwise returns false
     * @author Alex
     * @author Dylan
     */
    public boolean equals(Object comparator){
        return super.equals(comparator);
    }

    /** Method to simulate change in concentration over time step
     *
     * @param timeStep time step
     * @param flowRate updated flow rate
     * @param currentConcentrations current concentrations
     * @param currentSpeciesNumber current species to simulate
     * @return change in concentration over time step
     * @author Alex
     * @author Dylan
     */
    public double simulateStep(double timeStep,double flowRate, double[] currentConcentrations, int currentSpeciesNumber){
        double reactionRate;
        double[] inletConcentrations = new double[super.getInletConcentrations().length];

        double changeRate = 0;

        inletConcentrations = super.getInletConcentrations();
        try {
            reactionRate = super.getReaction().calculateReactionRate(currentConcentrations, currentSpeciesNumber);

            changeRate = flowRate*inletConcentrations[currentSpeciesNumber];
            changeRate = changeRate - (flowRate*currentConcentrations[currentSpeciesNumber]);
            changeRate = changeRate + (reactionRate*super.getVolume());

            changeRate = ((1/super.getVolume())*changeRate);
        }
        catch (IllegalArgumentException e) {
            System.out.println("Failed to simulate step: " + e.getMessage());
        }

        return changeRate;
    }

    //
    public double[] getSystemOutput(double t, double timeStep, double tolerance) {
        int n = super.getInletConcentrations().length;
        super.setCurrentSpeciesNumber(0);
        DifferentialEquation[] equations =  new DifferentialEquation[n];
        for (int i =0; i<n; i++){
            equations[i] = this;
        }
        double[] solution = RK45.solve(t, super.getCurrentConcentrations(), timeStep, equations, tolerance);

        //Controlled object is responsible for taking care of its own state when a timeStep occurs
        //in other words, maintain the fact that classes should take care of their own business when possible
        super.setCurrentConcentrations(solution);

        return solution;

    }

    @Override
    public double getControlledVar() {
        double[] inletConcentrations = super.getInletConcentrations();
        return inletConcentrations[super.getControlled()];
    }


    @Override
    public double[] getInitialValues() {
        double[] doubles = super.getInletConcentrations();
        return doubles;
    }

    @Override
    public boolean getIsControlled() {
        return super.getIsControlled();
    }

    @Override
    public void setManipulatedVariable(double var) {
        super.setInitialFlow(var);
    }

    /** Applied function to calculate in RK45
     *
     * @param x time
     * @param y array of ys for time t
     * @return Rate of change of the dependent variable with respect to the independent variable, here is change in concentration
     *
     */
    @Override
    public double apply(double x, double y) {
        double reactionRate = 0;
        Reaction reaction = super.getReaction();
        double[] currentConcentrations = super.getCurrentConcentrations();
        double volume = super.getVolume();
        double  initialFlow = super.getInitialFlow();
        int currSpeciesNumber = super.getCurrentSpeciesNumber();
        double[] initialConcentrations = super.getInitialConcentrations();

        try {
            reactionRate = reaction.calculateReactionRate(currentConcentrations, currSpeciesNumber);

        }
        catch (IllegalArgumentException e) {
            System.out.println("Failed to simulate step: " + e.getMessage());
        }
        //reactant mass balance, the
        double changeRate =  initialFlow*(initialConcentrations[currSpeciesNumber]-currentConcentrations[currSpeciesNumber])/volume + reactionRate ;
        if(++currSpeciesNumber < currentConcentrations.length){ //increment before logical expression
            super.setCurrentSpeciesNumber(currSpeciesNumber);
        }else{super.setCurrentSpeciesNumber(0);}

        return changeRate; //changed rate can be negative, need to check for negative concentration else where since they don't get changed here.
    }
}
