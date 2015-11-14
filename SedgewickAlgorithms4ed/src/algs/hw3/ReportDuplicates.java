package algs.hw3;

// place the "10sonnets.txt" file within your top-level project so it can be found.

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * You are to modify this class to process all of the words in the given 10sonnets.txt file; you can
 * copy this file from the Git repository and place it directly in your project folder (make sure it
 * is a "sibling" file to the "src" folder in your project otherwise it won't be found.
 * 
 * You know you have this class correctly implemented when the output looks like this:
 * 
 * thou (37)
 * thy (35)
 * the (31)
 * to (27)
 * and (23)
 * in (20)
 * that (19)
 * ...
 * 
 */
public class ReportDuplicates {
	
	public static void main(String[] args) {
		In in = new In ("10sonnets.txt");
		
		// In solving this problem, you should take advantage of the following symbol table whose
		// implementation is provided for you. There is also a Max Priority Queue into which you 
		// can insert WordPair objects; please review that class to see how these WordPair objects
		// are to be compared against each other.
		SequentialSearchST<String,Integer> words = new SequentialSearchST<String,Integer>();
		MaxPQ<WordPair> pq = new MaxPQ<WordPair>(100);
		
		while (!in.isEmpty()) {
			String word = in.readString();
			
			// Instead of printing, you now need to do some processing.
			StdOut.println(word);
		}
		
		
	}
}
