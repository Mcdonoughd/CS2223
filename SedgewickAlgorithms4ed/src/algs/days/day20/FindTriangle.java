package algs.days.day20;

import algs.days.day19.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class FindTriangle {

	static int triangleSearch (Graph g, boolean print) {
		// find triangles. Assume no self loops, thus u can never be in g.adj(u)
		int count = 0;
		for (int u = 0; u < g.V(); u++) {             // for all vertices u...
/* V */		for (Integer v : g.adj(u)) {              //   go find a neighbor v whose id is greater
/* 2E */  	  if (u < v) {
/* E */			for (Integer w : g.adj(v)) {     	  //   then find neighbor w to v whose id is greater
					if (v < w) {                	  //   now check that w is adjacent to u.
						for (Integer x : g.adj(u)) {
							if (x == w) {
								if (print) {
									System.out.println("Triangle Found: (" + u + "," + v + "," + w + ")");
								}
								count++;
							}
						}
					}
				}
			  }
			}
		}

		return count;
	}

	public static void main(String[] args) {
		Graph g;
		int count;

		if (args.length != 0) {
			In in = new In(args[0]);
			g = new Graph(in);
			count = triangleSearch(g, true);
			System.out.println(count + " triangles found.");
			return;
		}

		int N = 64;
		while (N <= 4096) {
			
			g = new Graph(N);
			
			// with probability 1/2, generate each possible edge. Note this means
			// that the number of edges will be ~ (1/2) C(N,2) or N^2/4-N/4 
			for (int i = 0; i < N-1; i++) {
				for (int j = i+1; j < N; j++) {
					if (StdRandom.uniform() < 0.5) {
						g.addEdge(i,j);
						
					}
				}
			}
			
			count = triangleSearch(g, false);
			
			// Expected number of triangles is (1/8) C(N,3) 
			// http://www.sciencedirect.com/science/article/pii/S0012365X0400370X
			// however, this doesn't pan out. Curious to see the number of triangles
			// remain mostly constant...
			//		64		1023	5388	5208.0
			//		128		3994	40450	42672.0
			//		256		16114	39283	345440.0
			//		512		65553	44052	2779840.0
			//		1024	261793	43991	2.2304128E7

			float estimate = (1.0f*N*(N-1)*(N-2))/48;
			StdOut.println(N + "\t" + g.E() + "\t" + count + "\t" + estimate);
			N *= 2;
		}
	}

}
