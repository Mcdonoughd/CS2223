package algs.days.day26.search;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

// not done yet

public class AStarSearchAnimation extends AnimationEngine {

	boolean explore(EightPuzzleNode n) {
		Queue<EightPuzzleNode> queue = new Queue<EightPuzzleNode>();
		SeparateChainingHashST<EightPuzzleNode, Boolean> visited = new SeparateChainingHashST<EightPuzzleNode, Boolean>();
		String str;
		
		// positions in the Queue are Gray and under investigation.
		queue.enqueue(n);
		visited.put(n, true);
		n.transition = new Transition(null, null, 0);  // only here for graphics
		while (!queue.isEmpty()) {
			EightPuzzleNode u = queue.dequeue();
			System.out.println(u);
			str = u.toString();
			for (SlideMove sm : u.validMoves()) {
				EightPuzzleNode next = u.copy();
				next.transition = new Transition(sm, u, u.transition.depth+1); // only here for graphics
				
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

					queue.enqueue(next);
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
		new AStarSearchAnimation().launch();
	}
} 
