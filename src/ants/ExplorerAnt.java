package ants;

import maze.Maze;
import node.Node;
import java.util.ArrayList;
import java.util.Random;

public class ExplorerAnt extends Ant {
		/**
	 	 * The maximum amount of steps an Ant may take.
	 	 */
		int maxAge; 
		
		
		/**
		 * Constructor of an ExplorerAnt
		 * @param newMaze
		 * @param newMaxAge
		 */
		public ExplorerAnt(Maze newMaze, int newMaxAge) {
			super(newMaze);
			maxAge = newMaxAge;
		}
		
		/**
		 * The specific run tactics for this type of Ant
		 * This one goes completely YOLO on your Ass. 
		 */
		public void run(Maze maze,int[] startLocation,int[] goalLocation){
			location = startLocation;
			Random random = new Random();
			ArrayList<Node> ToGo = new ArrayList<Node>();

			for(int i = 0;i<maxAge||location!=goalLocation; i++){
			path.add(maze.getNode(location));
				// direction.look();
				// look(alignment);
				// Update the list ToGo, which ever way we want to do this now.
				moveTo(ToGo.get(random.nextInt(ToGo.size( ))));
			}
		}
}