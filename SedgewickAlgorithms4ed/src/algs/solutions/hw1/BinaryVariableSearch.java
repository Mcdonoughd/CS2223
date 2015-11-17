package algs.solutions.hw1;

import edu.princeton.cs.algs4.In;

/**
 * You have a string S which contains n strings in sorted order, separated by spaces. Each of these
 * strings is no more than k characters in length and is only composed of lower case letters drawn
 * from a standard English alphabet of 26 letters. The total length of the string S is Sn.
 *  
 * Review the following algorithm.
 *  
 * (a) In the worst case, count the number of character comparisons using just these terms: n, Sn and k
 * (b) Suggest a more efficient algorithm inspired by lecture material.
 * (c) In the worst case, count the number of character comparisons of your new algorithm using just 
 *     these terms: n, Sn and k
 * (d) Code the algorithm and provide empirical results to confirm your analysis 
 * (e) What if we remove the restriction that each string is smaller than length k. Analyze both algorithms
 *     again for worst-case character comparisons.
 * (f) Extra points for adding 'remove' and 'add' methods. Note that 'add' must maintain set semantics
 * 
 * Bonus Points Question
 * 
 * @author George Heineman
 */
public class BinaryVariableSearch {
	// reasonable default. Must never terminate with separator character.
	String target = "a alpha beta charlie david dog drinking elephant fraught me myself neverwhere pontificant";
	int k = 11;
	char separator = ' ';

	// count all character positions investigated.
	int ctr = 0;

	public static void main(String[] args) {
		In in = new In ("words.english.txt");
		String[] fullList = in.readAllStrings();
		StringBuffer sb = new StringBuffer();
		int maxLen = 0;
		for (String s : fullList) {
			sb.append(s).append(' ');
			if (s.length() > maxLen) {
				maxLen = s.length();
				System.out.println("Longest word so far is " + s);
			}
		}
		sb.deleteCharAt(sb.length()-1); // remove final space.

		// now this is space separated.
		BinaryVariableSearch bvs = new BinaryVariableSearch(sb.toString(), maxLen);
		System.out.println("Maximum word length is: " + maxLen);

		int maxCounter = 0;
		for (String s : fullList) {
			int priorCounter = bvs.ctr;
			bvs.contains(s);
			int realCounter = bvs.ctr - priorCounter;

			if (realCounter > maxCounter) {
				System.out.println("Took " + realCounter + " character comparisons to locate " + s);
				maxCounter = realCounter;
			}
		}

		// worst case is 612 characters.
		System.out.println("N = " + fullList.length);

		// test add by adding 'cat' -- seriously? It wasn't there before?
		System.out.println("Adding cat is:" + bvs.add("cat"));
		
		// test add by removing 'cat' 
		System.out.println("Removing cat is:" + bvs.remove("cat"));
				
	}

	public BinaryVariableSearch() {

	}

	public BinaryVariableSearch(String list, int k) {
		this.target = list;
		this.k = k;
	}

	/** 
	 * Return previous start of word not including position.
	 * If pos actually happens to identify a separator position then goal is to return the previous word.
	 */
	int previous(int pos) {
		pos--;
		if (pos < 0) { return pos; }   // special case if already at start

		// handle special case if 'pos' had actually been at the start of a word.
		ctr++;
		if (target.charAt(pos) == separator) { pos--; }

		// go back to first letter after a separator
		while (pos > 0) {
			ctr++;
			if (target.charAt(pos) == separator) { return pos+1; }
			pos--;
		}

		return pos;
	}

	/** 
	 * Return next start of word not including position.
	 * If pos actually happens to identify a separator position then goal is to return next word;
	 * If no more words, then must return -1.
	 */
	int next(int pos) {

		// handle special case if 'pos' had actually been at the start of a word.
		ctr++;
		if (target.charAt(pos) == separator) { return pos+1; }

		// move forward to first space (or end of word)
		while (pos < target.length()) {
			ctr++;
			if (target.charAt(pos) == separator) { return pos+1; }
			pos++;
		}

		// no more chance
		return -1;
	}

