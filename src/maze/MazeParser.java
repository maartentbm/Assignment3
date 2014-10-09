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

	public Maze createMaze(File f) {

		Maze m = new Maze();

		// Prepare file read
		BufferedReader r;
		try {
			r = this._prepareFileRead(f);
		} catch (FileNotFoundException e) {
			System.out.println("File read could not be prepared. Reason: "
					+ e.getMessage());
			return m;
		}

		// Get and set maze dimensions
		int[] dim = this._getMazeDimensions(r);
		m.setWidth(dim[0]);
		m.setHeight(dim[1]);

		// Create and set grid
		Node[][] grid = this._makeGrid(r, m.getWidth(), m.getHeight());
		m.setGrid(grid);

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
						System.out
								.println("Error: maze exceeds predefined width at line "
										+ y + ".");
					}

					// Add Node to grid
					grid[y][x] = NodeFactory.createNode(sc.nextInt(), x, y);
					
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
			System.out.println("Maze file could not be read. Reason: "
					+ e.getMessage());
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
			System.out.println("Maze file could not be read. Reason: "
					+ e.getMessage());
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
	private BufferedReader _prepareFileRead(File f)
			throws FileNotFoundException {

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
			System.out.println("BufferedReader could not be closed. Reason: "
					+ e.getMessage());
		}
	}

}
