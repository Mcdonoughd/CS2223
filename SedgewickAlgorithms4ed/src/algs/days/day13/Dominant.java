package algs.days.day13;

import edu.princeton.cs.algs4.StdOut;

public class Dominant {

	public static int dominant(int[] collection, int lo, int hi) {
		// one element only
		if (lo == hi) { return collection[lo]; }

		// find dominant value in left and right halves
		int mid = lo + (hi-lo)/2;
		int left = dominant(collection, lo, mid);
		int right = dominant(collection, mid+1, hi);

		// same on both sides? Return one arbitrarily
		if (left == right) { return left; }

		// which one wins? Have to count entirely. Performance ~N in worst case
		int i, leftCount = 0, rightCount = 0, max=collection.length/2;
		for (i = 0; i < collection.length; i++) {
			if (collection[i] == left) { 
				if (++leftCount > max) { return left; }
			} else if (collection [i] == right ) { 
				if (++rightCount > max) { return right; } 
			}
		}

		return Integer.MIN_VALUE; // none
	}
	
	public static void main(String[] args) {
		int vals[] = {1, 2, 1, 2, 1, 2, 2, 1, 2, 2};
		
		StdOut.println(dominant(vals, 0, vals.length-1));
	}
}
