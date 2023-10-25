package EulerSolver;

import java.text.*;
import java.util.*;

public class EulerExample
{
	private int numReports, maxIterations;	
	private double[] reportTimes;
	private double[] solutions;
	private double delta_t, y_Start;
	private double a,b;

	private DecimalFormat df = new DecimalFormat("0.0000");
	private DecimalFormat dfw = new DecimalFormat("0.0");
	private DecimalFormat dfx=new DecimalFormat("0.0000");
	

	public void runSystem()
	{
		    Scanner scanner = new Scanner(System.in);
		    System.out.println("Enter maxIterations");
			maxIterations=scanner.nextInt();
		    System.out.println("Enter numReports");
			numReports=scanner.nextInt();
			reportTimes=new double[numReports];
			solutions =new double[numReports];
			System.out.println("Enter "+numReports+" desired report times, one at a time");
			for(int i=0;i<numReports;i++)
				reportTimes[i]=scanner.nextDouble();
		    System.out.println("Enter time step size");
			delta_t=scanner.nextDouble();
		    System.out.println("Enter a");
			a=scanner.nextDouble();
		    System.out.println("Enter b");
			b=scanner.nextDouble();
		    y_Start=a;

			//create object that are needed for the simulation
			SampleODE ode = new SampleODE(a,b);
		    System.out.println("\nData read and variables initialized...\n");
			System.out.println("\nEulerSolver.SampleODE object created...\n");


			System.out.println("\nCalculating solution ...\n");
			solutions =ode.integrate(y_Start, reportTimes, delta_t, maxIterations);
			System.out.println("\nSolution generated...\n");
			double analytical;
			System.out.println("\nWriting solutions to screen \n");
		    System.out.println("\nTime (s)"+"     Calculated y         "+"Analytical y"+"\n");
		    for(int i=0;i<numReports;i++) {
		    	analytical=a+ a*reportTimes[i]+0.5*b*Math.pow(reportTimes[i],2.0);
				System.out.println(dfw.format(reportTimes[i]) + "         " + dfx.format(solutions[i])
						+ "                 " + dfx.format(analytical)+ "\n");
			}
	}//runSystem
	

	public static void main (String[] args)
	{
		EulerExample example = new EulerExample();
		example.runSystem();		
		
	}//main
}//class 


