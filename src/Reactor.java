public abstract class Reactor {

    private double volume;
    private Reaction reaction;
    private double[] inletConcentrations;

    private double[] initialConcentrations;

    private double initialFlow;

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

    public abstract Reactor clone();

    public double getVolume() {
        return this.volume;
    }

    public boolean setVolume(double volume) {
        if (volume<0) return false;
        this.volume = volume;
        return true;
    }

    public Reaction getReaction() {
        return this.reaction.clone();
    }

    public boolean setReaction(Reaction reaction) {
        if (reaction==null) return false;
        this.reaction = reaction.clone();
        return true;
    }

    public double getInitialFlow() {
        return this.initialFlow;
    }

    public boolean setInitialFlow(double initialFlow) {
        if (initialFlow<0) return false;
        this.initialFlow = initialFlow;
        return true;
    }

    public double[] getInletConcentrations() {
        double[] inletConcentrations = new double[this.inletConcentrations.length];
        for (int i=0; i<this.inletConcentrations.length; i++) {
            inletConcentrations[i] = this.inletConcentrations[i];
        }
        return inletConcentrations;
    }

    public boolean setInletConcentrations(double[] inletConcentrations) {
        this.inletConcentrations = inletConcentrations;
        return true;
    }

    public double[] getInitialConcentrations() {
        double[] initialConcentrations = new double[this.initialConcentrations.length];
        for (int i=0; i<this.initialConcentrations.length; i++) {
            initialConcentrations[i] = this.initialConcentrations[i];
        }
        return initialConcentrations;
    }

    public boolean setInitialConcentrations(double[] initialConcentrations) {
        this.initialConcentrations = initialConcentrations;
        return true;
    }

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

    public abstract double simulateStep(double timeStep,double flowRate, double[] currentConcentrations, int currentSpeciesNumber);
}
