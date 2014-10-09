package maze;

import node.Node;

public class Grid {

	private Node[][] grid;
	private int width, height;
	
	public Grid() {}
	
	public Grid(int width, int height) {
		this.grid = new Node[height][width];
		setWidth(width);
		setHeight(height);
	}

	public Node[][] getGrid() {
		return grid;
	}

	public void setGrid(Node[][] grid) {
		this.grid = grid;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Node getNode(int x, int y) {
		return this.grid[y][x];
	}
	
	public void setNode(Node n, int x, int y) {
		this.grid[y][x] = n;
	}
}
