package algs.days.day26;

public interface IScore {
	
	/** 
	 * Evaluate the given state and return an integer that is to be used during
	 * search algorithms.
	 * <p>
	 * In general, the lower the score, the closer one is to a desired goal state.
	 * 
	 * @param state    The board state to be evaluated. It is unchanged.
	 * @return         integer value for board evaluation (in general, low scores are closer to goal state).
	 */
	int eval (EightPuzzleNode state);
	
	/**
	 * Evaluate the given state and update its score using our scoring function.
	 * 
	 * @param state    The board state whose score value is to be updated.
	 */
	void score (EightPuzzleNode state);
}