package algs.days.day26.search;

public class GoodEvaluator implements IScore {
	/** Known goal state. */
	static EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
			{1,2,3},{8,0,4},{7,6,5}
	});
	
	/** index values [r][c] for proper locations, based on goal above. */
	static int[][] diffs = new int[][] {
		{1, 1},      // who cares about the blank? But here for consistency
		{0, 0},      // location where 1 belongs [0][0]
		{0, 1},
		{0, 2},
		{1, 2}, 
		{2, 2},
		{2, 1}, 
		{2, 0},
		{1, 0}
	};

	/**
	 * Description of this measure.
	 */
	public String toString() {
		return "P(n) + 3*S(n) where P(n) is the sum of the "              + "\n" +
			   "Manhattan distances that each tile is from \"home.\""     + "\n" +
			   "S(n) is a sequence score that checkes the non-"           + "\n" +
			   "central squares in turn, allotting 2 for every tile not"  + "\n" +
			   "followed by its proper successor and 0 for every "        + "\n" +
			   "other tile, except that a piece in the center scores 1.";
	}
	
	/**
	 * Location of the successor for each position, by location. We can take 
	 * 3*c+r as the index into succs and use that to generate the (r,c) of the 
	 * successive location clockwise around the central square.
	 */
	static int[][] succs = new int[][] {
		{0,1},     /* 0: top middle.          012         */
		{0,2},     /* 1: top right.           345         */
		{1,2},     /* 2: right middle.        678         */
		{0,0},     /* 3: top-left.                        */
		{-1,-1},   /* 4: central square has no successor. */
		{2,2},     /* 5: bottom right.                    */
		{1,0},     /* 6: left middle.                     */
		{2,0},     /* 7: bottom left.                     */
		{2,1},     /* 8: bottom middle.                   */
	};
	
	/** successors of individual tiles [deal with 8->1 successor evenly.] */
	static int[] succ = new int[] { 
		0,                     /** no successor for blank */
		2, 3, 4, 5, 6, 7, 8,   /** successors of 1..7. */
		1                      /** successor of 8. */
	};
	
	/**
	 * @see algs.model.searchtree.IScore#score(INode)
	 */
	public void score(EightPuzzleNode state) {
		state.score(eval(state));
	}
	
	/**
	 * h^(n) = P(n) + 3*S(n), where P(n) is the sum of the distances (via Manhattan
	 * metric) that each tile is from "home". S(n) is a sequence score obtained
	 * by checking around the non-central squares in turn, allotting 2 for every 
	 * tile not followed by its proper successor and 0 for every other tile, except
	 * that a piece in the center scores 1.
	 * <p>
	 * Compute f^(n) = g^(n) + h^(n) where g(n) is the length of the path form the start node
	 * n to the state.
	 * 
	 * @see algs.model.searchtree.IScore#score(INode)
	 * @param state   game state to be evaluated
	 * @return        integer evaluation.
	 */
	public int eval(EightPuzzleNode node) {
		// Each tile is between 0 and 4 moves away from its proper position.
		int Pn = 0;
		for (int r = 0; r <= EightPuzzleNode.MaxR; r++) {
			for (int c = 0; c <= EightPuzzleNode.MaxC; c++) {
				if (node.isEmpty(r,c)) { continue; }
				
				int digit = node.cell(r, c);
				Pn += Math.abs(diffs[digit][0] - r);
				Pn += Math.abs(diffs[digit][1] - c);
			}
		}
		
		// Compute S(N) by adding 2 points for every tile not followed by its proper successor
		// note that center tile square counts 1.
		int Sn = 0;
		if (!node.isEmpty(1, 1)) { Sn = 1; }

		for (int r = 0; r <= EightPuzzleNode.MaxR; r++) {
			for (int c = 0; c <= EightPuzzleNode.MaxC; c++) {
				int idx = r*3+c;
				
				// skip central square
				if (r == 1 && c == 1) {
					continue;
				}
				
				// skip the empty tile.
				if (node.cell(r,c) == EightPuzzleNode.EmptyMark) {
					continue;
				}
				
				// create array to deal with successor of 8->1
				if (succ[node.cell(r, c)] != node.cell(succs[idx][0], succs[idx][1])) {
					Sn += 2;
				}
			}
		}

		// compute g^(n)
		int gn = 0;
		Transition t = node.transition;
		if (t != null) { 
			gn = t.depth; 
		}
		
		return gn + Pn + 3*Sn;
	}
	
}