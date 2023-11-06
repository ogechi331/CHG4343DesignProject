/** Abstract parent class for reactor types
 * @author Alex
 * @author Dylan
 * @version 2.0
 */
public abstract class Reactor {

    //TODO still needs to be changed to throw exceptions

    private double volume;
    private Reaction reaction;
    private double[] inletConcentrations;

    private double[] initialConcentrations;

    private double initialFlow;

    /** Constructor for the abstract reactor class
     *
     * @param volume reactor volume
     * @param initialFlow initial volumetric flow rate into the reactor
     * @param reaction reaction object representing the reactor in the reactor
     * @param initialConcentrations initial concentrations of species in the reactor
     * @param inletConcentrations inlet concentrations to the reactor after step change
     * @author Alex
     * @author Dylan
     */
    public Reactor(double volume, double initialFlow, Reaction reaction, double[] initialConcentrations, double[] inletConcentrations) {

        if(reaction==null){
            System.out.println("A reaction is needed to initialize the reactor");
            System.exit(0);
        }

        this.reaction= reaction.clone();

        if (volume<0) {
            System.out.println("Reactor volume must not be negative");
            System.exit(0);
        }

        this.volume=volume;

        if (initialFlow<0) {
            System.out.println("Reactor initial flow must not be negative");
            System.exit(0);
        }
        this.initialFlow=initialFlow;

        //TODO add checks for these

        this.inletConcentrations = new double[inletConcentrations.length];
        this.initialConcentrations = new double[initialConcentrations.length];

        for(int i=0;i<initialConcentrations.length; i++){
            this.initialConcentrations[i] = initialConcentrations[i];
        }
        for(int i=0;i<inletConcentrations.length; i++){
            this.inletConcentrations[i] = inletConcentrations[i];
        }

    }

    /** Copy constructor for the abstract reactor class
     *
     * @param source source object to copy
     * @author Alex
     * @author Dylan
     */
    public Reactor(Reactor source) {
        if(source==null){
            System.out.println("Null object given to copy constructor");
            System.exit(0);
        }

        this.reaction= reaction.clone();
        this.volume=source.volume;
        this.initialFlow=source.initialFlow;

        for(int i=0;i<initialConcentrations.length; i++){
            this.initialConcentrations[i] = source.initialConcentrations[i];
        }
        for(int i=0;i<inletConcentrations.length; i++){
            this.inletConcentrations[i] = source.inletConcentrations[i];
        }
    }

    /** Clone method for the abstract reactor class
     *
     * @return a copy of the object
     * @author Alex
     * @author Dylan
     */
    public abstract Reactor clone();

    /** Accessor method for reactor volume
     *
     * @return reactor volume
     * @author Alex
     * @author Dylan
     */
    public double getVolume() {
        return this.volume;
    }

    /** Mutator method for reactor volume
     *
     * @param volume reactor volume which must not be negative
     * @return true if updated and false if not
     * @author Alex
     * @author Dylan
     */
    public boolean setVolume(double volume) {
        if (volume<0) return false;
        this.volume = volume;
        return true;
    }

    /** Accessor method for reaction
     *
     * @return reaction
     * @author Alex
     * @author Dylan
     */
    public Reaction getReaction() {
        return this.reaction.clone();
    }

    /** Mutator method for reactor reaction
     *
     * @param reaction takes reaction object
     * @return true if updated and false if not
     * @author Alex
     * @author Dylan
     */
    public boolean setReaction(Reaction reaction) {
        if (reaction==null) return false;
        this.reaction = reaction.clone();
        return true;
    }

    /** Accessor method for initial volumetric flow rate
     *
     * @return initial volumetric flow rate
     * @author Alex
     * @author Dylan
     */
    public double getInitialFlow() {
        return this.initialFlow;
    }

    /** Mutator method for initial volumetric flow rate
     *
     * @param initialFlow initial volumetric flow rate which must not be negative
     * @return true if updated and false if not
     * @author Alex
     * @author Dylan
     */
    public boolean setInitialFlow(double initialFlow) {
        if (initialFlow<0) return false;
        this.initialFlow = initialFlow;
        return true;
    }

    /** Accessor method for inlet concentrations after step change
     *
     * @return array of inlet concentrations after step change
     * @author Alex
     * @author Dylan
     */
    public double[] getInletConcentrations() {
        double[] inletConcentrations = new double[this.inletConcentrations.length];
        for (int i=0; i<this.inletConcentrations.length; i++) {
            inletConcentrations[i] = this.inletConcentrations[i];
        }
        return inletConcentrations;
    }

    /** Mutator method for inlet concentrations
     *
     * @param inletConcentrations array of inlet concentrations each of which must be non-negative
     * @return true if updated and false if not
     * @author Alex
     * @author Dylan
     */
    public boolean setInletConcentrations(double[] inletConcentrations) {
        for (int i=0; i<inletConcentrations.length; i++) {
            if (inletConcentrations[i]<0) return false;
        }
        this.inletConcentrations = inletConcentrations;
        return true;
    }

    /** Accessor method for initial concentrations
     *
     * @return array of initial concentrations before step change
     * @author Alex
     * @author Dylan
     */
    public double[] getInitialConcentrations() {
        double[] initialConcentrations = new double[this.initialConcentrations.length];
        for (int i=0; i<this.initialConcentrations.length; i++) {
            initialConcentrations[i] = this.initialConcentrations[i];
        }
        return initialConcentrations;
    }

    /** Mutator method for initial concentrations
     *
     * @param initialConcentrations array of initial concentrations each of which must be non-negative
     * @return true if updated and false if not
     * @author Alex
     * @author Dylan
     */
    public boolean setInitialConcentrations(double[] initialConcentrations) {
        for (int i=0; i<initialConcentrations.length; i++) {
            if (initialConcentrations[i]<0) return false;
        }
        this.initialConcentrations = initialConcentrations;
        return true;
    }

    /** Equals method
     *
     * @param comparator object to compare to current objec
     * @return true if all instant variables and class type are equal, otherwise returns false
     * @author Alex
     * @author Dylan
     */
    public boolean equals(Object comparator){
        if (comparator==null) return false;
        if (comparator.getClass()!=this.getClass()) return false;

        Reactor reactorComparator = (Reactor)comparator;

        if (reactorComparator.volume!=this.volume) return false;
        if(!(reactorComparator.reaction.equals(this.reaction))) return false;
        if (reactorComparator.inletConcentrations.length!=this.inletConcentrations.length) return false;
        if (reactorComparator.initialConcentrations.length!=this.initialConcentrations.length) return false;

        for (int i = 0; i<this.inletConcentrations.length; i++) {
            if (reactorComparator.inletConcentrations[i]!=this.inletConcentrations[i]) return false;
        }

        for (int i = 0; i<this.initialConcentrations.length; i++) {
            if (reactorComparator.initialConcentrations[i]!=this.initialConcentrations[i]) return false;
        }

        if (reactorComparator.initialFlow != this.initialFlow) return false;

        return true;

    }
    //not sure if we want this abstract method here
    /** Simulation method for change in reactor concentration over time step
     *
     * @param timeStep time step
     * @param flowRate updated flow rate
     * @param currentConcentrations current concentrations
     * @param currentSpeciesNumber current species to simulate
     * @return change in reactor concentration over time step
     * @author Alex
     * @author Dylan
     */
    public abstract double simulateStep(double timeStep,double flowRate, double[] currentConcentrations, int currentSpeciesNumber);
}
