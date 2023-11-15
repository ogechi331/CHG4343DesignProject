public abstract class Controller {
    protected double processVariable;
    protected double setPoint;
    protected double output;

    protected double P;
    protected double I;
    protected double D;

    protected double pastI;
    protected double pastD;
    /** Calculates the controller output for a given object
     *
     * @param object Object that implements Controllable
     * @return value of the controlled variable
     * @author Ogechi
     */
    public abstract double compute(Controllable object);
}
