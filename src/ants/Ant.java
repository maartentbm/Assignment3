package ants;

import maze.Maze;
import node.Node;

import java.util.ArrayList;
import java.util.Random;

public class Ant {
	public Maze maze;
	
	public int[] location = new int[2];
	public ArrayList<Node> path;
	
	public brain Brain();
	
	public Ant(Brain newBrain){
		Brain = newBrain
	}
	
	public void place(Maze newMaze){
		maze = newMaze;
	}
	
	public int[] getLocation(){
		return location;
	}
	
	public void setLocation(int[] newLocation){
		location = newLocation;
	}
	
	public void moveTo(Node newNode){
		location = newNode.getLocation();
	}
	
	public void moveTo(int moveDirection){
		// Ducasse would be proud of us.
		location = maze.getNode(location).getNeighbours().get(moveDirection).getLocation();
	}
	
	/**
	 * The running tactics of the Ant.
	 * @param maze
	 * @param startLocation
	 * @param goalLocation
	 */
	public void run(Maze maze,int[] startLocation,int[] goalLocation){
		location = startLocation;
		Random random = new Random();
		ArrayList<Node> ToGo = new ArrayList<Node>();
		
		// The first time is always special
		path.add(maze.getNode(location));
		ToGo = maze.getNode(location).getNeighbours();
		location = brain(ToGo);
				
				
		for(int i = 0;i<maxAge||location!=goalLocation; i++){				
			ToGo = maze.getNode(location).getNeighbours();
			ToGo.remove(path.get(i+1));
							
			path.add(maze.getNode(location));
		}
	}
}

