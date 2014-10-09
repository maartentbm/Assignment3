package tsp;

public class Route {
	Location[] locations = new Location[2];
	double length, pheromone;
	
	public Route(Location l1, Location l2, double L, double P){
			locations[0] = l1;
			locations[1] = l2;
			length = L;
			pheromone = P;
	}
	
	public boolean containsLocation(Location l){
		if (l.equals(locations[0]) || l.equals(locations[1])){
			return true;
		}
		return false;
	}
	
	public String toString(){
		return "{"+locations[0].toString()+" to "+locations[1].toString()+"}";
	}

}
