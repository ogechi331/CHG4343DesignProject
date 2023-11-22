public class RK45 {

    // NOTE: these instance variables should not have getters and setters as they are helper variables, and could mess with calculations
    private static double[] k1;
    private static double[] k2;
    private static double[] k3;
    private static double[] k4;
    private static double[] k5;
    private static double[] k6;

    /**
     * Determines the y values at the next step for the given x value.
     *
     * @param x independent variable of the differential equation
     * @param y array of dependent variables of differential equation
     * @param h height of integration step
     * @param equation Array of differential equations to be solved
     * @return Array of y at next step value for each differential equation
     * @author Ogechi
     */
    private static double[] rk4(double x, double[] y, double h, DifferentialEquation[] equation) {
        int n = y.length;
        k1 = new double[n];
        k2 = new double[n];
        k3 = new double[n];
        k4 = new double[n];

        // Update k1, k2, k3, and k4
        for (int i = 0; i < n; i++) {
            k1[i] = equation[i].apply(x, y[i]);
        }

        double[] temp = addArrays(y, multiplyArray(k1, h / 2.0));
        for (int i = 0; i < n; i++) {
            k2[i] = equation[i].apply(x + h / 2.0, temp[i]);
        }

        temp = addArrays(y, multiplyArray(k2, h / 2.0));
        for (int i = 0; i < n; i++) {
            k3[i] = equation[i].apply(x + h / 2.0, temp[i]);
        }

        temp = addArrays(y, multiplyArray(k3, h));
        for (int i = 0; i < n; i++) {
            k4[i] = equation[i].apply(x + h, temp[i]);
        }

        // Update the state vector
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = y[i] + (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]) * h / 6.0;
        }

        return result;
    }

    /**
     * Applies the Runge-Kutta-Fehlberg method for solving ordinary differential equations
     * with adaptive step size control.
     *
     * @param x             Current time.
     * @param y             Array of initial values for dependent variables.
     * @param h             Initial step size.
     * @param equation      Array of differential equations defining the system.
     * @param tolerance     Tolerance for adaptive step size control.
     * @param endTime       End time for the simulation.
     * @return              A linked list containing the final values of dependent variables, the next step size, and the step size used for the current calculation.
     * @author              Ogechi
     */
    private static SinglyLinkedList<Object> rk45Step(double x, double[] y, double h, DifferentialEquation[] equation, double tolerance, double endTime) {

        int n = y.length; //initial value length storage integer

        k1 = new double[n];
        k2 = new double[n];
        k3 = new double[n];
        k4 = new double[n];
        k5 = new double[n];
        k6 = new double[n];
        SinglyLinkedList<Object> list= new SinglyLinkedList<>();

        while(true){
            int minIndex = 0;
            h = Math.min(h, endTime - x);

            for (int i = 0; i < n; i++) {
                k1[i] = equation[i].apply(x, y[i]);
            }

            double[] temp = addArrays(y, multiplyArray(k1, h / 4));
            for (int i = 0; i < n; i++) {
                k2[i] = equation[i].apply(x + h / 4, temp[i]);
            }

            temp = addArrays(addArrays(y, multiplyArray(k1, 3*h / 32)),multiplyArray(k2,9 *h /32));
            for (int i = 0; i < n; i++) {
                k3[i] = equation[i].apply(x + 3*h / 8, temp[i]);
            }

            temp = addArrays(addArrays(addArrays(y, multiplyArray(k1, 1932*h/2197)),multiplyArray(k2,-7200*h/2197)),multiplyArray(k3,7296*h/2197));
            for (int i = 0; i < n; i++) {
                k4[i] = equation[i].apply(x + 12*h/13, temp[i]);
            }

            temp = addArrays(addArrays(addArrays(addArrays(y, multiplyArray(k1, 439*h/216)),multiplyArray(k2,-8*h)),multiplyArray(k3,3680*h/513)),multiplyArray(k4,-845*h/4104));
            for (int i = 0; i < n; i++) {
                k5[i] = equation[i].apply(x +h, temp[i]);
            }

            temp = addArrays(addArrays(addArrays(addArrays(addArrays(y, multiplyArray(k1, -8*h/27)),multiplyArray(k2,2*h)),multiplyArray(k3,-3544*h/2565)),multiplyArray(k4,1859*h/4104)),multiplyArray(k5,-11*h/40));
            for (int i = 0; i < n; i++) {
                k6[i] = equation[i].apply(x +h/2, temp[i]);
            }
            double[][] result = new double[4][n];
            double min = Double.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                result[0][i] = y[i] + (25*k1[i]/216 + 1408* k3[i]/2565 + 2197*k4[i]/4104 - k5[i]/5) * h;
                result[1][i] = y[i] + (16*k1[i]/135 + 6656* k3[i]/12825 + 28561*k4[i]/56430 - 9*k5[i]/50 + 2*k6[i]/55) * h;
                result[2][i] = Math.abs(result[1][i]- result[0][i])/h;
                result[3][i] = 0.84*Math.pow(tolerance/result[2][i],0.25);
                if(result[2][i] < min) {
                    min = result[2][i];
                    minIndex = i;
                }
            }
            if (result[2][minIndex] <= tolerance){
                //use a linked list instead of array to use less memory and quicker computation -> alternative was double[][] array with many unused spaces
                list.add(result[0]); //array of next values
                list.add(h * result[3][minIndex]); //next h
                list.add(h); //h used for this step
                break;
            }
            h = h * result[3][minIndex];
        }
        // Update k1, k2, k3, and k4
        return list;

    }

    // Helper method to add two arrays element-wise
    private static double[] addArrays(double[] a, double[] b) {
        int n = a.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = a[i] + b[i];
        }
        return result;
    }

    // Helper method to multiply an array by a scalar
    private static double[] multiplyArray(double[] a, double scalar) {
        int n = a.length;
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = a[i] * scalar;
        }
        return result;
    }

    /**Solves a system of ordinary differential equations using the Runge-Kutta-Fehlberg method.
     *
     * @param t0 Initial Time
     * @param y0 Initial values of the dependent variables
     * @param endTime End time for the simulation
     * @param equations Array of differential equaitons defining the system
     * @param tolerance Tolerance for the adaptive step size control
     * @return Array containing the final values of the dependent variables after simulating the system until the specified end time
     * @author Ogechi
     */
    public static double[] solve(double t0, double[] y0, double endTime, DifferentialEquation[] equations, double tolerance) {
        double h = (endTime-t0)/2; //initial guess of h

        double[] currentY = new double[y0.length];

        System.arraycopy(y0, 0, currentY, 0, y0.length);
        double currentTime = t0;
        SinglyLinkedList<Object> temp;

        while (currentTime < endTime) {
            temp = rk45Step(currentTime, currentY, h, equations, tolerance, endTime);
            currentY = (double[]) temp.getFirst().getElement();
            h = (double) temp.getAt(1).getElement();
            currentTime += (double) temp.getLast().getElement();
        }

        return currentY;
    }

    /**Main method used to test the validity of the RK45 solution
     * @param args
     */
    public static void main(String[] args) {
        //main to test rk45. Known solution in 8 steps with output value of 5.305486816572746
        //Test Example reference: https://math.okstate.edu/people/yqwang/teaching/math4513_fall11/Notes/rungekutta.pdf
        double tolerance = 0.00001;
        DifferentialEquation[] equations = {(t, y) -> y - Math.pow(t, 2) + 1};
        // Initial concentrations
        double t0 = 0.0;
        double[] y0 = {0.5};
        double endTime = 2;
        System.out.println(solve(t0, y0, endTime, equations, tolerance)[0]);

    }
}
