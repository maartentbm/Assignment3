package ants;

import java.util.ArrayList;
import java.util.Random;

import node.Node;

public class Rebel implements Brain {

	/**
	 * This decides on which node to to go
	 * The Rebel Brain takes the path with the least pheromone
	 * @throws Exception 
	 */
	
	public Node decide(ArrayList<Node> list){
		
		Random random = new Random();
		
		double sum = 0;
		// Check for every neighbouring cell
		for(int i = 0;i<list.size();i++){
			sum = sum + list.get(i).getPheromoneLevel();
		}

		// We want 0 exclusive and 1 inclusive
		// Thats why we do 1-random. It doesn't allow zero.
		double chosen = (1-random.nextDouble());
		
		double current = 0;
		int node = 0;
		
		for(node = 0; current > chosen; node++){
			current = 1/list.get(node).getPheromoneLevel();
			chosen -= current;
		}
		return list.get(node);
	}
	

	@Override
	public String toString() {
		return "<Brain[Rebel]>";
	}
}
