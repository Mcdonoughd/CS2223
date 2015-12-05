package algs.solutions.hw4.expression;

public class SubtractionNode extends BinaryOperatorNode {

	public SubtractionNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return left.value() - right.value();
	}

}
