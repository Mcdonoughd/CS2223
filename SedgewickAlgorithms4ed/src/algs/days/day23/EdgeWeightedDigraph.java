package algs.days.day23;

import edu.princeton.cs.algs4.In;

/**
 * The next extension for graphs; each edge is directed AND has a positive (double) weight associated
 * with it.
 */
public abstract class EdgeWeightedDigraph {
    final int V;                // number of vertices in this digraph
    int E;                      // number of edges in this digraph
   
    /**
     * Initializes an empty edge-weighted digraph with <tt>V</tt> vertices and 0 edges.
     *
     * @param  V the number of vertices
     * @throws IllegalArgumentException if <tt>V</tt> < 0
     */
    public EdgeWeightedDigraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.V = V;
        this.E = 0;
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
    public EdgeWeightedDigraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }

    public int V() { return V; }
    public int E() { return E; }

    /** Adds the directed edge e to this edge-weighted digraph. */
    public abstract void addEdge(DirectedEdge e);

    /** Returns the directed edges incident from vertex v. */
    public abstract Iterable<DirectedEdge> adj(int v);

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
            for (DirectedEdge e : adj(v)) {
                s.append(e + "  ");
            }
            s.append("\n");
        }
        return s.toString();
    }


}