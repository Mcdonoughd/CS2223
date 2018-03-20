package dmcdonough.hw1;

import java.util.Stack;   // for now, use the default Java implementation provided by the JDK.

import edu.princeton.cs.algs4.*;

/**
 * Code from p. 129 of Sedgewick (4ed).
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
		Stack<String> ops = new Stack<String>();
		Stack<Double> vals = new Stack<Double>();

		while (!StdIn.isEmpty()) {
			// Read token. push if operator.
			String s = StdIn.readString();
			if (s.equals ("(")) { /* do nothing */ }
			else if (s.equals ("+")) { ops.push(s); }
			else if (s.equals ("-")) { ops.push(s); }
			else if (s.equals ("*")) { ops.push(s); }
			else if (s.equals ("/")) { ops.push(s); }
			else if (s.equals ("sqrt")) { ops.push(s); }
			else if (s.equals (")")) {
				// pop, evaluate, and push result if token is ")".
				String op = ops.pop();
				double v = vals.pop();
				if (op.equals("+")) { v = vals.pop() + v; }
				else if (op.equals("-")) { v = vals.pop() - v; }
				else if (op.equals("+")) { v = vals.pop() + v; }
				else if (op.equals("*")) { v = vals.pop() * v; }
				else if (op.equals("/")) { v = vals.pop() / v; }
				else if (op.equals("sqrt")) { v = Math.sqrt(v); }
				else if (op.equals("^")) { v = Math.pow(vals.pop(), v);}
				else if (op.equals("floor")) { v = Math.floor(v);}
				vals.push(v);
			} else {
				// Token no operator or paren; must be double value to push
				vals.push(Double.parseDouble(s));
			}
		}
		StdOut.println(vals.pop());
	}
}
