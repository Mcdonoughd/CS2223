package algs.days.day25;


import algs.days.day23.DirectedEdge;
import algs.days.day23.EdgeWeightedDigraph;
import algs.days.day23.Stack;
import edu.princeton.cs.algs4.StdOut;

/**
 * Can handle negative edge weights and even detects when a negative-cycle exists in the graph. 
 */
public class BellmanFord {
	private double[] distTo;                 // distTo[v] = distance of shortest s->v path
	private DirectedEdge[] edgeTo;           // edgeTo[v] = last edge on shortest s->v path
	public final boolean hasNegativeCycle;   // record this 
			
	public BellmanFord(EdgeWeightedDigraph G, int s) {
		distTo = new double[G.V()];
		edgeTo = new DirectedEdge[G.V()];
		for (int v = 0; v < G.V(); v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		distTo[s] = 0.0;

		// Perform V steps. Only V-1 are needed, but if the Vth step continues to reduce 
		// our shortest path, then there must be a negative cycle somewhere in graph (though 
		// we won't know exactly where).
		int n = G.V();

		for (int i = 1; i <= n; i++) {
			boolean leaveEarly = true;
			
			// for each edge in the graph, see if we can relax
			for (DirectedEdge e : G.edges()) {
				if (relax(e)) {
					if (i == n) {
						System.err.println ("Negative Cycle must exist!");
						hasNegativeCycle = true;
						return;
					}
					
					leaveEarly = false;
				}
			}
			
			// no edge found which improves our results? leave now...
			if (leaveEarly) { break; }
		}
		
		hasNegativeCycle = false;
	}

	// relax edge e
	boolean relax(DirectedEdge e) {
		int v = e.from();
		int w = e.to();

		if (distTo[w] > distTo[v] + e.weight()) {
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
			return true;
		}
		
		return false;
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

	public static void printPaths(EdgeWeightedDigraph G, BellmanFord sp, int s) {
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
}