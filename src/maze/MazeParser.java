package maze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import node.Node;
import node.NodeFactory;

public class MazeParser {

	public static final float DEFAULT_PHEROMONE_LEVEL = 0.01f;
	
	public Maze createMaze(File f) {

		Maze m = new Maze();

		// Prepare file read
		BufferedReader r;
		try {
			r = this._prepareFileRead(f);
		} catch (FileNotFoundException e) {
			System.out.println("File read could not be prepared. Reason: " + e.getMessage());
			return m;
		}

		// Get and set maze dimensions
		int[] dim = this._getMazeDimensions(r);
		m.setWidth(dim[0]);
		m.setHeight(dim[1]);

		// Create and set grid
		Node[][] grid = this._makeGrid(r, m.getWidth(), m.getHeight());
		m.setGrid(grid);

		// Link nodes
		this._linkNodes(m);

		// Return maze
		return m;

	}

	private Node[][] _makeGrid(BufferedReader r, int width, int height) {

		Node[][] grid = new Node[height][width];

		// Read lines
		List<String> lines = this._loadFile(r);

		// Verify the file contains anything
		if (lines.size() > 0) {

			for (int y = 0; y < height; y++) {

				// Initialize scanner
				Scanner sc = new Scanner(lines.get(y));

				// Set current x position to 0
				int x = 0;

				// Loop integers in line
				while (sc.hasNextInt()) {

					// Check boundaries
					if (x >= width) {
						System.out.println("Error: maze exceeds predefined width at line " + y + ".");
					}

					// Add Node to grid
					grid[y][x] = NodeFactory.createNode(sc.nextInt(), new int[] { x, y });
					grid[y][x].setPheromoneLevel(DEFAULT_PHEROMONE_LEVEL);

					// Increase x-coord
					x++;
				}

				// Close scanner
				sc.close();

			}

		} else {
			System.out.println("Maze file is empty.");
		}

		return grid;

	}

	/**
	 * Read first line of maze file to get maze dimensions.
	 * 
	 * @param r
	 * @return
	 */
	private int[] _getMazeDimensions(BufferedReader r) {

		try {

			String dim_str = null;

			// Read file
			if (r.ready()) {
				dim_str = r.readLine();
			}

			if (dim_str != null) {

				// Init scanner
				Scanner sc = new Scanner(dim_str);
				int[] dim = new int[2];

				// Read dimensions
				dim[0] = sc.nextInt();
				dim[1] = sc.nextInt();

				// Close scanner
				sc.close();

				return dim;

			}

		} catch (IOException e) {
			System.out.println("Maze file could not be read. Reason: " + e.getMessage());
		}

		return new int[0];

	}

	/**
	 * Read all lines of file from BufferedReader.
	 * 
	 * @param f
	 * @return
	 */
	private List<String> _loadFile(BufferedReader r) {

		List<String> lines = new ArrayList<String>();

		try {

			// Read file
			while (r.ready()) {
				lines.add(r.readLine());
			}

		} catch (IOException e) {
			System.out.println("Maze file could not be read. Reason: " + e.getMessage());
		}

		return lines;
	}

	/**
	 * Open BufferedReader for file reading.
	 * 
	 * @param f
	 * @return
	 * @throws FileNotFoundException
	 */
	private BufferedReader _prepareFileRead(File f) throws FileNotFoundException {

		// Load reader
		return new BufferedReader(new FileReader(f));

	}

	/**
	 * Close BufferedReader.
	 * 
	 * @param r
	 */
	private void _closeFileRead(BufferedReader r) {
		try {
			r.close();
		} catch (IOException e) {
			System.out.println("BufferedReader could not be closed. Reason: " + e.getMessage());
		}
	}

	private void _linkNodes(Grid g) {

		// Verify grid exists
		if (g == null) {
			System.out.println("[Node linking] Grid does not exist.");
			return;
		}

		// Verify grid contain anything
		if (g.getWidth() <= 0 && g.getHeight() <= 0) {
			System.out.println("[Node linking] Grid does not contain anything.");
			return;
		}

		// Loop grid
		int[] loc = { 0, 0 };
		Node current;
		for (int y = 0; y < g.getHeight(); y++) {
			loc[1] = y;
			for (int x = 0; x < g.getWidth(); x++) {
				loc[0] = x;

				// Get node
				current = g.getNode(loc);

				// Skip if no node at location
				if (current == null)
					continue;

				// Set NORTH
				try {
					Node north = g.getNode(new int[] { x, y - 1 });
					current.setNorth(north);
				} catch (ArrayIndexOutOfBoundsException e) {
					current.setNorth(null);
				}

				// Set EAST
				try {
					Node east = g.getNode(new int[] { x + 1, y });
					current.setEast(east);
				} catch (ArrayIndexOutOfBoundsException e) {
					current.setEast(null);
				}

				// Set SOUTH
				try {
					Node south = g.getNode(new int[] { x, y + 1 });
					current.setSouth(south);
				} catch (ArrayIndexOutOfBoundsException e) {
					current.setSouth(null);
				}

				// Set WEST
				try {
					Node west = g.getNode(new int[] { x - 1, y });
					current.setWest(west);
				} catch (ArrayIndexOutOfBoundsException e) {
					current.setWest(null);
				}

			}
		}

	}
}
