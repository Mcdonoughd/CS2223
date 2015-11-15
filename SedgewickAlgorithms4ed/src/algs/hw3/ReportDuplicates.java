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
		// Make sure that the file "10sonnets.txt" is found as a file in your top-level project. Don't 
		// place it within your package folder where this code resides. See the level where I have placed 
		// it in the GitRepository and you should do the same.
		In in = new In ("10sonnets.txt");
		
		// In solving this problem, you should take advantage of the following symbol table whose
		// implementation is provided for you. There is also a Max Priority Queue into which you 
		// can insert WordPair objects; please review that class to see how these WordPair objects
		// are to be compared against each other.
		SequentialSearchST<String,Integer> words = new SequentialSearchST<String,Integer>();
		MaxPQ<WordPair> pq = new MaxPQ<WordPair>(100);
		
		// Demonstration: The following demonstrates what SequentialSearchST can do. Naturally you 
		// will remove this code from your final homework.
		words.put("friend", 19);
		words.put("another", 3);
		words.put("that", 1);
		
		for (String k : words) {
			StdOut.println(k + " is a key in SequentialSearchST");
		}
		
		// A wordPair can be compared
		WordPair wp1 = new WordPair("true", 13);
		WordPair wp2 = new WordPair("anyone", 13);
		WordPair wp3 = new WordPair("challenge", 10);
		
		StdOut.println ("wp1 compared to wp2: " + wp1.compareTo(wp2));
		StdOut.println("  Returns negative number because 'true' must come after 'anyone' in the output ");
		StdOut.println("  while they have same frequency, 'anyone' appears before 'true' alphabetically.");
		StdOut.println("wp2 compared to wp3: " + wp2.compareTo(wp3));
		StdOut.println("  Returns positive number because 'anyone' must come before challenge in the output ");
		StdOut.println("  'anyone' has greater frequency than 'challenge'");
		
		
		// This demonstrates how you read words from a file
		while (!in.isEmpty()) {
			String word = in.readString();
			
			// Instead of printing, you now need to do some processing.
			StdOut.println(word);
		}
		
		
	}
}
