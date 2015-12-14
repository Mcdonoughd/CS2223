package algs.days.day26;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Trials {
	
	static SeparateChainingHashST<EightPuzzleNode, Boolean> check;
	
	static EightPuzzleNode bfs (EightPuzzleNode n, int maxDepth) {
		Queue<EightPuzzleNode> queue = new Queue<EightPuzzleNode>();
		n = n.copy();
		n.transition = new Transition(null, n, 0);
		queue.enqueue(n);
		int depth = 0;
		
		while (!queue.isEmpty()) {
			n = queue.dequeue();
			depth = n.transition.depth;
			if (depth == maxDepth) {
				return n;
			}
			
			Bag<SlideMove> moves = n.validMoves();
			for (SlideMove m : moves) {
				EightPuzzleNode next = n.copy();
				m.execute(next);
				if (!check.contains(next)) {
					next.transition = new Transition(m, n, depth+1);
					check.put(next, true);
					queue.enqueue(next);
				}
			}
		}
		
		// return as far as we were able to get.
		System.out.println("stopping at:" + depth);
		return n;
	}
	
	static EightPuzzleNode dfs (EightPuzzleNode n, int depth, int maxDepth) {
		// found!
		if (depth == maxDepth) {
			return n;
		}
		
		// try DFS in any direction until hit maxDepth
		Bag<SlideMove> moves = n.validMoves();
		for (SlideMove m : moves) {
			EightPuzzleNode next = n.copy();
			m.execute(next);
			if (!check.contains(next)) {
				check.put(next, true);
				EightPuzzleNode answer = dfs(next, depth+1, maxDepth);
				if (answer != null) {
					return answer;
				}
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		StdRandom.setSeed(100);
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		// numMoves to make away from goal without duplicating a state.
		for (int numMoves = 4; numMoves < 32; numMoves++) {

			// make T random moves and never revisit a prior state
			check = new SeparateChainingHashST<EightPuzzleNode, Boolean>();
			//EightPuzzleNode n = dfs(goal, 0, numMoves);
			EightPuzzleNode n = bfs(goal, numMoves);
			
			if (n == null) {
				StdOut.println(numMoves + "\tUnable to find board " + numMoves + " away from solution.");
			} else {
				AStarSearch as = new AStarSearch(new GoodEvaluator());
				Solution sol = as.search(n, goal);
		
				StdOut.println(numMoves + "\t" + sol.numMoves());
			}
		}
	}
}
