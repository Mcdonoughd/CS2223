package algs.solutions.hw1;

import algs.hw1.TwiceSorted;

public class Solution_RepeatedBinarySearch extends TwiceSorted {

	public Solution_RepeatedBinarySearch(int[][] a) {
		super(a);
	}
	
	public Solution_RepeatedBinarySearch() {
		super();
	}
	
	/**
	 * Row/Col are inclusive indices (i.e., in the range from 0..n-1 for the collection. 
	 * 
	 * Note that 'a' is a square matrix with a number of rows that is a power of 2. 
	 * 
	 * @param target
	 * @param row      row to be searched
	 * @return
	 */
	int[] indexOfRow(int target, int row) {
		int lo = 0;
		int hi = length()-1;
		
		// Within this Row [0..rightCol] use Binary search for target.
		while (lo <= hi) {
			int mid = lo + (hi - lo)/2;
			
			int rc = inspect(row,mid) - target;
			if (rc < 0) {
				lo = mid+1;
			} else if (rc > 0) {
				hi = mid-1;
			} else {
				return new int[] {row, mid};
			}
		}
		
		// not found
		return null;
	}
	
	
	/** 
	 * Runs Binary search on all rows in order.
	 */
	@Override
	public int[] locate(int target) {
		for (int r = 0; r < length(); r++) {
			// too small, and will never be found again, because constantly increasing
			// However, checking EACH time actually costs us in the end, so omit. See
			// what happens if you leave this in...
			// ==========================================
			// if (target < inspect(r, 0)) { return null; }  
			
			int[] location = indexOfRow(target, r);
			if (location != null) { return location; }
		}
		
		return null;
	}

	/** Copy this code to work with the existing built-in trial. */ 
	public static void main (String args[]) {
		System.out.println("Number of inspections:" + new Solution_RepeatedBinarySearch().trial(512));
	}
	
}
