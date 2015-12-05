package algs.solutions.hw4;

/**
 * A visitor interface defines the API for processing values at given nodes in the tree.
 * This interface is flexible regarding the order by which the nodes in the binary tree
 * are visited. 
 *
 * @param <Value>
 */
public interface IVisitor<Value extends Comparable<Value>> {
	/** 
	 * Enable processing of the value at a given node.
	 * 
	 * Parameter level describes distance from root (when 0, this is the value of root). 
	 */
	void process(Value v, int level);
}
