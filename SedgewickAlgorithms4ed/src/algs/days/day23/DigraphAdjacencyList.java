package algs.days.day23;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The next extension for graphs; each edge is directed AND has a positive (double) weight associated
 * with it.
 */
public class DigraphAdjacencyList extends EdgeWeightedDigraph {
    Bag<DirectedEdge>[] adj;    // adj[v] = adjacency list for vertex v
    
    /**
     * Initializes an empty edge-weighted digraph with <tt>V</tt> vertices and 0 edges.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if <tt>V</tt> < 0
     */
    @SuppressWarnings("unchecked")
	public DigraphAdjacencyList(int V) {
    	super(V);
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }

    /**  
     * Initializes an edge-weighted digraph from the specified input stream.
     * 
     * Format is:
     * V E
     * u v w
     * u2 v2 w2
     * ...
     *
     * @param  in the input stream
     * @throws IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     */
    public DigraphAdjacencyList(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    /** Adds the directed edge e to this edge-weighted digraph. */
    public void addEdge(DirectedEdge e) {
        int v = e.from();
        adj[v].add(e);
        E++;
    }

    /** Returns the directed edges incident from vertex v. */
    public Iterable<DirectedEdge> adj(int v) { return adj[v]; }

    /** Returns all directed edges in this edge-weighted digraph. */
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> list = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++) {
            for (DirectedEdge e : adj(v)) {
                list.add(e);
            }
        }
        return list;
    } 

    /**
     * Returns a string representation of this edge-weighted digraph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists of edges
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + "\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Unit tests the <tt>EdgeWeightedDigraph</tt> data type.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        DigraphAdjacencyList G = new DigraphAdjacencyList(in);
        StdOut.println(G);
    }

}