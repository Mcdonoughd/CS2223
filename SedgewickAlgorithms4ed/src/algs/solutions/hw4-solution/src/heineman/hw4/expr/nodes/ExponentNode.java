package heineman.hw4.expr.nodes;

/**
 * 3 ^ 4 means 3 to the 4th power
 */
public class ExponentNode extends BinaryOperatorNode {

	public ExponentNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return Math.pow(left.value(), right.value());
	}
	
	/** Only here for bonus question. */
	@Override
	public int priority() {
		return 2;
	}
}
