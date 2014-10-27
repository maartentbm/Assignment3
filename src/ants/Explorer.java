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
	public Node decide(Path path){
		ArrayList<Node> list = path.get(path.size()-1).getNeighbours();
		
		Random random = new Random();

		ArrayList<Node> accessible = new ArrayList<Node>();

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

		if (accessible.size() > 0) {
			// Pick a random from the accessible nodes.
			// These are integers!
			return accessible.get(random.nextInt(accessible.size()));
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "<Brain[Explorer]>";
	}
}
