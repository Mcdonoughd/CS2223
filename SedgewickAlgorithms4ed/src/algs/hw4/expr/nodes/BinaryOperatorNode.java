package algs.hw4.expr.nodes;

/** 
 * Represent a binary operation. There is no need to changes this class.
 */
public abstract class BinaryOperatorNode extends OperatorNode {
	public BinaryOperatorNode(String op) {
		super(op);
	}
	
	/** Binary operators use two parameters. */
	public int numParameters() { return 2; }
	
	/** Represent binary operator as "val op val". */
	public String representation() {
		return "(" + left.representation() + " " + op + " " + right.representation() + ")";
	}
}
