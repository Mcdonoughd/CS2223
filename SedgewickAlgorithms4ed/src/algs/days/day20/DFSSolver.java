package algs.days.day20;

import java.util.Iterator;

/**
 * Solver conducts a modified Depth First Search. Specifically, it stops prematurely when a specific
 * destination has been reached.
 */
public class DFSSolver extends Thread {
	// GUI into that maze
	final MazePanel panel;
	final Maze      maze;
	
	/** Color records the progress of the Depth First Search. */
	final int color [][];
	
	/**
	 * Construct solver with the GUI that needs to be refreshed as the progress continues, and 
	 * store our destination.
	 */ 
	public DFSSolver (MazePanel panel) {
		this.panel = panel;
		this.maze = panel.maze;
		color = new int[maze.numrows][maze.numcols];
		
		this.panel.setProgress(color);
	}
	
	boolean dfsVisit(Position pos) {
		color[pos.row][pos.col] = Maze.Gray;
		panel.redraw();
		panel.repaint();

		// immediately force all processing to unwind...
		if (pos.equals (maze.destination)) {
			return true;
		}
		
		for (Iterator<Position> it = maze.finalNeighbors[pos.row][pos.col].iterator(); it.hasNext(); ) {
			Position cell = it.next();
			if (color[cell.row][cell.col] == Maze.White) {
				if (dfsVisit(cell)) {
					return true;
				}
			}
		}

		color[pos.row][pos.col] = Maze.Black;
		panel.redraw();
		panel.repaint();
		return false;
	}
	
	public void run () {
		try {
			dfsVisit(maze.start);
			System.out.println("normal termination");
			System.out.println("Done...");
		} catch (StackOverflowError e) {
			System.out.println ("Reecursion too deep to complete DFS.");
        }
        
		this.panel.setProgress(null);
	}

}
