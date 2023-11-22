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
    private static double rk45Step(double x, double[] y, double h, DifferentialEquation[] equation) {
        int n = y.length;
        k1 = new double[n];
        k2 = new double[n];
        k3 = new double[n];
        k4 = new double[n];
        k5 = new double[n];
        k6 = new double[n];

        // Update k1, k2, k3, and k4
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
        // Update the state vector
        double[][] result = new double[3][n];
        double min = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            result[0][i] = y[i] + (25*k1[i]/216 + 1408* k3[i]/2565 + 2197*k4[i]/4101 - k5[i]/5) * h;
            result[1][i] = y[i] + (16*k1[i]/135 + 6656* k3[i]/12825 + 28561*k4[i]/56430 - 9*k5[i]/50 + 2*k6[i]/55) * h;
            result[2][i] = Math.pow(0.84*Math.abs(result[1][i]- result[0][i]),0.25);
            min = Math.min(result[2][i], min);
        }
        if (min <= 0) return h;
        return h*min;
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
    private static double determineStepSize(double t0, double[] y0, double endTime, DifferentialEquation[] equations){
        return rk45Step(t0,y0,(endTime-t0)/10,equations);
    }

    // Solve the system of differential equations
    public static double[] solve(double t0, double[] y0, double endTime, DifferentialEquation[] equations, double tolerance) {
        double h = determineStepSize(t0, y0,endTime,equations);
        int numEquations = equations.length;
        int numSteps = (int) ((endTime - t0) / h) + 1;

        double[][] solution = new double[numSteps][numEquations];
        double[] currentY = new double[y0.length];

        for(int i = 0; i < y0.length; i++){
            currentY[i] = y0[i];
        }
        double currentTime = t0;

        for (int i = 0; i < numSteps; i++) {
            currentY = rk4(currentTime, currentY, h, equations);
            solution[i] = currentY;
            currentTime += h;
        }

        return solution[solution.length -1];
    }

    /*public static void test(String[] args) {
        // Example usage:
        // Define a system of differential equations for a CSTR using lambda expression
        double tolerance = 0.01;
        DifferentialEquation[] equations = {
                (x, y) -> 0.05 * (0.2 - y) - 0.2*y,          // dCA/dt = v0* (CA0 - CA)/V - kCA
                (x, y) -> 0.05 *(y) + 0.2*y        // dCB/dt not working but pretty sure the mass balance is wrong
        };

        // Initial concentrations
        double t0 = 0.0;
        double[] y0 = {0.2,0};  // Initial concentrations of A and B

        // Time step and end time
        double endTime = 50;

        // Solve the system
        double[][] solution = solve(t0, y0, endTime, equations, tolerance);

        // Print the results
        for (double[] step : solution) {
            for (double value : step) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }*/
}
