package node;

public class InaccessibleNode extends Node {

	public InaccessibleNode(int x, int y) {
		super(x, y);
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
