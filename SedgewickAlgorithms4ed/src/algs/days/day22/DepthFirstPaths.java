package algs.days.day22;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstPaths {
	boolean[] marked;    // marked[v] = is there an s-v path?
	int[] edgeTo;        // edgeTo[v] = last edge on s-v path
	final int s;         // source vertex
	final Graph g;       // Graph being searched.

	public DepthFirstPaths(Graph g, int s) {
		this.s = s;
		this.g = g;
		edgeTo = new int[g.V()];
		marked = new boolean[g.V()];
		dfs(s);
	}

	// depth first search from v
	private void dfs(int s) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(s);
		while (!stack.isEmpty()) {
			int v = stack.pop();
			marked[v] = true;
			for (int w : g.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					dfs(w);
				}
			}
		}
	}

	public boolean hasPathTo(int v) { return marked[v]; }

	/** recover path from s to v. */
	public Iterable<Integer> pathTo(int target) {
		if (!hasPathTo(target)) return null;
		
		Stack<Integer> path = new Stack<Integer>();
		int v = target;
		while (v != s) {
			path.push(v);
			v = edgeTo[v];
		}

		// last one to push is the source, which makes it
		// the first one to be retrieved
		path.push(s);
		return path;
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		Graph g = new Graph(in);
		
		// conduct a DFS over entire graph
		DepthFirstPaths dfp = new DepthFirstPaths(g, 0);
		
		// see who was connected
		StdOut.println("path from 0 to 7:");
		for (int v : dfp.pathTo(7)) {
			StdOut.print(v + " - ");
		}
		StdOut.println();
	}
}