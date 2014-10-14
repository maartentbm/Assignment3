package ants;

import maze.Maze;
import node.Node;

import java.util.ArrayList;

public class Ant {
	
	public int maxAge;
	public int[] location = new int[2];
	
	public Brain brain;
	public ArrayList<Node> path;

	public Ant(int newMaxAge){
		brain = new Explorer();
		maxAge = newMaxAge;
	}
	
	public Ant(Brain newBrain, int newMaxAge){
		brain = newBrain;
		maxAge = newMaxAge;
	}
	
	/**
	 * A getter for location
	 */
	public int[] getLocation(){
		return location;
	}
	
	/**
	 * A setter for location
	 * @param newLocation
	 */
	public void setLocation(int[] newLocation){
		location = newLocation;
	}
	
	/**
	 * A setter based on a node as argument
	 * @param newNode
	 */
	public void moveTo(Node newNode){
		location = newNode.getLocation();
	}
	
	/**
	 * The running tactics of the Ant.
	 * @param maze
	 * @param startLocation
	 * @param goalLocation
	 */
	public void run(Maze maze,int[] startLocation,int[] goalLocation){
		setLocation(startLocation);
		ArrayList<Node> ToGo = new ArrayList<Node>();
		
		// Take the first step without previous location
		path.add(maze.getNode(location));
		ToGo = maze.getNode(location).getNeighbours();
		location = brain.decide(ToGo).getLocation();

		//When we reach the goal, or when we reach maxAge
		for(int i = 0;i<maxAge||location!=goalLocation; i++){				
			path.add(maze.getNode(location));
			ToGo.clear();
			
			// We get all surrounding cells
			ToGo = maze.getNode(location).getNeighbours();
			// We remove the cell we came from last step
			ToGo.remove(path.get(i));
			
			boolean deadEnd = true;
			for(int j = 0; j<ToGo.size() || deadEnd == false ; j++){
				deadEnd = !ToGo.get(j).isAccessible();
			}
			
			if(deadEnd){
				abandon();
			}else{
				// Brain decides from ToGo were to go
				location = brain.decide(ToGo).getLocation();
			}
			path.add(maze.getNode(location));
		}
	}
	
	public void abandon(){
		// TODO Move back until we can go somewhere else.
		// TODO Pheromone to represent abandoned paths.
	}
}
