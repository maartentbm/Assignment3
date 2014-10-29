package node;

import java.util.ArrayList;

import maze.Direction;

public abstract class Node {

	private Node north, east, south, west;
	private int[] location;

	/**
	 * Pheromone level.
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
	 * 
	 * @param dir
	 * @return
	 */
	public double getPheromoneLevel() {
		// System.out.println("Getting level form node");
		return pheromoneLevel;
	}

	/**
	 * Sets pheromone level one way
	 * 
	 * @param dir
	 * @param p
	 */
	public synchronized void setPheromoneLevel(double p) {
		this.pheromoneLevel = p;
	}

	/**
	 * Increase the pheromone level of a given direction by any real value.
	 * 
	 * @param dir
	 * @param p
	 */
	public synchronized void updatePheromoneLevel(double p) {
		this.pheromoneLevel += p;
	}

	/**
	 * Return array of Node neighbours.
	 * 
	 * @return
	 */
	public ArrayList<Node> getNeighbours() {
		ArrayList<Node> list = new ArrayList<Node>();

		if (getNorth() != null)
			list.add(getNorth());

		if (getEast() != null)
			list.add(getEast());

		if (getSouth() != null)
			list.add(getSouth());

		if (getWest() != null)
			list.add(getWest());

		return list;
	}

	public int getRelativeDirectionTo(Node other) {

		if(other == null) {
			System.out.println("Error! Invalid direction. (other node is null)");
			return -1;
		}
		
		// Calculate direction
		int[] myLoc = getLocation();
		int[] otLoc = other.getLocation();
		
		// EAST / WEST
		if(myLoc[0] == otLoc[0]) {
			
			if(otLoc[1] == myLoc[1] - 1) {
				return Direction.SOUTH;
			}
			
			if(otLoc[1] == myLoc[1] + 1) {
				return Direction.NORTH;
			}
			
		}
		
		// NORTH / SOUTH
		if(myLoc[1] == otLoc[1]) {
			
			if(otLoc[0] == myLoc[0] - 1) {
				return Direction.EAST;
			}
			
			if(otLoc[0] == myLoc[0] + 1) {
				return Direction.WEST;
			}
			
		}
		
		// Same node
		if(myLoc[1] == otLoc[1] && myLoc[0] == otLoc[0]) {
			return -1;
		}
		
		// Node 
		System.out.println("Other error?");
		return -1;

	}

	@Override
	public String toString() {

		return "(" + location[0] + "," + location[1] + ")";
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj instanceof Node) {
			
			Node n = (Node) obj;
			
			return n.location[0] == this.location[0] && n.location[1] == this.location[1];
			
		}
		
		return false;
	}
	
	public abstract String toMazeString();
	
	public abstract String toPheromoneString();

	public abstract boolean isAccessible();

}
