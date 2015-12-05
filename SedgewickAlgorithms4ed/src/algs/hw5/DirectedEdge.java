package algs.hw5;

// from Sedgewick, p. 642
public class DirectedEdge { 
    final int v;
    final int w;
    final double weight;

    /** Construct directed edge (v,w) with given weight. */
    public DirectedEdge(int v, int w, double weight) {
        if (v < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
        if (w < 0) throw new IndexOutOfBoundsException("Vertex names must be nonnegative integers");
        if (Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /** Return source v of the edge (v,w). */
    public int from() {
        return v;
    }

    /** Return target w of the edge (v,w). */
    public int to() {
        return w;
    }

    /** Return weight associated with edge. */
    public double weight() {
        return weight;
    }

    /**
     * Returns a string representation of the directed edge.
     * @return a string representation of the directed edge
     */
    public String toString() {
        return v + "->" + w + " " + String.format("%5.2f", weight);
    }
}