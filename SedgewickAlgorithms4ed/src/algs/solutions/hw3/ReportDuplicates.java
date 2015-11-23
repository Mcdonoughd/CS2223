package algs.solutions.hw3;

// place the "10sonnets.txt" file within your top-level project so it can be found.

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * You are to modify this class to process all of the words in the given 10sonnets.txt file; you can
 * copy this file from the Git repository and place it directly in your project folder (make sure it
 * is a "sibling" file to the "src" folder in your project otherwise it won't be found.
 * 
 */
public class ReportDuplicates {
	
	public static void main(String[] args) {
		In in = new In ("10sonnets.txt");
		SequentialSearchST<String,Integer> words = new SequentialSearchST<String,Integer>();
		MaxPQ<WordPair> pq = new MaxPQ<WordPair>(100);
		
		while (!in.isEmpty()) {
			String word = in.readString();
			
			// Instead of printing, you now need to do some processing.
			if (words.contains(word)) {
				int count = words.get(word);
				words.put(word, count+1);
			} else {
				words.put(word, 1);
			}
		}
		
		
		for (String key : words) {
			Integer val = words.get(key);
			if (val > 1) {
				pq.insert(new WordPair(key, val));
			}
		}
		
		// now that we have this constructed, traverse each of the words to retrieve those
		// that are duplicated, and add to heap as priority queue. Note that we have to include
		// the necessary array resizing so we won't run out of room.
		

		while (!pq.isEmpty()) {
			WordPair pair = pq.delMax();
			
			StdOut.println (pair.word + " (" + pair.count + ")");
		}
	}
}
