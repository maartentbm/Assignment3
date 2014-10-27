import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import maze.Maze;
import maze.MazeParser;
import maze.Path;
import maze.ResultSet;
import ants.AntRunner;
import ants.AntSetup;
import ants.Brain;
import ants.Explorer;

public class Assignment3 {

	public static void main(String[] args) throws IOException {

		// Init MazeParser
		MazeParser mp = new MazeParser();

		// Read all locations (first is start, last is end)
		ArrayList<int[]> tsp = readTSPlocations(new File("res/hardCoord.txt"), new File("res/TSPproducts.txt"));
		
		for (int i = 0; i < tsp.size(); i++) {
			for (int j = i + 1; j < tsp.size(); j++) {
				
				// Set start location
				int[] startLoc = tsp.get(i);

				// Set goal location
				int[] goalLoc = tsp.get(j);

				System.out.println("Start: (" + startLoc[0] + "," + startLoc[1] + ")");
				System.out.println("End: (" + goalLoc[0] + "," + goalLoc[1] + ")");

				try {

					// Loop until path found
					Path shortest = null;
					while (shortest == null) {

						System.out.println("Looping!");

						// Determine shortest path
						ResultSet rs = determinePath(startLoc, goalLoc, mp.createMaze(new File("res/hard maze.txt")), null);
						shortest = rs.getShortestPath();

					}

					// Create result string
					String fileString = startLoc[0] + " " + startLoc[1] + " " + goalLoc[0] + " " + goalLoc[1] + " " + shortest.size() + " " + shortest.toFileString();

					// Write string to file
					PrintWriter pw = new PrintWriter(new FileWriter("res/TSP_output.txt", true));
					pw.println(fileString);
					pw.close();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}

	/**
	 * Determine the shortest path between two coordinates in a maze using ant colony optimization.
	 * 
	 * @param start
	 * @param goal
	 * @param m
	 * @throws InterruptedException
	 */
	public static ResultSet determinePath(int[] start, int[] goal, Maze m, AntSetup a) throws InterruptedException {

		// Create runner
		AntRunner runner = new AntRunner(m, start, goal);

		if (a != null)
			runner.setAntSetup(a);

		// Run ants
		runner.start();

		// Wait for thread to finish
		runner.join();

		// Get results
		return runner.getResults();

	}

	public static ArrayList<int[]> readTSPlocations(File antCoords, File products) throws IOException {

		// Init ArrayList
		ArrayList<int[]> locations = new ArrayList<int[]>();

		/*
		 * READ PRODUCT LOCATIONS
		 */

		if (products != null) {

			// Read file ..
			BufferedReader br = new BufferedReader(new FileReader(products));

			// .. line by line
			String line = null;
			boolean first = true;

			while ((line = br.readLine()) != null) {

				// Skip first line
				if (first == true) {
					first = false;
					continue;
				}

				// Prevent npe
				if (line == null) {
					continue;
				}

				// Create new scanner for file line
				Scanner sc = new Scanner(line);

				// Loop lines in file
				while (sc.hasNextLine()) {

					// Skip first int (line number)
					sc.next();

					int[] locationPair = new int[] { Integer.parseInt(sc.next().replace(",", "")), Integer.parseInt(sc.next().replace(";", "")) };
					locations.add(locationPair);

				}

				// Close scanner
				sc.close();
			}

			// Close BRD
			br.close();

		}

		/*
		 * READ ANT START / END LOCATIONS
		 */
		Scanner sc = new Scanner(antCoords);

		int[] start = new int[] { Integer.parseInt(sc.next().replace(",", "")), Integer.parseInt(sc.next().replace(";", "")) };
		int[] end = new int[] { Integer.parseInt(sc.next().replace(",", "")), Integer.parseInt(sc.next().replace(";", "")) };

		sc.close();

		// Add start / end to ArrayList
		locations.add(0, start);
		locations.add(end);

		return locations;
	}

}
