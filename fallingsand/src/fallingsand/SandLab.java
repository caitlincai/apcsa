package fallingsand;

import java.awt.*;
import java.util.*;
import java.awt.Color;
import java.util.Random;

public class SandLab {
	public static void main(String[] args) {
		SandLab lab = new SandLab(120, 80);
		lab.run();
	}

	// add constants for particle types here
	public static final int EMPTY = 0;
	public static final int METAL = 1;
	public static final int SAND = 2;
	public static final int WATER = 3;
	public static final int GLITTER = 4;

	Color black = Color.BLACK;
	Color grey = Color.GRAY;
	Color yellow = Color.YELLOW;
	Color blue = Color.BLUE;
	Color pink = Color.PINK;

	// do not add any more fields
	private int[][] grid;
	private SandDisplay display;

	public SandLab(int numRows, int numCols) {
		String[] names;
		names = new String[5];
		names[EMPTY] = "Empty";
		names[METAL] = "Metal";
		names[SAND] = "Sand";
		names[WATER] = "Water";
		names[GLITTER] = "Glitter";
		display = new SandDisplay("Falling Sand", numRows, numCols, names);
		grid = new int[numRows][numCols];
	}

	// called when the user clicks on a location using the given tool
	private void locationClicked(int row, int col, int tool) {
		grid[row][col] = tool;
	}

	// copies each element of grid into the display
	public void updateDisplay() {
		for (int i = 0; i < 120; i++) {
			for (int j = 0; j < 80; j++) {
				if (grid[i][j] == EMPTY) {
					display.setColor(i, j, black);
				} else if (grid[i][j] == METAL) {
					display.setColor(i, j, grey);
				} else if (grid[i][j] == SAND) {
					display.setColor(i, j, yellow);
				} else if (grid[i][j] == WATER) {
					display.setColor(i, j, blue);
				} else if (grid[i][j] == GLITTER) {
					display.setColor(i, j, pink);
				}
			}
		}
	}

	// called repeatedly.
	// causes one random particle to maybe do something.
	int down = 0;
	int left = 1;
	int right = 2;
	int up = 3;

	public void step() {
		int r = (int) (Math.random() * grid.length);
		int c = (int) (Math.random() * grid[0].length);
		int direction = (int) (Math.random() * 3);

		if (grid[r][c] == SAND) {
			if (grid[r][c] == SAND && r < grid.length - 1 && grid[r + 1][c] == EMPTY) {
				grid[r][c] = EMPTY;
				grid[r + 1][c] = SAND;
			} else if (grid[r][c] == SAND && r < grid.length - 1 && grid[r + 1][c] == WATER) {
				grid[r][c] = WATER;
				grid[r + 1][c] = SAND;
			}
		}
		if (grid[r][c] == WATER) {
			move(r, c, direction, WATER);
		}
		if (grid[r][c] == GLITTER) {
			move(r, c, (int)(Math.random() * 3) + 1, GLITTER);
		}
	}

	public void move(int r, int c, int direction, int tool) {
		if (direction == up && r > 0) {
			if (grid[r-1][c] == EMPTY) {
				grid[r][c] = EMPTY;
				grid[r-1][c] = tool;
			}
			if (grid[r + 1][c] != EMPTY) {
				grid[r][c] = grid[r][c];
			} 
		}
		if (direction == down && r < grid.length - 1) {
			if (grid[r + 1][c] == EMPTY) {
				grid[r][c] = EMPTY;
				grid[r + 1][c] = tool;
			}
			if (grid[r + 1][c] != EMPTY) {
				grid[r][c] = grid[r][c];
			} 
		}
		if (direction == left && c > 0) {
			if (grid[r][c - 1] == EMPTY)  {
				grid[r][c] = EMPTY;
				grid[r][c - 1] = tool;
			}
			if (grid[r][c-1] != EMPTY) {
				grid[r][c] = grid[r][c];
			} 
		}
		if (direction == right && c < grid[0].length - 1) {
			if (grid[r][c + 1] == EMPTY) {
				grid[r][c] = EMPTY;
				grid[r][c + 1] = tool;
			}
			if (grid[r][c+1] != EMPTY) {
				grid[r][c] = grid[r][c];
			} 
		}
	}

	// do not modify
	public void run() {
		while (true) {
			for (int i = 0; i < display.getSpeed(); i++)
				step();
			updateDisplay();
			display.repaint();
			display.pause(1); // wait for redrawing and for mouse
			int[] mouseLoc = display.getMouseLocation();
			if (mouseLoc != null) // test if mouse clicked
				locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
		}
	}
}
