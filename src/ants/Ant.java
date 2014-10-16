package ants;

import java.util.ArrayList;

import maze.Maze;
import node.Node;

public class Ant {

	private int maxAge;
	private float pheromoneAmount;
	private int[] location = new int[2];

	public Brain brain;
	public ArrayList<Node> path;

	public Ant(int newMaxAge, float newPheromoneAmount) {
		brain = new Explorer();
		maxAge = newMaxAge;
		pheromoneAmount = newPheromoneAmount;
		
		path = new ArrayList<Node>();
	}

	public Ant(Brain newBrain, int newMaxAge, float newPheromoneAmount) {
		brain = newBrain;
		maxAge = newMaxAge;
		pheromoneAmount = newPheromoneAmount;
		
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

		// Take the first step without a previous location
		path.add(maze.getNode(location));
		toGo = maze.getNode(location).getNeighbours();

		// When we reach the goal, or when we reach maxAge
		for (int i = 0; i < maxAge; i++) {
			path.add(maze.getNode(location));
			toGo.clear();

			// We get all surrounding cells
			toGo = maze.getNode(location).getNeighbours();

			// We remove the cell we came from last step
			toGo.remove(path.get(i));

			boolean deadEnd = checkDeadEnd(toGo);

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
			
			if(location == goalLocation){
				spreadPheromone();
				break;
			}
		}
		System.out.println("Done!");

	}		
	
	public void spreadPheromone(){
		float newPheromones = pheromoneAmount/path.size();
		for(int i = 0; i < path.size(); i ++){
			path.get(i).updatePheromoneLevel(newPheromones);
		}
		System.out.println("Reached the end in "+path.size()+" steps.");
	}
	
	public void abandon(){
	// TODO Return untill last decision
	// TODO Pheromone to represent abandoned paths.
	}

	private boolean checkDeadEnd(ArrayList<Node> toGo){
		boolean deadEnd = true;
		for (int j = 0; j < toGo.size() && !deadEnd == false; j++) {
			if (toGo.get(j) != null) {
				deadEnd = !toGo.get(j).isAccessible();
			}
		}
		return deadEnd;
	}
}
