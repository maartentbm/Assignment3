package ants;

import maze.Maze;
import node.Node;
import java.util.ArrayList;
import java.util.Random;

public class ExplorerAnt extends Ant {
		int maxAge; 
		
		public ExplorerAnt(Maze newMaze, int newMaxAge) {
			super(newMaze);
			maxAge = newMaxAge;
		}

		public void run(Maze maze,int[] startLocation,int[] goalLocation){
			location = startLocation;
			Random random = new Random();

			for(int i = 0;i<maxAge||location!=goalLocation; i++){
			path.add(maze.getNode(location));
			
			}
		}
		
		public void lookNorth()){
			int[] checkLocation = location;
			for(int i = -1; i < 2; i++){ 
				checkLocation[0] = location[0] + i;
				checkLocation[1] = location[1] + abs(i)-1;
			}
		}
		
		public void abandon(){
			
		}
}