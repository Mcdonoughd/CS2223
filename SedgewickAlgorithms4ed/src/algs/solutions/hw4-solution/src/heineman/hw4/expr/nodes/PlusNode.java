package heineman.hw4.expr.nodes;

/**
 * Represents the addition of two values.
 */
public class PlusNode extends BinaryOperatorNode {

	public PlusNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return left.value() + right.value();
	}
	
	/** Only here for bonus question. */
	@Override
	public int priority() {
		return 4;
	}

}
