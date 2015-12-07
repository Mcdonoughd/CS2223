package algs.days.day22;

public class DirectedDFS {
    boolean[] marked;  // marked[v] = true if v is reachable
    int[]     edgeTo;
    
    /** Conduct DFS over digraph from specific vertex s. */
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        dfs(G, s);
    }

    /** Computes the vertices in digraph connected from multiple sources. */
    public DirectedDFS(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int v : sources) {
            if (!marked[v]) {
            	dfs(G, v);
            }
        }
    }

    void dfs(Digraph G, int v) { 
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
            	edgeTo[w] = v;
            	dfs(G, w);
            }
        }
    }

    /** Check whether specific vertex is reachable. */
    public boolean marked(int v) {
        return marked[v];
    }
}