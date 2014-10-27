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
		
		ArrayList<Node> list = getAvailableNeighbours(path, keep_away);
		
		Random random = new Random();
		double sum = 0;
		
		// Check for every neighbouring cell
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i).getPheromoneLevel();
		}
		
		// We want 0 exclusive and 1 inclusive
		// Thats why we do 1-random. It doesn't allow zero.
		double chosen = (1 - random.nextDouble()) * sum;
		int node = 0;
		double current = 0;
		for (int i = 0; i < list.size(); i++) {

			current = list.get(i).getPheromoneLevel();
			chosen -= current;
			
			if (chosen < 0) {
				node = i;
				break;
			}
		}
		
		System.out.println(list.get(node));
		
		// System.out.println("Chosen the "+node);
		return list.get(node);
	}

	@Override
	public String toString() {
		return "<Brain[Follower]>";
	}
}