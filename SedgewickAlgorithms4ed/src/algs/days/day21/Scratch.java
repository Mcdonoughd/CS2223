package algs.days.day21;

import algs.days.day19.Graph;
// for lecture handout
public class Scratch {
	int[] edgeTo;
	boolean[] marked;
	Graph G;
	
	public void dfs(Graph G, int s) {
		edgeTo = new int[G.V()];
		marked = new boolean[G.V()];
		dfs(s);
	}

	// depth first search from v
	void dfs(int v) {
		marked[v] = true;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				edgeTo[w] = v;
				dfs(w);
			}
		}
	}
}
