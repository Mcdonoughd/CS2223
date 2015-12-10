package algs.days.day24;

import algs.days.day23.DirectedEdge;
import algs.days.day23.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWeightedDigraph {

	// play around with some values. Controls density of graph (.13 is good value).
	static double scale = 0.13;   

	/** Construct random graph with the default scale value. */
	static EdgeWeightedDigraph random (int N) {
		return random (N, scale);
	}

	/** 
	 * Construct random graph with given scale value; smaller scale values result in fewer edges.
	 * 
	 * A scale value of sqrt(2) and greater results in a complete graph of N vertices and N*(N-1)/2 edges.
	 */
	static EdgeWeightedDigraph random(int N, double scale) {
		EdgeWeightedDigraph dg = new EdgeWeightedDigraph(N);

		// Create random points to use for determining random graphs. The points in and of themselves
		// are not useful outside of this method.
		Point[] points = new Point[N];
		for (int i = 0; i < N; i++) {
			points[i] = new Point(StdRandom.uniform(), StdRandom.uniform());
		}

		// now: edge exists with probability directly proportional to distance. Note that 
		// unit square is the basis, and smaller values of scale results in fewer edges.
		for (int i = 0; i < N-1; i++) {
			for (int j = i+1; j < N; j++) {
				double dist = points[i].distance(points[j]);
				if (dist == 0) {
					dg.addEdge(new DirectedEdge(i, j, 1+StdRandom.uniform()));
				} else {
					if (dist <= scale) {
						dg.addEdge(new DirectedEdge(i, j, 1+StdRandom.uniform()));
					}
				}
			}
		}

		return dg;
	}
}