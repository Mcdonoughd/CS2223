package algs.hw1;

public class BinaryStringSearch {
	
	final int K;
	final String tokens;
	
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
		// your code here
		return -1;
	}
	
	// sample driver for your code.
	public static void main(String[] args) {
		String target = "ant apt awl box boy car cat dog man nap pot try you";
		int k = 3;
		
		// insert statements to validate you can look for tokens that are present
		// as well as tokens that are not present.
		
	}
}
