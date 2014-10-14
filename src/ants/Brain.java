package ants;

import java.util.ArrayList;
import node.Node;
import maze.Maze;

public interface Brain {

	abstract public Node decide(ArrayList<Node> list);
	
}
