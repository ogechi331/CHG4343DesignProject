/** Interface for controllable classes
 * @author Ogechi
 */
public interface Controllable {

    /**
     * Returns the current value of the variable set to be manipulated by the PIDController.
     *
     * @return the current value of the manipulated variable.
     */
    double getManipulatedVar();
    /**Returns the value of a Controllable object's controlled variable
     * @return Controlled variable as double
     * @author Ogechi
     */
    double getControlledVar();

    /**Returns the Initial Values at the current state of the Controllable Object.
     *
     * @return Array of Controllable's Initial Values
     * @author Ogechi
     */
    double[] getInitialValues();

    /***Returns true if the Controllable object is set to be controlled and false otherwise
     *
     * @return boolean state of Controlled object
     * @author Ogechi
     */
    boolean getIsControlled();

    /**Updates the value of the Manipulated Variable in the Controllable object
     * @param var value of the updated Manipulated Variable
     *@author Ogechi
     */
    void setManipulatedVariable(double var);

    /**Returns the value of the variable eligible to be disturbed in the Controllable object
     * @return value of disturbed variable
     * @author Ogechi
     */
    double getDisturbedVar();

    /**Obtains the output of the Controllable object after a time step.
     * WARNING: This method is responsible for updating the current state of the Controllable object.
     * @param t time
     * @param tolerance tolerance for error between RK4 and RK5 method
     * @return Array of current state output values
     * @author Ogechi
     * @author Dylan
     */
    double[] getSystemOutput(double t, double timeStep, double tolerance);


    /**Applies the disturbance to the controlled parameter of the Controllable object
     *
     * @param dist value of applied disturbance
     * @author Ogechi
     */
    void simulateDisturbance(double dist);

    /**
     * ...
     * @return A new instance of the {@code Controllable} object, identical
     *         to the original in terms of state and behavior.
     * @throws CloneNotSupportedException if the object's class does not
     *         support the {@code Cloneable} interface. Subclasses that
     *         override this method should also override this exception.
     * @see Cloneable
     */
    Controllable clone() throws CloneNotSupportedException;
}
