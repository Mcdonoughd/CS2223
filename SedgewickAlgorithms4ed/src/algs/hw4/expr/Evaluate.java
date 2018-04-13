package algs.hw4.expr;

import java.util.Stack;   // for convenience, continue to use existing JDK Stack class...

import algs.hw4.expr.nodes.ENode;
import algs.hw4.expr.nodes.ExpressionNode;
import algs.hw4.expr.nodes.NumberNode;
import algs.hw4.expr.nodes.OperatorNode;
import algs.hw4.expr.nodes.PlusNode;
import edu.princeton.cs.algs4.*;

/**
 * Code from p. 129 of Sedgewick (4ed) revised to construct binary expression tree from a
 * parenthetical infix expression
 * 
 * To run in Eclipse, you will need to enter your input into the Console window directly. 
 * After you press return, nothing appears to happen. This is because you need to "close" the
 * StdIn. 
 * 
 * This is done on a PC by pressing Control-z.
 * 
 * On a Macintosh (I am not making this up), to terminate the input, click the mouse anywhere else in Eclipse
 * (typically just back in the source code or in the package explorer), then click BACK in the console window
 * and press control-d (not Command-d).
 */

public class Evaluate {
	public static void main(String[] args) {
		Stack<OperatorNode> ops = new Stack<OperatorNode>();
		Stack<ExpressionNode> vals = new Stack<ExpressionNode>();
				
		while (!StdIn.isEmpty()) {
			// Read token. push if operator.
			String s= StdIn.readString();
			if (s.equals ("("))      { /** do nothing. */ }
			else if (s.equals ("+")) { ops.push(new PlusNode(s)); }
			
			// new operator recognition goes here...
			
			// these are constants to be pushed onto the vals stack.
			else if (s.equals ("e")) { vals.push(new ENode(s)); }
			// you can put more here...
			
			else if (s.equals (")")) {
				// Grab 0, 1 or 2 parameters based on the operator. 
				OperatorNode op = ops.pop();
				if (op.numParameters() == 0) {
					// This operator represents a constant value, so push onto that stack.
					vals.push(op);
				} else {
					// might be unary operator. If so, grab top value as left child.
					op.setLeft(vals.pop());
					if (op.numParameters() == 2) {  
						// oops. This is a binary operator. Order values properly
						// and get the second one
						op.setRight(op.getLeft());
						op.setLeft(vals.pop());
					}
				}
				
				// operator node is fully processed, so it goes onto the vals stack.
				vals.push(op);
			} else {
				// Token no operator or parenthesis; must be double value to push
				double d = Double.parseDouble(s);
				NumberNode n = new NumberNode(d);
				vals.push(n);
			}
		}
		
		// Retrieve the node representing the entire expression
		ExpressionNode expr = vals.pop();
		
		// The following shows a human-readable form of the expression
		StdOut.println("Formula:" +expr.representation());
		
		// now compute its value.
		StdOut.println(expr.value());
	}
}
