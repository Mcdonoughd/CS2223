package algs.solutions.hw4.expression;

/**
 * A Binary Tree structure representing mathematical expressions. This class is the base
 * class for two special subclasses: 
 * 
 * OperatorNode which represent operations
 * NumberNode which represent values
 * 
 * No longer meant to be a Search Tree, this Binary Tree represents a mathematical expression containing
 * Binary operators and Unary operators. 
 * 
 */
public abstract class ExpressionNode {
	ExpressionNode     left, right;  // left and right subtrees
	
	/** Determines whether this is an operator node. */
	public abstract boolean isOperator();
	
	/**
	 * This expression evaluator returns double, because that is the result of evaluating
	 * each node. Note that a NumberNode evaluates to its number while an OperatorNode
	 * evaluates to the result of its computation.
	 */
	public abstract double value();
}
