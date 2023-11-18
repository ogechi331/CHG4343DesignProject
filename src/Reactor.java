/** Abstract parent class for reactor types
 * @author Alex
 * @author Dylan
 * @version 2.2
 */
public abstract class Reactor implements Controllable, DifferentialEquation {

    private double volume; //reactor volume
    private Reaction reaction; //reaction taking place
    private double[] inletConcentrations; //inlet concentrations

    private double[] initialConcentrations; //initial concentrations in the reactor

    private double[] currentConcentrations; //current concentrations in the reactor

    private double initialFlow; //initial flow rate

    private int controlled;
    private double currentFlow; //current flow rate
    private int currentSpeciesNumber; //
    private PIDController controller;

    /** Constructor for the abstract reactor class
     *
     * @param volume reactor volume
     * @param initialFlow initial volumetric flow rate into the reactor
     * @param reaction reaction object representing the reactor in the reactor
     * @param initialConcentrations initial concentrations of species in the reactor
     * @param inletConcentrations inlet concentrations to the reactor after step change
     * @throws IllegalArgumentException if volume<0, initial flow<0, concentrations<0, inlet concentrations<0, and if reaction, initial concentrations or initial concentrations are null
     * @author Alex
     * @author Dylan
     */
    public Reactor(double volume, double initialFlow, Reaction reaction, double[] initialConcentrations, double[] inletConcentrations, int controlled, PIDController controller) {

        if(reaction==null) throw new IllegalArgumentException("A reaction is needed to initialize the reactor");
        this.reaction= reaction.clone();

        if (volume<0) throw new IllegalArgumentException("Reactor volume must not be negative");
        this.volume=volume;

        if (initialFlow<0) throw new IllegalArgumentException("Reactor initial flow must not be negative");
        this.initialFlow=initialFlow;

        if(inletConcentrations==null) throw new IllegalArgumentException("Inlet Concentrations are needed");
        for(int i=0;i<inletConcentrations.length;i++){
            if(inletConcentrations[i]<0) throw new IllegalArgumentException("Inlet Concentrations must not be negative");
        }
        this.inletConcentrations = new double[inletConcentrations.length];
        for(int i=0;i<inletConcentrations.length; i++){
            this.inletConcentrations[i] = inletConcentrations[i];
        }

        if(initialConcentrations==null) throw new IllegalArgumentException("Initial Concentrations are needed");
        for(int i=0;i<initialConcentrations.length;i++){
            if(initialConcentrations[i]<0) throw new IllegalArgumentException("Initial Concentrations must not be negative");
        }
        this.initialConcentrations = new double[initialConcentrations.length];
        for(int i=0;i<initialConcentrations.length; i++){
            this.initialConcentrations[i] = initialConcentrations[i];
        }
        this.controlled = controlled;
        this.currentFlow = initialFlow;
        for (int i =0; i<initialConcentrations.length;i++) {
            this.currentConcentrations[i]=initialConcentrations[i];
        }
        this.currentSpeciesNumber = 0;
        this.controller = controller;

    }

    /** Copy constructor for the abstract reactor class
     *
     * @param source source object to copy
     * @throws IllegalArgumentException if o object to copy is null
     * @author Alex
     * @author Dylan
     */
    public Reactor(Reactor source)  {
        if (source==null) throw new IllegalArgumentException("Error, copy of null PIDController object");

        this.reaction= reaction.clone();
        this.volume=source.volume;
        this.initialFlow=source.initialFlow;
        this.currentFlow=source.currentFlow;

        for(int i=0;i<initialConcentrations.length; i++){
            this.initialConcentrations[i] = source.initialConcentrations[i];
        }
        for(int i=0;i<inletConcentrations.length; i++){
            this.inletConcentrations[i] = source.inletConcentrations[i];
        }
        for (int i=0; i<source.currentConcentrations.length;i++) {
            this.currentConcentrations[i] = source.currentConcentrations[i];
        }
    }

    /** Clone method for the abstract reactor class
     *
     * @return a copy of the object
     * @throws IllegalArgumentException if an object to copy is null
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

    /** Accessor method for current flow rate
     *
     * @return current flow rate
     * @author Dylan
     */
    public double getCurrentFlow() {
        return this.currentFlow;
    }

    /** Mutator method for current flow rate
     *
     * @param currentFlow current flow rate
     * @return true if updated and false if not
     * @author Dylan
     */
    public boolean setCurrentFlowRate(double currentFlow) {
        if (currentFlow<0) return false;
        this.currentFlow=currentFlow;
        return true;
    }

    /** Accessor method for controller
     *
     * @return copy of controller object
     * @author Dylan
     */
    public PIDController getController() {
        return this.controller.clone();
    }

    /** Mutator method for controller
     *
     * @param controller controller object
     * @return true if updated and false if not
     * @author Dylan
     */
    public boolean setController(PIDController controller) {
        if (controller==null) return false;
        this.controller=controller.clone();
        return true;
    }

    /**  Accessor method for current species number
     *
     * @return current species number
     * @author Dylan
     */
    public int getCurrentSpeciesNumber() {
        return this.currentSpeciesNumber;
    }

    /** Mutator method for current species number
     *
     * @param currentSpeciesNumber current species number
     * @return true when updated
     */
    public boolean setCurrentSpeciesNumber(int currentSpeciesNumber) {
        this.currentSpeciesNumber=currentSpeciesNumber;
        return true;
    }

    /** Accessor method for controlled
     *
     * @return controlled
     * @author Dylan
     */
    public int getControlled(){
        return this.controlled;
    }

    /** Mutator method for controlled
     *
     * @param controlled
     * @return true when updated
     * @author Dylan
     */
    public boolean setControlled(int controlled) {
        this.controlled=controlled;
        return true;
    }

    /** Accessor method for current concentrations
     *
     * @return array of current concentrations
     * @author Dylan
     */
    public double[] getCurrentConcentrations() {
        double[] currentConcentrations = new double[this.currentConcentrations.length];
        for (int i=0; i<this.currentConcentrations.length; i++) {
            currentConcentrations[i] = this.currentConcentrations[i];
        }
        return currentConcentrations;
    }

    /** Mutator method for current concentrations
     *
     * @param currentConcentrations array of current concentrations
     * @return true if updated, false if not updated
     * @author Dylan
     */
    public boolean setCurrentConcentrations(double[] currentConcentrations) {
        if (currentConcentrations==null) return false;
        for (int i=0;i<currentConcentrations.length;i++) {
            if (currentConcentrations[i]<0 ) return false;
        }
        for (int i =0;i<currentConcentrations.length;i++) {
            this.currentConcentrations[i] =currentConcentrations[i];
        }
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


}
