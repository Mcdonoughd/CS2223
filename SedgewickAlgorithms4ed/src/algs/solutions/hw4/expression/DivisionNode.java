package algs.solutions.hw4.expression;

public class DivisionNode extends BinaryOperatorNode {

	public DivisionNode(String op) {
		super(op);
	}
	
	public int numParameters() { return 2; }

	/** Operator subclasses determine how to process given value. */
	public double value() {
		return left.value() / right.value();
	}

}
