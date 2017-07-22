import java.util.Random;

import static java.lang.Math.abs;

public class SimulatedAnnealing {


	public static final double MIN_COORDINATE = -2;
	public static final double MAX_COORDINATE = 2;
	public static final double MIN_TEMPERATURE = 1;
	public static final double START_TEMPERATURE = 100;
	public static final double COOLING_RATE = 0.02;

	private Random randomGenerator;
	private double currentCoordinateX;
	private double nextCoordinateX;
	private double bestCoordinateX;
	
	public SimulatedAnnealing(){
		this.randomGenerator = new Random();
	}
	
	public void findOptimum(){
		
		double temperature = START_TEMPERATURE;
		
		while( temperature > MIN_TEMPERATURE ){
			
			nextCoordinateX = getRandomX();
			
			double currentEnergy = getEnergy(currentCoordinateX);
			double newEnergy = getEnergy(nextCoordinateX);
			
			
			if( acceptanceProbability(currentEnergy, newEnergy, temperature) > Math.random() ){
				currentCoordinateX = nextCoordinateX;
			}
			
			if( f(currentCoordinateX) < f(bestCoordinateX)){
				bestCoordinateX = currentCoordinateX;
			}
			
			temperature *= 1 - COOLING_RATE;
		}
		
		System.out.println("Global extremum is: x="+bestCoordinateX +  "f(x) = " + f(bestCoordinateX));
		
	}

	private double getEnergy(double x) {
		return f(x);
	}

	private double getRandomX() {
		return randomGenerator.nextDouble()*(MAX_COORDINATE - MIN_COORDINATE) + MIN_COORDINATE;
	}

	private double f(double x1, double y1,double x2, double y2){
		return abs(x1-y1)+abs(y1-y2);
	}
	
	public double acceptanceProbability(double energy, double newEnergy, double temperature) {
		
		// If the new solution is better, accept it
		if (newEnergy < energy) {
			return 1.0;
		}
		
		// If the new solution is worse, calculate an acceptance probability
		// T is small: we accept worse solutions with lower probability !!!
		return Math.exp((energy - newEnergy) / temperature);
	}
}
