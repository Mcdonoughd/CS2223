package algs.solutions.hw1;

import java.util.StringTokenizer;

public class BinaryStringSearch {
	
	final int K;
	final String tokens;
	
	// used to count comparisons.
	static int compare = 0;
	
	/**
	 * Construct search structure.
	 * @param initial
	 * @param K
	 */
	public BinaryStringSearch(String initial, int K) {
		this.K = K;
		this.tokens = initial;
	}
	
	
	/**
	 * Modify this code to return the rank of the token within the tokens string
	 * if it exists, otherwise return -1.
	 * 
	 * note that rank is to be a number from 0 up to and including N-1 where N
	 * is the number of tokens in the string.
	 * 
	 * Take inspiration from the cod on page 47 of the text.
	 * @param token   a string of exactly K characters
	 * @return    -1 if token does not exist in tokens string, otherwise rank of that
	 *            token in our tokens string
	 */
	public int rank (String token) {
		
		// the easiest way to code this is to keep <lo,hi> refering to the nth token
		// rather than the location of the nth token
		int lo = 0;
		int hi = (tokens.length()+1)/(K+1) - 1;
		compare = 0;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			
			// only here do you both to figure out which character position is being compared
			// note that this is wasteful in space and can be optimized later. In worst
			// case there are K comparisons made
			String ms = tokens.substring(mid*(K+1), mid*(K+1) + K); 
			compare += K;
			int rc = token.compareTo(ms);
			
			if (rc < 0) hi = mid - 1;
			else if (rc > 0) lo = mid + 1;
			else return mid;
		}
		
		return -1;
	}
	
	// sample driver for your code.
	public static void main(String[] args) {
		String target = "ant apt awl box boy car cat";
		int k = 3;
		
		BinaryStringSearch bss = new BinaryStringSearch(target, k);
		
		StringTokenizer st = new StringTokenizer(target);
		while (st.hasMoreTokens()) {
			String w = st.nextToken();
			
			System.out.println(w + "," + bss.rank(w) + "," + compare);
		}
		
	}
}
