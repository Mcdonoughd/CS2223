package algs.days.day26.search;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Trials {
	
	static SeparateChainingHashST<EightPuzzleNode, Boolean> check;
	
	static int numVisitedBFS = 0;
	static void bfs (EightPuzzleNode start, EightPuzzleNode target) {
		Queue<EightPuzzleNode> queue = new Queue<EightPuzzleNode>();
		
		queue.enqueue(start);
		
		while (!queue.isEmpty()) {
			EightPuzzleNode n = queue.dequeue();
			numVisitedBFS++;
			
			// stop once found
			if (n.equals(target)) {
				return;
			}
			
			Bag<SlideMove> moves = n.validMoves();
			for (SlideMove m : moves) {
				EightPuzzleNode next = n.copy();
				m.execute(next);
				if (!check.contains(next)) {
					check.put(next, true);
					queue.enqueue(next);
				}
			}
		}
		
	}
	
	static int numVisitedDFS = 0;
	static EightPuzzleNode dfs (EightPuzzleNode start, EightPuzzleNode target, int depth, int maxDepth) {
		// found! or dead end
		if (target.equals(start)) { 
			return target;
		}
		if (depth == maxDepth) {
			return null;
		}
		numVisitedDFS++;
		
		// try DFS in any direction until hit maxDepth
		Bag<SlideMove> moves = start.validMoves();
		for (SlideMove m : moves) {
			EightPuzzleNode next = start.copy();
			m.execute(next);
			if (!check.contains(next)) {
				check.put(next, true);
				EightPuzzleNode answer = dfs(next, target, depth+1, maxDepth);
				if (answer != null) {
					return answer;
				}
			}
		}
		
		return null;
	}
	
	public static SlideMove chooseRandom(Bag<SlideMove> moves) {
		int n = moves.size();
		int rnd = StdRandom.uniform(n);
		
		Bag<SlideMove>.Node node = moves.first;
		while (rnd-- > 0) {
			node = node.next;
		}

		return node.item;
	}
	
	public static void main(String[] args) {
		StdRandom.setSeed(100);
		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		StdOut.println("Following shows the number of PuzzleNodes investigated for the different approaches.");
		StdOut.println("The Final column shows the identified A* Solution in distance from the start.\n");
		// numMoves to make away from goal without duplicating a state.
		StdOut.println("N\tDFS\tBFS\tA*\tSolutionLength");
		for (int numMoves = 4; numMoves < 30; numMoves++) {

			// make T random moves and never revisit a prior state
			check = new SeparateChainingHashST<EightPuzzleNode, Boolean>();
			EightPuzzleNode n = goal.copy();
			check.put(n, true);
			for (int i = 0; i < numMoves; i++) {
				SlideMove sm = chooseRandom (n.validMoves());
				EightPuzzleNode next = n.copy();
				if (sm.execute(next)) {
					// check if we have already seen. Ignore the move if we go back to a prior state
					// we have already seen.
					if (!check.contains(next)) {
						n = next;
						check.put(n, true);
					}
				}
			}
			
			check = new SeparateChainingHashST<EightPuzzleNode, Boolean>();
			dfs(goal, n, 0, numMoves);
			
			check = new SeparateChainingHashST<EightPuzzleNode, Boolean>();
			bfs(goal, n);
			
			if (n == null) {
				StdOut.println(numMoves + "\tUnable to find board " + numMoves + " away from solution.");
			} else {
				AStarSearch as = new AStarSearch(new GoodEvaluator());
				Solution sol = as.search(n, goal);
		
				StdOut.printf("%d\t%d\t%d\t%d\t%d\n", numMoves, numVisitedDFS, numVisitedBFS, as.numVisited, sol.numMoves());
			}
		}
	}
}
