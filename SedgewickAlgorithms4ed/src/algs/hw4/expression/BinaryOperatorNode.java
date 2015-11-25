package algs.hw4.expression;

public abstract class BinaryOperatorNode extends OperatorNode {
	public BinaryOperatorNode(String op) {
		super(op);
	}
	

	/** Binary operators use two parameters. */
	public int numParameters() { return 2; }
	
	public String representation() {
		return "(" + left.representation() + " " + op + " " + right.representation() + ")";
	}
}
