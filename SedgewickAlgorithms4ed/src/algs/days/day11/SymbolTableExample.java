package algs.days.day11;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SymbolTableExample {

	/**
	 * Unit tests the <tt>SequentialSearchST</tt> data type.
	 */
	public static void main(String[] args) {
		SequentialSearchST<Double, String> st = new SequentialSearchST<Double, String>();
		
		// build the table
		st.put(99.5, "WCRB Classical Boston");
		st.put(98.5, "Sports Hub");
		st.put(104.5, "WXLO");
		st.put(101.7, "WXLZ Classic Rock");
		
		// remove one
		st.delete(104.5);
		
		StdOut.println("Enter radio station signal (enter 0 to exit)");
		while (true) {
			Double signal = StdIn.readDouble();
			if (signal == 0) { break; }
			
			if (st.contains(signal)) {
				StdOut.println(signal + " is radio station " + st.get(signal));
			} else {
				System.out.println("No radio associated with " + signal);
			}
		}
		
	}
}
