package dmcdonough.hw4.expr.bonus;

import algs.hw4.expr.nodes.NoParameterOperatorNode;

/**
 * This subclass is only used by the EvaluateBonus question....
 * Ignore if you are only working on main one.
 */
public class StartExpressionNode extends NoParameterOperatorNode {

	public StartExpressionNode(String op) {
		super(op);
	}
	
	/** This is the only OperatorNode subclass that can claim it is not an operator. */
	public boolean isOperator() { return false; }
	
	/** Really a placeholder to be able to remember the start of an expression. */
	public double value() {
		return 0;
	}

}
