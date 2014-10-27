package ants;

import java.util.ArrayList;
import java.util.Random;

import maze.Path;
import node.Node;

public class Explorer implements Brain {

	/**
	 * This decides on which node to to go Warning! Can not handle zero accessible nodes The explorer brain chooses randomly from the accessible nodes in the list
	 * 
	 * @throws Exception
	 */
	public Node decide(ArrayList<Node> list, Path path){
		Random random = new Random();

		ArrayList<Node> accessible = new ArrayList<Node>();
		ArrayList<Node> unvisited = new ArrayList<Node>();
		
		// Check for every neighboring cell
		for (int i = 0; i < list.size(); i++) {
			
			// Check for borders etc.
			if (list.get(i) != null) {
				
				// The explorer only looks at accessibility.
				if (list.get(i).isAccessible()) {
					accessible.add(list.get(i));
				}
			}
			
		}

		Node n;
		
		// Skip nodes which have already been visited
		for (int j = 0; j < accessible.size(); j++) {
			
			n = accessible.get(j);
			
			if(n != null && !path.contains(n)) {
				unvisited.add(n);
			}
			
		}
		
		
		// Determine next node
		Node next;
		
		if(!unvisited.isEmpty()) {
			
			// Pick a random from the accessible nodes.
			next = unvisited.get(random.nextInt(unvisited.size()));
			
		} else if (!accessible.isEmpty()) {
			
			// Pick a random from the accessible nodes.
			next = accessible.get(random.nextInt(accessible.size()));
			
		} else {
			return null;
		}
		
		// Calculate direction
		//int direction = next.getRelativeDirectionTo(path.get(path.size()-1));
		
		return next;
	}
	
	@Override
	public String toString() {
		return "<Brain[Explorer]>";
	}
}
