package tsp;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * map that contains locations and salesman
 * @author robin
 *
 */
public class Map {
	Location mazeBegin;
	Location mazeEnd;
	Random RNG;
	double forgettingFactor;
	int pheromoneFactor;
	ArrayList<Location> locations;
	ArrayList<Route> routes;
	ArrayList<Salesman> swarm = new ArrayList<Salesman>();
	ArrayList<Double> epochAverage = new ArrayList<Double>();
	ArrayList<Double> epochBest = new ArrayList<Double>();
	Salesman bestSalesman;
	double bestDistance;
	
	
	public Map(String Coord, String TSPproducts, String TSPpaths) {
		forgettingFactor = 0.1; 
		pheromoneFactor = 10000;
		RNG = new Random();
		
		locations = ProductsParser.read(new File(Coord), new File(TSPproducts));
		mazeBegin = locations.get(0);
		mazeEnd = locations.get(1);
		bestDistance = -1;
		
		routes = RoutesParser.read(locations);
		DistanceParser.read(new File(TSPpaths), locations);
		
		
	
	}
	/**
	 * Returns list of routes from location from excluding ones that go to locations contained in exclude
	 * @param from
	 * @param excluded
	 * @return ArrayList<Route>
	 */
	public ArrayList<Route> getRoutesFrom(Location from, ArrayList<Location> excluded){
		ArrayList<Route> ret = new ArrayList<Route>();
		
		for(Route i : from.getRoutes()){
			if ((!excluded.contains(i.getLocation1()) || i.getLocation1().equals(from)) 
					&& (!excluded.contains(i.getLocation2()) || i.getLocation2().equals(from))){
				ret.add(i);
			}
			
		}
		
		return ret;
	}
	
	public Route getRouteFromTo(Location from, Location to){
		for(Route i : from.getRoutes()){
			if (i.containsLocation(to)){ 
					return i;
			}
			
		}
		
		System.out.println("Map.getRouteFromTo error");
		return null;
	}
	
	/**
	 * Applys forgetting factor to all routes
	 */
	public void routesForget(){
		
		for(Route i : routes){
			i.pheromone = i.pheromone*(1-forgettingFactor);
		}
	}
	
	/**
	 * Clears previous swarm and builds new swarm of salesman
	 * @param size
	 */
	public void initSwarm(int size){
		swarm.clear();
		
		for(int i=0; i<size; i++){
			swarm.add(new Salesman(mazeBegin, mazeEnd));
		}
		
	}
	
	/**
	 * picks random route from list
	 * @param routes
	 * @return Route
	 */
	public Route randomRoute(ArrayList<Route> routes){
		return routes.get(RNG.nextInt(routes.size()));
	}
	
	/**
	 * pick route from list roullet style
	 * @param routes
	 * @return Route
	 */
	public Route chanceRoute(ArrayList<Route> routes){
		
		double[] dart = getDartList(routes);
		double random = RNG.nextDouble();
		
		for(int i=0; i<routes.size(); i++){
			if(random <= dart[i]){
				return routes.get(i);
			}
		}
		
		System.out.println("Map.chanceRoute error");
		return null;
	}
	
	/**
	 * Returns chance dependent list
	 * @param routes
	 * @return
	 */
	public double[] getDartList(ArrayList<Route> routes){
		double[] ret = new double[routes.size()];
		double total=0;
		
		//Calculate fitness
		for(int i=0; i<routes.size(); i++){
			ret[i] = routes.get(i).pheromone *Math.sqrt(1/routes.get(i).length);
			total += ret[i];
		}
		
		//Calculate chance
		for(int i=0; i<routes.size(); i++){
			ret[i] /= total;
		}
		
		//Convert to checking list
		for(int i=1; i<routes.size(); i++){
			ret[i] += ret[i-1];
		}
		
		
		return ret;
	}
	
	/**
	 * Moves salesman trough routes randomly
	 */
	public void swarmRunRandom(){
		//Main loop
		for(int i=0; i<locations.size()-2; i++){
			for(Salesman s : swarm){
				s.takeRoute(randomRoute( getRoutesFrom(s.currentLocation, s.getExclude())));
			}
		}
		
		//To mazeEnd
		for(Salesman s : swarm){
			s.takeRoute(getRouteFromTo(s.currentLocation, mazeEnd));
		}
		
	}
	
