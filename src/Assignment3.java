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
		AntRunner runner = new AntRunner(easy, new int[]{0,0}, new int[]{25,15});
		runner.start();
		
				boolean[] test = new boolean[]{false,false,false,false,false};
		
		if(test[0]){
		Maze Test1 = mp.createMaze(new File("res/Test1.txt"));
		System.out.println(Test1);
		System.out.println("\n");
		AntRunner runnerTest1 = new AntRunner(Test1, new int[]{1,1}, new int[]{5,1});
		runnerTest1.start();
		}
		
		if(test[1]){
		Maze Test2 = mp.createMaze(new File("res/Test2.txt"));
		System.out.println(Test2);
		System.out.println("\n");
		AntRunner runnerTest2 = new AntRunner(Test2, new int[]{1,3}, new int[]{8,3});
		runnerTest2.start();
		}
		
		// Medium maze
//		Maze medium = mp.createMaze(new File("res/medium maze.txt"));
//		System.out.println(medium);
	}

}
