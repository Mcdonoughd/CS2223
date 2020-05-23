package algs.days.day21;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class BFSSearchAnimation extends AnimationEngine {

	boolean explore(int s, int target) {
		
		Queue<Integer> queue = new Queue<Integer>();
		color = new int[N];

		// positions in the Queue are Gray and under investigation.
		queue.enqueue(s);
		color[s] = Gray;
		while (!queue.isEmpty()) {
			Integer u = queue.dequeue();

			checkPause();  // see if request pause by space bar. 
			
			for (int v : g.adj(u)) {
				if (color[v] == White) {
					color[v] = Gray;
					StdDraw.setPenColor(colors[color[v]]);
					StdDraw.filledCircle(points[v].x, points[v].y, Radius);
					StdDraw.show(delay);
					
					if (v == target) {
						color[v] = Black;
						StdDraw.setPenColor(colors[color[v]]);
						StdDraw.filledCircle(points[v].x, points[v].y, Radius);
						return true;
					}

					queue.enqueue(v);
				}
			}
			
			color[u] = Black; // done with vertex
			StdDraw.setPenColor(colors[color[u]]);
			StdDraw.filledCircle(points[u].x, points[u].y, Radius);
			StdDraw.show(delay);
		}

		return false;
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			StdRandom.setSeed(Long.parseLong(args[0]));
		}
		new BFSSearchAnimation().launch();
	}
} 
