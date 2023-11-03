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
        double updatedConcentration;
        double[] inletConcentrations = new double[super.getInletConcentrations().length];
        inletConcentrations = super.getInletConcentrations();
        reactionRate=super.getReaction().calculateReactionRate(currentConcentrations,currentSpeciesNumber);

        updatedConcentration=currentConcentrations[currentSpeciesNumber]+timeStep*((flowRate/super.getVolume())*
                inletConcentrations[currentSpeciesNumber]-currentConcentrations[currentSpeciesNumber]*flowRate/super.getVolume()+
                currentConcentrations[currentSpeciesNumber]*reactionRate);

        return updatedConcentration;
    }
}
