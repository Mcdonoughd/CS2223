package heineman.hw4.expr.nodes;

/**
 * Represents the division of two values.
 */
public class DivideNode extends BinaryOperatorNode {

	public DivideNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return left.value() / right.value();
	}
	
	/** Only here for bonus question. */
	@Override
	public int priority() {
		return 3;
	}

}
