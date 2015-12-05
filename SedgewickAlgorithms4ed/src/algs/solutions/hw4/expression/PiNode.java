package algs.solutions.hw4.expression;

public class PiNode extends NoParameterOperatorNode {

	public PiNode(String op) {
		super(op);
	}
	
	/** No Parameter operator returns its value. */
	public double value() {
		return Math.PI;
	}

}
