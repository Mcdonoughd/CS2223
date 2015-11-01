package algs.days.day03;

import java.util.ArrayList;
import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/** 
 * Observe that we can short-circuit a search when (a) a word does not match; and (b) we find our
 * 
 * largest prefix which is not the prefix for some word in the dictionary. At that point, we 
 * can short circuit.
 *
 * As an aside, I am really excited that this casual example -- which I came up with earlier in the
 * week -- has proven to be so productive! Note that I haven't eliminate the combinatorial explosion
 * of the problem. Rather, I am taking advantage of the non-uniform distribution of the English language
 * to eliminate countless permutations that are simply not valid.
 * 
 * @author George Heineman
 */
public class AnagramThird {

	// store letters here and the words from the dictionary.
	static char[] letters;
	static String[] words;

	// maximum number of anagrams to discover
	static int MAX_NUM_ANAGRAMS = 1000; 
	
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
	static void process() {
		Stack<Integer> candidate = new Stack<Integer>();
		
		// minStart is an odd state variable. It only comes into play when we short-circuit a specific solution,
		// in which case we want to start with the next available position, rather than starting from 0.
		int minStart = 0;

		// instead of using recursion, keep the stack and manipulate it constantly. 
		while (true) {
			
			// once we have a full stack representing a permutation, see if we have a word or we can short-circuit.
			if (candidate.size() == letters.length) {
				
				// StringBuilder is faster for appending characters.
				StringBuilder word = new StringBuilder();
				for (int pos : candidate) {
					word.append(letters[pos]);
				}
				String s = word.toString();
				
				// if the constructed word exists in dictionary (i.e., its rank is not -1), then add to our
				// collection of anagrams. Do this way to prevent duplicates.
				if (rank(s, words) != -1) {
					if (!anagrams.contains(s)) {
						anagrams.add(s);
					}
				} else {
					// otherwise, find our largest prefix which is not the prefix for any word in the dictionary.
					// we can short-circuit the process from that point, which we do by removing letters from the
					// stack and continuing on to the next opportunity if possible.
					int shortCircuit = largestPrefixNonDictionaryPrefix(s);
					while (shortCircuit < candidate.size()) {
						candidate.pop();
					}
				}

				// at this point we either have a short-circuit or a max-letter word. In either case, we pop
				// off the final position in the candidate and advance it to be the "next one" that we will 
				// attempt to add to candidate.
				minStart= candidate.pop();
				minStart++;

			} else {
				// bulk back up to letters.length. Ok, here is the real complexity. The key is we don't want to
				// miss any permutations. We remember the 'minStart' from the short-circuit and start from that 
				// point. 
				while (candidate.size() < letters.length) {
					// assume we can't add another candidate, which can happen when the final position has
					// run out of values (which can only be in the range 0 .. letters.length-1). Honestly, 
					// this inner while loop was one of the most challenging parts of getting this working.
					// I wish I could explain how I got this to work, but it was done carefully within a debugger
					// tracing the results of the first short-circuit request that I got working.
					boolean added = false;
					int i = minStart;
					while (i < letters.length) {
						if (!candidate.contains(i)) {
							candidate.push(i);   // only come inside loop if i not already contained. This limits infinite loops.
							added = true;
							if (minStart != 0) {
								i = 0;
								minStart = 0;
								continue;  // make sure to reset at 0 so we don't miss any positions.
							}
						}
						
						i++;
					}

					if (!added) {
						// we cannot proceed with our existing candidate. We must advance the final position 
						// in our candidate. If we are even empty, then we hav exhausted all possible permutations.
						if (candidate.isEmpty()) { 
							return; // we are done!
						}
						
						// this retrieves the final position in our candidate and advances it. We break so we are
						// able to restart our outer loop which grows the partial candidate until it has reached
						// the maximum number of letters allowed.
						minStart= candidate.pop();
						minStart++;
						break;
					}
				}
			}
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
		
		process();
		
		System.out.println("Here are the anagrams.");
		for (String s : anagrams) {
			System.out.println(s);
		}
	}
}