package algs.days.day04;

import edu.princeton.cs.algs4.StdOut;

public class SampleBooleanArrayInvestigation {
	/** 
	 * Given a two-dimensional array, count the number of true values it contains.
	 * 
	 * Need more help on arrays? Check out http://math.hws.edu/javanotes/c7/s5.html
	 *  
	 */
	static int countNumberTrue(boolean ar[][]) {
		// keep track of the number of true values in this variable
		int ctr = 0;
		
		/** Visit each row in the array exactly once. */
		for (int row = 0; row < ar.length; row++) {
			
			// For each row, visit all of its columns. Assume the array is rectangular
			// which means each row has the same number of columns
			int numColumns = ar[row].length;
			for (int col = 0; col < numColumns; col++) {
				if (ar[row][col] == true) {
					ctr++;
				}
			}
		}		
		
		return ctr;
	}
	
	// sample code to validate above works.
	public static void main(String[] args) {
		boolean [][] sample = new boolean[][] {
				{ true,  false, true,  true },
				{ false, false, true,  false },  
				{ false, false, false, false },
				{ true,  false, false, true }
		};
		
		StdOut.println("Count number true: " + countNumberTrue(sample));
		StdOut.println("The above should be six. Doh!");
	}
}
