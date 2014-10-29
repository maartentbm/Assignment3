package ants;

import java.util.ArrayList;
import java.util.Random;

import maze.Path;
import node.Node;

public class Follower extends Brain {

	/**
	 * This decides on which node to to go The Follower Brain takes the path
	 * with the most pheromone
	 * 
	 * @throws Exception
	 */
	public Node decide(Path path, ArrayList<Node> keep_away) {
		
		// Find available neighbours
		ArrayList<Node> list = getAvailableNeighbours(path, keep_away);
		
		if (list.size() > 0) {
			
			Random random = new Random();
			double sum = 0;
			
			// Sum all pheromones from neighbours
			for (int i = 0; i < list.size(); i++) {
				sum += list.get(i).getPheromoneLevel();
			}
			
			// Determine a random value in the pheromone space
			double chosen = (1 - random.nextDouble()) * sum;
			
			// Find the node which's pheromone level most closely approaches this level
			int node = 0;
			double current = 0;
			for (int i = 0; i < list.size(); i++) {

				// Get pheromone level, subtract from sum
				current = list.get(i).getPheromoneLevel();
				chosen -= current;
				
				// If sum < 0, we have found the node
				if (chosen < 0) {
					node = i;
					break;
				}
			}
			
			//System.out.println(list.get(node));
			
			// System.out.println("Chosen the "+node);
			return list.get(node);
			
			
		} else {
			return null;
		}
		
	}

	@Override
	public String toString() {
		return "<Brain[Follower]>";
	}
}