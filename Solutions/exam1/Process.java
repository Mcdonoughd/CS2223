package algs.solutions.exam1;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Process {
	static int sq;

	/** Assume length of array is a power of 2. */
	static int process(int[] a, int lo, int hi) {
		if (lo == hi) { sq++; return (int) Math.sqrt(a[lo]); }

		int mid = lo + (hi-lo)/2;
		int x = process(a, lo, mid) + process(a, mid+1, hi);

		for (int i = lo; i <= hi; i += 2) { sq++;
			if (Math.sqrt(a[i]) == x) { x++; }
		}
		return x;
	}

	public static void main(String[] args) {
		// Run and see how the ratio converges to 1....
		StdOut.println("N\tMaxCmp\test");
		for (int n = 4, idx =2 ; n <= 1048576; n*= 2, idx++) {
			int est = n*idx;
			int[] set = new int[n];

			// since a new integer array, all values are ZERO. Make ZERO the dominant value, and 
			// put random values into first n - (n/2 + 1), or n/2-1 spots
			for (int k = 0; k < n; k++) {
				set[k] = StdRandom.uniform(1, n);  // none of these can be dominant.
			}

			int maxCompares = 0;
			for (int t = 0; t < 200; t++) {
				// move things around and try random permutations in 100 trials
				StdRandom.shuffle(set);
				sq = 0;
				process(set, 0, n-1);
				
				if (sq > maxCompares) {
					maxCompares = sq;
				}

			}

			est = est/2 + n;
			StdOut.print(n + "\t" + sq + "\t" + est + "\n");

		}
	}
}
