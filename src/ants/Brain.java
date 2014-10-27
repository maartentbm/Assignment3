package ants;

import java.util.ArrayList;

import maze.Path;
import node.Node;

public interface Brain {

	public Node decide(ArrayList<Node> list, Path p);
	
}
