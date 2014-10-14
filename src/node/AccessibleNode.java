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
	
	@Override
	public String toString() {
		return "  ";
	}
	
}
