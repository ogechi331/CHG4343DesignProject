public class CSTRReactor extends Reactor {

    public CSTRReactor(double V, double initialFlow, Reaction reaction, double[] initialConcentrations, double[] inletConcentrations){
        super(V, initialFlow, reaction, initialConcentrations, inletConcentrations);
    }

    public CSTRReactor(CSTRReactor source) {
        super(source);
    }

    public CSTRReactor clone() {
        return new CSTRReactor(this);
    }

    public boolean equals(Object comparator){ //fix this
        if (!(super.equals(comparator))) return false;
        return true;
    }

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
