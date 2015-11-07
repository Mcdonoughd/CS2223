package algs.hw2;

import edu.princeton.cs.algs4.*;

// Q3 on Homeework 2
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
	// [n][0] = fewest number of comparisons for delMax on heap of size n
	// [n][1] = most number of comparisons for delMax on heap of size n
	// [n][2] = fewest number of comparisons for insert on heap of size n
	// [n][3] = most number of comparisons for insert on heap of size n
		
	static int[][] results = new int[12][4];

	public static void generateReport() {
		StdOut.println("Heap Trials");
		StdOut.println("N\tDelMax\tInsert");
		for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
			StdOut.println(n + "\t" + results[idx][0] + "-" + results[idx][1] + "\t" +
					 results[idx][2] + "-" + results[idx][3]);
		}
	}

	// Update results, given information for the given trial, data size N, number of comparisons
	// during the delete operation and number of comparisons during the insert operation.
	private static void updateEntry(int trial, int n, int delComparisons, int insertComparisons) {

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
