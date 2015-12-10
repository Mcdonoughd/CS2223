package algs.days.day24;

import algs.days.day23.DijkstraSP;
import algs.days.day23.DirectedEdge;
import algs.days.day23.EdgeWeightedDigraph;

public class DijkstraAllPairsSP {
    DijkstraSP[] all;

    /** Computes a shortest paths tree from each vertex to to every other vertex in
     * the edge-weighted digraph. */
    public DijkstraAllPairsSP(EdgeWeightedDigraph G) {
        all  = new DijkstraSP[G.V()];
        for (int v = 0; v < G.V(); v++)
            all[v] = new DijkstraSP(G, v);
    }

    /** Returns a shortest path from vertex s to t. */
    public Iterable<DirectedEdge> path(int s, int t) { return all[s].pathTo(t); }
    
    /** Returns the length of a shortest path from s to t. */
    public double dist(int s, int t) { return all[s].distTo(t); }

    /** Determines if path exists. */
    public boolean hasPath(int s, int t) { return dist(s, t) < Double.POSITIVE_INFINITY; }
}