package algs.days.day25;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class HamiltonianCycle {

	int V;
	int[] path;     
	boolean[] marked;
	boolean matrix[][];

	// if there is a cycle, we can start at any vertex. Choose 0.
	public void findHamiltonianCycle(Graph g) {
		V = g.V();
		// grab matrix for quick processing
		matrix = new boolean[V][V];
		marked = new boolean[V];
		for (int u = 0; u < V; u++) {
			for (int v : g.adj(u)) {
				matrix[u][v] = true;
			}
		}

		path = new int[V];
		for (int i = 0; i < V; i++) {
			path[i] = -1;
		}

		path[0] = 0;      
		marked[0] = true;
		if (!solve(0, 0)) {
			System.out.println("No solution");
		} else {
			StdOut.print("0-");
			for (int i = 0; i < V-1; i++) {
				StdOut.print(path[i] + "-");
			}
			StdOut.println(0);
		}

	}

	public boolean solve(int v, int count) {

		/** Done when last vertex in path has edge back to the source=0. */
		if (count == V-1) {
			return matrix[v][0];
		}

		// extend path in direction that exists and is not yet marked.
		for (int w = 0; w < V; w++) {
			if (!marked[w] && matrix[v][w]) {
				path[count] = w;    
				marked[w] = true;

				if (solve(w, count+1)) {
					return true;
				}
				marked[w] = false;
				path[count] = -1;                    
			}
		}
		
		// no more.
		return false; 
	}    

	public static void main (String[] args) {
		HamiltonianCycle hc = new HamiltonianCycle();
		Graph g;
		if (args.length == 1) {
			In in = new In(args[0]);
			g = new Graph(in);
			hc.findHamiltonianCycle(g);
		} else {
			for (int N = 4; N <= 128; N*=2) {
				Stopwatch w = new Stopwatch();
				g = RandomGraph.random(N, 0.45);
				hc.findHamiltonianCycle(g);
				StdOut.println(N + "\t" + w.elapsedTime());
			}
		}
		        
	}    

}