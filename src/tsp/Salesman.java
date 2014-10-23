package tsp;

import java.util.ArrayList;

public class Salesman {
	private ArrayList<Location> visitedLocations = new ArrayList<Location>();
	private ArrayList<Route> routesTaken = new ArrayList<Route>();
	Location currentLocation;
	double distance;
	
	public Salesman(Location begin, Location end){
		currentLocation = begin;
		
		//Exculding begin and end from finding algorithm
		visitedLocations.add(begin);
		visitedLocations.add(end);
		
		distance = 0;
	}
	
	public Salesman(Salesman toClone){
		currentLocation = toClone.currentLocation;
		visitedLocations = new ArrayList<Location>(toClone.getExclude());
		routesTaken = new ArrayList<Route>(toClone.getRoute());
		distance = toClone.distance;
	}
	
	public void addExclude(Location l){
		visitedLocations.add(l);
	}
	
	public ArrayList<Location>  getExclude(){
		return visitedLocations;
	}
	
	public ArrayList<Route> getRoute(){
		return routesTaken;
	}
	
	public void takeRoute(Route r){
		routesTaken.add(r);
		visitedLocations.add(r.getOtherLocation(currentLocation));
		currentLocation = r.getOtherLocation(currentLocation);
		distance = distance+r.length;
	}
	
	public String toString(){
		String temp;
		temp ="[Salesman< distance: " +Math.round(distance) +">]";
		//temp += " order: " + routesTaken.toString() +"]";
		//temp += "\nLocations Vis: " +visitedLocations.size() +" Routes tak: " +routesTaken.size() +" Current Loc: " +currentLocation.toString();
		return temp;
	}
	


}
