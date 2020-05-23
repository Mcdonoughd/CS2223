package algs.solutions.hw1;

import algs.hw1.TwiceSorted;

/**
	{2,  4,  19, 28, 30, 42, 60, 92},
	{6,  8,  20, 29, 36, 48, 80, 93},
	{31, 33, 34, 35, 38, 49, 82, 94},
	{32, 37, 39, 41, 47, 51, 83, 95},
	{40, 50 ,52, 53, 55, 57, 85, 96},
	{43, 54, 56, 61, 63, 65, 87, 97},
	{44, 58, 59, 62, 64, 68, 88, 98},
	{74, 75, 76, 77, 78, 79, 89, 99}};
	
	Assume you are looking for 37. Work the diagonals, until you can determine that the value '50' must
	exist between '34' and '41'. Now, where could 50 possibly exist in the 2d array?
	
	{x,  x,  xx, 28, 30, 42, 60, 92},
	{x,  x,  xx, 29, 36, 48, 80, 93},
	{xx, xx, xx, 35, 38, 49, 82, 94},
	{32, 37, 39, xx, xx, xx, xx, xx},
	{40, 50 ,52, xx, xx, xx, xx, xx},
	{43, 54, 56, xx, xx, xx, xx, xx},
	{44, 58, 59, xx, xx, xx, xx, xx},
	{74, 75, 76, xx, xx, xx, xx, xx}}; 
	
	It could be in either remaining quadrant. So recursively try each one. The diagonal is a bit
	confusing when it is rectangular, it all works out because of the base case in the recursion.
	Specifically, if you get down to a region that has negative width or height, you return null.

	
*/
public class Solution_TwiceSorted_Diagonal extends TwiceSorted {

	public Solution_TwiceSorted_Diagonal(int[][] a) {
		super(a);
	}
	
	public Solution_TwiceSorted_Diagonal() {
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
		int[] pair = indexOfDiagonal(target, 0, length()-1, 0, length()-1);
		return pair;
	}

	/** Copy this code to work with the existing built-in trial. */ 
	public static void main (String args[]) {
		System.out.println("Number of inspections:" + new Solution_TwiceSorted_Diagonal().trial(512));
	}
	
}
