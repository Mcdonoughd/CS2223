package algs.days.day21;

import java.awt.Color;

import algs.days.day19.Graph;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

public abstract class AnimationEngine {

	static Graph g;
	static Point[] points;
	
	static int N;
	static int color[];
	static int delay = 10;


	static double scale = 0.13;   // play around with some values. Controls density of graph (.13 is good value).
	
	static double Radius = 0.01;
	static int White = 0;
	static int Gray = 1;
	static int Black = 2;
	static Color colors[] = { Color.gray, Color.blue, Color.green };
	
	/** Filled in by subclasses. */
	abstract boolean explore(int s, int target);
	
	/** Allow animation to be paused if space key is down. */
	void checkPause() {

		// pause on space bar...
		while (StdDraw.isKeyPressed(32)) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				// not much to do.
			}
		}
		
	}
	
	void drawLine(Color c, int u, int v) {
		Point p = points[u];
		Point q = points[v];
		StdDraw.setPenColor(c);
		StdDraw.line(p.x, p.y, q.x, q.y);
	}

	void runTrial(int N) {
		g = new Graph(N);
		
		// Create random points 
		points = new Point[N];
		for (int i = 0; i < N; i++) {
			points[i] = new Point(StdRandom.uniform(), StdRandom.uniform());
		}

		// compute index of leftmost point and rightmost point
		int leftX = 0;
		int rightX = 0;
		for (int i = 1; i < N; i++) {
			if (points[i].x < points[leftX].x) { leftX = i; }
			if (points[i].x > points[rightX].x) { rightX = i; }
		}

		// now: edge exists with probability directly proportional to inverse distance
		for (int i = 0; i < N-1; i++) {
			for (int j = i+1; j < N; j++) {
				double dist = points[i].distance(points[j]);
				if (dist == 0) {
					g.addEdge(i,j);
				} else {
					if (dist < scale) {
						g.addEdge(i,j);
					}
				}
			}
		}

		// set the scale of the coordinate system
		StdDraw.setXscale(0, 1.0);
		StdDraw.setYscale(0, 1.0);

		// draw graph
		for (int v = 0; v < N; v++) {
			Point p = points[v];
			for (int u : g.adj(v)) {
				drawLine(Color.gray, u, v);
			}
		}
		
		// 
		StdDraw.show(100); // wait 100 milliseconds.
			
		explore(leftX, rightX);  // BFS
	}
	
	/** Must be called by subclass main. */
	public void launch() {
		N = 128;
		
		while (N <= 2048) {
			runTrial(N);
	
			while (!StdDraw.mousePressed()) {
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					// not much to do.
				}
			}
			
			N *= 2;
			StdDraw.clear();
		}
	}
} 
