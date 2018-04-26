package algs.days.day25;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import algs.days.day23.DigraphAdjacencyList;
import algs.days.day23.DijkstraSP;
import algs.days.day23.DirectedEdge;
import algs.hw5.SeparateChainingHashST;

// searches for a graph (randomly) that fails Dijkstra but succeeds with Bellman Ford
public class DijkstraFailsNegative {
	public static void main(String[] args) {
		int n = 5;
		
		while (true) {
			DigraphAdjacencyList digraph = new DigraphAdjacencyList(n); // new In("day25.txt"));  // tinyEWDn
			SeparateChainingHashST<String, Boolean> used = new SeparateChainingHashST<>();
			for (int i = 1; i < 6; i++) {
				int rndn = 1;
				if (StdRandom.uniform() < 0.2) { rndn = -1; }
				int weight = rndn*StdRandom.uniform(4);
				if (weight == 0) { weight = 1; }
				DirectedEdge de = new DirectedEdge(StdRandom.uniform(n), StdRandom.uniform(n), weight);
				if (de.from() != de.to() && !used.contains(de.from() + ":" + de.to())) {
					digraph.addEdge(de);
					used.put(de.from()+":" + de.to(), true);
				}
			}
			
	
			DijkstraSP sp = new DijkstraSP(digraph, 0);
			
			BellmanFord bf = new BellmanFord(digraph, 0);
			if (bf.hasNegativeCycle) { continue; }
			
			for (int i = 0; i < n; i++) {
				if (sp.distTo(i) != bf.distTo(i)) {
					StdOut.println(digraph.toString());
					
					sp.printPaths(digraph, sp, 0);
					bf.printPaths(digraph, bf, 0);
					System.exit(1);
				}
			}
		}
	}
}
