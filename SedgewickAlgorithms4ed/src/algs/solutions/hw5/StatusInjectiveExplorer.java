package algs.solutions.hw5;

import edu.princeton.cs.algs4.StdOut;

public class StatusInjectiveExplorer {
	public static void main(String[] args) {
		// 7 gives 15120 (which equals 3*7!)
		// 8 gives 483840 (which equals 12*8!)
		// 9 gives 
		int N=9;
		int ct = 0;
		
		DirectedEdge[] edges = new DirectedEdge[N*(N-1)/2];
		int idx = 0;
		for (int i = 0; i < N-1; i++) {
			for (int j = i+1; j < N; j++) {
				edges[idx++] = new DirectedEdge(i, j, 1);
			}
		}
		
		// each possible trial encodes an edge setting
		for (long t = 0; t < Math.pow(2, N*(N-1)/2); t++) {
			Graph g = new Graph(N);
			
			long mask = 1;
			for (int b = 0; b <= N*(N-1)/2; b++) {
				if ((t & mask) != 0) {
					g.addEdge(edges[b].v, edges[b].w);
				}
				mask = mask << 1;
			}
			
			// at this point edges are all added.
			if (g.statusInjective()) {
				//StdOut.println(g.toString());
				ct++;
			}
		}
		
		StdOut.println(N + "\t" + ct);
	}
}
