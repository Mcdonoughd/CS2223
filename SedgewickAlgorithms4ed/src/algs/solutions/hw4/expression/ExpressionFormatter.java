package algs.solutions.hw4.expression;

public class ExpressionFormatter {

	/** 
	 * Returns the string representation of a Binary Expression Tree.
	 */
	public String representation(ExpressionNode expr) {
		if (expr.isOperator()) {
			OperatorNode oper = (OperatorNode) expr;
			if (oper.numParameters() == 0) {
				return oper.op;
			} else if (oper.numParameters() == 1) {
				return "(" + oper.op + " " + representation(oper.left) + ")";
			} else if (oper.numParameters() == 2) {
				return "(" + representation(oper.left) + " " + oper.op + " " + representation(oper.right) + ")";
			} else {
				return "( Unknown:" + oper.op + ")";
			}
		} else {
			return "" + expr.value();
		}
	}

}
