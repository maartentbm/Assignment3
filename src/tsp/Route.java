package tsp;

/**
 * A 2 way route containing 2 locations and the routes between them
 * @author robin
 *
 */
public class Route {
	private Location[] locations = new Location[2];
	double length, pheromone;
	String forwardRoute, backwardRoute;
	//boolean explored;
	
	public Route(Location l1, Location l2, double L, double P){
			locations[0] = l1;
			locations[1] = l2;
			length = L;
			pheromone = P;
			//explored = false;
			forwardRoute = "null";
			backwardRoute = "reverseNull";
	}
	
	public boolean containsLocation(Location l){
		if (l.equals(locations[0]) || l.equals(locations[1])){
			return true;
		}
		return false;
	}
	
	public Location getOtherLocation(Location l){
		if (locations[0].equals(l)){
			return locations[1];
		}
		
		if (locations[1].equals(l)){
			return locations[0];
		}
		
		System.out.println("Route.getOtherLocation error");
		return locations[0];
	}
	
	public String toString(){
		return "{"+locations[0].toString()+" to "+locations[1].toString()+"}";
	}
	
	public Location getLocation1(){
		return locations[0];
	}
	
	public Location getLocation2(){
		return locations[1];
	}
	
	public void setRoute(Location l,double distance, String path){
		if(l.equals(locations[0])){
			forwardRoute = path;
			backwardRoute = DistanceParser.stringInvertor(path);
			length = distance;
		}
		if(l.equals(locations[1])){
			backwardRoute = path;
			forwardRoute = DistanceParser.stringInvertor(path);
			length = distance;
		}
		System.out.println("Route.setRoute error");
	}
	
	public String getRouteFrom(Location l){
		if (locations[0].equals(l)){
			return forwardRoute;
		}
		
		if (locations[1].equals(l)){
			return backwardRoute;
		}
		
		System.out.println("Route.getRouteFrom error");
		return "error";
	}

}
