import java.io.File;

import maze.Maze;
import maze.MazeParser;

public class Assignment3 {

	public static final int NORTH = 1;
	public static final int EAST = 0;
	public static final int SOUTH = 3;
	public static final int WEST = 2;
	
	public static void main(String[] args) {
		
		MazeParser mp = new MazeParser();
		
		// Easy maze
		Maze easy = mp.createMaze(new File("res/easy maze.txt"));
		System.out.println(easy);
		
		System.out.println("\n");
		
		// Medium maze
		Maze medium = mp.createMaze(new File("res/medium maze.txt"));
		System.out.println(medium);
	}

}
