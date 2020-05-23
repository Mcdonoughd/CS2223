package algs.solutions.hw1;

import algs.hw1.TwiceSorted;

/**
 * 
 	{2, 4, 19, 28, 30, 42, 60, 92},
	{6, 8, 20, 29, 36, 48, 80, 93},
	{31, 33, 34, 35, 38, 49, 82, 94},
	{32, 37, 39, 41, 47, 51, 83, 95},
	{40 ,50 ,52, 53, 55, 57, 85, 96},
	{43, 54, 56, 61, 63, 65, 87, 97},
	{44, 58, 59, 62, 64, 68, 88, 98},
	{74, 75, 76, 77, 78, 79, 89, 99}};
	
	Assume you are looking for 50. Start with the top-most row indexOfRow(target, 0, 0, length()-1);
	The API for this method states that you believe the value could exist in rows [0,len-1], bounded by 
	the columns [0,len-1]. 
	
	indexOfRow(target, 0, 0, length()-1);

	Once you find row-0 doesn't contain 50, then lo=6 and hi=5. This tells you that 50 cannot exist
	in the 6th column (and beyond) but perhaps it might yet appear in the column [0-5] and rows
	[1,len-1]

	{2, 4, 19, 28, 30, 42, xx, xx},
	{6, 8, 20, 29, 36, 48, xx, xx},
	{31, 33, 34, 35, 38, 49, xx, xx},
	{32, 37, 39, 41, 47, 51, xx, xx},
	{40 ,50 ,52, 53, 55, 57, xx, xx},
	{43, 54, 56, 61, 63, 65, xx, xx},
	{44, 58, 59, 62, 64, 68, xx, xx},
	{74, 75, 76, 77, 78, 79, xx, xx}}

	indexOfCol(target, 5, 1, length()-1)

	Once you find column-5 doesn't contain 50, then lo=3 and hi=2. We are only able to advance
	one column, but we now need to check...

	{2, 4, 19, 28, 30, yy, xx, xx},
	{6, 8, 20, 29, 36, yy, xx, xx},
	{31, 33, 34, 35, 38, yy, xx, xx},
	{32, 37, 39, 41, 47, yy, xx, xx},
	{40 ,50 ,52, 53, 55, yy, xx, xx},
	{43, 54, 56, 61, 63, yy, xx, xx},
	{44, 58, 59, 62, 64, yy, xx, xx},
	{74, 75, 76, 77, 78, yy, xx, xx}}

	indexOfRow(target, 3, 0, 4). Once you find row-3 doesn't contain 50, we try to find in column 4,
	from rows [4,len-1]. Note how we can now completely eliminate the top-left corner of the array.
	
	{2, 4, 19, 28, 30, yy, xx, xx},
	{6, 8, 20, 29, 36, yy, xx, xx},
	{31, 33, 34, 35, 38, yy, xx, xx},
	{zz, zz, zz, zz, zz, yy, xx, xx},
	{40 ,50 ,52, 53, 55, yy, xx, xx},
	{43, 54, 56, 61, 63, yy, xx, xx},
	{44, 58, 59, 62, 64, yy, xx, xx},
	{74, 75, 76, 77, 78, yy, xx, xx}}

	indexOfCol(target, 4, 4, len-1). Once you find column-4 doesn't contain 50, we try to find in
	row 4, columns [0,3]
 
    {2, 4, 19, 28, 30, yy, xx, xx},
	{6, 8, 20, 29, 36, yy, xx, xx},
	{31, 33, 34, 35, 38, yy, xx, xx},
	{zz, zz, zz, zz, zz, yy, xx, xx},
	{40 ,50 ,52, 53, aa, yy, xx, xx},
	{43, 54, 56, 61, aa, yy, xx, xx},
	{44, 58, 59, 62, aa, yy, xx, xx},
	{74, 75, 76, 77, aa, yy, xx, xx}}
 
 	Eventually you find it!
 
 *
 */
public class Solution_TwiceSorted_CheckRowFirst extends TwiceSorted {

	public Solution_TwiceSorted_CheckRowFirst(int[][] a) {
		super(a);
	}
	
	public Solution_TwiceSorted_CheckRowFirst() {
		super();
	}
	
	/**
	 * Row/Col are inclusive indices (i.e., in the range from 0..n-1 for the collection. 
	 * 
	 * Note that 'a' is a square matrix with a number of rows that is a power of 2. 
	 * Target could still exist in the array from rows [row..len-1] and col [left,right]
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
		
		// if we get here, then must check 'right' column, starting from next row
		return indexOfCol(target, right, row+1, length()-1);
	}
	
	/** Target could still exist in the array rows [lo,hi] and columns [0, col]. */
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
		
		// if we get here, then must check 'high' column, starting from row, and back one column
		return indexOfRow(target, lo, 0, col-1);
	}

	/** 
	 * Checks the row first, then recursively the column.
	 */
	@Override
	public int[] locate(int target) {
		return indexOfRow(target, 0, 0, length()-1);
	}

	/** Copy this code to work with the existing built-in trial. */ 
	public static void main (String args[]) {
		System.out.println("Number of inspections:" + new Solution_TwiceSorted_CheckRowFirst().trial(512));
	}
	
}
