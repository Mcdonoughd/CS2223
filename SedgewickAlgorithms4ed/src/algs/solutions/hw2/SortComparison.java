package algs.solutions.hw2;

import edu.princeton.cs.algs4.*;

// Q1 on Homeework 2
public class SortComparison {

	static Double[] generateData(int n) {
		Double[] vals = new Double[n];
		for (int i = 0; i < n; i++) {
			vals[i] = StdRandom.uniform();
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
	static double[][][] results = new double[12][5][3];
	
	public static void generateReport() {
		StdOut.println("Time Trials");
		StdOut.println("N\tInsert\tSelect\tMerge\tQuick\tQuickA\n");
		for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
			StdOut.println(n + "\t" + results[idx][0][0] + 
					"\t" + results[idx][1][0] + 
					"\t" + results[idx][2][0] + 
					"\t" + results[idx][3][0] +
					"\t" + results[idx][4][0]);
		}
		
		StdOut.println();
		StdOut.println("Exch Results");
		StdOut.println("N\tInsert\tSelect\tMerge\tQuick\tQuickA\n");
		for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
			StdOut.println(n + "\t" + (int)results[idx][0][1] + 
					"\t" + (int)results[idx][1][1] + 
					"\t" + (int)results[idx][2][1] + 
					"\t" + (int)results[idx][3][1] +
					"\t" + (int)results[idx][4][1]);
		}
		
		StdOut.println();
		StdOut.println("Less Results");
		StdOut.println("N\tInsert\tSelect\tMerge\tQuick\tQuickA\n");
		for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
			StdOut.println(n + "\t" + (int)results[idx][0][2] + 
					"\t" + (int)results[idx][1][2] + 
					"\t" + (int)results[idx][2][2] + 
					"\t" + (int)results[idx][3][2] +
					"\t" + (int)results[idx][4][2]);
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
		
		int T = 10;
		StopwatchCPU watch;
		Double[] data;
		double time;
		
		for (int t = 0; t < T; t++) {
			for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
				
				data = generateData(n);
				watch = new StopwatchCPU();
				Insertion.sort(data);
				time = watch.elapsedTime();
				if (!isSorted(data)) {
					System.out.println("ERROR with Insertion sort");
				}
				updateEntry (t, idx, 0, Insertion.exchCount, Insertion.lessCount, time);
				
				
				data = generateData(n);
				watch = new StopwatchCPU();
				Selection.sort(data);
				time = watch.elapsedTime();
				if (!isSorted(data)) {
					System.out.println("ERROR with Selection sort");
				}
				updateEntry (t, idx, 1, Selection.exchCount, Selection.lessCount, time);
				
				data = generateData(n);
				watch = new StopwatchCPU();
				Merge.sort(data);
				time = watch.elapsedTime();
				if (!isSorted(data)) {
					System.out.println("ERROR with Merge sort");
				}
				updateEntry (t, idx, 2, Merge.exchCount, Merge.lessCount, time);
				
				data = generateData(n);
				watch = new StopwatchCPU();
				Quick.sort(data);
				time = watch.elapsedTime();
				if (!isSorted(data)) {
					System.out.println("ERROR with Quick sort");
				}
				updateEntry (t, idx, 3, Quick.exchCount, Quick.lessCount, time);
				
				data = generateData(n);
				watch = new StopwatchCPU();
				QuickAlternate.sort(data);
				time = watch.elapsedTime();
				if (!isSorted(data)) {
					System.out.println("ERROR with QuickAlternate sort");
				}
				updateEntry (t, idx, 4, QuickAlternate.exchCount, QuickAlternate.lessCount, time);
			}
		}
		
		generateReport();
	}

}
