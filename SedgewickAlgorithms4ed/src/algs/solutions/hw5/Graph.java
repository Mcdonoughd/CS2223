package algs.solutions.hw5;

import algs.days.day21.Stack;
import edu.princeton.cs.algs4.In;

public class Graph {
    
    final int V;
    int E;
    Bag<Integer>[] adj;
    
    /**
     * Initializes an empty graph with <tt>V</tt> vertices and 0 edges.
     * param V the number of vertices
     *
     * @param  V number of vertices
     * @throws IllegalArgumentException if <tt>V</tt> < 0
     */
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    /** Added this method for day20 to load graph from file. */
    public Graph (In in) {
    	this (in.readInt());
    	int E = in.readInt();
    	for (int i = 0; i < E; i++) {
    		int v = in.readInt();
    		int w = in.readInt();
    		addEdge (v,w);
    	}
    }

    public int V() { return V; }
    public int E() { return E; }


    /** Adds the undirected edge v-w to this graph. */
    public void addEdge(int v, int w) {
        E++;
        adj[v].add(w);
        adj[w].add(v);
    }


    /** Returns the vertices adjacent to vertex <tt>v</tt>. */
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    /** Returns the degree of vertex <tt>v</tt>. */
    public int degree(int v) {
        return adj[v].size();
    }

    /** Implement as part of HW5, Question 1. */
    public Graph complement() { 
    	Graph g = new Graph(V);
    	
    	for (int i = 0; i < V; i++) {
    		boolean exists[] = new boolean[V];
    		for (int v : adj(i)) {
    			exists[v] = true;
    		}
    		
    		for (int j = 0; j < V; j++) {
    			if (!exists[j]) {
    				g.addEdge(i, j);
    			}
    		}
    	}
    	
    	return g;
    }

    /** StraightForward BFS solution. Check all marked. Don't bother with distTo. */
    public boolean connected() { 
    	Queue<Integer> queue = new Queue<Integer>();
		boolean marked[] = new boolean[V];
		queue.enqueue(0);
		marked[0] = true;
		while (!queue.isEmpty()) {
			int w = queue.dequeue();
			for (int v : adj(w)) {
				if (!marked[v]) {
					marked[v] = true;
					queue.enqueue(v);
				}
			}
		}
		
		// not connected...
		for (int j = 0; j < V; j++) {
			if (!marked[j]) { return false; }
		}
		return true;
    }
    
    /** Implement as part of HW5, Question 2. */
    public int status(int s) { 
    	Queue<Integer> queue = new Queue<Integer>();
		boolean marked[] = new boolean[V];
		int distTo[] = new int[V];
		for (int j = 0; j < V; j++) {
			distTo[j] = Integer.MAX_VALUE;
		}
		
		queue.enqueue(s);
		marked[s] = true;
		distTo[0] = 0;
		while (!queue.isEmpty()) {
			int w = queue.dequeue();
			for (int v : adj(w)) {
				if (!marked[v]) {
					distTo[v] = distTo[w] + 1;
					marked[v] = true;
					queue.enqueue(v);
				}
			}
		}
		
		// not connected...
		for (int j = 0; j < V; j++) {
			if (!marked[j]) { return -1; }
		}
		
		// sum up
		int status = 0;
		for (int j = 0; j < V; j++) {
			status += distTo[j];
		}
		return status;
    }
    
    /** Implement as part of HW5, Question 2. */
    public boolean statusInjective() { 
    	// conduct BFS for all vertices
    	int status[] = new int[V];
    	for (int i = 0; i < V; i++) {
    		
    		// compute status and return false if not connected.
    		status[i] = status(i);
    		if (status[i] == -1) {
    			return false;
    		}
    		
    		// check if any same and leave early
    		for (int j = 0; j < i; j++) {
    			if (status[j] == status[i]) {
    				return false;
    			}
    		}
    	}
    	
    	// checks out!
    	return true;
    }
    
    /** Implement as part of HW5, Question 3. */
    public int findSafeVertex() { 
    	if (!connected()) { 
			return -1;
		}
		int N = V();
		boolean [] marked = new boolean[N];

		// positions in the Queue are Gray and under investigation.
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(0);
		marked[0] = true;
		while (!stack.isEmpty()) {
			Integer u = stack.pop();

			boolean isSafe = true;
			for (int v : adj(u)) {
				if (!marked[v]) {
					isSafe = false;
					marked[v] = true;
					stack.push(v);
				}
			}
			
			// if neighbors are all marked, then this is safe to be deleted.
			if (isSafe) {
				return u;
			}
		}
		
		return -1;
    }

    /** The length of the shortest path from that vertex to the
     *  furthest vertex from v in the graph. Conduct a BFS and 
     *  find largest distance.
     * @param v
     * @return
     */
    int eccentricity(int s) {
    	Queue<Integer> queue = new Queue<Integer>();
		boolean marked[] = new boolean[V];
		int distTo[] = new int[V];
		for (int j = 0; j < V; j++) {
			distTo[j] = Integer.MAX_VALUE;
		}
		
		queue.enqueue(s);
		marked[s] = true;
		distTo[0] = 0;
		while (!queue.isEmpty()) {
			int w = queue.dequeue();
			for (int v : adj(w)) {
				if (!marked[v]) {
					distTo[v] = distTo[w] + 1;
					marked[v] = true;
					queue.enqueue(v);
				}
			}
		}
		
		// not connected...
		for (int j = 0; j < V; j++) {
			if (!marked[j]) { return -1; }
		}
		
		// Find max
		int ecc = 0;
		for (int j = 0; j < V; j++) {
			if (distTo[j] > ecc) {
				ecc = distTo[j];
			}
		}
		return ecc;
    }
    
    /** Implement as part of HW5, Question 3. */
    public int diameter () {
    	int diam = 0;
    	for (int i = 0; i < V(); i++) {
    		int ecc = eccentricity(i);
    		if (ecc > diam) {
    			diam = ecc;
    		}
    	}
    	
    	return diam;
    }
    
    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + "\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

}
