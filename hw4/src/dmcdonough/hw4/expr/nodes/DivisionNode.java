package dmcdonough.hw4.expr.nodes;

public class DivisionNode extends BinaryOperatorNode {

	public DivisionNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return left.value() / right.value();
	}
}
