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
		location = newNode.getLocation();
	}
	
	public void moveTo(int moveDirection){
		// Ducasse would be proud of us.
		location = maze.getNode(location).getNeighbours()[moveDirection].getLocation();
	}
	
	public void look(ArrayList<Node> list ,int direction){
		// This, not so much.
		switch(direction){
		case 0: lookEast(list);
		break;
		case 1: lookNorth(list);
		break;
		case 2: lookWest(list);
		break;
		case 3: lookSouth(list);
		break;
		}
	}
	
	public void lookEast(ArrayList<Node> list){
		// Check North, East, South in that order
		int[] checkLocation = location;
		for(int i = -1; i < 2; i++){ 
			checkLocation[0] = location[0] - i^2 + 1;
			checkLocation[1] = location[1] + i;
			maze.getNode(checkLocation).askAccess(list);
		}
	}

	public void lookNorth(ArrayList<Node> list){
		// Checks West, North, East in that order
		int[] checkLocation = location;
		for(int i = -1; i < 2; i++){ 
			checkLocation[0] = location[0] + i;
			checkLocation[1] = location[1] - i^2 + 1;
			maze.getNode(checkLocation).askAccess(list);
		}
	}

	public void lookWest(ArrayList<Node> list){
		// Checks South, West, North in that order
		int[] checkLocation = location;
		for(int i = -1; i < 2; i++){ 
			checkLocation[0] = location[0] + i^2 - 1;
			checkLocation[1] = location[1] + i;
			maze.getNode(checkLocation).askAccess(list);
		}
	}
	
	public void lookSouth(ArrayList<Node> list){
		// Checks West, South, East in that order
		int[] checkLocation = location;
		for(int i = -1; i < 2; i++){ 
			checkLocation[0] = location[0] + i;
			checkLocation[1] = location[1] + i^2 - 1;
			maze.getNode(checkLocation).askAccess(list);
		}
	}
	
	/**
	 * The running tactics of the Ant.
	 * @param maze
	 * @param startLocation
	 * @param goalLocation
	 */
	abstract public void run(Maze maze,int[] startLocation,int[] goalLocation);

}

