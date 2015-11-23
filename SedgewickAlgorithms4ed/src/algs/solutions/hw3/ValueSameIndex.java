package algs.solutions.hw3;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class ValueSameIndex {
	static int numArrayInspections;

	static int[] generateAscendingWithCandidate(int size) {
		int[] a = new int[size];
		int k = StdRandom.uniform(1, size-1);

		// put in place.
		a[k] = k;

		// smaller on left, always going to be < index.
		int small = k-2;
		for (int i = k-1; i >= 0; i--) {
			a[i] = small;
			small--;
		}

		// larger numbers on right, always going to be > index
		small = k + 2;
		for (int i = k+1; i <size; i++) {
			a[i] = small;
			small++;
		}

		return a;
	}

	static int[] generateAscendingWithNoSuchCandidate(int size) {
		int[] a = new int[size];
		int k = StdRandom.uniform(1, size-1);

		// NO CANDIDATE!
		a[k] = k+1;

		// smaller on left, always going to be < index.
		int small = k-2;
		for (int i = k-1; i >= 0; i--) {
			a[i] = small;
			small--;
		}

		// larger numbers on right, always going to be > index
		small = k + 2;
		for (int i = k+1; i <size; i++) {
			a[i] = small;
			small++;
		}

		return a;
	}
	

	/** 
	 * Given index values, lo and hi, which are inclusive to the array, a,
	 * return index such that a[idx] = idx.
	 * 
	 * If no such index exists, then return -1.
	 */
	public static int index (int[] a, int lo, int hi) {
		while (lo <= hi) {
			int mid = (lo+hi)/2;

			numArrayInspections++;
			int rc = a[mid] - mid;
			if (rc < 0) {
				lo = mid+1;
			} else if (rc > 0) {
				hi = mid-1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int [] hasOne = new int[] { -3, -2, -1, 2, 4, 9, 22};

		StdOut.println("found at 4 = " + index(hasOne, 0, hasOne.length-1));

		// none exist
		int []nope = new int[] { -5, -3, 11, 40, 60 };
		StdOut.println("nope:" + index(nope, 0, hasOne.length-1));

		StdOut.println("N\t#A(+)\t#A(-)\tPrediction");
		for (int n = 4; n <= 1000000; n*=2) {
			int maxComparisonsWithCandidate = 0, maxComparisonsWithNoCandidate = 0;
			for (int T = 0; T < 5000; T++) {
				int[] a = generateAscendingWithCandidate(n);

				numArrayInspections = 0;

				int k = index(a, 0, a.length);
				if (k != a[k]) {
					StdOut.println ("NOT CORRECT!");
				}
				if (numArrayInspections > maxComparisonsWithCandidate) {
					maxComparisonsWithCandidate = numArrayInspections;
				}
				
				a = generateAscendingWithNoSuchCandidate(n);

				numArrayInspections = 0;

				k = index(a, 0, a.length);
				if (k != -1) {
					StdOut.println ("NOT CORRECT!");
				}
				if (numArrayInspections > maxComparisonsWithNoCandidate) {
					maxComparisonsWithNoCandidate = numArrayInspections;
				}
			}

			double log2 = Math.log(n) / Math.log(2);
			int prediction = 1 + (int)(log2);
			StdOut.println(n + "\t" + maxComparisonsWithCandidate + "\t" + maxComparisonsWithNoCandidate + "\t" + prediction);
		}


	}
}
