package algs.hw2;

import edu.princeton.cs.algs4.*;

// Useful template to construct a heap from a collection of random values.
public class HeapExercise {

	static Double[] generateData(int n) {
		Double[] vals = new Double[n];
		for (int i = 0; i < n; i++) {
			vals[i] = StdRandom.uniform();
		}
		return vals;
	}

	// You are responsible for updating these values.
	//
	// [n][0] = fewest number of comparisons for delMin on heap of size n
	// [n][1] = most number of comparisons for delMin on heap of size n
		
	static int[][] results = new int[12][4];

	public static void generateReport() {
		StdOut.println("Heap Trials");
		StdOut.println("N\tDelMin");
		for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
			StdOut.println(n + "\t" + results[idx][0] + "-" + results[idx][1]);
		}
	}

	// Update results, given information for the given trial, data size N, number of comparisons
	// during the delMin operation.
	// NOTE: (3-26-2018) -- IN initial version, there had been a parameter: int insertComparisons
	// but this is no longer needed.
	private static void updateEntry(int trial, int n, int delComparisons) {

	}

	public static void main(String[] args) {

		int T = 10;
		Double[] data;
		
		for (int t = 0; t < T; t++) {
			for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {

				// Fill in here...

			}
		}

		generateReport();
	}

}
