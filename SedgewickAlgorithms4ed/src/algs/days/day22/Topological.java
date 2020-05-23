package algs.days.day22;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Topological {
	Iterable<Integer> order;  // topological order
	int[] rank;               // rank[v] = position of vertex v in topological order

	/** Determines whether the digraph <tt>G</tt> has a topological order and, if so,
	 * finds such a topological order. */
	public Topological(Digraph G) {
		DirectedCycle finder = new DirectedCycle(G);
		if (!finder.hasCycle()) {
			DirectedDFS dfs = new DirectedDFS(G);
			order = dfs.reversePostorder();
			rank = new int[G.V()];
			int i = 0;
			for (int v : order)
				rank[v] = i++;
		}
	}

	/** Returns a topological order if the digraph has a topologial order. */
	public boolean hasOrder() { return order != null; }
	public Iterable<Integer> order() { return order; }

	/** The the rank of vertex v in the topological order. */
	public int rank(int v) {
		if (hasOrder()) return rank[v];
		else            return -1;
	}

	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph dg = new Digraph(in);
		Topological top = new Topological(dg);
		if (top.hasOrder()) {
			for (int i : top.order()) {
				StdOut.print(i + " ");
			}
			StdOut.println();
		} else {
			StdOut.println ("Digraph has cycle:");
		}
	}
}