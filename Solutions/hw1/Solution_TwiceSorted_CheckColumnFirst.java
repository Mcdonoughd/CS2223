package algs.solutions.hw1;

import algs.hw1.TwiceSorted;

/**
	{2, 4, 19, 28, 30, 42, 60, 92},
	{6, 8, 20, 29, 36, 48, 80, 93},
	{31, 33, 34, 35, 38, 49, 82, 94},
	{32, 37, 39, 41, 47, 51, 83, 95},
	{40 ,50 ,52, 53, 55, 57, 85, 96},
	{43, 54, 56, 61, 63, 65, 87, 97},
	{44, 58, 59, 62, 64, 68, 88, 98},
	{74, 75, 76, 77, 78, 79, 89, 99}};
	
	Assume you are looking for 50. Start with the left-most column.  indexOfCol(target, 0, 0, length()-1);
	The API for this method states that you believe the value could exist in columns 0 (and higher), bounded by the rows [0,len-1]. 
	
	indexOfCol(target, 0, 0, length()-1);
	
	Once you find column-0 doesn't contain 50, then lo=7 and hi=6. This tells you that 50 cannot exist in the 7th row (and below), 
	but perhaps it might yet appear above it somewhere.
	
	col=0
	{xx, 4, 19, 28, 30, 42, 60, 92},
	{xx, 8, 20, 29, 36, 48, 80, 93},
	{xx, 33, 34, 35, 38, 49, 82, 94},
	{xx, 37, 39, 41, 47, 51, 83, 95},
	{xx ,50 ,52, 53, 55, 57, 85, 96},
	{xx, 54, 56, 61, 63, 65, 87, 97},
	{xx, 58, 59, 62, 64, 68, 88, 98},   hi=6
	{xx, xx, xx, xx, xx, xx, xx, xx}};  lo=7
	
	So we now search indexOfRow(target, hi, col+1, length()-1), which means the value
	could exist in rows [0,hi] and columns[col+1,len-1], so start searching within row hi (the 6th row) and you find that it
	doesn't exist, and what remains is left=1
	
	    left=1
	{xx, 4, 19, 28, 30, 42, 60, 92},
	{xx, 8, 20, 29, 36, 48, 80, 93},
	{xx, 33, 34, 35, 38, 49, 82, 94},
	{xx, 37, 39, 41, 47, 51, 83, 95},
	{xx ,50 ,52, 53, 55, 57, 85, 96},
	{xx, 54, 56, 61, 63, 65, 87, 97},
	{xx, yy, yy, yy, yy, yy, yy, yy},
	{xx, xx, xx, xx, xx, xx, xx, xx}};
	
	This leaves us to search IndexOfCol(target,left,0,5) and it will be found.
	
*/
public class Solution_TwiceSorted_CheckColumnFirst extends TwiceSorted {

	public Solution_TwiceSorted_CheckColumnFirst(int[][] a) {
		super(a);
	}
	
	public Solution_TwiceSorted_CheckColumnFirst() {
		super();
	}
	
	/**
	 * Row/Col are inclusive indices (i.e., in the range from 0..n-1 for the collection. 
	 * 
	 * Note that 'a' is a square matrix with a number of rows that is a power of 2. 
	 * When calling this method, you are looking for a value in (inclusively) 
	 * rows [0,row] and columns[left,right]
	 * 
	 * @param target   target to search
	 * @param row      row of interest, within [left, right] sub-range
	 * @return
	 */
	int[] indexOfRow(int target, int row, int left, int right) {
		if (row < 0 || row >= length()) { return null; }
		
		// Within this Row [0..rightCol] use Binary search for target.
		while (left <= right) {
			int mid = left + (right - left)/2;
			
			int rc = inspect(row,mid) - target;
			if (rc < 0) {
				left = mid+1;
			} else if (rc > 0) {
				right = mid-1;
			} else {
				return new int[] {row, mid};
			}
		}
		
		// if we get here, then must check 'left' column, starting from 0 up to row-1.
		return indexOfCol(target, left, 0, row-1);
	}
	
	/**
	 * When calling this method, you are looking for a value
	 * in rows [lo,hi] and columns [col, len-1]
	 * 
	 * @param target
	 * @param col
	 * @param lo
	 * @param hi
	 * @return
	 */
	int[] indexOfCol(int target, int col, int lo, int hi) {
		if (col < 0 || col >= length()) { return null; }
		
		// Within this Col [rightRow..a.length] use Binary search for target.
		while (lo <= hi) {
			int mid = lo + (hi - lo)/2;
			
			int rc = inspect(mid,col) - target;
			if (rc < 0) {
				lo = mid+1;
			} else if (rc > 0) {
				hi = mid-1;
			} else {
				return new int[] {mid, col};
			}
		}
		
		// if we get here, then must check 'high' column, starting from col+1 up to len-1
		return indexOfRow(target, hi, col+1, length()-1);
	}

	/** 
	 * Try to check column first
	 */
	@Override
	public int[] locate(int target) {
		int[] pair = indexOfCol(target, 0, 0, length()-1);
		return pair;
	}

	/** Copy this code to work with the existing built-in trial. */ 
	public static void main (String args[]) {
		System.out.println("Number of inspections:" + new Solution_TwiceSorted_CheckColumnFirst().trial(512));
	}
	
}
