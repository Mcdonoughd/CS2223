package algs.solutions.hw1;

public class NPlusOneCrossingPoint {

	/**
	 * This requires only N+1 to locate the crossing point.
	 * @param ar
	 */
	public void locate (boolean ar[][]) {
		int M = ar.length;
		int N = ar[0].length;

		if (M <= 1) { return; }
		if (N <= 1) { return; }
		int check = 0;

		int crossRow = -1, crossCol = -1;
		
		int r = 0, c = 0;
		while ((r < M) && (c < N)) {
			// if reached LAST ROW without being found, it MUST be crossRow
			// if reached LAST COL without being found, it MUST be crossCol
			// because these could both be at the SAME TIME for square matrix, allow both to be set.
			boolean done = false;
			if (r == M-1) { crossRow = r; done = true; }
			if (c == N-1) { crossCol = c; done = true; }
			if (done) { break; }
			
			check++;
			if (ar[r][c]) {
				// check other column value to see if this is the crossCol
				check++;
				if (ar[(r+1)%M][c]) {
					crossCol = c;
					break;
				} else {
					crossRow = r;
					break;
				}
			}
			
			r++;
			c++;
		}

		if (crossRow == -1) {
			// search for row
			// NOTE: Can't advance row since it has not yet been checked. Asymmetry in solution.
			c = (c+1)%N;
			while (r < M) {
				// can stop prematurely since you KNOW last row is Cross row if not yet discovered
				if (r == M-1) { crossRow = M-1; break; }
				check++;
				if (ar[r][c]) {
					crossRow = r;
					break;
				}
				r++;
			}
		} else {
			// search for column
			c++; // can advance since we have already checked this column
			r = (r+1)%M;
			while (c < N) {
				// can stop prematurely since you KNOW last columns is Cross Col if not yet discovered
				if (c == N-1) { crossCol = N-1; break; }
				
				check++;
				if (ar[r][c]) {
					crossCol = c;
					break;
				}
				c++;
			}
		}
		
		// we have found the crossing point.
		System.out.println("Crossing Point: " + crossRow + "," + crossCol);
		System.out.println("Required " + check + " array lookups.");
	}

	public static void main(String[] args) {
		// create each one 
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				boolean [][] ar = new boolean[][] {
						{ false, false, false, },
						{ false, false, false, },
						{ false, false, false, },
				};

				// insert crossing point
				for (int i = 0; i < ar[0].length; i++) { ar[r][i] = true; }
				for (int i = 0; i < ar.length; i++) { ar[i][c] = true; }

				System.out.print(" should be " + r + "," + c + ": ");
				new NPlusOneCrossingPoint().locate(ar);
			}
		}
	}
}
