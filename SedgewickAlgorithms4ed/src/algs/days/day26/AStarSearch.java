package algs.days.day26;

public class AStarSearch  {
	
	/** Scoring function to use. */
	IScore scoringFunction;
	
	/**
	 * Prepare an A* search using the given scoring function.
	 * 
	 * @param sf   static evaluation function 
	 */ 
	public AStarSearch (IScore sf) {
		this.scoringFunction = sf;
	}
	
	/** 
	 * Initiate the search for the target state.
	 * 
	 * Store with each INode object a Transition (Move m, INode prev) so we
	 * can retrace steps to the original solution.
	 */
	public Solution search(EightPuzzleNode initial, EightPuzzleNode goal) {
		// Open state are stored in balanced 
		OpenStates open = new OpenStates();
		EightPuzzleNode copy = initial.copy();
		scoringFunction.score(copy);
		open.insert(copy); 
		
		// states we have already visited.
		SeparateChainingHashST<EightPuzzleNode, EightPuzzleNode> closed = new SeparateChainingHashST<EightPuzzleNode, EightPuzzleNode>();
		while (!open.isEmpty()) {
			 // Remove node with smallest evaluated score and insert into closed.
			EightPuzzleNode best = open.getMinimum();
			
			// Return if goal state reached.
			if (best.equals(goal)) {
				return new Solution (initial, best, true);
			}
			closed.put(best,best);
			
			// Compute successor, recording the move that makes the transition
			Transition trans = best.transition;
			int depth = 1;
			if (trans != null) { depth = trans.depth+1; }

			for (SlideMove move : best.validMoves()) {
				
				// Make move and score the new board state.
				EightPuzzleNode successor = best.copy();
				move.execute(successor);
				if (closed.contains(successor)) {
					continue;
				}
				
			    // Record previous move for solution trace and compute
			    // evaluation function to see if we have improved
				successor.transition(new Transition(move, best, depth));
				scoringFunction.score(successor);
				
				// If not yet visited, or it has better score, take care of it. Note that
				// we need these operations to be efficient:
				//    1. check if open has already seen a board
				//    2. Find that existing score associated with that board
				//    3. Remove that board from the open so it can be reinserted with lower score.
				EightPuzzleNode exist = open.contains(successor);
				if (exist == null || successor.score() < exist.score()) {
					
					// remove old one, if one had existed, and insert better one
					if (exist != null) {
						open.remove(exist);
					}
					open.insert(successor);
				}
			}
		}
		
		// No solution.
		return new Solution (initial, goal, false);
	}
}