	/**
	 * Move salesman trough swarm roullet style
	 */
	public void swarmRunChance(){
		//Main loop
		for(int i=0; i<locations.size()-2; i++){
			for(Salesman s : swarm){
				s.takeRoute(chanceRoute( getRoutesFrom(s.currentLocation, s.getExclude())));
			}
		}
		
		//To mazeEnd
		for(Salesman s : swarm){
			s.takeRoute(getRouteFromTo(s.currentLocation, mazeEnd));
		}
		
		trackPreformance();
	}
	
	/**
	 * Applys pheromone to routes for swarm
	 */
	public void applyPheromone(){
		for(Salesman s : swarm){
			for(Route r : s.getRoute()){
				r.pheromone += pheromoneFactor/ s.distance;
			}
		}
	}
	
	/**
	 * Applys pheromone to best route found so far
	 * @param factor
	 */
	public void applyPheromoneBestRoute(double factor){
		if (bestDistance != -1){
			for(Route r : bestSalesman.getRoute()){
				r.pheromone += (pheromoneFactor/ bestSalesman.distance)*factor;
			}
		}
	}
	
	/**
	 * Tracks preformance of swarm
	 */
	public void trackPreformance(){
		Salesman best = swarm.get(0);
		for(Salesman s : swarm){
			if(best.distance>s.distance){
				best = s;
			}
		}
		
		//TrackBest
		if(bestDistance != -1){
			if(bestDistance > best.distance){
				bestDistance = best.distance;
				bestSalesman = new Salesman(best);	
			}
		}else{
			bestDistance = best.distance;
			bestSalesman = new Salesman(best);	
		}
		
		this.epochAverage.add(averageDistance());
		this.epochBest.add(best.distance);
	}
	
	public double averageDistance(){
		double temp=0;
		
		for (Salesman s : swarm){
			temp += s.distance;
		}
		
		return temp/swarm.size();
	}
	
	
	public void printSwarmInfo(){
		for(Salesman s : swarm){
			System.out.println(s.toString());
		}
	}
	
	public void printRouteInfo(){
		for(Location l : locations){
			System.out.print(l.toString() +" Route pheromone scores: ");
			for (Route r : l.getRoutes()){
				System.out.print( "("+Math.round(r.pheromone)+") ");
			}
			System.out.print("\n");
		}
	}
	

	

	
	/**
	 * Functie alleen toepasselijk bij volledige convertie
	 */
	public void printEndRoute(){
		
		System.out.println("Best route had a distance of: " +bestSalesman.distance +"\nThis route was:");
		System.out.println("Start: " +mazeBegin.toString());
		Location trackLoc = mazeBegin;
		
		for(Route r :bestSalesman.getRoute()){
			System.out.println("From " +trackLoc.toString() +" to " +r.getOtherLocation(trackLoc).toString() +" with distance "+ r.length);
			trackLoc = r.getOtherLocation(trackLoc);
		}
		System.out.println("End: " +mazeEnd.toString());
	}

	
	public void writeGraph(String filename){
		try{
			FileWriter fileId = new FileWriter(new File(filename));
			PrintWriter pr = new PrintWriter(fileId);
			
			for(int i=0; i<epochAverage.size(); i++){
				pr.println(epochAverage.get(i)+","+epochBest.get(i));
			}
			
			pr.close();
			
		}catch(Exception e){
			System.out.println("Map.writeGraph error " +e);
		}
	}
	
	public void writeRouteFile(String filename){
		try{
			FileWriter fileId = new FileWriter(new File(filename));
			PrintWriter pr = new PrintWriter(fileId);
			
			pr.println(Math.round(bestDistance) +";");
			pr.println(mazeBegin.getx() +"," +mazeBegin.gety() +";");
			
			Location temp = mazeBegin;
			for(Route r : bestSalesman.getRoute()){
				pr.println(r.getRouteFrom(temp));
				temp = r.getOtherLocation(temp);
				if(locations.indexOf(temp) != 1){
					pr.println("take product #"+(locations.indexOf(temp)-1)+";");
				}
			}
			
			pr.close();
			
		}catch(Exception e){
			System.out.println("Map.writeRouteFile error " +e);
		}
		
	}
}
