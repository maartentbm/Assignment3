package node;

public class AccessibleNode extends Node {

	public AccessibleNode(int x, int y) {
		super(x, y);
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
