package algs.solutions.hw2;

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

	// you complete this function.
	private static void updateEntry(int trial, int n, int delComparisons, int insertComparisons) {
		if (trial == 0) {
			results[n][0] = delComparisons;
			results[n][1] = delComparisons;
			results[n][2] = insertComparisons;
			results[n][3] = insertComparisons;
			return;
		}
		
		if (delComparisons < results[n][0]) {
			results[n][0] = delComparisons;
		}
		if (delComparisons > results[n][1]) {
			results[n][1] = delComparisons;
		}

		if (insertComparisons < results[n][2]) {
			results[n][2] = insertComparisons;
		}
		if (insertComparisons > results[n][3]) {
			results[n][3] = insertComparisons;
		}
	}

	public static void main(String[] args) {

		int T = 10;
		Stopwatch watch;
		Double[] data;
		double time;

		for (int t = 0; t < T; t++) {
			for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
				MaxPQ<Double> mpq = new MaxPQ<Double>(n);
				data = generateData(n);
				for (double d : data) {
					mpq.insert(d);
				}

				// ready for the trial to start:
				data = generateData(1000);
				int ctr = 0;
				for (double d : data) {
					int preCount = mpq.lessCount;
					mpq.delMax();  // remove the maximum
					int postCount = mpq.lessCount;
					mpq.insert(d);
					int secondCount = mpq.lessCount;

					updateEntry (ctr++, idx, postCount-preCount, secondCount-postCount);
				}
			}
		}

		generateReport();
	}

}
