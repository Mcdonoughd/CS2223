package algs.hw4.expression;


/**
 * Represents a unary operation within a Binary Expression Tree
 */
public abstract class UnaryOperatorNode extends OperatorNode {

	public UnaryOperatorNode(String op) {
		super(op);
	}
	
	/** Unary operators have 1 parameter. */
	public int numParameters() { return 1; }
	
	/** Unary representation. */
	public String representation() {
		return "(" + op + " " + left.representation() + ")";
	}
}
