import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import ants.AntSetup;
import ants.Brain;
import ants.Explorer;
import ants.Follower;
import maze.Maze;
import maze.MazeParser;
import maze.Path;
import maze.ResultSet;

public class CalculateHard {

	/**
	 * Manually change this when a new highscore is found!
	 */

	// 928
	public static int CURRENT_HIGHSCORE = 922;

	public static void main(String[] args) throws IOException {

		// Init MazeParser
		MazeParser mp = new MazeParser();

		// Hard AntSetup
		AntSetup hardAntSetup = new AntSetup();

		// Init waves
		ConcurrentHashMap<Brain, Integer> wave_01, wave_02;

		wave_01 = new ConcurrentHashMap<Brain, Integer>();
		wave_01.put(new Explorer(), 500);
		hardAntSetup.add(wave_01);

		for (int i = 0; i < 100; i++) {
			wave_02 = new ConcurrentHashMap<Brain, Integer>();
			wave_02.put(new Follower(), 50);
			hardAntSetup.add(wave_02);
		}

		// Read all locations (first is start, last is end)
		ArrayList<int[]> tsp = Assignment3.readTSPlocations(new File("res/hardCoord.txt"), null);

		for (int i = 0; i < tsp.size(); i++) {
			for (int j = i + 1; j < tsp.size(); j++) {

				// Set start location
				int[] startLoc = tsp.get(i);

				// Set goal location
				int[] goalLoc = tsp.get(j);

				try {

					// Loop until path found
					Path shortest = null;
					while (shortest == null || shortest.size() >= CURRENT_HIGHSCORE) {

						// Determine shortest path
						Maze m = mp.createMaze(new File("res/hard maze.txt"));
						ResultSet rs = Assignment3.determinePath(startLoc, goalLoc, m, hardAntSetup, (int) (CURRENT_HIGHSCORE * 1.2));
						shortest = rs.getShortestPath();

						int newSize = shortest.size();
						
						// New highscore!
						if (newSize < CURRENT_HIGHSCORE) {
							
							CURRENT_HIGHSCORE = newSize;
							
							rs.printToFile(new File("res/hard_test_results.txt"));
							System.out.println("Shorter path found!\nSize:" + newSize + "\nPath:" + shortest);

						}

					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				break;
			}
			break;
		}

	}

}
