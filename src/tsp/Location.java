package tsp;

import java.util.ArrayList;

/**
 * Location containing an x and y and a list of routes departing from it
 * @author robin
 *
 */
public class Location {
	
	private int x, y;
	private ArrayList<Route> routes = new ArrayList<Route>();
	
	public Location(int X, int Y){
		x = X;
		y = Y;
	}
	
	public String toString(){
		return "["+x+", "+y+"]";
	}
	
	public int getx(){
		return x;
	}
	
	public int gety(){
		return y;
	}
	
	public void addRoute(Route r){
		routes.add(r);
	}
	
	public int numberOfRoutes(){
		return routes.size();
	}
	
	public boolean containsRouteTo(Location l){
		for(Route i : routes){
			if (i.containsLocation(l)){
				return true;
			}
		}
		return false;
	}
	
	public Route getRouteTo(Location l){
		if(containsRouteTo(l)){
			for(Route r : routes){
				if(r.getOtherLocation(this).equals(l))
					return r;
			}
		}
		
		System.out.println("Location.getRouteTo error");
		return null;
	}
	
	public ArrayList<Route> getRoutes(){
		return routes;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Location))
			return false;
		Location other = (Location) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
}
