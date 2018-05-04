package algs.hw3;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Question3 {
	
	static Double[] generateData(int n) {
		Double[] vals = new Double[n];
		for (int i = 0; i < n; i++) {
			vals[i] = StdRandom.uniform();
		}
		return vals;
	}
	
	static Double[] sorted(int n) {
		Double[] vals = new Double[n];
		double fract = 1.0 / n;
		for (int i = 0; i < n; i++) {
			vals[i] = i*fract;
		}
		return vals;
	}
	
	// It turns out reverse is not the worst case for merge sort. Can you see why?
	// Hint: In each sub-problem, one side will always contain values that are
	// all greater than the other half, which means you will drain one side before
	// another one advances; this is exactly the N/2 scenario for best case.
	// Instead, need something with greater structure. They aren't hard to find.
	static Integer[] duplicate(Integer[] a) {
		int n = a.length;
		Integer[] dbl = new Integer[n*2];
		for (int i = 0; i < n; i++) {
			dbl[i] = dbl[i+n] = a[i];
		}
		return dbl;
	}
	
	/** Works for powers of 2. */
	static Integer[] worstCase(int n) {
		if (n == 2) { return new Integer[]{0,1}; }        // only here to provide base case for small arrays
		if (n == 4) { return new Integer[]{1,3,0,2}; }    // only here to provide base case for small arrays
		if (n == 8) { return new Integer[]{5,0,3,6,4,1,7,2}; }  // expected structure.
		
		Integer[] sub = worstCase(n/2);
		return duplicate(sub);
	}
	
	static Double[] random(int n) {
		Double[] vals = new Double[n];
		for (int i = 0; i < n; i++) {
			vals[i] = StdRandom.uniform();
		}
		return vals;
	}
	
	/** Quick and dirty indexOf checker. */
	static int indexOf(Double[] sorted, double val) {
		for (int i = 0; i < sorted.length; i++) {
			if (sorted[i] == val) { return i; }
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		

		// 16, 32, 64, … 512. For each size N, run T=10 trials, 
		int T = 100;
		StdOut.println("N\t#Comp\tEstimate");
		for (int n= 16; n <= 512; n*= 2) {
			Selection.sort(generateData(n));
			double cn = (n*n - n)/2.0;
			StdOut.printf("%d\t%d\t%.2f%n", n, Selection.numCompares, cn );
		}
		
		StdOut.println("\nConfirm Selection Sort C(n).\n");
		
		// best case and worst case for MergeSort occur when values are in sorted order, and 
		// then in reverse sorted order.
		StdOut.println("N\tBestC\tWorstC\t#B\t#W");
		for (int n= 2; n <= 512; n*= 2) {
			Merge.sort(sorted(n));
			long bestCase = Merge.lessCount;
			
			long worstCase = 0;  // 10,000 case up to 32.
			for (int t = 0; t < 10000; t++) {
				Double[] vals = random(n);
				Merge.sort(vals);
				if (Merge.lessCount > worstCase) {
					worstCase = Merge.lessCount;
				}
			}
			
			// note to compute Log2(N) we do Log(N)/Log(2)
			double bestEst  = n * Math.log(n) / (Math.log(2) * 2);
			double worstEst = (n * Math.log(n) / Math.log(2)) - (n-1);
			StdOut.printf("%d\t%d\t%d\t%.2f\t%.2f%n", n, bestCase, worstCase, bestEst, worstEst);
		}
		
		StdOut.println("\nConfirm Merge Sort C(n) has best case (n/2)*log N, with worst N*log N\n");
		
		
		StdOut.println("What do these worst case arrays look like? Here are some sample values up to 64.");
		StdOut.println("The number in parentheses tells how many random trials it took to find the array."); 
		StdOut.println("Turn these doubles into integers to see if there is a pattern.\n"); 
		
		
		// Find worst-case arrays for merge sort
		for (int n= 2; n <= 64; n*= 2) {
			Merge.sort(sorted(n));
			long bestCase = Merge.lessCount;
			
			long worstCase = 0;  // 10,000 case up to 32.
			double worstEst = (n * Math.log(n) / Math.log(2)) - (n-1);

			double[] copy = new double[n]; 
			Double[] sorted = null;
			int numTrials = 0;
			while (worstCase < worstEst) {
				numTrials++;
				Double[] vals = random(n);
				for (int i=0; i < n; i++) { copy[i] = vals[i]; }
				Merge.sort(vals);
				if (Merge.lessCount > worstCase) {
					worstCase = Merge.lessCount;
					sorted = vals;
				}
			}
			StdOut.printf("(%d) ", numTrials);
			for (double d : copy) {
				StdOut.printf("%d ", indexOf(sorted, d));
			}
			StdOut.println();

			// note to compute Log2(N) we do Log(N)/Log(2)
			double bestEst  = n * Math.log(n) / (Math.log(2) * 2);
			StdOut.printf("%d\t%d\t%d\t%.2f\t%.2f%n", n, bestCase, worstCase, bestEst, worstEst);
		}
		
		StdOut.println("\nUsing [5, 0, 3, 6, 4, 1, 7, 2] as a 'seed', you can *almost* construct worst case arrays of any size.");
		
		for (int n= 2; n <= 512; n*= 2) {
			Integer[] worst = worstCase(n);
			Merge.sort(worst);
			double worstEst = (n * Math.log(n) / Math.log(2)) - (n-1);
			StdOut.printf("%d\t%d\t%.1f%n ", n, Merge.lessCount, worstEst);
		}
		
		
	}
}
