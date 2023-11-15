public interface Controllable {

    /**Returns true if the Controllable object is set to be controlled and false otherwise
     *
     * @return boolean state of Controlled object
     * @author Ogechi
     */
    boolean isControlled();

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

    /**Obtains the Controller of the Controllable object
     * @return PIDController
     * @author Ogechi
     */
    PIDController getController();

    /**Updates the value of the Manipulated Variable in the Controllable object
     * @param var value of the updated Manipulated Variable
     *@author Ogechi
     */
    void setManipulatedVariable(double var);

    /**Obtains the output of the Controllable object after a time step.
     *
     * WARNING: This method is responsible for updating the current state of the Controllable object.
     * @param t0
     * @param t
     * @return Array of current state output values
     * @author Ogechi
     */
    double[] getSystemOutput(double t0, double t);
    //void tunePIDParameters(); -> bonus
}
