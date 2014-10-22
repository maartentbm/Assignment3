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
	public Node decide(ArrayList<Node> list){
		
		Random random = new Random();
		
		double sum = 0;
		// Check for every neighbouring cell
		for(int i = 0;i<list.size();i++){
			sum = sum + list.get(i).getPheromoneLevel();
		}
		System.out.println("Out of "+sum);
		// We want 0 exclusive and 1 inclusive
		// Thats why we do 1-random. It doesn't allow zero.
		double chosen = (1-random.nextDouble())*sum;
		System.out.println("Look for "+chosen+"\n");
		
		
		double current = 0;
		int node = 0;
		for(int i = 0;i < list.size(); i++){
			if(chosen > current){
				System.out.println("Between 0 and "+current);
				current = list.get(i).getPheromoneLevel();
				chosen = chosen - current;
			} else {
				node = i;
				break;
			}
		}
		//System.out.println("Chosen the "+node);
		return list.get(node);
	}
}