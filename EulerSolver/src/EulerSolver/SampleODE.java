package EulerSolver;

public class SampleODE implements Function{
    private double a;
    private double b;

    public SampleODE(double a, double b)
    {
        this.a=a;
        this.b=b;
    }

    public SampleODE(SampleODE source)
    {
        this.a=source.a;
        this.b=source.b;
    }

    public SampleODE clone()
    {
        return new SampleODE(this);
    }

    public double getA()
    {
        return this.a;
    }
    public void setA(double a)
    {
        this.a=a;
    }

    public double getB()
    {
        return this.b;
    }

    public void setB(double b)
    {
        this.b=b;
    }

    public boolean equals(Object comparator)
    {
        if(comparator==null) return false;
        if(comparator.getClass()!=this.getClass()) return false;
        if((this.a!=((SampleODE)comparator).a)||(this.b!=((SampleODE)comparator).b)) return false;
        return true;
    }

    public double[] integrate(double y_start, double[] timeReports, double delta_t, int maxSteps)
    {
        return ODESolver.integrate(0, y_start, this, timeReports, delta_t, maxSteps, new Euler());
    }
    public double evaluate(double t, double y)
    {
        return this.a+this.b*t;
    }

}
