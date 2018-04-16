package algs.hw4;

import edu.princeton.cs.algs4.StdOut;

/** 
 * Validates question 1c.
 */
public class Question1C {

	/**
	 * Once you have the constructor working, this code will validate it (somewhat)
	 */
	public static void main(String[] args) {
		
		// Create sorted array of size 2^k - 1 = 31.
		// This array is {1, 3, 5, ... }
		Integer[] sorted = new Integer[31];
		for (int i = 0; i < sorted.length; i++) {
			sorted[i] = 2*i+1;
		}
		
		AVL<Integer> avl = new AVL<Integer>(sorted);
		
		// done
		StdOut.println("AVL tree should have size of 31:" + avl.size());
		StdOut.println("Number of rotations should be zero:" + avl.rotations);
		if (avl.size() != 31) {
			StdOut.println ("  *** UNABLE TO VALIDATE ***");
		}
		
		// make sure all values are there...
		for (int i = 0; i < sorted.length; i++) {
			if (!avl.contains(2*i+1)) {
				StdOut.printf ("  *** UNABLE TO VALIDATE: Missing %d ***\n", 2*i+1);		
			}
		}
	}

}
