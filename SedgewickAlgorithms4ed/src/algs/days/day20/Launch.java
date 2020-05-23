package algs.days.day20;

import algs.days.day20.maze.MazeGUI;

/**
 * Simpler launcher that constructs a Frame and makes it visible
 * Resize to generate smaller or larger mazes.
 * Left Click anywhere to launch a DepthFirst Search solver.
 * Right Click anywhere to launch a BreadthFirst Search solver.
 */
public class Launch {
	
	public static void main(String[] args) {
		MazeGUI frame = new MazeGUI();
		frame.setVisible(true);
	}
}
