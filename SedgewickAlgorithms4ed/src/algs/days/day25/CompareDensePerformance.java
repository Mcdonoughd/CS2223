package algs.days.day25;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import algs.days.day23.DigraphAdjacencyList;
import algs.days.day23.DigraphAdjacencyMatrix;
import algs.days.day23.DijkstraSP;
import algs.days.day23.DirectedEdge;
import algs.days.day23.EdgeWeightedDigraph;

public class CompareDensePerformance {
	public static void main(String[] args) {

		/** Generate random complete graph of N nodes, and perform Dijkstra SP algorithm. */
		for (int N = 128; N <= 4096; N *= 2) {
			EdgeWeightedDigraph list = new DigraphAdjacencyList(N);
			EdgeWeightedDigraph matrix = new DigraphAdjacencyMatrix(N);
			
			/** Generate random graph, p=30% of the time edge exists. */
			for (int u = 0; u < N-1; u++) {
				for (int v = u+1; v < N; v++) {
					int start = u;
					int end = v;					
					double weight = StdRandom.uniform();
					
					if (StdRandom.uniform() < 0.3) {
						DirectedEdge e = new DirectedEdge(start, end, weight);
						list.addEdge(e);
						matrix.addEdge(e);
					}
				}
			}
			
			/** Perform DSP on both. */
			System.gc();
			Stopwatch list_sw = new Stopwatch();
			DijkstraSP list_sp = new DijkstraSP(list, 0);
			double sw_list = list_sw.elapsedTime();
			
			Stopwatch matrix_sw = new Stopwatch();
			DijkstraSP matrix_sp = new DijkstraSP(matrix, 0);
			double sw_matrix = matrix_sw.elapsedTime();
			
			StdOut.printf("%d\t%d\t%f\t%f\n", N, list.E(), sw_list, sw_matrix);			
		}
	}
}
