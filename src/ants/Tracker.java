package ants;

import java.util.ArrayList;

import node.Node;

public class Tracker implements Brain{

	@Override
	public Node decide(ArrayList<Node> list) {
		double maxLevel = 0;
		Node nodeOfChoice = list.get(0);
		
		for(int i = 0; i < list.size(); i ++){
			if(maxLevel < list.get(i).getPheromoneLevel()){
				nodeOfChoice = list.get(i);
				maxLevel = nodeOfChoice.getPheromoneLevel();
			}
		}
		return nodeOfChoice;
	}

	@Override
	public String toString() {
		return "<Brain[Tracker]>";
	}
}
