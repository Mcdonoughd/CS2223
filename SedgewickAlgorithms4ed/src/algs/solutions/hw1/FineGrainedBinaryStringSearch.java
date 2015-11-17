package algs.solutions.hw1;

import java.util.StringTokenizer;

// complete a more fine-grained analysis regarding actual number of comparisons,
// given that the distribution of letters means you don't always need K comparisons
// to compare two K-character tokens.
public class FineGrainedBinaryStringSearch {
	
	final int K;
	final String tokens;
	
	// used to count comparisons.
	static int compare = 0;
	
	/**
	 * Construct search structure.
	 * @param initial
	 * @param K
	 */
	public FineGrainedBinaryStringSearch(String initial, int K) {
		this.K = K;
		this.tokens = initial;
	}
	
	/** 
	 * Perform fewest number of comparisons to compare word with tokens[pos,pos+K).
	 * Assumes that all tokens are K characters. Assumes word has K characters. 
	 */
	int compare (String word, int pos) {
		// will check NO MORE than K. Might stop earlier.
		for (int i = 0; i < K; i++) {
			compare++;
			
			char ch = tokens.charAt(pos);
			int diff = word.charAt(i) - ch;
			if (diff < 0) { return -1; }  // "cat" to "cot"
			if (diff > 0) { return +1; }  // "cry" to "cat"
			pos++;
		}

		return 0;
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
			// Avoid creating substring by acting right on the string.
			int rc = compare(token, mid*(K+1));
			
			if (rc < 0) hi = mid - 1;
			else if (rc > 0) lo = mid + 1;
			else return mid;
		}
		
		return -1;
	}
	
	// sample driver for your code.
	public static void main(String[] args) {
		String target = "aa ab ac ba bb bc ca cb cc";
		int k = 2;
		
		FineGrainedBinaryStringSearch bss = new FineGrainedBinaryStringSearch(target, k);
		
		// K*(1 + Floor(log (N))) 
		// 2*(1 + Floor(log (9))) is 8
		//
		// but as you can see, the worst case is now just 7
		StringTokenizer st = new StringTokenizer(target);
		while (st.hasMoreTokens()) {
			String w = st.nextToken();
			w = "cc";
			System.out.println(w + "," + bss.rank(w) + "," + compare);
		}
		
	}
}
