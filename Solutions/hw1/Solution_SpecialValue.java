package algs.solutions.hw1;

import edu.princeton.cs.algs4.StdOut;

/**
 * Worked out best, worst and average cases for the HW1 example.
 */
public class Solution_SpecialValue {

	static int M=7;
	static long numChecked;

	static int[][] values;

	static int inspect(int r, int c) {
		numChecked++;
		return values[r][c];
	}

	// all values must be distinct. Do so by adjusting so no possibility of overlap
	static int min, max;
	static void fill(int row, int col) {
		for (int i = col-1; i >=0; i--) { values[row][i] = min--; }
		for (int i = col+1; i <values[row].length; i++) { values[row][i] = max++; }
	}

	// all values must be distinct. Do so by adjusting so no possibility of overlap
	// This was my first implementation, but it SKEWED the results. CAN YOU SEE WHY???
	//	static void fill(int row, int col) {
	//		for (int i = col-1; i >=0; i--) { values[row][i] = values[row][i+1] - (int)Math.pow(10, row); }
	//		for (int i = col+1; i <values[row].length; i++) { values[row][i] = values[row][i-1] + (int)Math.pow(10, row); }
	//	}

	/**
	 * In the worst case, the special value is found in the rightmost column. To find it, however, you need
	 * to inspect each element in top row (N) and compare against worst case search on 2nd row. Most number
	 * of searches is floor(log_2(N)) + 1. This gives us N+N*(floor(log_2(N) + 1)). Then you need to search
	 * through the M-2 remaining rows, which gives worst case (M-2)*(floor(log_2(N)+1)).
	 * In total, N+(M+N-2)*(floor(log_2(N)+1))
	 * 
	 * @param N
	 */
	static void worstCase(int N) {
		values = new int[M][N];
		int special = 12;
		for (int r = 0; r < M; r++) {
			values[r][N-1] = special; 
			fill(r,N-1);
		}

		min = special-1;
		max = special+1;
		numChecked = 0;
		special();

		/** Need to count one inspection for each element in row1, then max in row 2. */
		double estimate = N+(M+N-2)*(1+Math.floor(Math.log(N)/Math.log(2)));

		StdOut.println (N + "," + numChecked + "," + estimate);
	}

	static void bestCase(int N) {
		values = new int[M][N];
		int special = 12;
		min = special-1;
		max = special+1;

		int low = 0;
		int high = values[0].length-1;
		int mid = (low+high)/2;

		// first row, the special is first value
		values[0][0] = special;
		fill(0,0);

		// thereafter, the middle.
		for (int r = 1; r < M; r++) {
			values[r][mid] = special; 
			fill(r,mid);
		}

		numChecked = 0;
		special();
		double estimate = M;
		StdOut.println (N + "," + numChecked + "," + estimate);
	}

	/** Compute Average case. */
	static void averageCase(int N) {

		values = new int[M][N];
		int special = 12;
		long numTrials = 0;
		min = special-1;
		max = special+1;
		numChecked = 0;
		for (int c1 = 0; c1 < N; c1++) {
			values[0][c1] = special; 
			fill(0,c1);
			for (int c2 = 0; c2 < N; c2++) {
				values[1][c2] = special; 
				fill(1,c2);
				for (int c3 = 0; c3 < N; c3++) {
					values[2][c3] = special; 
					fill(2,c3);
					for (int c4 = 0; c4 < N; c4++) {
						values[3][c4] = special; 
						fill(3,c4);
						for (int c5 = 0; c5 < N; c5++) {
							values[4][c5] = special; 
							fill(4,c5);
							for (int c6 = 0; c6 < N; c6++) {
								values[5][c6] = special; 
								fill(5,c6);
								for (int c7 = 0; c7 < N; c7++) {
									values[6][c7] = special; 
									fill(6,c7);

									special();
									numTrials++;
								}
							}
						}
					}
				}
			}
		}

		// make sure that 'avg' is float, so we don't inadvertently truncate the actual value.
		float avg = numChecked;
		avg /= numTrials;

		StdOut.println (N + "," + numTrials + "," + numChecked + "," +  avg );
	}

	

	/** Storage for indices computed by special() method. */
	static int[] columns = new int[M];

	/**
	 * Compute the special value and returns the column indices for this value in each row. 
	 * 
	 * Note that all array access is done through {@link #inspect(int, int)} method.
	 * 
	 * @return integer array of column locations in each row where special value can be found.
	 */
	static int[] special () {
		int special = -1;

		// find special by going through each column in the first row and looking for that value in second row.
		for (int c = 0; c < values[0].length; c++) {
			special = inspect(0,c);
			columns[0] = c;
			int rank = rank(1, special);
			if (rank != -1) {
				columns[1] = rank;
				break;
			}
		}

		// so we have the special. Now find on each level
		for (int row = 2; row < values.length; row++) {
			int rank = rank(row, special);
			columns[row] = rank;
		}
		return columns;
	}

	/** 
	 * Look for target in the given values[row] row.
	 * 
	 * Returns the location where object is stored, or -1 if not exists.
	 * Uses the {@link #inspect(int, int)} method to properly count array accesses.
	 */
	static int rank (int row, int target) {
		int low = 0;
		int high = values[row].length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = inspect(row,mid) - target;
			if (rc < 0) {
				low = mid+1;
			} else if (rc > 0) {
				high = mid-1;
			} else {
				return mid;
			}
		}
		return -1;
	}


	/** Evaluate a simple case, and then check best, worst, and average cases. */
	public static void main (String args[]) {

		values =  new int[][] {
				{5, 12, 18, 22}, 
				{2, 10, 12, 70},
				{1, 3, 9, 12},
				{12,17, 24, 76},
				{8, 11 ,12, 19},
				{7, 12, 49, 51}
		};
		special();
		StdOut.println ("num Checked:" + numChecked);

		// Run Worst Case Analysis
		StdOut.println("Worst Case (M=7)");
		StdOut.println ("N,#inspections,estimate");
		for (int N = 2; N < 32; N++) {
			worstCase(N);
		}

		// Run Best Case Analysis
		StdOut.println("Best Case (M=7)");
		for (int N = 2; N < 32; N++) {
			bestCase(N);
		}

		StdOut.println("Average Case (M=7)");
		StdOut.println ("N,#Trials,#inspections,average");
		for (int N = 2; N < 32; N++) {
			averageCase(N);
		}
	}

}
