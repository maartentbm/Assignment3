package tsp;

import java.io.File;
import java.util.ArrayList;

public class Map {
	
	
	public static void main(String[] args) {
		ArrayList<Location> locations = ProductsParser.read(new File("res/TSPproducts.txt"));
		
		System.out.println("number of nodes: "+locations.size());
		System.out.println(locations.toString() +"\n");
		
		ArrayList<Route> routes = RoutesParser.read(locations);
		
		System.out.println("number of routes: "+routes.size());
		for (Location i : locations){
			System.out.println("Routes of: "+i.toString()+" : "+i.getRoutes().toString());
		}
	}

}
