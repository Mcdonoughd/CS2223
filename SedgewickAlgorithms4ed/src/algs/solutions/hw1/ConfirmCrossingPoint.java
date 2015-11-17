package algs.solutions.hw1;

public class ConfirmCrossingPoint {

	public static void main(String[] args) {
		
		// create each one 
		for (int r = 0; r < 5; r++) {
			for (int c = 0; c < 7; c++) {
				boolean [][] ar = new boolean[][] {
						{ false, false, false, false, false, false, false },
						{ false, false, false, false, false, false, false },
						{ false, false, false, false, false, false, false },
						{ false, false, false, false, false, false, false },
						{ false, false, false, false, false, false, false },
				};
				
				// insert crossing point
				for (int i = 0; i < 7; i++) { ar[r][i] = true; }
				for (int i = 0; i < 5; i++) { ar[i][c] = true; }
				
				System.out.print(" should be " + r + "," + c + ": ");
				new CrossingPoint().locate(ar);
			}
		}
	}
}
