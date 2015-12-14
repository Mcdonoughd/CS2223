package algs.days.day20;

import java.util.Iterator;

import algs.days.day20.astar.IndexMinPQ;
import algs.days.day20.astar.SeparateChainingHashST;

/**
 * THIS depends on code written for the day26 lecture. Because I went back in time to include
 * this functionality as a comparison.
 */
public class AStarSolver extends Thread {
	// GUI into that maze
	final MazePanel panel;
	final Maze      maze;
	
	/** Color records the progress of the Breadth First Search. */
	final int color [][];
	
	/** Maintain distance from source. */
	final int g[][];
	
	// ids must be between 0 and N-1 so we make it R*NUMCOLS + C
	
	/**
	 * Construct solver with the GUI that needs to be refreshed as the progress continues, and 
	 * store our destination.
	 */ 
	public AStarSolver (MazePanel panel) {
		this.panel = panel;
		this.maze = panel.maze;
		color = new int[maze.numrows][maze.numcols];
		g = new int[maze.numrows][maze.numcols];
		
		this.panel.setProgress(color);
	}
	
	// 5x Manhattan distance
	int distanceToGoal(Position p) {
		return 5*(Math.abs(p.row - maze.destination.row) + Math.abs(p.col - maze.destination.col));
	}
	int id(Position p) {
		return maze.numcols*p.row + p.col;
	}
	Position pos(int id) {
		return new Position(id/maze.numcols, id % maze.numcols);
	}
	
	boolean astarVisit(Position pos) {
		IndexMinPQ<Integer> openSet = new IndexMinPQ<Integer>(maze.numrows*maze.numcols);
		SeparateChainingHashST<Integer,Boolean> closed = new SeparateChainingHashST<Integer,Boolean>();
		
		// positions in the Queue are Gray and under investigation.
		g[pos.row][pos.col] = 0;
		openSet.insert(id(pos), distanceToGoal(pos)+g[pos.row][pos.col]);
		
		color[pos.row][pos.col] = Maze.Gray;
		panel.redraw();
		panel.repaint();

		while (!openSet.isEmpty()) {
			// get closest by distanceToGoal.
			Position cell = pos(openSet.delMin());
			closed.put(id(cell), true);
			
			for (Iterator<Position> it = maze.finalNeighbors[cell.row][cell.col].iterator(); it.hasNext(); ) {
				Position next = it.next();
				
				if (closed.contains(id(next))) {
					continue;
				}
				
				// not yet seen, perhaps we have hit solution
				if (next.equals(maze.destination)) {
					color[next.row][next.col] = Maze.Black;
					panel.redraw();
					panel.repaint();
					return true;
				}
				
				
				// if White then haven't seen at all.
				if (color[next.row][next.col] == Maze.White) {
					// increased one distance.
					g[next.row][next.col] = g[cell.row][cell.col] + 1;
					
					openSet.insert(id(next), distanceToGoal(next)+g[next.row][next.col]);
					
					color[next.row][next.col] = Maze.Gray;
					panel.redraw();
					panel.repaint();
				} else {
					// perhaps we are improving a score? Might happen since AStar doesn't fix BFS path
					g[next.row][next.col] = g[cell.row][cell.col] + 1;
					
				}
			}
			
			color[cell.row][cell.col] = Maze.Black;
			panel.redraw();
			panel.repaint();
		}
		
		return false;
	}
	
	public void run () {
		astarVisit(maze.start);
		System.out.println("Done...");
		this.panel.setProgress(null);
	}

}
