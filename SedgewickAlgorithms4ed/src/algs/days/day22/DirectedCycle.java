package algs.days.day22;

import edu.princeton.cs.algs4.In;

public class DirectedCycle {
	boolean[] marked;        // marked[v] = has vertex v been marked?
	int[] edgeTo;            // edgeTo[v] = previous vertex on path to v
	boolean[] onStack;       // onStack[v] = is vertex on the stack?
	Stack<Integer> cycle;    // directed cycle (or null if no such cycle)

	/**
	 * Determines whether the digraph <tt>G</tt> has a directed cycle and, if so,
	 * finds such a cycle.
	 * @param G the digraph
	 */
	public DirectedCycle(Digraph G) {
		marked  = new boolean[G.V()];
		onStack = new boolean[G.V()];
		edgeTo  = new int[G.V()];
		for (int v = 0; v < G.V(); v++) {
			if (!marked[v] && cycle == null) {
				dfs(G, v);
			}
		}
	}

	// Conduct DFS until you visit an active vertex you are still processing. 
	void dfs(Digraph G, int v) {
		onStack[v] = true;
		marked[v] = true;

		// terminate once a cycle has been found...
		if (hasCycle()) { return; }
		
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				edgeTo[w] = v;
				dfs(G, w);
			} else {
				// might be a cycle if w is still on stack. Construct cycle on demand
				if (onStack[w]) {
					cycle = new Stack<Integer>();
					for (int x = v; x != w; x = edgeTo[x]) {
						cycle.push(x);
					}
					cycle.push(w);
					cycle.push(v);
				}
			}
		}
		onStack[v] = false; // done
	}

	/** Does the digraph have a directed cycle? Return when asked.*/
	public boolean hasCycle()        { return cycle != null; }
	public Iterable<Integer> cycle() { return cycle; }

	public static void main(String[] args) {
		In in = new In(args[0]);
		Digraph dg = new Digraph(in);

		System.out.println(dg);
		DirectedCycle cycleDetector = new DirectedCycle(dg);

		System.out.println("Has cycle:" + cycleDetector.hasCycle());
		if (cycleDetector.hasCycle()) {
			String s = "";
			for (int w : cycleDetector.cycle()) {
				s += w + "-";
			}
			System.out.println("Cycle:" + s.substring(0, s.length()-1));
		}
	}

}