	/**
	 * If word exists in target at the given pos, then return 0. Must handle special case
	 * when run out of characters.
	 * 
	 * @param word
	 * @param pos
	 * @return
	 */
	int compare (String word, int pos) {

		// if any letters run out before word is done, not valid
		for (int i = 0; i < word.length(); i++) {

			// have run out. word is greater
			if (pos > target.length()) { return +1; }
			ctr++;
			char ch = target.charAt(pos);
			if (word.charAt(i) < ch) { return -1; }
			if (word.charAt(i) > ch) { return +1; }
			pos++;
		}

		// must make sure we either are at limit of string or are on a separator.
		if (pos == target.length()) { return 0; }

		// terminated cleanly
		ctr++;
		if (target.charAt(pos) == separator) { return 0; }

		// whoops. word is smaller.
		return -1;
	}

	/**
	 * Note this might be how you would implement this functionality, but it requires
	 * far too many characters to search and it takes no advantage of the sorted nature
	 * of the original string.
	 */
	public boolean reallyNaiveContains (String word) {
		// first check if only one word in collection. If so, then we must check entire string
		// Worst Case: k+1 comparisons
		if (target.indexOf(separator) == -1) {
			return target.equals(word);
		}

		// at least one separator. Check to see if last one or first one
		// Worst Case: k+1 comparisons
		if (target.endsWith(separator + word)) {
			return true;
		}

		// Worst Case: k+1 comparisons
		if (target.startsWith(word + separator)) {
			return true;
		}

		// ok, look to see if anywhere else.
		// worst case: Each word is k characters long
		return (target.indexOf(separator + word + separator) != -1);
	}

	public boolean contains (String word) {
		int low = 0;
		int high = previous (target.length()-1);

		while (low != -1 && low <= high) {
			// special case:
			int mid;
			if (low == high) { 
				mid = low; 
			} else { 
				mid = previous((low + high) / 2); 
			}

			int rc = compare(word, mid);
			if (rc < 0) {
				high = previous(mid);
			} else if (rc > 0) {
				low = next(mid);
			} else {
				return true;
			}
		}

		return false;
	}

	/**
	 * Add word to our sorted collection if it doesn't already exist [Optional Method].
	 * 
	 * Observe that when the rank method fails, instead of returning FALSE, you 
	 * actually have information that you can use to deal with insert. Namely, 
	 * it fails because the given word should have been present at that location.
	 * This gives you the ability to insert. Note that this operation is not efficient 
	 * because it must recreate the entire string by inserting just one word.
	 * 
	 * @param word   word of fewer than k characters to be added to the collection.
	 * @return  true if the word was added; false otherwise.
	 */
	public boolean add (String word) {

		// this is slightly modified RANK
		int low = 0;
		int high = previous (target.length()-1);

		while (low != -1 && low <= high) {
			// special case:
			int mid;
			if (low == high) { 
				mid = low; 
			} else { 
				mid = previous((low + high) / 2); 
			}

			int rc = compare(word, mid);
			if (rc < 0) {
				high = previous(mid);
			} else if (rc > 0) {
				low = next(mid);
			} else {
				// can't add! Already present!
				return false;
			}
		}

		// if we get here, then low tells us exactly where we should be inserted!
		// check it out yourself!
		target = target.substring(0, low) + word + " " + target.substring(low);
		return true;
	}

	/**
	 * Remove word from our sorted collection that has previously been confirmed to be in the collection.
	 * 
	 * Observe that when the rank method succeeds, you have found the word, so it can
	 * be deleted. Note that this operation is not efficient because it must recreate
	 * the entire string by excising just one word.
	 * 
	 * @param word   word to remove
	 * @return  true if the word was removed; false otherwise [Optional Method].
	 */
	public boolean remove (String word) {
		// this is slightly modified RANK
		int low = 0;
		int high = previous (target.length()-1);

		while (low != -1 && low <= high) {
			// special case:
			int mid;
			if (low == high) { 
				mid = low; 
			} else { 
				mid = previous((low + high) / 2); 
			}

			int rc = compare(word, mid);
			if (rc < 0) {
				high = previous(mid);
			} else if (rc > 0) {
				low = next(mid);
			} else {
				// We can remove. Skip separator by adding 1
				target = target.substring(0, mid) + target.substring(mid + word.length() + 1);
				return true;
			}
		}

		// not present! return without making changes.
		return false;
	}
}
