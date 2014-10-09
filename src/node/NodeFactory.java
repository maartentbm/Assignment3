package node;

public class NodeFactory {

	public static Node createNode(int type, int[] loc) {

		switch (type) {
		case 0:
			return new InaccessibleNode(loc);
		case 1:
			return new AccessibleNode(loc);
		}
		
		return null;

	}

}
