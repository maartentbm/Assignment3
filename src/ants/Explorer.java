package ants;

import java.util.ArrayList;
import java.util.Random;

import node.Node;

public class Explorer implements Brain {

	/**
	 * This decides on which node to to go
	 * Warning! Can not handle empty lists.
	 * Warning! Can not handle zero accessible nodes
	 * The explorer brain chooses randomly from the accessible nodes in the list
	 */
	public Node decide(ArrayList<Node> list) {
		Random random = new Random();
		
		int sum = 0;
		// Check for every neighbouring cell
		for(int i = 0;i<list.size();i++){
			// The explorer only looks at accessibility.
			sum += (list.get(i).isAccessible() == true)?1:0;
		}
		// Pick a random from the accessible nodes.
		// These are integers!
		int k = random.nextInt(sum)+1;

		// Check for every neighbouring cell
		int j = 0;
		int node = 0;
		for(node = 0; j < k; node++){
			// Is this cell accessible
			j = (list.get(node).isAccessible() == true)?1:0;
			k -= j;
		}
		return list.get(node);
	}
}
