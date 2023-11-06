/** Concrete class for the CSTR reactor
 * @author Alex
 * @author Dylan
 * @version 2.0
 */
public class CSTRReactor extends Reactor {

    //TODO still needs to be changed to throw exceptions

    /** Constructor for CSTR reactor
     *
     * @param V reactor volume
     * @param initialFlow initial volumetric flow rate into the reactor
     * @param reaction reaction object representing the reactor in the reactor
     * @param initialConcentrations initial concentrations of species in the reactor
     * @param inletConcentrations inlet concentrations to the reactor after step change
     * @author Alex
     * @author Dylan
     */
    public CSTRReactor(double V, double initialFlow, Reaction reaction, double[] initialConcentrations, double[] inletConcentrations){
        super(V, initialFlow, reaction, initialConcentrations, inletConcentrations);
    }

    /** Copy constructor for the CSTR reactor
     *
     * @param source source object to copy
     * @author Alex
     * @author Dylan
     */
    public CSTRReactor(CSTRReactor source) {
        super(source);
    }

    /** Clone method for the CSTR reactor
     *
     * @return a copy of the object
     * @author Alex
     * @author Dylan
     */
    public CSTRReactor clone() {
        return new CSTRReactor(this);
    }

    /** Equals method
     *
     * @param comparator object to compare to current object
     * @return true if all instant variables and class type are equal, otherwise returns false
     * @author Alex
     * @author Dylan
     */
    public boolean equals(Object comparator){
        if (!(super.equals(comparator))) return false;
        return true;
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
        //double updatedConcentration;
        double[] inletConcentrations = new double[super.getInletConcentrations().length];

        double changeRate;

        inletConcentrations = super.getInletConcentrations();

        reactionRate=super.getReaction().calculateReactionRate(currentConcentrations,currentSpeciesNumber);

        changeRate = flowRate*inletConcentrations[currentSpeciesNumber];
        changeRate = changeRate - (flowRate*currentConcentrations[currentSpeciesNumber]);
        changeRate = changeRate + (reactionRate*super.getVolume());

        changeRate = ((1/super.getVolume())*changeRate);

        return changeRate;
    }

}
