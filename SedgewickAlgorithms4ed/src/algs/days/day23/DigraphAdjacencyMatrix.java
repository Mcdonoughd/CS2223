package algs.days.day23;

import edu.princeton.cs.algs4.In;

/**
 * Provides abd alternative implementation that uses 2D array of weights.
 */
public class DigraphAdjacencyMatrix extends EdgeWeightedDigraph{
	double[][] weights;
	
	public DigraphAdjacencyMatrix (int V) {
		super(V);
		weights = new double[V][V];
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				weights[i][j] = Double.NEGATIVE_INFINITY;
			}
		}
	}
	
	  public DigraphAdjacencyMatrix(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
    }
	
	/** Add edge is simply storing the weight. */
	public void addEdge (int source, int target, double weight) {
		weights[source][target] = weight;
	}
	
	/** Add edge is simply storing the weight. */
	public void addEdge(DirectedEdge e) {
		weights[e.from()][e.to()] = e.weight();
	}
	
	/** Returns information about given directed edge, or null if doesn't exist. */
	public DirectedEdge getEdge (int source, int target) {
		return new DirectedEdge(source, target, weights[source][target]);
	}
	
	/**
	 * Uses extra storage which could be turned into a true iterator that uses no
	 * extra storage.
	 * 
	 * @param v
	 * @return
	 */
	public Iterable<DirectedEdge> adj(int v) {
		// Hint: You could create a Queue of DirectedEdges, populating it from the
		// specific row of the matrix 'weights' and then return that. 
		Queue<DirectedEdge> queue = new Queue<DirectedEdge>();
		for (int i = 0; i < V; i++) {
			if (weights[v][i] != Double.NEGATIVE_INFINITY) {
				queue.enqueue(getEdge(v,i));
			}
		}

		return queue;
	}
	
	// Don't Bother to implement reverse() as shown on p. 569
	
	public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + "\n");
        for (int v = 0; v < V; v++) {
            s.append(String.format("%d: ", v));
            for (DirectedEdge e : adj(v)) {
                s.append(e.toString());
            }
            s.append("\n");
        }
        return s.toString();
    }
	
}
