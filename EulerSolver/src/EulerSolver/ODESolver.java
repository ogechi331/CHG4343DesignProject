package EulerSolver;

public class ODESolver
{		
	public static double[] integrate(double xStart, double yStart, Function dydx, double[] xReport, double h, int maxIterations, ODEStepper stepper)
	{
		
		
		int numReport=xReport.length;
		double[] yReport=new double[numReport];
		int nextReport=0;
		int numSteps=0;
		int iter=0;
		double y=yStart;
		double x=xStart;
		double hReturn=h;
		
		//check to see if initial conditions to be stored in yReport
		if(xReport[0]==xStart)
		{
			yReport[0]=yStart;
			nextReport++;
		}	
		
		//begin stepping through domain
		while((nextReport<numReport) && (iter<maxIterations))
		{
			if((x+h)>xReport[nextReport])
			{  
				h=xReport[nextReport]-x;
			}
			y=stepper.step(h,x,y,dydx);
			if((x+h)==xReport[nextReport]) //need to save y and reset h...
			{
				yReport[nextReport]=y;
				h=hReturn;
				nextReport++;
			}
			x=x+h;		
			iter++;
		}
		if(iter>=maxIterations) System.out.println("Maximum number of iterations performed before completing solution.");
		return yReport;
	}
}