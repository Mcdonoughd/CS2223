package algs.days.day26.search;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class DFSSearchAnimation extends AnimationEngine {

	// max depth of 32 to give a chance of success.
	// note: takes advantage of some preliminary analysis that suggests any 8-puzzle is no more than 
	// 31 moves away from a solution.
	final int MaxDepth = 31; 
	
	boolean explore (EightPuzzleNode n) {
		Stack<EightPuzzleNode> stack = new Stack<EightPuzzleNode>();
		SeparateChainingHashST<EightPuzzleNode, Boolean> visited = new SeparateChainingHashST<EightPuzzleNode, Boolean>();
		

		// positions in the Stack are Gray and under investigation.
		stack.push(n);
		n.transition = new Transition(null, null, 0);
		while (!stack.isEmpty()) {
			EightPuzzleNode u = stack.pop();

			// stop searching after exceeds depth.
			if (u.transition.depth > MaxDepth) {
				continue;
			}
			
			for (SlideMove sm : u.validMoves()) {
				EightPuzzleNode next = u.copy();
				sm.execute(next);
				if (!visited.contains(next)) {
					visited.put(next, true);
					next.transition = new Transition(sm, u, u.transition.depth+1);
					StdDraw.setPenColor(colors[Gray]);
					Point p = getLocation(next);
					drawLine(colors[Gray], u, next);
					StdDraw.filledCircle(p.x, p.y, Radius);
					StdDraw.show(delay);
					
					if (next.equals(goal)) {
						StdDraw.setPenColor(colors[Black]);
						StdDraw.filledCircle(p.x, p.y, Radius);
						return true;
					}

					stack.push(next);
				}
			}
			
			StdDraw.setPenColor(colors[Black]);
			Point p = getLocation(u);
			StdDraw.filledCircle(p.x, p.y, Radius);
			StdDraw.show(delay);
		}

		return false;
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			StdRandom.setSeed(Long.parseLong(args[0]));
		}
		new DFSSearchAnimation().launch();
	}
} 
