package algs.solutions.hw2;

import edu.princeton.cs.algs4.*;

// Q3 on Homeework 2
public class Solution_HeapExercise {

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

	static int[][] results = new int[12][4];

	public static void generateReport() {
		StdOut.println("Heap Trials");
		StdOut.println("N\tDelMin");
		for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
			double estimate = (n/2.0) + Math.log(n)/Math.log(2) - 2;
			StdOut.println(n + "\t" + results[idx][0] + "-" + results[idx][1] + "\t" + estimate);
		}
	}

	// you complete this function.
	private static void updateEntry(int trial, int n, int delComparisons) {
		if (trial == 0) {
			results[n][0] = delComparisons;
			results[n][1] = delComparisons;
			return;
		}
		
		if (delComparisons < results[n][0]) {
			results[n][0] = delComparisons;
		}
		if (delComparisons > results[n][1]) {
			results[n][1] = delComparisons;
		}

	}

	public static void main(String[] args) {

		int T = 10;
		Double[] data;

		for (int t = 0; t < T; t++) {
			for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
				MaxPQ<Double> mpq = new MaxPQ<Double>(1);
				data = generateData(n);
				for (double d : data) {
					mpq.insert(d);
				}

				// ready for the trial to start:
				data = generateData(1000);
				int ctr = 0;
				for (double d : data) {
					int preCount = mpq.lessCount;
					mpq.delMin();  // remove the minimum value (note: not the norm!)
					int postCount = mpq.lessCount;
					mpq.insert(d);

					updateEntry (ctr++, idx, postCount-preCount);  
				}
				
				// check del min
				double min = -1;   // lower than [0,1)
				while (!mpq.isEmpty()) {
					double m = mpq.delMin();
					if (m < min) {
						StdOut.println ("ERROR!");
					} else {
						min = m;
					}
				}
			}
		}

		generateReport();
	}

}
