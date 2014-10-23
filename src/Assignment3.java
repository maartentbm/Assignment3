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

		// Easy maze - which is to hard for now
		Maze easy = mp.createMaze(new File("res/easy maze.txt"));
		System.out.println(easy);
		// Run ants!
		AntRunner runner = new AntRunner(easy, new int[]{0,0}, new int[]{24,14});
		runner.start();
		
		/*	REMINDERS
		 * Test1 S: 0,0 G: 4,0
		 * Test2 S: 0,0 G: 0,4
		 * Test3 S: 0,2 G: 7,2
		 * 
		 */
		
//		Maze Test = mp.createMaze(new File("res/Test4-e.txt"));
//		System.out.println(Test);
//		System.out.println("\n");
//		AntRunner runnerTest1 = new AntRunner(Test, new int[]{8,1}, new int[]{8,7});
//		runnerTest1.start();

		// Medium maze
//		Maze medium = mp.createMaze(new File("res/medium maze.txt"));
//		System.out.println(medium);
	}

}
