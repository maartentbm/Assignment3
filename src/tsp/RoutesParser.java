package tsp;

import java.util.ArrayList;

public class RoutesParser {
	
	
	
	public static ArrayList<Route> read(ArrayList<Location> locations){
		ArrayList<Route> routes = new ArrayList<Route>();
		
		for (Location i : locations){
			
			for (Location j : locations){
				if((!i.equals(j)) && (!i.containsRouteTo(j))){
					routes.add(new Route(i,j,euclid(i,j),1));
					
					i.addRoute(routes.get(routes.size()-1));
					j.addRoute(routes.get(routes.size()-1));
				}
			}
		}
		
		return routes;
	}
	
	public static double euclid(Location l1, Location l2){
		return Math.sqrt(Math.pow(l1.getx() - l2.getx(), 2) + Math.pow(l1.gety()+ l2.gety(), 2));
	}
}
