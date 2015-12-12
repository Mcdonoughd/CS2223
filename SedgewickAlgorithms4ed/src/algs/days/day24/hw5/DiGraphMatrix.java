package algs.days.day24.hw5;

import algs.days.day23.DirectedEdge;

// Complete this implementation which represents a Directed Graph using
// an Adjacency Matrix. Assumes NEGATIVE NUMBER is invalid 

public class DiGraphMatrix {
	final int V;
	int E;
	double[][] weights;
	
	public DiGraphMatrix (int V) {
		this.V = V;
		this.E = 0;
		weights = new double[V][V];
		for (int i = 0; i < V; i++) {
			for (int j = 0; j < V; j++) {
				weights[i][j] = Double.NEGATIVE_INFINITY;
			}
		}
	}
	
	public void addEdge (int source, int target, double weight) {
		weights[source][target] = weight;
	}
	
	
	/** Returns information about given directed edge, or null if doesn't exist. */
	public DirectedEdge getEdge (int source, int target) {
		return new DirectedEdge(source, target, weights[source][target]);
	}
	
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
