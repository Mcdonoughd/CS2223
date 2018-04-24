package algs.days.day23;

import java.io.File;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class DijkstraSP {
	private double[] distTo;        // distTo[v] = distance of shortest s->v path
	private DirectedEdge[] edgeTo;  // edgeTo[v] = last edge on shortest s->v path
	private IndexMinPQ<Double> pq;  // priority queue of vertices

	public DijkstraSP(EdgeWeightedDigraph G, int s) {
		distTo = new double[G.V()];
		edgeTo = new DirectedEdge[G.V()];
		for (int v = 0; v < G.V(); v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		distTo[s] = 0.0;

		// relax vertices in order of distance from s.
		pq = new IndexMinPQ<Double>(G.V());
		pq.insert(s, distTo[s]);
		for (int v = 0; v < G.V(); v++) {
			if (v != s) {
				pq.insert(v, Double.POSITIVE_INFINITY);
			}
		}

		// PQ contains all vertices; only one has distance of 0, while others are all +infinity.
		while (!pq.isEmpty()) {
			int v = pq.delMin();

			// see if we can find a shorter path through the vertex we have popped off. 
			for (DirectedEdge e : G.adj(v))
				relax(e);
		}
	}

	// relax edge e and update PQ if changed
	void relax(DirectedEdge e) {
		int v = e.from();
		int w = e.to();

		if (distTo[w] > distTo[v] + e.weight()) {
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
			pq.decreaseKey(w, distTo[w]);
		}
	}

	/** Returns the length of a shortest path from the source vertex s to v. */
	public double distTo(int v) { return distTo[v]; }

	/** Determine if path from s to v. */
	public boolean hasPathTo(int v) { return distTo[v] < Double.POSITIVE_INFINITY; }

	/** Returns a shortest path from the source vertex s to v. */
	public Iterable<DirectedEdge> pathTo(int v) {
		if (!hasPathTo(v)) return null;
		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}

	public static void printPaths(EdgeWeightedDigraph G, DijkstraSP sp, int s) {
		// print shortest path for a number of sample graphs.
		for (int t = 0; t < G.V(); t++) {
			if (sp.hasPathTo(t)) {
				StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
				for (DirectedEdge e : sp.pathTo(t)) {
					StdOut.print(e + "   ");
				}
				StdOut.println();
			}
			else {
				StdOut.printf("%d to %d         no path\n", s, t);
			}
		}
	}

	/**
	 * Unit tests the <tt>DijkstraSP</tt> data type.
	 */
	public static void main(String[] args) {
		
		// inclass example
		EdgeWeightedDigraph sample = new DigraphAdjacencyList(new In(new File ("day24.txt")));
		DijkstraSP sample_sp = new DijkstraSP(sample, 0);
		for (int v = 0; v < sample.V(); v++) {
			StdOut.println(v + ":" + sample_sp.distTo(v));
			for (DirectedEdge e : sample_sp.pathTo(v)) {
				StdOut.print(e + " ");
			}
			StdOut.println();
		}
		
		File dir = new File ("src");
		File dir2 = new File (dir, "algs");
		File dir3 = new File (dir2, "days");
		File dir4 = new File (dir3, "day23");
		File dir5 = new File (dir4, "benchmark");

		for (int n = 2; n <= 64; n *= 2) {
			In in = new In(new File (dir5, "bench-1." + n + ".dat"));
			//EdgeWeightedDigraph G = new DigraphAdjacencyMatrix(in);
			EdgeWeightedDigraph G = new DigraphAdjacencyList(in);
			int s = 0;

			// compute shortest paths
			System.gc();
			Stopwatch watch = new Stopwatch();
			DijkstraSP sp = new DijkstraSP(G, s); 
			System.out.printf("%d\t%.2f\n", n, watch.elapsedTime());
			// printPaths(G, sp,s);
		}

	}
}