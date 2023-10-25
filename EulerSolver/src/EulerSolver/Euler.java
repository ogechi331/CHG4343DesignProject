package EulerSolver;

public class Euler extends ODEStepper
{ 
	public double step(double hStart, double xStart, double yStart, Function dydx)
	{
		return dydx.evaluate(xStart,yStart)*hStart+yStart;
		
	}
}
		
	