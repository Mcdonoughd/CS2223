package heineman.hw4.expr.bonus;

import java.util.Stack;






import heineman.hw4.expr.nodes.DivideNode;
import heineman.hw4.expr.nodes.ENode;
import heineman.hw4.expr.nodes.ExponentNode;
import heineman.hw4.expr.nodes.ExpressionNode;
import heineman.hw4.expr.nodes.MultNode;
import heineman.hw4.expr.nodes.NumberNode;
import heineman.hw4.expr.nodes.OperatorNode;
import heineman.hw4.expr.nodes.PiNode;
import heineman.hw4.expr.nodes.PlusNode;
import heineman.hw4.expr.nodes.SqrtNode;
import heineman.hw4.expr.nodes.SubtractNode;
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
 * 
 * This is here for those interested in pursuing the Bonus Question...
 */
public class EvaluateBonus {
	public static void main(String[] args) {
		Stack<OperatorNode> ops = new Stack<OperatorNode>();
		Stack<ExpressionNode> vals = new Stack<ExpressionNode>();
				
		while (!StdIn.isEmpty()) {
			// Read token. push if operator.
			String s= StdIn.readString();
			if (s.equals ("("))      {
				// this change ensures we will be able to pop values back to beginning of expression
				// to support arbitrarily long infix expressions.
				ops.push(new StartExpressionNode(s)); 
			} else if (s.equals ("+")) { ops.push(new PlusNode(s)); }
			else if (s.equals ("-")) { ops.push(new SubtractNode(s)); }
			else if (s.equals ("*")) { ops.push(new MultNode(s)); }
			else if (s.equals ("/")) { ops.push(new DivideNode(s)); }
			else if (s.equals ("^")) { ops.push(new ExponentNode(s)); }
			else if (s.equals ("sqrt")) { ops.push(new SqrtNode(s)); }
			

			// new operator recognition goes here...
			
			// these are constants to be pushed onto the vals stack.
			else if (s.equals ("e")) { vals.push(new ENode(s)); }
			else if (s.equals ("pi")) { vals.push(new PiNode(s)); }
			// you can put more here...
			
			else if (s.equals (")")) {
				// Grab 0, 1 or 2 parameters based on the operator. Will pop
				// back to the StartExpressionNode just to be sure we have got all
				// of the parameters. If not, then attempt to clone operator and
				// build up small subtrees for expressions such as (3 + 4 + 5 + 7)
				OperatorNode op = ops.pop();
				if (op.numParameters() == 0) {
					// leave alone. Push right back.
					vals.push(op);
				} else {
					op.setLeft(vals.pop());
					if (op.numParameters() == 2) {  // do in proper order
						op.setRight(op.getLeft());
						op.setLeft(vals.pop());
					}
				}
				
				// should always get the StartExpressionNode here. If not, must keep going, 
				// simply replacing the right child with an expanded version. No concept
				// of operator precedence, thus can accept "2 + 3 * 7 + 5 / 6" which would 
				// be treated as ((((2 + 3) * 7) + 5) / 6). Hey this is a bonus question. See
				// if you can take it from here...
				OperatorNode check = ops.pop();
				OperatorNode topmost = op;
				while (check.isOperator()) {  		// If operator IS an operator, then have unexpectedly
													// run into more of the expression.
					check.setRight(op.getLeft()); 	// grab what was there before
					op.setLeft(check);  		    // insert into place so it won't get lost
						
											  // could actually insert operator precedence no PEMDAS
											  // though would be tricky! Idea would be to have 
											  // OperatorNode have new method int priority() which 
											  // would default to 1. Then each would override and 
											  // increase priority. Note the "P" in PEMDAS stands
											  // for parenthesis so those would not exist. Lowest
											  // priority would win. Nice expansion.
						
					check.setLeft(vals.pop()); // new param is the right-hand one
					op = check; 
					check = ops.pop();        // keep on going.
				}
				
				
				// Once operator node is fully processed, it goes onto the vals stack.
				/// operator node is fully processed, so it goes onto the vals stack.
				vals.push(topmost);
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
