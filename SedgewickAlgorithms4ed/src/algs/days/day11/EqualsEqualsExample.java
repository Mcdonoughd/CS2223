package algs.days.day11;

import edu.princeton.cs.algs4.StdOut;

public class EqualsEqualsExample {
	public static void main(String[] args) {
		Double D = 4.5;
		Double D2 = 4.5;
		
		StdOut.println(D == D2);
		
		double d = 3.5;
		double d2 = 3.5;
		StdOut.println(d == d2);
		
		String s = new String("test");
		String t = new String("test");
		StdOut.println(s == t);
		
		// Java compiler optimizes storage by creating string pool of all
		// string constants, so in the compiled code these are actually
		// the same exact string object.
		s = "test";
		t = "test";
		StdOut.println(s == t);
	}
}
