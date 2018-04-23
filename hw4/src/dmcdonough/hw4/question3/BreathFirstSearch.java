package dmcdonough.hw4.question3;

import algs.hw4.Queue;

public class BreathFirstSearch {
	 private static final int INFINITY = Integer.MAX_VALUE;
	    private boolean[] marked;  // marked[v] = is there an s-v path
	    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
	    private int[] distTo;      // distTo[v] = number of edges shortest s-v path


	    public void BreadthFirstPaths(Graph G, int s) {
	        marked = new boolean[G.V()];
	        distTo = new int[G.V()];
	        edgeTo = new int[G.V()];
	        bfs(G, s);
	    }

	    /**
	     * Computes the shortest path between any one of the source vertices in <tt>sources</tt>
	     * and every other vertex in graph <tt>G</tt>.
	     * @param G the graph
	     * @param sources the source vertices
	     * @return 
	     */
	    public void BreadthFirstPaths(Graph G, Iterable<Integer> sources) {
	        marked = new boolean[G.V()];
	        distTo = new int[G.V()];
	        edgeTo = new int[G.V()];
	        for (int v = 0; v < G.V(); v++)
	            distTo[v] = INFINITY;
	        bfs(G, sources);
	    }


	    // breadth-first search from a single source
	    private void bfs(Graph G, int s) {
	        Queue<Integer> q = new Queue<Integer>();
	        for (int v = 0; v < G.V(); v++)
	            distTo[v] = INFINITY;
	        distTo[s] = 0;
	        marked[s] = true;
	        q.enqueue(s);

	        while (!q.isEmpty()) {
	            int v = q.dequeue();
	            for (int w : G.adj(v)) {
	                if (!marked[w]) {
	                    edgeTo[w] = v;
	                    distTo[w] = distTo[v] + 1;
	                    marked[w] = true;
	                    q.enqueue(w);
	                }
	            }
	        }
	       // System.out.println(marked);
	    }

	    // breadth-first search from multiple sources
	    private void bfs(Graph G, Iterable<Integer> sources) {
	        Queue<Integer> q = new Queue<Integer>();
	        for (int s : sources) {
	            marked[s] = true;
	            distTo[s] = 0;
	            q.enqueue(s);
	        }
	        while (!q.isEmpty()) {
	            int v = q.dequeue();
	            for (int w : G.adj(v)) {
	                if (!marked[w]) {
	                    edgeTo[w] = v;
	                    distTo[w] = distTo[v] + 1;
	                    marked[w] = true;
	                    q.enqueue(w);
	                }
	            }
	        }
	    }

	    /**
	     * Is there a path between the source vertex <tt>s</tt> (or sources) and vertex <tt>v</tt>?
	     * @param v the vertex
	     * @return <tt>true</tt> if there is a path, and <tt>false</tt> otherwise
	     */
	    public boolean hasPathTo(int v) {
	        return marked[v];
	    }

	    /**
	     * Returns the number of edges in a shortest path between the source vertex <tt>s</tt>
	     * (or sources) and vertex <tt>v</tt>?
	     * @param v the vertex
	     * @return the number of edges in a shortest path
	     */
	    public int distTo(int v) {
	        return distTo[v];
	    }
	    
	    

}
