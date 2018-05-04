package heineman.hw4.expr.nodes;

/**
 * Represents the triangle node N, which is N*(N+1)/2
 */
public class TriangleNode extends UnaryOperatorNode {

	public TriangleNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		double t = left.value();
		return (t*(t+1)/2);
	}

}
