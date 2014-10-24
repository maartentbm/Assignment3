package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import node.Node;

public class ResultSet extends ArrayList<Path> {

	public Path getShortestPath() {

		Path shortest = null;

		for (Path p : this) {

			if (shortest == null || p.size() < shortest.size()) {
				shortest = p;
			}

		}

		return shortest;

	}

	public void printToFile(File f) {

		// Get shortest path
		Path p = getShortestPath();

		if (p == null || p.size() == 0) {
			System.out.println("ERROR: Couldn't write result file because shortest path is unknown.");
			return;
		}

		// Start location
		int[] startLocation = p.get(0).getLocation();

		// Get direction path
		ArrayList<Integer> dirPath = getShortestPath().getDirectionPath();
		int pathLength = dirPath.size();

		// Init printwriter
		try {
			PrintWriter pw = new PrintWriter(f);

			// Create file
			pw.println(pathLength + ";");
			pw.println(startLocation[0] + ", " + startLocation[1] + ";");

			for (Integer i : dirPath) {
				pw.print(i + ";");
			}
			
			pw.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
