package ants;

import java.util.ArrayList;
import java.util.Arrays;

import maze.Maze;
import maze.Path;
import node.Node;

public class Ant {

	private int maxAge;
	private float pheromoneAmount;
	private int[] location = new int[2];
	private Brain brain;

	public Path path;

	public Ant(int newMaxAge, float newPheromoneAmount) {
		brain = new Explorer();
		maxAge = newMaxAge;
		pheromoneAmount = newPheromoneAmount;
	}

	public Ant(Brain newBrain, int newMaxAge, float newPheromoneAmount) {
		brain = newBrain;
		maxAge = newMaxAge;
		pheromoneAmount = newPheromoneAmount;
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

		System.out.println("Ant started: " + brain + "\n");
		
		// Create path
		path = new Path(maze.getNode(startLocation));
		
		location = startLocation;
		path.add(maze.getNode(location));
		ArrayList<Node> toGo = new ArrayList<Node>();

		// First step - without predecessor
		toGo = maze.getNode(location).getNeighbours();
		path.add(brain.decide(toGo));

		
		
		for (int i = 0; i < maxAge; i++) {

			// Goal reached?
			if (Arrays.equals(location, goalLocation)) {
				// System.out.println("[Ant|run] Reached goal");
				spreadPheromone();

				return;
			}

			// Collect neighbours where I can go.
			toGo = selectNeighbours();

			Node nextNode;

			// Followers don't make loops!
			if (brain instanceof Follower) {

				nextNode = brain.decide(toGo);
				if (path.contains(nextNode)) {
					backOff();
					nextNode = path.get(path.size() - 1);
				}

			} else {
				nextNode = brain.decide(toGo);
			}

			if (nextNode != null) {

				location = nextNode.getLocation();
				path.add(maze.getNode(location));

			} else {
				System.out.println("Can't find a next node from location (" + location[0] + ", " + location[1] + "). (previous path: " + path + ", current brain: " + this.brain + ")");
			}
		}
		System.out.println("Ant: " + brain + "\nThis ant died of old age.\n");
	}

	private ArrayList<Node> selectNeighbours() {

		// The neighbours of current locations
		ArrayList<Node> list = path.get(path.size() - 1).getNeighbours();
		
		if (accessibleNeighbours(list) < 2) {
			backOff();
		}

		list.clear();
		list = path.get(path.size() - 1).getNeighbours();
		// We do not want to move back to where we came from.
		list.remove(path.get(path.size() - 2));

		if (list.size() == 0)
			System.out.println("Empty list!");

		return list;
	}

	/**
	 * Add pheromone to node based on path. Currently adds uniquely to nodes in reversed quadratic comparison. Aka: 1/x^2.
	 */
	private void spreadPheromone() {

		float newPheromones = pheromoneAmount / (path.size() ^ 2);
		ArrayList<Node> route = new ArrayList<Node>();

		// Add to route unique from path
		for (int i = 0; i < path.size(); i++) {
			if (!route.contains(path.get(i))) {
				route.add(path.get(i));
			}
		}

		for (int i = 0; i < route.size(); i++) {
			route.get(i).updatePheromoneLevel(newPheromones);
		}
		// Not very readable like this, but keeps the message in 1 part with
		// treating.
		System.out.println("Ant: " + brain + "\nReached the end in " + path.size() + " steps.\nReleasing " + newPheromones + " onto the path.\n");
	}

	// If we find a dead end.
	private void backOff() {
		int size = path.size();
		for (int i = size - 1; (i >= 0) && (accessibleNeighbours(path.get(i).getNeighbours()) < 3); i--) {
			// Step back is there are less then three accesible neighbours.

			// int[] loc = path.get(i).getLocation();
			// System.out.println("[Ant|backOff] Removing: " + loc[0] + "x" +
			// loc[1]);
			path.remove(i);
		}
		// you are now on the old crossroad.
	}

	private int accessibleNeighbours(ArrayList<Node> neighbours) {

		if (neighbours == null) {
			return 0;
		}

		// How many of the given nodes are accessible
		int count = 0;
		for (int i = 0; i < neighbours.size(); i++) {

			if (neighbours.get(i) == null)
				continue;

			count += neighbours.get(i).isAccessible() ? 1 : 0;
		}
		return count;
	}
}
