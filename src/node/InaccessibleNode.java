package node;

import java.util.ArrayList;

public class InaccessibleNode extends Node {

	public InaccessibleNode(int[] loc) {
		super(loc);
	}

	@Override
	public boolean isAccessible() {
		return false;
	}
	
	public String toMazeString() {
		return "[]";
	}
	
}
