package algs.solutions.hw3;

import edu.princeton.cs.algs4.*;

// Q1 on Homeework 2
public class SortComparison {

	/**
	 * Generate data with given duplication frequency (between 0 and 1).
	 * 
	 * @param n  must be > 0.
	 * 
	 * @param duplicationFrequency
	 * @return
	 */
	static Double[] generateData(int n, double duplicationFrequency) {
		Double[] vals = new Double[n];
		vals[0] = StdRandom.uniform();
		for (int i = 1; i < n; i++) {
			if (StdRandom.uniform() <= duplicationFrequency) {
				vals[i] = vals[StdRandom.uniform(0,i)];
			} else {
				vals[i] = StdRandom.uniform();
			}
		}
		return vals;
	}

	static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}
	static boolean isSorted(Comparable[] a) {
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}

	// You are responsible for updating these values.
	//
	// [n][s][0] = best time for algorithm #s on a dataset of size n
	// [n][s][1] = fewest number of exch operations for algorithm #s on a dataset of size n
	// [n][s][2] = fewest number of less operations for algorithm #s on a dataset of size n
	static double[][][] results = new double[8][4][3];

	public static void generateReport() {
		StdOut.println("Time Trials");
		StdOut.println("N\tMerge\tMerge3-way\tQuick\tQuick3-way\n");
		for (int n = 8192, idx = 0; n <= 1048576; n*= 2, idx++) {
			StdOut.println(n + "\t" + results[idx][0][0] + 
					"\t" + results[idx][1][0] + 
					"\t" + results[idx][2][0] + 
					"\t" + results[idx][3][0]);
		}

		StdOut.println();
		StdOut.println("Exch Results");
		StdOut.println("N\tMerge\tMerge3-way\tQuick\tQuick3-way\n");
		for (int n = 8192, idx = 0; n <= 1048576; n*= 2, idx++) {
			StdOut.println(n + "\t" + (int)results[idx][0][1] + 
					"\t" + (int)results[idx][1][1] + 
					"\t" + (int)results[idx][2][1] + 
					"\t" + (int)results[idx][3][1]);
		}

		StdOut.println();
		StdOut.println("Less Results");
		StdOut.println("N\tMerge\tMerge3-way\tQuick\tQuick3-way\n");
		for (int n = 8192, idx = 0; n <= 1048576; n*= 2, idx++) {
			StdOut.println(n + "\t" + (int)results[idx][0][2] + 
					"\t" + (int)results[idx][1][2] + 
					"\t" + (int)results[idx][2][2] + 
					"\t" + (int)results[idx][3][2]);
		}
	}

	private static void updateEntry(int trial, int n, int entry, int exch, int less, double time) {
		if (trial == 0) {
			results[n][entry][0] = time;
			results[n][entry][1] = exch;
			results[n][entry][2] = less;
			return;
		}

		if (time < results[n][entry][0]) {
			results[n][entry][0] = time;
		}

		if (exch < results[n][entry][1]) {
			results[n][entry][1] = exch;
		}

		if (less < results[n][entry][2]) {
			results[n][entry][2] = less;
		}
	}

	public static void main(String[] args) {

		int T = 5;
		StopwatchCPU watch;
		Double[] data;
		double time;

		for (double dupFrequency = 0.0; dupFrequency <= 1.0; dupFrequency += 0.20) {
			StdOut.println ("Duplicate Frequency:" + dupFrequency);
			for (int t = 0; t < T; t++) {
				for (int n = 8192, idx = 0; n <= 1048576; n*= 2, idx++) {

					data = generateData(n, dupFrequency);
					watch = new StopwatchCPU();
					Merge.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Merge sort");
					}
					updateEntry (t, idx, 0, Merge.exchCount, Merge.lessCount, time);

					data = generateData(n, dupFrequency);
					watch = new StopwatchCPU();
					MergeThreeWay.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Merge-3-way sort");
					}
					updateEntry (t, idx, 1, MergeThreeWay.exchCount, MergeThreeWay.lessCount, time);

					data = generateData(n, dupFrequency);
					watch = new StopwatchCPU();
					Quick.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Quick sort");
					}
					updateEntry (t, idx, 2, Quick.exchCount, Quick.lessCount, time);

					data = generateData(n, dupFrequency);
					watch = new StopwatchCPU();
					Quick3way.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Quick3way sort");
					}
					updateEntry (t, idx, 3, Quick3way.exchCount, Quick3way.lessCount, time);
				}
			}
		
		generateReport();
		}
	}

}
