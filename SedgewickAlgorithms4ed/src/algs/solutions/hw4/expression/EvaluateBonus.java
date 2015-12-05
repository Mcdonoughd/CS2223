package algs.solutions.hw4.expression;

import java.util.Stack;

import edu.princeton.cs.algs4.*;

// Code from p. 129 of Sedgewick (4ed) revised to construct binary expression tree from a
// parenthetical infix expression
// note that to terminate input you must do either CTRL-D or CTRL-Z depending
// on whether you are on a Mac or a PC. Also, it might help to click in the 
// Java source panel and then click back in the console before trying to 
// terminate input. It is an Eclipse thing....

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
			} else if (s.equals ("+")) { ops.push(new AdditionNode(s)); }
			else if (s.equals ("-")) { ops.push(new SubtractionNode(s)); }
			else if (s.equals ("*")) { ops.push(new MultiplicationNode(s)); }
			else if (s.equals ("/")) { ops.push(new DivisionNode(s)); }
			else if (s.equals ("^")) { ops.push(new ExponentNode(s)); }
			else if (s.equals ("sqrt")) { ops.push(new SquareRootNode(s)); }
			
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
					op.left = vals.pop();
					if (op.numParameters() == 2) {  // do in proper order
						op.right = op.left;
						op.left = vals.pop();
					}
				}
				
				// should always get the StartExpressionNode here. If not, must keep going, 
				// simply replacing the right child with an expanded version. No concept
				// of operator precedence, thus can accept "2 + 3 * 7 + 5 / 6" which would 
				// be treated as 2 + (3 * (7 + (5 / 6) ))
				OperatorNode check = ops.pop();
				OperatorNode topmost = op;
				while (check.isOperator()) {  // If operator IS an operator, then have unexpectedly
											  // run into more of the expression.
					check.right = op.left;    // grab what was there before
					op.left = check;          // insert into place so it won't get lost
					
											  // could actually insert operator precedence no PEMDAS
											  // though would be tricky! Idea would be to have 
											  // OperatorNode have new method int priority() which 
											  // would default to 1. Then each would override and 
											  // increase priority. Note the "P" in PEMDAS stands
											  // for parenthesis so those would not exist. Lowest
											  // priority would win. Nice expansion.
					
					check.left = vals.pop(); // new param is the right-hand one
					op = check; 
					check = ops.pop();        // keep on going.
				}
				
				
				// operator node is fully processed, so it goes onto the vals stack.
				vals.push(topmost);
			} else {
				// Token no operator or parenthesis; must be double value to push
				double d = Double.parseDouble(s);
				NumberNode n = new NumberNode(d);
				vals.push(n);
			}
		}
		
		ExpressionNode expr = vals.pop();
		StdOut.println("Formula:" + new ExpressionFormatter().representation(expr));
		StdOut.println(expr.value());
	}
}
