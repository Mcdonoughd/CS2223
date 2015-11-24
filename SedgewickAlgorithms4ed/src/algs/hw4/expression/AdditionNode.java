package algs.hw4.expression;

public class AdditionNode extends BinaryOperatorNode {

	public AdditionNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		return left.value() + right.value();
	}

}
