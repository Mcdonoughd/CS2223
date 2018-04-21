package algs.days.day23.random;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import algs.days.day19.Graph;
import algs.days.day22.Digraph;

/**
 * Generates random graphs using a flawed methodology. Specifically, given a graph with V vertices,
 * it randomly chooses two different vertices using uniform random numbers. The edge is then added
 * to the graph.
 * 
 */
public class RandomConnectedGraphs {
	static boolean[] marked;    // marked[v] = is there an s-v path?
	static Graph graph;
	static Digraph digraph;
	static int TRIALS = 1024;

	
	// depth first search from v
	static void dfs(int v) {
		marked[v] = true;
		for (int w : graph.adj(v)) {
			if (!marked[w]) {
				dfs(w);
			}
		}
	}
	
	// depth first search from v
	static void di_dfs(int v) {
		marked[v] = true;
		for (int w : digraph.adj(v)) {
			if (!marked[w]) {
				di_dfs(w);
			}
		}
	}
	
	/** Check if iterator contains the value. */
	static boolean contains (Iterable<Integer> it, int value) {
		for (int x : it) {
			if (x == value) { return true; }
		}
		
		return false;
	}
	
	/**
	 * There are V*(V-1)/2 potential edges in a graph. With a given probability p (0..1) each 
	 * edge is added.
	 * 
	 * Return how many of TRIALS are actually connected.
	 * 
	 * @param n
	 * @return
	 */
	static int runUndirectedTrial (int n, double p) {
		int numConnected = 0;
		
		for (int t = 0; t < TRIALS; t++) {
			graph = new Graph(n);
			marked = new boolean[n];
			
			for (int u = 0; u < n-1; u++) {
				for (int v = u+1; v < n; v++) {
					if (StdRandom.uniform() <= p) {
						graph.addEdge(u,v);
					}
				}
			}
			dfs(0);
			boolean connected = true;
			for (int i = 0; i < marked.length; i++) {
				if (!marked[i]) { connected = false; break; }
			}
			
			if (connected) { numConnected++; }
		}
		
		return numConnected;
	}
	
	/**
	 *  how many random edges do we need to add to ensure graph of N vertices is connected.
	 *  The following table comes from using f
	 *  
	 * Note that for large enough graphs, you can see that even for very low values of p, 
	 * it is almost guaranteed that the graph will remain connected.
	 * 
		N		0.001	0.002	0.003	0.004	0.005	0.006	0.007	0.008	0.009	
		8		0		0		0		0		0		0		0		0		0	
		16		0		0		0		0		0		0		0		0		0	
		32		0		0		0		0		0		0		0		0		0	
		64		0		0		0		0		0		0		0		0		0	
		128		0		0		0		0		0		0		0		0		0	
		256		0		0		0		0		0		0		0		0		0	
		512		0		0		0		0		0		0		0		0		9	
		1024	0		0		0		0		3		112		466		768		927	
		2048	0		0		16		588		952		1015	1023	1024	1024	
		4096	0		337		1003	1024	1024 ...
	*/
	
	public static void main(String[] args) {
		StdOut.printf("N\t");
		double lo = 0.001;
		double delta = 0.001;
		double hi = 0.01;
		
		for (double p = lo; p <= hi; p += delta) { StdOut.printf("%.3f\t", p); }
		StdOut.println();
		for (int n = 8; n <= 65536; n*= 2) {
			StdOut.printf("%d\t", n);
			for (double p = lo; p <= hi; p += delta) {
				int numCon = runUndirectedTrial(n, p);
				StdOut.printf("%d\t", numCon);
			}
			StdOut.println();
		}
	}
}
