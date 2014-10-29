package node;

import java.util.ArrayList;

public class AccessibleNode extends Node {

	public AccessibleNode(int[] loc) {
		super(loc);
	}

	@Override
	public boolean isAccessible() {
		return true;
	}
	
	public String toMazeString() {
		return "  ";
	}
	
	@Override
	public String toPheromoneString() {
		return " "+AccessibleNode.padLeft(String.format("%2.3f", getPheromoneLevel()), 6)+" ";
	}
	
	public static String padLeft(String s, int n) {
	    return String.format("%1$" + n + "s", s);  
	}
	
}
