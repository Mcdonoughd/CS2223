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
 * 
 * These works
 * 
 * ( 3 ^ 4 * 2 / 3 + 6 ) -> (((3.0 ^ 4.0) * (2.0 / 3.0)) + 6.0)
 * ( 7 / 2 + 3 * 4 + 5 * 6 )     --> ((7.0 / 2.0) + ((3.0 * 4.0) + (5.0 * 6.0)))
 * ( 7 + 2 / 3 + 4 * 5 + 6 )     --> (7.0 + ((2.0 / 3.0) + ((4.0 * 5.0) + 6.0)))
 * 
 * HOWEVER, still doesn't work for ....
 * 
 * ( 2 - 5 * 2 ^ 3 + 6 )
 * 
 * Issue with exponent and its addition afterwards.... grr...
 */
public class EvaluateBonusBonus {
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
				
				
				// Now process each operator and, working from right to left, process all binary operations. However, as we build
				// up the expression trees, we have to deal with priority inversions of the operators 
				// observe the following: ( 9 + 4 / 5 * 6 ^ 3 * 4 ) which should be
				
				// ( 9 + 4 / 5 * 6 ^ 3 * 4 )
				// 1   4   3   3   2   3
				//
				// The numbers below the operators represent the PEMDAS ordering, with "(" being 1, +/- are 4, */ are 3, ^ is 2.
				//
				// Working from RIGHT to left, the expression built up is as follows:
				//
				// (3 * 4)
				// (6^3) * 4                  < steals the 3 since ^ has priority lower-than-or-equal-to *
				// 5 * ((6^3) * 4)            < since * has priority greater than ^, close right and make an argument
				// (4/5) * ((6^3) * 4)        < since / is lower-than-or-equal-to *, steal the 5
				//
				// Now we handle + and - specially, since these are the least powerful. Just push them (should
				// they exist) onto a stack, and then knit them all together once done.
				//
				// 9 + 4 * 5 + 6 * 7
				//
				// On stack (right to left) get (+, -, (6*7)), (+,-, (4*5)) 
				//
				// then (since done) pop off, and insert left values, starting from the last value
				// on the stack and working backwards.
				
				// (9 + (4/5) * ((6^3) * 4)   < since + has priority greater than *, close right and make an argument
				//
				// The only confusing part is that lower priority value means more powerful operator.
				
				OperatorNode right = ops.pop();
				right.setRight(vals.pop());
				right.setLeft(vals.pop());
				
				int rightPriority = right.priority();
				while (!ops.isEmpty()) {
					OperatorNode left = ops.pop();
					int leftPriority = left.priority();
					
					// stop at startExpression...
					if (!left.isOperator()) { break; }
					left.setLeft(vals.pop());
										
					if (leftPriority <= rightPriority) {
						// STEAL: left is more powerful than right
						// must find left-most operator within right and extract
						OperatorNode rmp = right;
						while (rmp.getLeft() != null && rmp.getLeft().isOperator()) {
							rmp = (OperatorNode) rmp.getLeft();
						}
						left.setRight(rmp.getLeft());
						rmp.setLeft(left);
						rightPriority = leftPriority;  // remember for next time through loop.	
					} else {
						left.setRight(right);
						right = left;
						rightPriority = right.priority();  // remember for next time through loop.
					}		
				}
				vals.push(right);
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
