import java.util.Random;

import static java.lang.Math.abs;

public class SimulatedAnnealing {


	public static final double MIN_COORDINATE = -2;
	public static final double MAX_COORDINATE = 2;
	public static final double MIN_TEMPERATURE = 1;
	public static final double START_TEMPERATURE = 100;
	public static final double COOLING_RATE = 0.02;

    private double targetX = 1;
    private double targetY = 1;
    private double currX = 10;
    private double currY = 10;

    private Random randomGenerator;
	private int nextCoordinateX;
    //private double nextCoordinateY;
	private double bestCoordinateX;
    private double bestCoordinateY;
	
	public SimulatedAnnealing(){
		this.randomGenerator = new Random();
	}

	public int[] findNextMove(){
		
		double temperature = START_TEMPERATURE;
		

			
			nextCoordinateX = getRandomX();
            double[] posx = {currX + 1, currX, currX, currX - 1, currX + 1, currX - 1, currX - 1, currX + 1};
            double[] posy = {currY, currY + 1, currY - 1, currY, currY + 1, currY - 1, currY + 1, currY - 1};

			double currentEnergy = getEnergy(currX,currY,targetX,targetY);

			double newEnergy = getEnergy(posx[nextCoordinateX],posy[nextCoordinateX],targetX,targetY);
			
			
			if( acceptanceProbability(currentEnergy, newEnergy, temperature) > Math.random() ){
				currX = posx[nextCoordinateX];
                currY = posy[nextCoordinateX];
			}
			
			if( distance(currX,currY,targetX,targetY) < distance(bestCoordinateX,bestCoordinateY,targetX,targetY)){
				bestCoordinateX = currX;
                bestCoordinateY = currY;
			}
			
			temperature *= 1 - COOLING_RATE;

		
		 System.out.println("Global extremum is: x="+bestCoordinateX +  ", " + bestCoordinateY);
		int temp[]={(int)bestCoordinateX,(int)bestCoordinateY};
		return temp;
	}

	private double getEnergy(double x1, double y1,double x2, double y2)
    {
		return distance( x1,  y1, x2,  y2);
	}

	private int getRandomX() {
        int random = (int )(Math.random() * 8 + 0);
        return random;
	}

	private double distance(double x1, double y1,double x2, double y2){
		return abs(x1-x2)+abs(y1-y2);
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
