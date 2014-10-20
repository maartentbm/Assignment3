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
		if(checkMaze(maze, startLocation, goalLocation)==false){
			System.out.println("Start or goal location invalid!");
			return;
		}
		
		System.out.println("This Ant: "+this+"\n|Start: "+ startLocation[0]+" "+startLocation[1]+"\n|End:   "+goalLocation[0]+" "+goalLocation[1]);
		
		// Set current location to start position
		setLocation(startLocation);
		// Initialize toGo list
		ArrayList<Node> toGo = new ArrayList<Node>();

		// Take the first step without a previous location
		path.add(maze.getNode(location));
		toGo = maze.getNode(location).getNeighbours();
		try {
			// Brain decides from ToGo were to go
			location = brain.decide(toGo).getLocation();
		} catch (Exception e) {
			System.out.println("Error: Brain got an empty Node list.");
			e.printStackTrace();
			return;
		}
		
		// When we reach the goal, or when we reach maxAge
		for (int i = 0; i < maxAge; i++) {
			System.out.println("Location " + location[0] + " " + location[1]);
			path.add(maze.getNode(location));
			toGo.clear();

			// We get all surrounding cells
			// We remove the cell we came from last step
			toGo = maze.getNode(location).getNeighbours();
			toGo.remove(path.get(i));
			
			// Check if we are on a dead end. If so, back off to were we last decided.
			System.out.println(accessibleNeighbours(toGo));
			if(accessibleNeighbours(toGo)==0){
				System.out.println("We have hit a dead end on "+location[0]+"x"+location[1]);
				backOff();
			}
			//
				try {
					// Brain decides from ToGo were to go
					location = brain.decide(toGo).getLocation();
				} catch (Exception e) {
					System.out.println("Error: Brain got an empty Node list.");
					e.printStackTrace();
					return;
				}
				
			if(Arrays.equals(location,goalLocation)){
				spreadPheromone();
				return;
			}		
				
		}
		System.out.println("This Ant died of old age.");
	}		
	
	public void spreadPheromone(){
		float newPheromones = pheromoneAmount/path.size();
		for(int i = 0; i < path.size(); i ++){
			path.get(i).updatePheromoneLevel(newPheromones);
		}
		System.out.println("Reached the end in "+path.size()+" steps.");
		System.out.println("Releasing "+newPheromones+" onto the path.");
	}
	
	// If we find a dead end. 
	public void backOff(){
		for(int i = path.size(); i > 0 || (accessibleNeighbours(path.get(i).getNeighbours()) < 3) ; --i){
			path.remove(path.get(i));
		}
	}

	/**
	 * Returns true if both start and goal locations are valid.
	 * @param maze
	 * @param startLocation
	 * @param goalLocation
	 * @return
	 */
	private boolean checkMaze(Maze maze, int[] startLocation, int[] goalLocation){
		return (maze.getNode(startLocation).isAccessible() && maze.getNode(goalLocation).isAccessible());		
	}
	
	private int accessibleNeighbours(ArrayList<Node> neighbours){
		int count = 0;
		for(int i = 0; i < neighbours.size(); i++){
			count += neighbours.get(i).isAccessible()?1:0;
		}
		return count;
	}
}
