package ants;

import java.util.ArrayList;

import node.Node;

public interface Brain {

	public Node decide(ArrayList<Node> list) throws Exception;
	
}
