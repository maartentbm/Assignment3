package maze;

import java.util.ArrayList;

import node.Node;

public class ResultSet extends ArrayList<ArrayList<Node>> {

	public ArrayList<Node> getShortestPath() {
		
		ArrayList<Node> shortest = null;
		
		for(ArrayList<Node> alist : this) {
			
			if(shortest == null || alist.size() < shortest.size()) {
				shortest = alist;
			}
			
		}
		
		return shortest;
		
	}
	
}
