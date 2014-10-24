package maze;

import java.util.ArrayList;

import node.Node;

public class Path extends ArrayList<Node> {

	public ArrayList<Integer> directionPath;

	public Path(Node startNode) {
		this.add(startNode);
		directionPath = new ArrayList<Integer>();
	}

	public ArrayList<Integer> getDirectionPath() {
		return this.directionPath;
	}
	
	@Override
	public boolean add(Node e) {

		try {

			if (this.size() > 0) {

				// Get previous Node
				Node prev = this.get(this.size() - 1);

				if (prev != null) {
					
					// Calculate direction and add to path
					Integer dir = e.getRelativeDirectionTo(prev);
					
					if(dir < 0) {
						return true;
					}
					
					directionPath.add(dir);

				} else {
					System.out.println("ERROR: Could not calculate direction from previous node.");
				}

			}

		} catch (Exception a) {
			a.printStackTrace();
			System.exit(-1);
		}

		// Add node to path
		return super.add(e);
	}

}
