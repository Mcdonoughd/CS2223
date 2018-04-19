package dmcdonough.hw4.expr.nodes;

public class SqrtNode extends UnaryOperatorNode {

	public SqrtNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return Math.sqrt(left.value());
	}

}
