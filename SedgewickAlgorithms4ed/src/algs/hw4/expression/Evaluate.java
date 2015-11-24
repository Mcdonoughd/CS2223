package algs.hw4.expression;

import java.util.Stack;
import edu.princeton.cs.algs4.*;

// Code from p. 129 of Sedgewick (4ed) revised to construct binary expression tree from a
// parenthetical infix expression
// note that to terminate input you must do either CTRL-D or CTRL-Z depending
// on whether you are on a Mac or a PC. Also, it might help to click in the 
// Java source panel and then click back in the console before trying to 
// terminate input. It is an Eclipse thing....

public class Evaluate {
	public static void main(String[] args) {
		Stack<OperatorNode> ops = new Stack<OperatorNode>();
		Stack<ExpressionNode> vals = new Stack<ExpressionNode>();
				
		while (!StdIn.isEmpty()) {
			// Read token. push if operator.
			String s= StdIn.readString();
			if (s.equals ("("))      { /** do nothing. */ }
			else if (s.equals ("+")) { ops.push(new AdditionNode(s)); }
			
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
					op.left = vals.pop();
					if (op.numParameters() == 2) {  
						// oops. This is a binary operator. Order values properly
						// and get the second one
						op.right = op.left;
						op.left = vals.pop();
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
