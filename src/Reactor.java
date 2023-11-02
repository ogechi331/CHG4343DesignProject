import java.util.ArrayList;
import java.util.Collections;
public abstract class Reactor {
    double setPoint;
    double controlledVariable;
    double commandedVariable;
    double[] inFlowConcentrations;
    double[] concentrations;
    Reaction reaction;
    ArrayList<Species> species = new ArrayList<Species>();//not given
    double timeStep;

    public Reactor(double setPoint, double controlledVariable, double commandedVariable, double[] inFlowConcentrations,
                   double[] concentrations, Reaction reaction, double timeStep) {
        this.setPoint = setPoint;
        this.commandedVariable = commandedVariable;
        if(reaction==null){
            System.out.println("a list of species is needed to initialize the reactor");
            System.out(0);
        }
        this.reaction = reaction.clone();
        Collections.addAll(species,reaction.getReactants());
        Collections.addAll(species,reaction.getProducts());

        if (inFlowConcentrations==null) {
            this.inFlowConcentrations=new double[species.length];//says that it should default to 0?
        }
        this.inFlowConcentrations = inFlowConcentrations.clone();
        if (concentrations==null){
            this.concentrations=new double[species.length];//says that it should default to 0?
        }
        this.reaction = reaction;
        this.timeStep = timeStep;
    }
    public boolean equals(Object comparator){
        if comparator==null return false;
        else return (this.getClass()==comparator.getClass());
    }

    public void updateDisturbanceVariable(double disturbanceVariable){
    }

    public double simulateStep(double commandedVariable){
        return controlledVariable;
    }
}
