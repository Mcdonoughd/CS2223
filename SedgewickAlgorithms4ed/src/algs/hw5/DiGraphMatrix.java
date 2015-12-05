package algs.hw5;

// Complete this implementation which represents a Directed Graph using
// an Adjacency Matrix.

public class DiGraphMatrix {
	final int V;
	int E;
	double[][] weights;
	
	public DiGraphMatrix (int V) {
		this.V = V;
		this.E = 0;
		weights = new double[V][V];
	}
	
	public void addEdge (int source, int target, double weight) {
		// TODO: Replace
	}
	
	
	/** Returns information about given directed edge, or null if doesn't exist. */
	public DirectedEdge getEdge (int source, int target) {
		// TODO: REPLACE
		return null;
	}
	
	public Iterable<DirectedEdge> adj(int v) {
		// Hint: You could create a Queue of DirectedEdges, populating it from the
		// specific row of the matrix 'weights' and then return that. 
		
		// TODO: REPLACE
		return null;
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
