package algs.days.day24;

import edu.princeton.cs.algs4.StdOut;
import algs.days.day23.DijkstraSP;
import algs.days.day23.DirectedEdge;
import algs.days.day23.EdgeWeightedDigraph;

public class Example {
	public static void main(String[] args) {
		EdgeWeightedDigraph dg = new EdgeWeightedDigraph(6);
		dg.addEdge(new DirectedEdge(0, 1, 10));
		dg.addEdge(new DirectedEdge(0, 2, 6));
		
		dg.addEdge(new DirectedEdge(1, 3, 4));
		
		dg.addEdge(new DirectedEdge(2, 1, 2));
		dg.addEdge(new DirectedEdge(2, 3, 9));
		
		dg.addEdge(new DirectedEdge(3, 5, 7));
		
		dg.addEdge(new DirectedEdge(5, 2, 3));
		dg.addEdge(new DirectedEdge(5, 4, 4));
		
		// first demonstrate from 0
		DijkstraSP dp = new DijkstraSP(dg, 0);
		for (int i = 0; i < dg.V(); i++) {
			StdOut.print(dp.distTo(i) + " ");
		}
		StdOut.println();
		StdOut.println();
		
		
		DijkstraAllPairsSP dsp = new DijkstraAllPairsSP(dg);
		
		for (int i = 0; i < dg.V(); i++) {
			for (int j = 0; j < dg.V(); j++) {
				StdOut.print(dsp.dist(i, j) + " ");
			}
			StdOut.println();
		}
		
		
	}
}
