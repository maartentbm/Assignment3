package node;


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

	@Override
	public String toPheromoneString() {
		int[] loc = getLocation();
		return "[=====]";
	}
	
}
