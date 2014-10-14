package ants;

import maze.Maze;
import node.Node;

import java.util.ArrayList;

public class Ant {
	
	public int maxAge;
	public int[] location = new int[2];
	
	public Brain brain;
	public ArrayList<Node> path;

	public Ant(){
		brain = new Explorer();
		
	}
	
	public Ant(Brain newBrain, int newMaxAge){
		brain = newBrain;
		maxAge = newMaxAge;
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
	
	/**
	 * The running tactics of the Ant.
	 * @param maze
	 * @param startLocation
	 * @param goalLocation
	 */
	public void run(Maze maze,int[] startLocation,int[] goalLocation){
		setLocation(startLocation);
		ArrayList<Node> ToGo = new ArrayList<Node>();
		
		// The first time is always special
		path.add(maze.getNode(location));
		ToGo = maze.getNode(location).getNeighbours();
		location = brain.decide(ToGo).getLocation();

		for(int i = 0;i<maxAge||location!=goalLocation; i++){				
			path.add(maze.getNode(location));
			ToGo.clear();
			
			// We get all surrounding cells
			ToGo = maze.getNode(location).getNeighbours();
			// We remove the cell we came from last step
			ToGo.remove(path.get(i));
			
			setLocation(brain.decide(ToGo).getLocation());
			path.add(maze.getNode(location));
		}
	}
}
