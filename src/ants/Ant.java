package ants;

import maze.Maze;
import node.Node;
import java.util.ArrayList;

public abstract class Ant {
	public Maze maze;
	
	public int[] location = new int[2];
	public int alignment;
	public ArrayList<Node> path;
	
	public Ant(Maze newMaze){
		maze = newMaze;
		alignment = 0;
	}
	
	public int[] getLocation(){
		return location;
	}
	
	public void setLocation(int[] newLocation){
		location = newLocation;
	}
	public int getAlignment(){
		return alignment;
	}
	public void setAlignment(int newDirection){
		alignment = newDirection;
	}
	
	public void moveTo(Node newNode){
		// TODO move Ant according to Node
	}
	
	public void moveTo(int moveDirection){
		// TODO move Ant according to direction
	}
	
	/**
	 * The running tactics of the Ant.
	 * @param maze
	 * @param startLocation
	 * @param goalLocation
	 */
	abstract public void run(Maze maze,int[] startLocation,int[] goalLocation);

}
