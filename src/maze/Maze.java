package maze;

import java.util.ArrayList;

import ants.Ant;
import ants.AntRunner;
import node.Node;

public class Maze extends Grid {

	public void evaporate(float pheromoneEvaporation, AntRunner ar, int wave) {

		System.out.print(wave + " ");

		// Strenghten current shortest path
		// Path p = ar.getResults().getShortestPath();
		// for(int i = 0; i < wave; i++) {
		// Ant.spreadPheromone(p);
		// }

		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				getGrid()[j][i].setPheromoneLevel(getGrid()[j][i].getPheromoneLevel() * (1 - pheromoneEvaporation));
			}
		}

	}

	public String toString() {

		String disp = "";

		// Add top border
		disp += new String(new char[getWidth() + 2]).replace("\0", "[]");

		// Loop maze rows
		for (int y = 0; y < getHeight(); y++) {

			// Add left border
			disp += "\n[]";

			// Loop maze columns
			for (int x = 0; x < getWidth(); x++) {
				disp += getGrid()[y][x].toMazeString();
			}

			// Add right border
			disp += "[]";

		}

		// Add bottom border
		disp += "\n" + new String(new char[getWidth() + 2]).replace("\0", "[]");

		return disp;
	}

	public String toPheromoneString() {

		String disp = "";

		// Add top border
		disp += new String(new char[getWidth() + 2]).replace("\0", "[======]");

		// Loop maze rows
		for (int y = 0; y < getHeight(); y++) {

			// Add left border
			disp += "\n[======]";

			// Loop maze columns
			for (int x = 0; x < getWidth(); x++) {
				disp += getGrid()[y][x].toPheromoneString();
			}

			// Add right border
			disp += "[======]";

		}

		// Add bottom border
		disp += "\n" + new String(new char[getWidth() + 2]).replace("\0", "[======]");

		return disp;
	}

	/**
	 * Evaluate maze and find shortest path based on pheromone level.
	 * 
	 * @param startLocation
	 * @return
	 */
	public Path findShortestPath(int[] startLocation, int[] goalLocation) {

		Path p = new Path();

		// Check validity of start- and goalLocation
		Node start = getNode(startLocation);
		Node goal = getNode(goalLocation);

		if (start != null && goal != null) {

			// Add startlocation to path
			p.add(start);
			Node current = start;

			while (true) {

				if (current == null || current.equals(goal))
					break;

				Node next = this._findNeighbourWithHighestPheromoneLevel(current, p);

				if (next == null) {
					System.out.println("Could not find any neighbour with highest pheromone from Node " + current);
					return null;
				} else {

					p.add(current);
					current = next;

				}

			}

		}

		p.add(goal);

		// Return path
		return p;
	}

	private Node _findNeighbourWithHighestPheromoneLevel(Node n, Path p) {

		ArrayList<Node> nbours = n.getNeighbours();

		// Check
		if (nbours == null || nbours.size() == 0)
			return null;

		// Remove origin node
		if (nbours.contains(n)) {
			nbours.remove(n);
		}

		double maxPheromone = Double.MIN_VALUE;
		int indexOfMax = -1;

		for (int i = 0; i < nbours.size(); i++) {

			Node c = nbours.get(i);

			if (p.contains(c))
				continue;

			if (c != null && c.isAccessible() && c.getPheromoneLevel() > maxPheromone) {
				indexOfMax = i;
				maxPheromone = c.getPheromoneLevel();
			}

		}

		if (indexOfMax >= 0) {
			return nbours.get(indexOfMax);
		} else {
			return null;
		}

	}

}