package node;

public class NodeFactory {

	public static Node createNode(int type, int x, int y) {

		switch (type) {
		case 0:
			return new InaccessibleNode(x, y);
		case 1:
			return new AccessibleNode(x, y);
		}
		
		return null;

	}

}
