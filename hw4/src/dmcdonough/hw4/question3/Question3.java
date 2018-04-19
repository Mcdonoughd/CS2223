package dmcdonough.hw4.question3;

import dmcdonough.hw4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdOut;

public class Question3 {
	/** Each location gets its own unique id, mapped using the symbol table. */
	static SeparateChainingHashST<String, Integer> table = new SeparateChainingHashST<>();
	
	/** Each unique ID gets mapped to a location. This is the inverse map of table. */
	static SeparateChainingHashST<Integer,String> reverse = new SeparateChainingHashST<>();

	// Add whatever methods you see fit to add...
	
	public static void main(String[] args) {
		
		StdOut.println ("Enter locations on a single line, separated by spaces. Note 'home' is special and does not need to be included in list.");

		// add necessary code. See homework instructions...
	}
}
