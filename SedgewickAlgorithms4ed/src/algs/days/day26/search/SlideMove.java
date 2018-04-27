package algs.days.day26.search;

public class SlideMove {

	/** tile being moved. */
	public final int tile;
	
	/** row coordinate of the move's source. */
	public final int fromR;
	
	/** column coordinate of the move's source. */
	public final int fromC;
	
	/** row coordinate of the move's destination. */
	public final int toR;
	
	/** column coordinate of the move's destination. */
	public final int toC;
	
	/**
	 * Move from (fromC, fromR) to (toC, toR)
	 * 
	 * @param tile   tile to move.
	 * @param fromR  row coordinate of the move's source.
	 * @param fromC  column coordinate of the move's source.
	 * @param toR    row coordinate of the move's destination
	 * @param toC    column coordinate of the move's destination
	 */
	public SlideMove (int tile, int fromR, int fromC, int toR, int toC) {
		this.tile = tile;
		this.fromR = fromR;
		this.fromC = fromC;
		this.toR = toR;
		this.toC = toC;
	}
	
	/**
	 * Execute the move on the given board state.
	 * 
	 * @param state   state on which to execute the move.
	 */
	public boolean execute(EightPuzzleNode state) {
		if (state.isAdjacentAndEmpty(fromR, fromC, toR, toC)) {
			return state.swap(fromR, fromC, toR, toC);
		}
		
		// no move possible
		return false;
	}

	/**
	 * Determine if move is valid for the given state.
	 * 
	 * @param state     game state in which move is evaluated.
	 * @exception   IllegalArgumentException if n is not an EightPuzzleNode
	 */
	public boolean isValid(EightPuzzleNode state) {
		if (fromR < 0 || fromR > EightPuzzleNode.MaxC) { return false; }
		if (fromC < 0 || fromC > EightPuzzleNode.MaxR) { return false; }
		if (toR < 0 || toR > EightPuzzleNode.MaxC) { return false; }
		if (toC < 0 || toC > EightPuzzleNode.MaxR) { return false; }
		
		return state.isAdjacentAndEmpty(fromR, fromC, toR, toC);
	}

	/** 
	 * Assume move had been valid, so the undo is a straightforward swap.
	 *  
	 * Note that we simply swap the fromC/fromR and toC/toR in the invocation.
	 * @param n    game state whose move is to be undone.  
	 */
	public boolean undo(EightPuzzleNode state) {
		return state.swap(toR, toC, fromR, fromC);
	}
	
	/** Reasonable implementation. */
	public String toString () {
		return "move " + tile;
	}
}