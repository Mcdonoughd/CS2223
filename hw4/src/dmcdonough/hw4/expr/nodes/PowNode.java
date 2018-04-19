package dmcdonough.hw4.expr.nodes;

public class PowNode extends BinaryOperatorNode {

	public PowNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return Math.pow(left.value(),right.value());
	}
}
