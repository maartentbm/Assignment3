package ants;

import java.util.ArrayList;
import java.util.Random;

import node.Node;

public class Explorer implements Brain {

	/**
	 * This decides on which node to to go
	 * The explorer brain chooses randomly
	 */
	public Node decide(Node node) {
		ArrayList<Node> list = node.getNeighbours();
		Random random = new Random();
		
		int sum = 0;
		// Check for every neighbouring cell
		for(int i = 0;i<list.size();i++){
			// The explorer only looks at accessibility.
			sum += (list.get(i).isAccessible() == true)?1:0;
		}
		// Pick a random from the accessible nodes.
		int k = random.nextInt(sum)+1;

		// Check for every neighbouring cell
		for(int i = 0;i<list.size();i++){
			// Is this cell accessible
			int j = (list.get(i).isAccessible() == true)?1:0;
			// Is this the numbered cell we want
			if( k > j){
				k -= j;
			}
			
		}
	}
}
