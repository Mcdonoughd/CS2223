package dmcdonough.hw5;
import edu.princeton.cs.algs4.In;

/** Standard undirected Grah implementation, as starting point for Q3 on HW5. */
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

    /** Fill in this method to determine if undirected graph is connected. */
    public boolean connected() {
    	//use bfs to see if there is a path from every vertex to every other vertex
    	for(int v = 0; v < V; v++){
    		BreathFirstSearch bfp = new BreathFirstSearch(this, v);
    		for(int v1 = 0; v1 < V; v1++){
    			if(!bfp.hasPathTo(v)){
    				return false;
    			}
    		}
    	}
    	return true;
    	// TODO: REPLACE WITH YOUR CODE
    	//return false;
    }

    /** Implement as part of HW5, Question 3. */
    public int findSafeVertex() {
    	//first check that the graph is connected
    	if(!connected()){
    		return -1;
    	}
    	DepthFirstPaths d = new DepthFirstPaths(this,0);
    	boolean[] marked = d.getMarked();
    	//return a vertex whose neighbors have all been marked
    	for(int v = 0; v < V; v++){
    		boolean allNeighborsVisited = true;
    		for(int w: this.adj(v)){
    			if(marked[w] == false){
    				allNeighborsVisited = false;
    			}
    		}
    		if(allNeighborsVisited == true){
    			return v;
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
    	// TODO: REPLACE WITH YOUR CODE
    	BreathFirstSearch search = new BreathFirstSearch(this,s);
    	int largestdist = 0;
    	int ecc = 0;
    	for(int i = 0; i < V; i++){
    		
    		if(search.distTo(i)> largestdist && search.distTo(i) < Integer.MAX_VALUE){
    			ecc = search.distTo(i);
    		}
    	}
    	return ecc;
    }
    
    /** Implement as part of HW5, Question 3. */
    public int diameter () {
    	int diameter = 0;
    	int MAXeccentricity =0;
    	for(int v = 0; v < V; v++){

        	//bfs from v to generate all distto values on other vertices
        	BreathFirstSearch b = new BreathFirstSearch(this,v);
        	int newecc = eccentricity(v);
    		
        	if(newecc > diameter){
        		diameter = newecc;
        	}
    	}
    	return diameter;
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
