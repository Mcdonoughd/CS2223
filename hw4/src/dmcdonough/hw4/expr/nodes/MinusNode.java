package dmcdonough.hw4.expr.nodes;

public class MinusNode extends BinaryOperatorNode {

	public MinusNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return left.value() - right.value();
	}
}
