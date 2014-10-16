package node;

import java.util.ArrayList;

public abstract class Node {

	private Node north, east, south, west;
	private int[] location;
	
	/**
	 * Pheromone level. Array index == direction.
	 * 0 -> EAST
	 * 1 -> NORTH
	 * 2 -> WEST
	 * 3 -> SOUTH
	 */
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

	/**
	 * Get the pheromone level of a given connection.
	 * @param dir
	 * @return
	 */
	public double getPheromoneLevel() {
		return pheromoneLevel;
	}

	/**
	 * Sets pheromone level one way
	 * @param dir
	 * @param p
	 */
	public synchronized void setPheromoneLevel(double p){
		this.pheromoneLevel = p; 
	}
	
	/**
	 * Increase the pheromone level of a given direction by any real value.
	 * @param dir
	 * @param p
	 */
	public synchronized void updatePheromoneLevel(double p) {
		this.pheromoneLevel += p;
	}

	/**
	 * Return array of Node neighbours.
	 * @return
	 */
	public ArrayList<Node> getNeighbours() {
		ArrayList<Node> list = new ArrayList<Node>();
		list.add(getNorth());
		list.add(getEast());
		list.add(getSouth());
		list.add(getWest());
		
		return list;
	}

	public abstract boolean isAccessible();

}
