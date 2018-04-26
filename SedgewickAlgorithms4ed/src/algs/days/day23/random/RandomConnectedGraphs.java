package algs.days.day23.random;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import algs.days.day19.Graph;
import algs.days.day23.Stack;

/**
 * Generates random graphs using a flawed methodology. Specifically, given a graph with V vertices,
 * it randomly chooses two different vertices using uniform random numbers. The edge is then added
 * to the graph.
 * 
 */
public class RandomConnectedGraphs {
	static boolean[] marked;    // marked[v] = is there an s-v path?
	static Graph graph;
	static int TRIALS = 1024;

	
	// depth first search from v. Note a traditional DFS will cause stack overflow for graphs with size above 4096, so this 
	// shows an alternate non-recursive approach using a Stack to store the state.
	static void dfs(int v) {
		Stack<Integer> state = new Stack<Integer>();
		marked[v] = true;
		state.push(v);
		while (!state.isEmpty()) {
			v = state.pop();
			
			for (int w : graph.adj(v)) {
				if (!marked[w]) {
					marked[w] = true;
					state.push(w);
				}
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
	static int runUndirectedTrial (int n, double p, int numTrials) {
		int numConnected = 0;
		
		for (int t = 0; t < numTrials; t++) {
			if (runOneTrial(n, p)) {
				numConnected++;
			}
		}
		
		return numConnected;
	}
	
	
	/** Run a single trial. */
	static boolean runOneTrial (int n, double p) {
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
		
		return connected;
	}
	
	/** 
	 * Use binary array search to try to find the edge. 
	 * 
	 * Presumably with p=1.0 the graph is fully connected. Keep trying to cut region in half 
	 */
	public static void probe () {
		
		
		double epsilon = 0.0001;
		
		for (int n = 8; n <= 512; n*= 2) {
			double lo = 0; 
			double hi = 1.0;
			double mid = 0;
			while (hi-lo > epsilon) {
				mid = (lo + hi)/2;
				
				// sample on just 200 random graphs.
				if (runUndirectedTrial(n, mid, 200) == 200) {
					// we are still connected. Go to left and reduce
					hi = mid;
				} else {
					// try to go back up
					lo = mid;
				}
			}
			
			System.out.printf("%d\t%.5f\n", n, mid);
		}
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
		4096	0		337		1003	1024	1024 	1024	1024	1024	1024
	*/
	
	public static void main(String[] args) {
		
		probe();
		
		StdOut.printf("N\t");
		double lo = 0.001;
		double delta = 0.001;
		double hi = 0.01;
		
		for (double p = lo; p <= hi; p += delta) { StdOut.printf("%.3f\t", p); }
		StdOut.println();
		for (int n = 8; n <= 4096; n*= 2) {
			StdOut.printf("%d\t", n);
			for (double p = lo; p <= hi; p += delta) {
				int numCon = runUndirectedTrial(n, p, TRIALS);
				StdOut.printf("%d\t", numCon);
			}
			StdOut.println();
		}
	}
}
