package ants;

import java.util.ArrayList;
import java.util.Arrays;

import maze.Maze;
import maze.Path;
import node.Node;

public class Ant {

	private int maxAge;
	private static float pheromoneAmount;
	private int[] location = new int[2];
	private Brain brain;

	public Path path;
	public ArrayList<Node> keep_away = new ArrayList<Node>();

	public Ant(Brain newBrain, int newMaxAge, float newPheromoneAmount) {
		brain = newBrain;
		maxAge = newMaxAge;
		pheromoneAmount = newPheromoneAmount;
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
		// System.out.println("Ant started: " + brain + "\n");

		// Create path
		path = new Path(maze.getNode(startLocation));

		location = startLocation;
		path.add(maze.getNode(location));

		// First step - without predecessor
		path.add(brain.decide(path, keep_away));

		// The big loop
		//
		for (int i = 0; i < maxAge; i++) {

			// Goal reached?
			if (Arrays.equals(location, goalLocation)) {
				spreadPheromone();
				return;
			}

			// Collect neighbours where I can go.
			Node nextNode = brain.decide(path, keep_away);

			/*
			 * LUS PREVENTION
			 */

			// Check if any neighbours are already in path (except from source)
//			ArrayList<Node> neighbours = nextNode.getNeighbours();

			// Remove origin from list
//			Node origin = path.get(path.size() - 1);
//			neighbours.remove(origin);
//
//			for(Node n: neighbours) {
//				if(path.contains(n)) {
//					moveBack();
//				}
//			}
			
			/*
			 * /LUS PREVENTION
			 */

			if (nextNode != null) {

				location = nextNode.getLocation();
				path.add(maze.getNode(location));

			} else {

				moveBack();

			}
		}

		// System.out.println("Killed: " + brain + "\nReason: Took to long");
	}

	private ArrayList<Node> selectNeighbours() {

		// The neighbours of current locations
		ArrayList<Node> list = path.get(path.size() - 1).getNeighbours();

		// Catched dead ends.
		if (accessibleNeighbours(list) < 2) {
			backOff(false);
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
//		ArrayList<Node> route = new ArrayList<Node>();

		// Add to route unique from path
//		for (int i = 0; i < path.size(); i++) {
//			if (!route.contains(path.get(i))) {
//				route.add(path.get(i));
//			}
//		}

		for (int i = 0; i < path.size(); i++) {
			path.get(i).updatePheromoneLevel(newPheromones);
		}
		// Not very readable like this, but keeps the message in 1 part with
		// treating.
		//System.out.println("Ant: " + brain + "\nReached the end in " + path.size() + " steps.\nReleasing " + newPheromones + " onto the path.\n");
	}
	
	/**
	 * Add pheromone to node based on path. Currently adds uniquely to nodes in reversed quadratic comparison. Aka: 1/x^2.
	 */
	public static void spreadPheromone(Path p) {

		if(p == null)
			return;
		
		float newPheromones = pheromoneAmount / (p.size() ^ 2);

		for (int i = 0; i < p.size(); i++) {
			p.get(i).updatePheromoneLevel(newPheromones);
		}
		
	}

	/**
	 * Goes back to last decision Boolean : Allowed to return:
	 * 
	 * @param allowAcces
	 */
	private void backOff(boolean allowToReturn) {
		// System.out.println("Stuck on: " + path.get(size - 1) + "\nPath size: "
		// + size);

		for (int i = path.size() - 1; (i >= 0) && (accessibleNeighbours(path.get(i).getNeighbours()) < 3); i--) {

			// Step back is there are less then three accesible neighbours.
			int[] loc = path.get(i).getLocation();

			// System.out.println("[Ant|backOff] Removing: " + loc[0] + "x"
			// + loc[1]);

			if (allowToReturn == false) {
				path.get(i).setPheromoneLevel(0);
			}

			path.remove(i);
		}

		// you are now on the old crossroad.
		// System.out.println("Backed off to :" + path.get(path.size() - 1));
	}

	private void moveBack() {

		Node last = path.remove(path.size() - 1);
		//last.updatePheromoneLevel(-0.1f);
		keep_away.add(last);

	}

	private int accessibleNeighbours(ArrayList<Node> neighbours) {

		if (neighbours == null) {
			// System.out.println("Empty neighbours list");
			return 0;
		}

		// How many of the given nodes are accessible
		int count = 0;
		for (int i = 0; i < neighbours.size(); i++) {

			if (neighbours.get(i) == null)
				continue;

			count += neighbours.get(i).isAccessible() ? 1 : 0;
		}
		// System.out.println("Neighbours " + count);
		return count;
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
}
