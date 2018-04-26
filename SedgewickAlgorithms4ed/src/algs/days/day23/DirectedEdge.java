package algs.days.day23;

public class DirectedEdge { 
    final int v;
    final int w;
    final double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /** Tail vertex of edge. */
    public int from()      { return v; }

    /** Head vertex of edge. */
    public int to()        { return w; }

    /** Weight of edge. */
    public double weight() { return weight; }

    /** String representation. */
    public String toString() {
    	return String.format("%d->%d (%.2f)", v, w, weight);
    }
}
