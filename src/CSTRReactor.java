/** Concrete class for the CSTR reactor
 * @author Alex
 * @author Dylan
 * @version 2.1
 */
public class CSTRReactor extends Reactor {

    /** Constructor for CSTR reactor
     *
     * @param V reactor volume
     * @param initialFlow initial volumetric flow rate into the reactor
     * @param reaction reaction object representing the reactor in the reactor
     * @param initialConcentrations initial concentrations of species in the reactor
     * @param inletConcentrations inlet concentrations to the reactor after step change
     * @throws IllegalArgumentException if volume<0, initial flow<0, concentrations<0, inlet concentrations<0
     * @throws NullPointerException if reaction is null, initial concentrations are null or inlet concentrations are null
     * @author Alex
     * @author Dylan
     */
    public CSTRReactor(double V, double initialFlow, Reaction reaction, double[] initialConcentrations, double[] inletConcentrations) throws NullPointerException, IllegalArgumentException{
        super(V, initialFlow, reaction, initialConcentrations, inletConcentrations);
    }

    /** Copy constructor for the CSTR reactor
     *
     * @param source source object to copy
     * @throws NullPointerException if o object to copy is null
     * @author Alex
     * @author Dylan
     */
    public CSTRReactor(CSTRReactor source) throws NullPointerException {
        super(source);
    }

    /** Clone method for the CSTR reactor
     *
     * @return a copy of the object
     * @throws NullPointerException if o object to copy is null
     * @author Alex
     * @author Dylan
     */
    public CSTRReactor clone() throws NullPointerException {
        try{
            return new CSTRReactor(this);
        } catch(NullPointerException e){
            throw new NullPointerException("Failed to clone CSTRReactor: "+ e.getMessage());
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
