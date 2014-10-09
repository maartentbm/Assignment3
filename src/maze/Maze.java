package maze;

public class Maze extends Grid {
	
	public Maze() {
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
				disp += getGrid()[y][x];
			}

			// Add right border
			disp += "[]";

		}

		// Add bottom border
		disp += "\n" + new String(new char[getWidth() + 2]).replace("\0", "[]");

		return disp;
	}

}
