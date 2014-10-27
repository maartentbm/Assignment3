package ants;

import java.util.ArrayList;
import java.util.Random;

import maze.Path;
import node.Node;

public abstract class Brain {

	public abstract Node decide(Path path, ArrayList<Node> keep_away);

	public ArrayList<Node> getAvailableNeighbours(Path path, ArrayList<Node> keep_away) {

		ArrayList<Node> list = path.get(path.size() - 1).getNeighbours();

		ArrayList<Node> accessible = new ArrayList<Node>();

		// Check for every neighboring cell
		for (int i = 0; i < list.size(); i++) {
			// Check for borders etc.
			if (list.get(i) != null) {
				// The explorer only looks at accessibility.
				if (list.get(i).isAccessible() && !keep_away.contains(list.get(i)) && !path.contains(list.get(i))) {
					accessible.add(list.get(i));
				}
			}
		}
		
		return accessible;

	}

}
