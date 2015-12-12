package algs.days.day24.hw5;

import algs.days.day22.Stack;
import algs.days.day23.DijkstraSP;
import algs.days.day23.DirectedEdge;
import algs.days.day23.EdgeWeightedDigraph;

public class FloydWarshall {
	double dist[][];
	int    pred[][];
	final  EdgeWeightedDigraph dg;
	
	 /** Computes a shortest paths tree from each vertex to to every other vertex in
     * the edge-weighted digraph. */
    public FloydWarshall(EdgeWeightedDigraph dg) {
    	this.dg = dg;
    	dist = new double[dg.V()][dg.V()];
    	pred = new int[dg.V()][dg.V()];
    	
    	// initialize
    	for (int u = 0; u < dg.V(); u++) {
    		for (int v = 0; v < dg.V(); v++) {
    			dist[u][v] = Double.POSITIVE_INFINITY;
    			pred[u][v] = -1;
    		}
    		dist[u][u] = 0;
    		for (DirectedEdge e : dg.adj(u)) {
    			dist[u][e.to()] = e.weight();
    			pred[u][e.to()] = u;
    		}
    	}
    	
    	for (int t = 0; t < dg.V(); t++) { 
    		for (int u = 0; u < dg.V(); u++) {
    			for (int v = 0; v < dg.V(); v++) {
    				double newLen = dist[u][t] + dist[t][v];
    				if (newLen < dist[u][v]) {
    					dist[u][v] = newLen;
    					pred[u][v] = pred[t][v];
    				}
    			}
    		}
    	}
    }

    /** Returns a shortest path from vertex s to t. */
    public Iterable<DirectedEdge> path(int s, int t) {
    	// TODO: not implementing for now...
    	return null;
    }
    
    /** Returns the length of a shortest path from s to t. */
    public double dist(int s, int t) { return dist[s][t]; }

    /** Determines if path exists. */
    public boolean hasPath(int s, int t) { return dist(s, t) < Double.POSITIVE_INFINITY; }
}
