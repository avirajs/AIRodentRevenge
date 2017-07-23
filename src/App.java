public class App {

	public static void main(String[] args) {
		
		SimulatedAnnealing annealing = new SimulatedAnnealing();
		for(int i=0;i<10;i++)
		annealing.findNextMove();
		
	}
}
