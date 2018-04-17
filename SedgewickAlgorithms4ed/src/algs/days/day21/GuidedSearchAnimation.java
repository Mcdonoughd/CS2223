package algs.days.day21;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public class GuidedSearchAnimation extends AnimationEngine {

	boolean explore (int s, int target) {
		MinPQ<Point> pq = new MinPQ<Point>(N);
		color = new int[N];
		
		// set known target in Point for comparisons.
		Point.target = points[target];
		for (int i = 0; i < N; i++) {
			points[i].id = i;
		}

		// positions in the Queue are Gray and under investigation.
		pq.insert(points[s]);
		while (!pq.isEmpty()) {
			Point p = pq.delMin();
			for (int v : g.adj(p.id)) {
				if (color[v] == White) {
					color[v] = Gray;
					StdDraw.setPenColor(colors[color[v]]);
					StdDraw.filledCircle(points[v].x, points[v].y, Radius);
					StdDraw.show(delay);
					
					checkPause();  // see if request pause by space bar. 
					
					if (v == target) {
						color[v] = Black;
						StdDraw.setPenColor(colors[color[v]]);
						StdDraw.filledCircle(points[v].x, points[v].y, Radius);
						return true;
					}

					pq.insert(points[v]);
				}
			}
			
			color[p.id] = Black;
			StdDraw.setPenColor(colors[color[p.id]]);
			StdDraw.filledCircle(p.x, p.y, Radius);
			StdDraw.show(delay);
		}

		return false;
	}
	
	public static void main(String[] args) {
		if (args.length > 0) {
			StdRandom.setSeed(Long.parseLong(args[0]));
		}
		new GuidedSearchAnimation().launch();
	}
} 
