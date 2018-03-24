package algs.solutions.hw1;

import algs.hw1.TwiceSorted;

/**
 * Same as TwiceSorted_Diagonal, but first checks against largest.
 *
 */
public class Solution_TwiceSorted_Diagonal_pre_check extends TwiceSorted {

	public Solution_TwiceSorted_Diagonal_pre_check(int[][] a) {
		super(a);
	}
	
	public Solution_TwiceSorted_Diagonal_pre_check() {
		super();
	}
	
	/**
	 * Row/Col are inclusive indices (i.e., in the range from 0..n-1 for the collection. 
	 * 
	 * Note that 'a' is a square matrix with a number of rows that is a power of 2. 
	 * 
	 * [t,b] is row range and stands for [top,bottom]
	 * [l,r] is column range and stands for [left,right]
	 * 
	 * @param target   target to search
	 */
	int[] indexOfDiagonal(int target, int t, int b, int l, int r) {
		// any invalid cases are resolved immediately
		if (l > r || t > b) { return null; }
		
		// we need to remember our original parameters, so we introduce new variables.
		int left = l, right = r, top = t, bottom = b;
		
		// Look diagonally down a[t..b][l..r]; advance diagonally as well.
		while (left <= right && top <= bottom) {
			int midr = top + (bottom - top)/2;
			int midc = left + (right - left)/2;
			
			int rc = inspect(midr,midc) - target;
			if (rc < 0) {
				left = midc+1; 
				top = midr+1;
			} else if (rc > 0) {
				right = midc-1; 
				bottom = midr-1;
			} else {
				return new int[] { midr, midc};
			}
		}
		
		// try "upper right" quadrant
		int[] found = indexOfDiagonal(target, t, bottom, left, r);
		if (found != null) { return found; }

		// go ahead and try "lower left" quadrant.
		return indexOfDiagonal(target, top, b, l, right);
	}


	/** 
	 * Search diagonally.
	 */
	@Override
	public int[] locate(int target) {
		int corner = inspect(length()-1, length()-1);
		if (corner <= target) {
			if (corner == target) { return new int[] { length()-1, length()-1 }; }
			return null;
		}
		
		// now check for contents.
		int[] pair = indexOfDiagonal(target, 0, length()-1, 0, length()-1);
		return pair;
	}

	/** Copy this code to work with the existing built-in trial. */ 
	public static void main (String args[]) {
		System.out.println("Number of inspections:" + new Solution_TwiceSorted_Diagonal_pre_check().trial(512));
	}
	
}
