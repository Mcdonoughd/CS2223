package algs.hw4.expr.nodes;

/**
 * Example of a no-parameter operation node, that returns the constant e.
 */
public class ENode extends NoParameterOperatorNode {

	public ENode(String op) {
		super(op);
	}
	
	/** No Parameter operator returns its value. */
	public double value() {
		return Math.E;
	}

}
