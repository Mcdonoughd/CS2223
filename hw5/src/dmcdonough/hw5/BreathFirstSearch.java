package dmcdonough.hw5;

import java.util.Iterator;

public class BreathFirstSearch {
	 private static final int INFINITY = Integer.MAX_VALUE;
	    private boolean[] marked;  // marked[v] = is there an s-v path
	    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
	    private int[] distTo;      // distTo[v] = number of edges shortest s-v path

	    
	    public Iterable<Integer> shortestPathTo(int v,int s) {
	        if (! hasPathTo(v)) 
	            return null;
	        
	        // since edgeTo[w] gives the parent of w, we
	        // use a stack to reverse/store the path vertices
	        Stack<Integer> path = new Stack<Integer>();
	        for (int w = v; w != s; w = edgeTo[w]) {
	            path.push(w);
	        }
	        return path;
	    }
	    
	    // this is a method that lets us see the states of the queue, 
	    // and the marked[] and edgeTo[] arrays
	    private void printStateSoFar(Graph g, Queue q,int s) {
	        Iterator qIterator = q.iterator();
	        System.out.println("queue  edgeTo[]");
	        
	        for (int i = 0; i < g.V(); i++) {
	            System.out.println( " |" + (qIterator.hasNext() ? qIterator.next() : " ") + 
	                                "|     " + i + "|" + 
	                                ((i == s) ? "-" : (marked[i] ? edgeTo[i] : "")));
	        }
	        System.out.println();
	    }

		public  BreathFirstSearch(Graph G, int s) {
	        marked = new boolean[G.V()];
	        distTo = new int[G.V()];
	        edgeTo = new int[G.V()];
	        bfs(G, s);
	    }
	    
	

		public  BreathFirstSearch(Graph G, int s,int f) {
	        marked = new boolean[G.V()];
	        distTo = new int[G.V()];
	        edgeTo = new int[G.V()];
	        bfs(G, s, f);
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


	    
	    // breadth-first search from a single source to and end point
	    public void bfs(Graph G, int s,int f) {
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
	      // return q;
	    }

	    
	    // breadth-first search from a single source to and end point
	    public void bfs(Graph G, int s) {
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
	      // return q;
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
	    
		public boolean[] getMarked(){
	    	return this.marked;
	    }

}
