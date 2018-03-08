package algs.hw1;

/**
 * For your homework assignment, you will design a subclass that extends this class.
 * 
 * You do not need to copy this class into your USERID.hw1 package.
 */
public abstract class TwiceSorted {

	/** Array containing the values. */
	final private int[][] arr;
	
	/** Number of inspections. */
	int numChecked = 0;
	
	/** 
	 * Uses the default data to run your trial.
	 */
	public TwiceSorted() {
		this.arr = big;
		checkProperty(big);
	}
	
	/** 
	 * Validates that array maintains twice sorted property, namely that all values in the array are distinct
	 * and that the rows are organized in increasing order, and the columns are also organized in increasing order.
	 * Also makes sure this is a Square Matrix, with equal number of rows and columns.
	 * 
	 * Note: You do not need to modify this code.
	 */
	public TwiceSorted(int[][] a) {
		this.arr = a;
		checkProperty(a);
	}
	
	/** 
	 * Return the contents of array [r][c] and increment count of such inspections. 
	 *
	 * FOR THIS HOMEWORK ASSIGNMENT, this is the only allowed way to retrieve the element at a[r][c].
	 * Note how we keep track of all array inspections.
	 */
	protected int inspect(int r, int c) {
		numChecked++;
		return arr[r][c];
	}
	
	/** 
	 * Return the number of rows (or columns, since square array).
	 */
	public final int length() { return arr.length; }
	
	/**
	 * Attempt to locate the target value among a twice-sorted int[][] 2D array.
	 * 
	 * Note: To return an array of two values, use code that looks like this:
	 * 
	 *   return new int[] { r, c};
	 *   
	 * where 'r' and 'c' are the row and column of the desired value that you have found.
	 * 
	 * @param target value to be searched
	 * @return pair of integers in array [r, c] for found value, or null of not found.
	 */
	public abstract int[] locate (int target);

	/** Small example to use. */
	static final int[][] sample = new int[][] {
		{  5, 12, 18, 22},
		{  7, 24, 37, 50},
		{ 26, 27, 38, 57},
		{ 29, 33, 60, 62},
	};
	
	/** larger example to use. Do not make any modifications to these values. */
	static final int[][] big = new int[][] { 
			{2, 4, 19, 28, 30, 42, 60, 92},
			{6, 8, 20, 29, 36, 48, 80, 93},
			{31, 33, 34, 35, 38, 49, 82, 94},
			{32, 37, 39, 41, 47, 51, 83, 95},
			{40 ,50 ,52, 53, 55, 57, 85, 96},
			{43, 54, 56, 61, 63, 65, 87, 97},
			{44, 58, 59, 62, 64, 68, 88, 98},
			{74, 75, 76, 77, 78, 79, 89, 99}};
	
	/** 
	 * Ensures the array conforms to the problem specification:
	 * 
	 *   1. Array is a square 2D array
	 *   2. Values appear in ascending sorted order in each row
	 *   3. Values apepar in ascending sorted order in each column
	 *   4. No value is duplicated in the array 
	 * 
	 * Throws Exception if a is not twice sorted or contains duplicate values, as per homework instructions.
	 * 
	 * @param a    array to be investigated
	 */
	final void checkProperty(int[][] a) {
		
		// check property by storing all 
		int[] values = new int[a.length*a.length];
		int idx = 0;
		
		for (int i = 0; i < a.length; i++) {
			// make sure that all rows are sorted from left to right.
			for (int c = 0; c < a[i].length-1; c++) {
				if (a[i][c] >= a[i][c+1]) {
					throw new IllegalArgumentException ("Array is not Twice Sorted (Row " + i + ").");
				}
				values[idx] = a[i][c];
				for (int x = 0; x < idx; x++) { 
					if (values[x] == values[idx]) { throw new IllegalArgumentException ("Duplicate value in a:" + values[idx]); } 
				}
				idx++;
			}
			values[idx] = a[i][a[i].length-1];
			for (int x = 0; x < idx; x++) { 
				if (values[x] == values[idx]) { throw new IllegalArgumentException ("Duplicate value in a:" + values[idx]); } 
			}
			idx++;
			
			// make sure that all columns are sorted from top to bottom
			for (int r = 0; r < a[i].length-1; r++) {
				// Make sure each column is same size as number of rows
				if (a[r].length != a.length) {
					throw new IllegalArgumentException ("");
				}
				
				if (a[r][i] >= a[r+1][i]) {
					throw new IllegalArgumentException ("Array is not Twice Sorted (Column " + i + ").");
				}
			}
		}
	}
	
	/**
	 * This runs a trial looking for all integers from 0 up to and not including max.
	 * 
	 * It returns the total number of elements inspected.
	 * 
	 * @param max  The total number of integers to search (from 0 up to but not including max).
	 * @return The number of array inspections
	 * @throws IllegalStateException if the implementation is wrong.
	 */
	public final int trial(int max) {
		numChecked = 0;
		int numRight = 0;
		for (int i = 0; i < max; i++) {
			int[] spot = locate(i);
			if (spot != null) {
				if (arr[spot[0]][spot[1]] != i) {
					throw new IllegalStateException("Trial returned wrong location for:" + i);
				} else {
					numRight++;
				}
			}
		}
		
		if (numRight != length() * length()) {
			throw new IllegalStateException("Only found " + numRight + " values.");
		}
		return numChecked;
	}
	
}
