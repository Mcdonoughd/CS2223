package algs.days.day25;

import edu.princeton.cs.algs4.StdOut;
import algs.days.day23.DigraphAdjacencyList;
import algs.days.day23.DijkstraSP;
import algs.days.day23.DirectedEdge;

public class BadDijkstra {
	public static void main(String[] args) {
		// example graph showing Dijkstra provides different values than Bellman-Ford
		DigraphAdjacencyList digraph = new DigraphAdjacencyList(5); 
		digraph.addEdge(new DirectedEdge(0, 1, 3));
		digraph.addEdge(new DirectedEdge(0, 4, 1));
		digraph.addEdge(new DirectedEdge(1, 2, -2));
		digraph.addEdge(new DirectedEdge(4, 2, 1));
		digraph.addEdge(new DirectedEdge(2, 3, 2));
		
		DijkstraSP sp = new DijkstraSP(digraph, 0);
		
		BellmanFord bf = new BellmanFord(digraph, 0);

		StdOut.println ("Dijkstra");
		sp.printPaths(digraph, sp, 0);
		
		StdOut.println();
		StdOut.println ("Bellman-Ford");
		bf.printPaths(digraph, bf, 0);
	}
}
