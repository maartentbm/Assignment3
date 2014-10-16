package ants;

import java.util.ArrayList;
import java.util.Random;

import node.Node;

public class Follower implements Brain {

	/**
	 * This decides on which node to to go
	 * The Follower Brain takes the path with the most pheromone
	 * @throws Exception 
	 */
	public Node decide(ArrayList<Node> list) throws Exception {
		
		if(list.size() == 0) {
			throw new Exception("Nodelist is empty.");
		}
		
		Random random = new Random();
		
		double sum = 0;
		int iFlipped = 0;
		// Check for every neighbouring cell
		for(int i = 0;i<list.size();i++){
			iFlipped = (i+2)%4;
			sum = sum + list.get(i).getPheromoneLevel();
		}

		// We want 0 exclusive and 1 inclusive
		// Thats why we do 1-random. It doesn't allow zero.
		double chosen = (1-random.nextDouble())*sum;
		
		double current = 0;
		int node = 0;
		
		for(node = 0; current < chosen; node++){
			iFlipped = (node+2)%4;
			current = list.get(node).getPheromoneLevel();
			chosen -= current;
		}
		return list.get(node);
	}
}