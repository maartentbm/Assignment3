package node;

public class InaccessibleNode extends Node {

	public InaccessibleNode(int[] loc) {
		super(loc);
	}

	@Override
	public boolean isAccessible() {
		return false;
	}
	
	@Override
	public String toString() {
		return "[]";
	}

}
