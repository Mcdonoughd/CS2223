package algs.solutions.hw4.expression;

public class ExponentNode extends BinaryOperatorNode {

	public ExponentNode(String op) {
		super(op);
	}
	
	public int numParameters() { return 2; }

	/** Operator subclasses determine how to process given value. */
	public double value() {
		return Math.pow(left.value(), right.value());
	}

}
