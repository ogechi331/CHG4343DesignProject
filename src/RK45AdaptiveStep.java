/** RK45 adaptive step size class
 * @author Dylan
 * @version 1.0
 */
public class RK45AdaptiveStep {

    /** Static method to calculate results with RK45
     *
     * @param t takes time value
     * @param y takes array of y values for each time value
     * @param timeStep takes time step to use initially
     * @param equation takes array of differential equations to solve
     * @param tolerance takes tolerance required between RK4 and RK5
     * @return Array of RK5 estimations for [0] to [n] and actual step size used at [n+1]
     * @throws IllegalArgumentException if equation or element of equation is null
     * @author Dylan
     */
    public static double[] solve (double t, double[] y, double timeStep, DifferentialEquation[] equation, double tolerance) {

        if(equation == null){
            throw new IllegalArgumentException("Differential Equation array cannot be null");
        }

        for (int i = 0; i<equation.length; i++) {
            if (equation[i] == null) {
                throw new IllegalArgumentException("Differential Equation cannot be null");
            }
        }

        int n = y.length;
        double wholeTimeStep = timeStep;
        double answerRK4[] = new double[n];
        double answerRK5[] = new double[n];
        double[] answer = new double[n+1];
        double[] K1 = new double[n];
        double[] K2 = new double[n];
        double[] K3 = new double[n];
        double[] K4 = new double[n];
        double[] K5 = new double[n];
        double[] K6 = new double[n];
        double[] valueForK2 = new double[n];
        double[] valueForK3 = new double[n];
        double[] valueForK4 = new double[n];
        double[] valueForK5 = new double[n];
        double[] valueForK6 = new double[n];
        boolean valid = false;


        do {
            for (int i = 0; i<n; i++) {
                K1[i] = wholeTimeStep * equation[i].apply(wholeTimeStep, y, timeStep, i);
                valueForK2[i] = y[i] + (K1[i] / 4);
            }
            for (int i = 0; i<n; i++) {
                K2[i] = wholeTimeStep * equation[i].apply(wholeTimeStep, valueForK2, timeStep, i);
                valueForK3[i] = y[i] + (3 * K1[i] / 32) + (9 * K2[i] / 32);
            }
            for (int i = 0; i<n; i++) {
                K3[i] = wholeTimeStep * equation[i].apply(wholeTimeStep, valueForK3, timeStep, i);
                valueForK4[i] = y[i] + (1932 * K1[i] / 2197) - (7200 * K2[i] / 2197) + (7296 * K3[i] / 2197);
            }
            for (int i = 0; i<n; i++) {
                K4[i] = wholeTimeStep * equation[i].apply(wholeTimeStep, valueForK4, timeStep, i);
                valueForK5[i] = y[i] + (439 * K1[i] / 216) - (8 * K2[i]) + (3680 * K3[i] / 513) - (845 * K4[i] / 4104);
            }
            for (int i = 0; i<n; i++) {
                K5[i] = wholeTimeStep * equation[i].apply(wholeTimeStep, valueForK5, timeStep, i);
                valueForK6[i] = y[i] - (8 * K1[i] / 27) + (2 * K2[i]) - (3544 * K3[i] / 2565) + 1859 * K4[i] / 4104 - (11 * K5[i] / 40);
            }
            for (int i = 0; i<n; i++) {
                K6[i] = wholeTimeStep * equation[i].apply(wholeTimeStep, valueForK6, timeStep, i);
            }
            for (int i = 0; i<n; i++) {
                answerRK4[i] = y[i] + (25 * K1[i] / 216) + (1408 * K3[i] / 2565) + (2197 * K4[i] / 4101) - (K5[i] / 5);
                answerRK5[i] = y[i] + (16 * K1[i] / 135) + (6656 * K3[i] / 12825) + (28561 * K4[i] / 56430) - (9 * K5[i] / 50) + (2 * K6[i] / 55);
            }
            for (int i = 0; i<n; i++) {
                if (Math.abs(answerRK5[i] - answerRK4[i]) < tolerance) {
                    valid = true;
                } else if (Math.abs(answerRK5[i] - answerRK4[i]) > tolerance) {
                    valid = false;
                    wholeTimeStep = wholeTimeStep / 2;
                }
            }

        } while (valid==false);
        for (int i = 0; i<(n+1); i++) {
            answer[i] = answerRK5[i];//function estimation with RK5
            answer[n] = wholeTimeStep; //step size used for calculation
        }
        return answer;

    }
}
