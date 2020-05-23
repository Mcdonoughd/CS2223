package algs.days.day26.search;

public class Transition {
	public final SlideMove move;        // actual move
	public final EightPuzzleNode prev;  // previous state
	public final int depth;             // distance from source 
	
	/** Record the move and previous state of this transition. */
	public Transition (SlideMove move, EightPuzzleNode prev, int depth) {
		this.move = move;
		this.prev = prev;
		this.depth = depth;
	}

}