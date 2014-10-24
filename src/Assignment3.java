import java.io.File;

import maze.Maze;
import maze.MazeParser;
import maze.ResultSet;
import ants.Ant;
import ants.AntRunner;
import ants.Explorer;

public class Assignment3 {

	public static void main(String[] args) {

		MazeParser mp = new MazeParser();

		// Easy maze - which is to hard for now
		Maze m = mp.createMaze(new File("res/medium maze.txt"));
		System.out.println(m);

		// System.exit(0);

		// Run ants!
		try {

			// Set start location
			int[] startLoc = new int[] { 0, 0 };

			// Set goal location
			int[] goalLoc = new int[] { 49, 39 };

			// Create
			AntRunner runner = new AntRunner(m, startLoc, goalLoc);
			runner.start();

			// Wait for thread to finish
			runner.join();

			// Get results
			ResultSet res = runner.getResults();
			
			// Print results to file
			res.printToFile(new File("res/test.txt"));

			// Display results in console
			if (res.getShortestPath() != null) {

				System.out.println("Shortest path: " + res.getShortestPath());
				System.out.println("Shortest path: " + res.getShortestPath().getDirectionPath());
				System.out.println("       length: " + res.getShortestPath().size());

			} else {
				
				System.out.println("No path found!");
				
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/*
		 * REMINDERS Test1 S: 0,0 G: 4,0 Test2 S: 0,0 G: 0,4 Test3 S: 0,2 G: 7,2
		 */

	}

}
