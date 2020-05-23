package algs.hw4.expr.nodes;


/**
 * Represents an operation within a Binary Expression Tree that has no parameters.
 * 
 * It might, for example, simply generate a random number. It might be a constant value.
 */
public abstract class NoParameterOperatorNode extends OperatorNode {

	public NoParameterOperatorNode(String op) {
		super(op);
	}
	
	/** No expected parameters. */
	public int numParameters () { return 0; }
	
	/** NoParameterOperatorNode subclasses determine how to process given value. */
	public abstract double value();
	
	/** Representation is simply the operator. */
	public String representation() { return op; }
}
