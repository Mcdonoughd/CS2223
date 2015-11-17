package algs.solutions.hw1;

public class CrossingPoint {
	
	/**
	 * This requires 
	 * @param ar
	 */
	public void locate (boolean ar[][]) {
		int M = ar.length;
		int N = ar[0].length;
		
		if (M <= 1) { return; }
		if (N <= 1) { return; }
		
		int check = 0;            // records number of checks
		int crossCol, crossRow;   // will be the solution
		
		// find first pair of neighboring vertical cells in the top two
		// rows that are true. Takes no worse than 2(N-1) or 2N-2. Assume
		// it is the rightmost column, until proven otherwise.
		crossCol = N-1;
		int numTrueSeen = 0;
		for (int c = 0; c < N-1; c++) {
			check++;
			if (ar[0][c]) {
				numTrueSeen++;
				check++;
				if (ar[1][c]) {
					crossCol = c;
					break;
				}
			}
		}
		
		// if we get here and numTrueSeen > 1, then we have found crossing point in 
		// this special case where first row is the one with all TRUE values
		if (numTrueSeen > 1) {
			crossRow = 0;
		} else if (crossCol == 0) {
			// given 0th column has two TRUE values, we search through the 1st column for the 
			// first TRUE value. Assume it is the last row, unless proven otherwise
			crossRow = M-1;
			for (int r = 0; r < M-1; r++) {
				check++;
				if (ar[r][1]) {
					crossRow = r;
					break;
				}
			}
		} else {
			
			// Now, repeat the same for the remaining M-2 rows, starting at row 1, 
			// checking the first two entries. Assume is in the bottom row, unless
			// found earlier.
			crossRow = M-1;
			for (int r = 1; r < M-1; r++) {
				check++;
				if (ar[r][0]) {
					check++;
					if (ar[r][1]) {
						crossRow = r;
						break;
					}
				}
			}
		}
		
		// we have found the crossing point.
		System.out.println("Crossing Point: " + crossRow + "," + crossCol);
		System.out.println("Required " + check + " array lookups.");
	}
	
	public static void main(String[] args) {
		boolean [][] ar =  {
				{ false, false, false, false, true, false, false, false },
				{ false, false, false, false, true, false, false, false },
				{ true,  true,  true,  true,  true, true,  true,  true },
				{ false, false, false, false, true, false, false, false },
		};
		
		new CrossingPoint().locate(ar);
	}
}
