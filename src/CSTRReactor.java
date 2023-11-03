public class CSTRReactor extends Reactor {
    private double V;
    private Reaction reaction;

    private double[] inletConcentrations;

    public CSTRReactor(double setPoint,double V, Reaction reaction
                       double inletConcentrations[]){
        this.setPoint=setPoint;
        this.V=V;
        this.reaction=new Reaction(reaction);
        for(int i;i=0;i<inletConcentrations.length){
            this.inletConcentrations[i] = inletConcentrations[i];
        }
    }

    public double getV() {
        return V;
    }

    public void setV(double v) {
        V = v;
    }

    public double[] getInletConcentrations() {
        return inletConcentrations;
    }

    public void setInletConcentrations(double[] inletConcentrations) {
        this.inletConcentrations = inletConcentrations;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }


    public boolean equals(Object comparator){ //fix this
        if comparator==null return false;
        else return (this.getClass()==comparator.getClass());
    }
    public double simulateStep(double timeStep,double flowRate, double[] currentConcentrations, double currentSpeciesNumber){
        double reactionRate;
        double updatedConcentration;
        reactionRate=reaction.calculateReactionRate(currentConcentrations,currentSpeciesNumber);
        updatedConcentration=currentConcentrations[currentSpeciesNumber]+timeStep*((flowRate/this.V)*
                inletConcentrations[currentSpeciesNumber]-currentConcentrations[currentSpeciesNumber]*flowRate/this.V+
                currentConcentrations[currentSpeciesNumber]*reactionRate);
        return updatedConcentration;
    }
}
