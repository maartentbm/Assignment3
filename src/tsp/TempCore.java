package tsp;


/**
 * Testclasse functinalitijd Traveling Salesman problem
 * @author robin
 *
 */
public class TempCore {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		//New map reads[TSPproducts.txt for locations, hardCoord.txt for maze start and end]
		//Then makes routes between them
		Map map = new Map("res/hardCoord.txt","res/TSPproducts.txt","res/TSP_Paths_FINAL.txt");
		
		
		//Run random salesman for coverage
		for(int i=0; i<50; i++){
			//Initializes 100 salesman and clears old list gives salesman mazeStart and mazeEnd
			map.initSwarm(100);
			//Runs all salesman randomly trough the map (Not passing to citys more then once)
			map.swarmRunRandom();
			//Lets all routes forget pheromone by 1-"forgettingFactor"
			map.routesForget();
			//Applys pheromone from salesman to routes
			map.applyPheromone();		
			
			System.out.println("Random epoch "+i+" Fitness: " +map.averageDistance());
		}
		System.out.println("\nSwarm info after random");
		map.printSwarmInfo();
		System.out.println("\nRoute info after random");
		map.printRouteInfo();
		
		System.out.println("\n-----------------------------------------------");
		System.out.println("---------------------Main---------------------/n");
		
		//Loop for chance based search agorithm
		for(int i=0; i<5000; i++){
			//Initializes 100 salesman and clears old list gives salesman mazeStart and mazeEnd
			map.initSwarm(200);
			//Runs all salesman chance based(by formula from lecture) trough the map (Not passing to citys more then once)			
			map.swarmRunChance();
			//Lets all routes forget pheromone by 1-"forgettingFactor"
			map.routesForget();
			//Applys pheromone from salesman to routes
			map.applyPheromone();	
			//Applys pheromone of best found route(all epochs) 5 times "elitism"
			map.applyPheromoneBestRoute(10);
			
			System.out.println("epoch "+i+" Fitness: " +map.averageDistance());
		}
		System.out.println("\nSwarm info");
		map.printSwarmInfo();
		System.out.println("\nRoute info");
		map.printRouteInfo();
		System.out.println("\nFinal info");
		map.printEndRoute();
		
		//Writes best and averige per eppoch to file
		map.writeGraph("res/RememberTest");
		map.writeRouteFile("res/29_actions_TSP.txt");
		
	}
	
	

}
