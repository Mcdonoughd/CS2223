package algs.days.day22;

public class DirectedDFS {
    boolean[] marked;  // marked[v] = true if v is reachable
    
    /** Conduct DFS over digraph from specific vertex s. */
    public DirectedDFS(Digraph G, int s) {
        marked = new boolean[G.V()];
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

    /** Stack-based implementation of dfs. */
    void dfs(Digraph G, int v) { 
    	Stack<Integer> stack = new Stack<Integer>();
        marked[v] = true;
        stack.push(v);
        while (!stack.isEmpty()) {
        	v = stack.pop();
	        for (int w : G.adj(v)) {
	            if (!marked[w]) {
	            	marked[w] = true;
	            	stack.push(w);
	            }
	        }
        }
    }

    /** Check whether specific vertex is reachable. */
    public boolean marked(int v) {
        return marked[v];
    }
}