package ants;

import maze.Path;
import node.Node;

public interface Brain {

	public Node decide(Path path);
	
}
