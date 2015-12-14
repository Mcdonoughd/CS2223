package algs.days.day26;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class DFSSearchAnimation extends AnimationEngine {

	boolean explore (EightPuzzleNode n) {
		Stack<EightPuzzleNode> stack = new Stack<EightPuzzleNode>();
		SeparateChainingHashST<EightPuzzleNode, Boolean> visited = new SeparateChainingHashST<EightPuzzleNode, Boolean>();
		

		// positions in the Queue are Gray and under investigation.
		stack.push(n);
		while (!stack.isEmpty()) {
			EightPuzzleNode u = stack.pop();

			for (SlideMove sm : u.validMoves()) {
				EightPuzzleNode next = u.copy();
				sm.execute(next);
				if (!visited.contains(next)) {
					visited.put(next, true);
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
