package algs.hw4.expr.nodes;

public class NumberNode extends ExpressionNode {
	double number;

	public NumberNode(double v) {
		this.number = v;
	}
	
	public double value() {
		return number;
	}
	
	/** a NumberNode is not an operator node. */
	public boolean isOperator() { return false; } 
	
	/** Representation. */
	public String representation() { return "" + number; }
}
