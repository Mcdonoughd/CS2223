package dmcdonough.hw4.expr.nodes;

public class TriNode extends UnaryOperatorNode {

	public TriNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		
		return (left.value() * (left.value() + 1)) / left.value();
		
	}
}
