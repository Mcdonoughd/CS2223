package dmcdonough.hw4.expr.nodes;

public class TimesNode extends BinaryOperatorNode{

	public TimesNode(String op) {
		super(op);
	}
	
	/** Operator subclasses determine how to process given value. */
	public double value() {
		//System.out.println(left.value());
		return left.value() * right.value();
	}
}
