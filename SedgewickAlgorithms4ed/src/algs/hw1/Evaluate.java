package algs.hw1;

import java.util.Stack;
import edu.princeton.cs.algs4.*;

// Code from p. 129 of Sedgewick (4ed)
// note that to terminate input you must do either CTRL-D or CTRL-Z depending
// on whether you are on a Mac or a PC. Also, it might help to click in the 
// Java source panel and then click back in the console before trying to 
// terminate input. It is an Eclipse thing....

public class Evaluate {
	public static void main(String[] args) {
		Stack<String> ops = new Stack<String>();
		Stack<Double> vals = new Stack<Double>();
		System.out.println(Math.PI);
		System.out.println(Math.E);
		
		while (!StdIn.isEmpty()) {
			// Read token. push if operator.
			String s= StdIn.readString();
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
				vals.push(v);
			} else {
				// Token no operator or paren; must be double value to push
				vals.push(Double.parseDouble(s));
			}
		}
		
		StdOut.println(vals.pop());
	}
}
