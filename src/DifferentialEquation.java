/** Interface for classes with a differential equation to solve
 * @author Ogechi
 * @author Dylan
 * @version 2.0
 */
public interface DifferentialEquation {
    /** Applies the differential equation to calculate the rate of change of the dependent variable with respect to the independent variable.
     *
     * <p>The method takes the current values of the independent variable and the dependent variable, and it computes the rate of change based on the rules defined by the differential equation.
     *  This method is intended to be used in numerical integration algorithms, such as the
     *  Runge-Kutta methods, to advance the solution of the differential equation over small time steps.</p>
     *
     * @param t time
     * @param y array of ys for time t
     * @param timeStep initial timeStep to use
     * @param i which differential equation value to provide
     * @return Rate of change of the dependent variable with respect to the independent variable.
     * @see RK45AdaptiveStep
     * @author Ogechi
     * @author Dylan
     */
    double apply(double t, double[] y, double timeStep, int i);

}
