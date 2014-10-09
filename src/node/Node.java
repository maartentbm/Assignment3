package node;

public abstract class Node {

	private Node north, east, south, west;
	private int[] location;
	private double pheromoneLevel;
	
	public Node(int[] loc) {
		this.location = loc;
		
		// Initial pheromone level = 0
		this.pheromoneLevel = 0;
	}

	public Node getNorth() {
		return north;
	}

	public void setNorth(Node north) {
		this.north = north;
	}

	public Node getEast() {
		return east;
	}

	public void setEast(Node east) {
		this.east = east;
	}

	public Node getSouth() {
		return south;
	}

	public void setSouth(Node south) {
		this.south = south;
	}

	public Node getWest() {
		return west;
	}

	public void setWest(Node west) {
		this.west = west;
	}
	
	public int[] getLocation() {
		return this.location;
	}

	public void setLocation(int[] loc) {
		this.location = loc;
	}

	public double getPheromoneLevel() {
		return pheromoneLevel;
	}

	public void setPheromoneLevel(double p) {
		this.pheromoneLevel = p;
	}

	public abstract boolean isAccessible();

}
