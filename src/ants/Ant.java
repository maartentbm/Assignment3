package ants;

import java.util.ArrayList;

import maze.Maze;
import node.Node;

public class Ant {

	public int maxAge;
	public int[] location = new int[2];

	public Brain brain;
	public ArrayList<Node> path;

	public Ant(int newMaxAge) {
		brain = new Explorer();
		maxAge = newMaxAge;
	}

	public Ant(Brain newBrain, int newMaxAge) {
		brain = newBrain;
		maxAge = newMaxAge;
		path = new ArrayList<Node>();
	}

	/**
	 * A getter for location
	 */
	public int[] getLocation() {
		return location;
	}

	/**
	 * A setter for location
	 * 
	 * @param newLocation
	 */
	public void setLocation(int[] newLocation) {
		location = newLocation;
	}

	/**
	 * A setter based on a node as argument
	 * 
	 * @param newNode
	 */
	public void moveTo(Node newNode) {
		location = newNode.getLocation();
	}

	/**
	 * The running tactics of the Ant.
	 * 
	 * @param maze
	 * @param startLocation
	 * @param goalLocation
	 */
	public void run(Maze maze, int[] startLocation, int[] goalLocation) {

		// Set current location to start position
		setLocation(startLocation);

		// Init toGo list
		ArrayList<Node> toGo = new ArrayList<Node>();

		// Take the first step without previous location
		path.add(maze.getNode(location));
		toGo = maze.getNode(location).getNeighbours();

		// When we reach the goal, or when we reach maxAge
		for (int i = 0; i < maxAge && location != goalLocation; i++) {
			path.add(maze.getNode(location));
			toGo.clear();

			// We get all surrounding cells
			toGo = maze.getNode(location).getNeighbours();

			// We remove the cell we came from last step
			toGo.remove(path.get(i));

			boolean deadEnd = true;
			for (int j = 0; j < toGo.size() && !deadEnd == false; j++) {
				if (toGo.get(j) != null) {
					deadEnd = !toGo.get(j).isAccessible();
				}
			}

			if (deadEnd) {
				abandon();
			} else {

				try {
					// Brain decides from ToGo were to go
					location = brain.decide(toGo).getLocation();
				} catch (Exception e) {
					System.out.println("Error: Brain got an empty Node list.");
					e.printStackTrace();
					return;
				}
			}
			path.add(maze.getNode(location));
		}

		System.out.println("Done!");

	}

	public void abandon() {
		// TODO Move back until we can go somewhere else.
		// TODO Pheromone to represent abandoned paths.
	}

}
