package ants;

import java.util.ArrayList;
import java.util.Random;

import maze.Path;
import node.Node;

public class Explorer extends Brain {

	/**
	 * This decides on which node to to go Warning! Can not handle zero accessible nodes The explorer brain chooses randomly from the accessible nodes in the list
	 * 
	 * @throws Exception
	 */
	public Node decide(Path path, ArrayList<Node> keep_away){
		
		Random random = new Random();
		ArrayList<Node> accessible = this.getAvailableNeighbours(path, keep_away);

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
