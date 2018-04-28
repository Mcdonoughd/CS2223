package algs.days.day26.search;

import java.util.Iterator;

/**
 * Records the solution for a search from an initial state to a solved
 * goal state.
 * 
 * In some search algorithms, the goal state is known in advance; in others,
 * it is discovered as the search progresses.
 * 
 * Upon a completed search, the set of moves can be recovered.
 *
 */
public class Solution implements Iterable<SlideMove> {

	/** Initial and goal nodes. */
	public final EightPuzzleNode initial;
	public final EightPuzzleNode goal;
	
	/** Sequence of moves. */
	Bag<SlideMove> moves;
	
	/** Was this a successful search? */
	boolean success;

	/**
	 * Build the solution and work backwards with a debugger.
	 * 
	 * @param initial  initial state
	 * @param goal     final state
	 */
	public Solution (EightPuzzleNode initial, EightPuzzleNode goal, boolean success) {
		this.initial = initial;
		this.goal = goal;
		
		solve();
		this.success = success;
	}
	
	/** 
	 * Was this a successful solution?
	 * @return true if a successful solution; false otherwise. 
	 */
	public boolean succeeded() {
		return success;
	}
	
	/** 
	 * Return number of moves in the solution. 
	 * @return number of moves in the solution. 
	 */
	public int numMoves() {
		return moves.size();
	}
	
	
	/**
	 * Generate the solution for the search by working backwards to initial
	 * goal and then regenerating in forward order.
	 */
	private void solve () {
		EightPuzzleNode n = goal;
		
		// Regenerate the trail of nodes into a DoubleLinkedList. 
		moves = new Bag<SlideMove>();
		
		// work our way backwards until we terminate at the initial state.
		while (n != null) {
			Transition trans = n.transition;
			
			// gone to the end!
			if (trans == null) {
				break;
			}
			
			moves.add(trans.move);
			n = trans.prev;
		}
	}

	public Iterator<SlideMove> iterator() {
		return moves.iterator();
	}
}