package maze;

import java.util.ArrayList;

import node.Node;

public class Path extends ArrayList<Node> {

	public Path() {}
	
	public Path(Node startNode) {
		this.add(startNode);
	}

	public ArrayList<Integer> getDirectionPath() {
		
		// Init direction path
		ArrayList<Integer> dirPath = new ArrayList<Integer>(size()-1);
		
		// Check list is large enough
		if(size() < 1) {
			return dirPath;
		}
		
		Node prev = null;
		Integer dir;
		
		// Loop nodes
		for(Node n : this) {
			
			if(prev == null) {
				prev = n;
				continue;
			}
			
			// Calculate direction
			dir = n.getRelativeDirectionTo(prev);

			// Check validity
			if (dir < 0) {
				System.out.println("\nPATH ERRORR;");
				System.out.println(prev);
				System.out.println(n);
				System.out.println();
				continue;
			}

			// Add to path
			dirPath.add(dir);
			
			// Set previous node
			prev = n;
		}
		
		return dirPath;
	}

	@Override
	public boolean add(Node e) {

		try {

			if (this.size() > 0) {

				// Get previous Node
				Node prev = this.get(this.size() - 1);

				if (prev != null) {

					// Cannot navigate to same node
					if (prev.equals(e)) {
						return false;
					}

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
	
	public String toFileString() {

		String str = "";

		ArrayList<Integer> dirPath = this.getDirectionPath();
		
		for (int i = 0; i < dirPath.size(); i++) {

			str += dirPath.get(i) + ";";

		}

		return str;

	}
	
	public Node getLast() {
		return get(size()-1);
	}

}
