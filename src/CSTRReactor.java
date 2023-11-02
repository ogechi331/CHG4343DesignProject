public class CSTRReactor extends Reactor {
    //takes reaction rate and updates C and flowrate TimeStepReaction
    //Gives controlled ot PID and takes in commanded
    private double setPoint;
    //intensive varialbes
    private double V;
    private  double v0;
    private double[] concentrations; //setEqualtoA&B
    private double[] flowConcentrations;
    private double flowRate;//assuming ideal mixing and constant level
    private Species[] species;
    private Species[] reactants;
    private Species[] products;

    private double timeStep;
    private Reaction zeroOrderReaction;

    public CSTRReactor(double setPoint,double V,double v0, Reaction zeroOrderReaction, double timeStep, double flowRate,
                       double flowConcentrations[],double concentrations[]){
        this.setPoint=setPoint;
        this.V=V;
        this.v0=v0;
        this.zeroOrderReaction=new Reaction(zeroOrderReaction);
        this.timeStep=timeStep;
        this.flowRate-flowRate;
        this.reactants= zeroOrderReaction.getReactants();
        this.products= zeroOrderReaction.getProducts();
        for(int i;i=0;i<flowConcentrations.length){
            this.flowConcentrations[i] = flowConcentrations[i];
        }
        this.flowRate=flowRate;
        for(int i;i=0;i<(this.reactants.length+this.products.length)){
            if(i<this.reactants.length) {
                species[i] = new Species(this.reactants[i]);
            }
            else {
                species[i] = new Species(this.reactants[i]);
            }
        }
        this.flowConcentrations.clone();
        this.concentrations=concentrations.clone();

    }
    public boolean equals(Object comparator){
        if comparator==null return false;
        else return (this.getClass()==comparator.getClass());
    }
    public double simulateStep(double commandedVariable){
        flowRate=commandedVariable;
        private double k;
        k=zeroOrderReaction.calculateReactionRate(concentrations);
        for(int i;i=0;i<concentrations.length){
            concentrations[i] += concentrations[i] * k *timeStep*species[i].getCoefficient();
            concentrations[i] += flowConcentrations[i] * timeStep*flowRate/V;//inflow
            concentrations[i] -= concentrations[i] *timeStep*flowRate/V;//outflow
        }
        return controlledVariable;
    }




}
