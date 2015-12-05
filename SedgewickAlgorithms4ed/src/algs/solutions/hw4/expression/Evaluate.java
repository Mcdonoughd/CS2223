package algs.solutions.hw4.expression;

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
				
				// operator node is fully processed, so it goes onto the vals stack.
				vals.push(op);
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
