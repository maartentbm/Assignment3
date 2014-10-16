import java.io.File;

import maze.Maze;
import maze.MazeParser;
import ants.Ant;
import ants.AntRunner;
import ants.Explorer;

public class Assignment3 {

	public static final int EAST = 0;
	public static final int NORTH = 1;
	public static final int WEST = 2;
	public static final int SOUTH = 3;
	
	public static void main(String[] args) {
		
		MazeParser mp = new MazeParser();
		
		// Easy maze
		Maze easy = mp.createMaze(new File("res/easy maze.txt"));
		System.out.println(easy);
		
		// Run ants!
		AntRunner runner = new AntRunner(easy, new int[]{1,1}, new int[]{25,1});
		runner.start();
		
		System.out.println("\n");
		
		// Medium maze
//		Maze medium = mp.createMaze(new File("res/medium maze.txt"));
//		System.out.println(medium);
	}

}
