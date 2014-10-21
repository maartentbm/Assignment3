package ants;

import java.util.ArrayList;
import java.util.Arrays;

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
	 * @param newLocation
	 */
	public void setLocation(int[] newLocation) {
		location = newLocation;
	}

	/**
	 * A setter based on a node as argument
	 * @param newNode
	 */
	public void moveTo(Node newNode) {
		location = newNode.getLocation();
	}

	/**
	 * The running tactics of the Ant.
	 * @param maze
	 * @param startLocation
	 * @param goalLocation
	 */
	public void run(Maze maze, int[] startLocation, int[] goalLocation) {
		location = startLocation;
		path.add(maze.getNode(location));
		ArrayList<Node> toGo = new ArrayList<Node>();
		
		//First step - without predecession
		toGo = maze.getNode(location).getNeighbours();
		path.add(brain.decide(toGo));
		
		for(int i = 0; i < maxAge; i++){
			// Goal reached?
			if(Arrays.equals(location,goalLocation)){
				System.out.println("[Ant|run] Reached goal");
			}
			
			// Check neighbours, where can I go.
			
			//
			location = brain.decide(toGo).getLocation();
			
		}
	}		
	
	public void spreadPheromone(){
		// linear dependent
		float newPheromones = pheromoneAmount/path.size();
		for(int i = 0; i < path.size(); i ++){
			path.get(i).updatePheromoneLevel(newPheromones);
		}
		System.out.println("Reached the end in "+path.size()+" steps.");
		System.out.println("Releasing "+newPheromones+" onto the path.");
	}
	
	// If we find a dead end. 
	public void backOff(){
		int size = path.size();
		for(int i = size-1;(i >= 0)&&(accessibleNeighbours( path.get(i).getNeighbours() ) < 3) ; i--){
			// Step back is there are less then three accesible neighbours.
			int [] loc = path.get(i).getLocation();
			System.out.println("Removing: "+loc[0]+"x" + loc[1]); 
			path.remove(i);
		}
		// you are now on the old crossroad.
	}

	private int accessibleNeighbours(ArrayList<Node> neighbours){
		// How many of the given nodes are accessible
		int count = 0;
		for(int i = 0; i < neighbours.size(); i++){
			count += neighbours.get(i).isAccessible()?1:0;
		}
		return count;
	}
}
