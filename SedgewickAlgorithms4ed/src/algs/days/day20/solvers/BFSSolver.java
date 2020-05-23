package algs.days.day20.solvers;

import java.util.Iterator;

import algs.days.day20.Queue;
import algs.days.day20.maze.Maze;
import algs.days.day20.maze.MazePanel;
import algs.days.day20.maze.Position;

/**
 * Solver conducts a modified Breadth First Search. Specifically, it stops prematurely when a specific
 * destination has been reached.
 */
public class BFSSolver extends PausableThread {
	// GUI into that maze
	final MazePanel panel;
	final Maze      maze;
	
	/** Color records the progress of the Breadth First Search. */
	final int color [][];
	
	/**
	 * Construct solver with the GUI that needs to be refreshed as the progress continues, and 
	 * store our destination.
	 */ 
	public BFSSolver (MazePanel panel) {
		this.panel = panel;
		this.maze = panel.getMaze();
		color = new int[maze.numrows][maze.numcols];
		
		this.panel.setProgress(color);
	}
	
	boolean bfsVisit(Position pos) {
		Queue<Position> queue = new Queue<Position>();

		// positions in the Queue are Gray and under investigation.
		queue.enqueue(pos);
		color[pos.row][pos.col] = Maze.Gray;
		panel.redraw();
		panel.repaint();

		while (!queue.isEmpty()) {
			checkPaused();
			Position cell = queue.dequeue();
			
			for (Iterator<Position> it = maze.finalNeighbors[cell.row][cell.col].iterator(); it.hasNext(); ) {
				Position next = it.next();
				
				if (next.equals(maze.destination)) {
					color[next.row][next.col] = Maze.Black;
					panel.redraw();
					panel.repaint();
					return true;
				}
				
				if (color[next.row][next.col] == Maze.White) {
					queue.enqueue(next);
					color[next.row][next.col] = Maze.Gray;
					panel.redraw();
					panel.repaint();
				}
			}
			
			color[cell.row][cell.col] = Maze.Black;
			panel.redraw();
			panel.repaint();
		}
		
		return false;
	}
	
	public void run () {
		bfsVisit(maze.start);
		System.out.println("Done...");
		this.panel.setProgress(null);
	}

}
