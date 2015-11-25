package algs.hw4.expression;

public abstract class OperatorNode extends ExpressionNode {
	String op;  // reflects current operator.

	public OperatorNode(String op) {
		this.op = op;
	}
	
	/** Every subclass of this class is an operator. */
	public boolean isOperator() { return true; }
	
	/** 
	 * Determines number of parameters this operator consumes.
	 * 
	 * Unary operator returns 1
	 * Binary operator returns 2
	 * 0-ary operator (like constants) return 0. 
	 */
	public abstract int numParameters();
	
	/**
	 * Subclasses must override this method to properly
	 * demonstrate their representation. Note that the NoParameterOperatorNode,
	 * UnaryOperatorNode and BinaryOperatorNode do this.
	 */
	public abstract String representation();
}
