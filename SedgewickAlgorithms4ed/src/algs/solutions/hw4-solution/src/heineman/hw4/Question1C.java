package heineman.hw4;

import edu.princeton.cs.algs4.StdOut;

public class Question1C {

	/**
	 * Once you have merge working, complete this code so it works.
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
		
		// validate we also work for arbitrary shaped trees. Use the one in the homework example
		// Note that this will no
		Integer[] vals = new Integer[] { 1,2,3,4,5,6,7,8,9,10,11,12 };
		avl = new AVL<Integer>(vals);
		
		StdOut.println("AVL tree should have size of 12:" + avl.size());
		StdOut.println("Number of rotations should be zero:" + avl.rotations);
		if (avl.size() != 12) {
			StdOut.println ("  *** UNABLE TO VALIDATE ***");
		}
		
		// what if all values are the same?
		vals = new Integer[15];
		for (int i = 0; i < vals.length; i++) { vals[i] = 5; }
		avl = new AVL<Integer>(vals);
		StdOut.println("AVL tree should have size of 15:" + avl.size());
		StdOut.println("Number of rotations should be zero:" + avl.rotations);
	}

}
