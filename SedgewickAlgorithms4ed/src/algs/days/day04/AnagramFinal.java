package algs.days.day04;

import java.util.ArrayList;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/** 
 * Rephrase this algorithm in pseudocode and see if that leads to a cleaner solution
 * 
 * INPUT: letters to use
 * OUTPUT: list of anagrams using permutation of those letters only
 * 
 * 
 * # positions is lowest permutation of n index positions
 * positions = [0, 1, 2, 3, ..., n-1]
 * while positions is not empty
 *    if positions reflects a word in dictionary
 *       add word to list
 *    else
 *       find largest prefix of word which is not a prefix of any word in dictionary
 *       truncate positions[] to this spot
 * 
 *	  advance to next permutation, removing prior positions as needed.
 *    
 * 
 * @author George Heineman
 */
public class AnagramFinal {

	// store letters here and the words from the dictionary.
	static char[] letters;
	static String[] words;

	// maximum number of anagrams to discover
	static int MAX_NUM_ANAGRAMS = 1000; 
	
	static int ctr = 0;
	
	// existing words that have been found as anagrams.
	static ArrayList<String> anagrams = new ArrayList<String>();

	/** See if there exists a word in a[] whose prefix matches key for the designated prefixSize characters. */
	public static boolean matchPrefix(String key, String[] a, int prefixSize) {
		int lo = 0;
		int hi = a.length -1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;

			// this could likely be optimized to avoid a second check to key.compare() but deal with later.
			// we want to see if we have hit upon a word whose prefix (for prefixSize characters) matches
			// the desired key for whom we are looking for a prefix of designated length.
			//
			// see if the first 'prefixSize' characters of key matches any prefix of same 
			// size for any word in a[] that we see in our binary search.
			boolean match = true;
			String matchS = a[mid];
			for (int ch = 0; ch < prefixSize; ch++) {
				// too short? can't say anything...
				if (ch >= matchS.length()) {
					match = false; 
					break;
				}

				// or not match at that position? leave
				if (key.charAt(ch) != matchS.charAt(ch)) { match = false; break; }
			}
			if (match) { return true; }
			
			// thereafter, simply continue the binary search 

			int cmp = key.compareTo(a[mid]);

			if (cmp < 0) hi = mid - 1;
			else if (cmp > 0) lo = mid + 1;
			else return true; // a match! so prefix is a match too. Shouldn't get here
		}


		return false;
	}

	/** Look up word via binary array search. */
	public static int rank(String key, String[] a) {
		int lo = 0;
		int hi = a.length -1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = key.compareTo(a[mid]);
			if (cmp < 0) hi = mid - 1;
			else if (cmp > 0) lo = mid + 1;
			else return mid;
		}

		return -1;
	}

	/** Find largest prefix which IS NOT a prefix to any word in the dictionary. */
	static int largestPrefixNonDictionaryPrefix(String word) {
		for (int i = 1; i <= letters.length; i++) {
			if (!matchPrefix(word, words, i)) {
				return i;
			}
		}

		return letters.length;
	}

	/**
	 * Construct iteratively a stack 'candidate' which contains a permutation of the letters in canonical 
	 * order (that is, starting with 0,1,2,3,...,n-1 and eventually getting to n-1,n-2,...,2,1,0
	 * 
	 * Should a sample word NOT exist, then find that word's largest prefix which is not the prefix for 
	 * any word in the dictionary. Short-circuit the evaluation to deny any further investigations with 
	 * that prefix and advance the last letter to the next valid position, if possible.
	 * 
	 * Note: I had initially had a recursive solution, but it ran out of stack space because of the deep
	 * recursive calls necessary. This tricky bit of logic was the hard-fought result of wrestling the
	 * recursion into an iterative solution. 
	 */
	static void process(Stack<Integer> positions) {
		
		// instead of using recursion, keep the stack and manipulate it constantly. 
		while (positions.size() > 0) {
			
			// We have a full stack representing a permutation, see if we have a word or we can short-circuit.
				
			// StringBuilder is faster for appending characters.
			StringBuilder word = new StringBuilder();
			for (int pos : positions) {
				word.append(letters[pos]);
			}
			String s = word.toString();
			
			ctr++;
			
			// if the constructed word exists in dictionary (i.e., its rank is not -1), then add to our
			// collection of anagrams. Do this way to prevent duplicates.
			if (rank(s, words) != -1) {
				if (!anagrams.contains(s)) {
					anagrams.add(s);
				}
				
				// return once done...
				if (anagrams.size() > MAX_NUM_ANAGRAMS) {
					return;
				}
			} else {
				// otherwise, find our largest prefix which is not the prefix for any word in the dictionary.
				// we can short-circuit the process from that point, which we do by removing letters from the
				// stack and continuing on to the next opportunity if possible.
				int shortCircuit = largestPrefixNonDictionaryPrefix(s);
				while (shortCircuit < positions.size()) {
					positions.pop();
				}
			}
			
			// advance! Do this by popping last one off and try to refill back to 15. If fail, because there
			// are no more options, pop off previous one and continue. Stop when FULL or EMPTY.
			int next = positions.pop()+1;
			while (positions.size() != 0 && positions.size() != letters.length) {

				// try to fill back up to max size.
				while (positions.size() != letters.length) {
					while (next < letters.length) {
						if (!positions.contains(next)) {
							break;
						}
						next++;
					}

					// advance the prior value
					if (next == letters.length) {
						// if no more positions we are done!
						if (positions.isEmpty()) { break; }
						
						next = positions.pop() + 1;
						continue;
					}
					
					positions.push(next);
					next = 0;
				}
			}
			
			// if we get to 15 we are ready to try again...
		}
	}


	public static void main(String[] args) {
		In in = new In ("words.english.txt");
		words = in.readAllStrings();

		// note that if a letter appears multiple times in the input, there
		// could be multiple reports of the same word, since this only 
		// considers permutations. To prevent this, once a word is discovered
		// it is stored to prevent multiple reports of the same word.
		
		StdOut.println("Enter letters for which you want a SINGLE word anagram");
		String word = StdIn.readString().toLowerCase();
		letters = word.toCharArray();
		
		Stack<Integer> positions = new Stack<Integer>();
		for (int i = 0; i < letters.length; i++) {
			positions.push(i);
		}
		Stopwatch sw = new Stopwatch();
		process(positions);
		
		System.out.println("Here are the anagrams.");
		for (String s : anagrams) {
			System.out.println(s);
		}
		System.out.println("total time:" + sw.elapsedTime() + " with " + ctr + " lookups.");
	}
}