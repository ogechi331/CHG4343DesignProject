/** Abstract parent class for reactor types
 * @author Alex
 * @author Dylan
 * @version 2.1
 */
public abstract class Reactor {

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
     * @throws IllegalArgumentException if volume<0, initial flow<0, concentrations<0, inlet concentrations<0
     * @throws NullPointerException if reaction is null, initial concentrations are null or inlet concentrations are null
     * @author Alex
     * @author Dylan
     */
    public Reactor(double volume, double initialFlow, Reaction reaction, double[] initialConcentrations, double[] inletConcentrations) throws NullPointerException, IllegalArgumentException {

        if(reaction==null) throw new NullPointerException("A reaction is needed to initialize the reactor");
        this.reaction= reaction.clone();

        if (volume<0) throw new IllegalArgumentException("Reactor volume must not be negative");
        this.volume=volume;

        if (initialFlow<0) throw new IllegalArgumentException("Reactor initial flow must not be negative");
        this.initialFlow=initialFlow;

        if(inletConcentrations==null) throw new NullPointerException("Inlet Concentrations are needed");
        for(int i=0;i<inletConcentrations.length;i++){
            if(inletConcentrations[i]<0) throw new IllegalArgumentException("Inlet Concentrations must not be negative");
        }
        this.inletConcentrations = new double[inletConcentrations.length];
        for(int i=0;i<inletConcentrations.length; i++){
            this.inletConcentrations[i] = inletConcentrations[i];
        }

        if(initialConcentrations==null) throw new NullPointerException("Initial Concentrations are needed");
        for(int i=0;i<initialConcentrations.length;i++){
            if(initialConcentrations[i]<0) throw new IllegalArgumentException("Initial Concentrations must not be negative");
        }
        this.initialConcentrations = new double[initialConcentrations.length];
        for(int i=0;i<initialConcentrations.length; i++){
            this.initialConcentrations[i] = initialConcentrations[i];
        }

    }

    /** Copy constructor for the abstract reactor class
     *
     * @param source source object to copy
     * @throws NullPointerException if o object to copy is null
     * @author Alex
     * @author Dylan
     */
    public Reactor(Reactor source) throws NullPointerException {
        if (source==null) throw new NullPointerException("Error, copy of null PIDController object");

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
     * @throws NullPointerException if an object to copy is null
     * @author Alex
     * @author Dylan
     */
    public abstract Reactor clone() throws NullPointerException;

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
