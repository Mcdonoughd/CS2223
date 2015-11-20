package algs.solutions.exam1;

// This file contains a number of solutions that work and some attempted solutions that fail
// 
// My solution is maxUpDownArrayInByOne

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class UpDown {
	static int numComparisons = 0;

	static int[] generateUpDown(int size) {
		int[] a = new int[size];
		int k = StdRandom.uniform(1, size-1);

		// even numbers smaller on left
		int small = 2*k - 2;
		for (int i = k; i >= 0; i--) {
			a[i] = small;
			small -= 2;
		}

		// odd numbers on right
		small = 2*k - 3;
		for (int i = k+1; i <size; i++) {
			a[i] = small;
			small -= 2;
		}

		return a;
	}

	// this won't work because it returns wrong index. Note that arrays are getting 
	// smaller and smaller and thus original position is lost. This will
	// return a maximum value but not position
	public static int maxUpDownArrayRecursiveNewArrays (int[] a) {
		int k = a.length/2;
		numComparisons += 2;
		if (a[k] > a[k-1] && a[k] > a[k+1]) {
			return a[k];
		}

		numComparisons++;
		if (a[k] < a[k+1]) {  // still ascending
			int[] newar = new int[a.length-k];
			System.arraycopy(a, k, newar, 0, a.length-k);
			return maxUpDownArrayRecursiveNewArrays (newar);
		} else { // descending
			int[] newar = new int[k+1];
			System.arraycopy(a, 0, newar, 0, k+1);
			return maxUpDownArrayRecursiveNewArrays (newar);
		}
	}

	// this won't work because it returns wrong index. Note that arrays are getting 
	// smaller and smaller and thus original position is lost. This will
	// return a maximum value but not position
	public static int maxUpDownArrayRecursiveAlternate (int[] a) {
		if (a.length == 1) { return 0; }
		int k = a.length/2;
		int[] left = new int[k];
		int[] right = new int[a.length-k];
		System.arraycopy(a, k, right, 0, a.length-k);
		System.arraycopy(a, 0, left, 0, k);
		
		int i = maxUpDownArrayRecursiveAlternate (left);
		int ii = maxUpDownArrayRecursiveAlternate (right);
		numComparisons++;
		if (left[i] > right[ii]) { return i; } else { return left.length + ii; }
	}

	/** Return index k of maximum elements a[k] in UpDown array. */
	public static int maxUpDownArrayRecursive (int[] a) {
		return maxUpDownArrayRecursive(a, 0, a.length-1);
	}

	public static int maxUpDownArrayRecursive(int[] a, int lo, int hi) {
		if (hi < lo) { return lo; }

		int mid = lo + (hi-lo)/2;
		numComparisons++;
		if (a[mid] < a[mid+1]) {
			return maxUpDownArrayRecursive(a, mid+1, hi);
		} else {
			return maxUpDownArrayRecursive(a, lo, mid-1);
		}
	}
	
	public static int maxUpDownArrayThirdRecursive (int[] a) {
		return maxUpDownArrayThirdRecursive(a, 1, a.length-2);
	}
	public static int maxUpDownArrayThirdRecursive(int[] a, int lo, int hi) {
		if (lo == hi) { return lo; }

		int mid = (lo+hi)/2;
		numComparisons++;
		if (a[lo] > a[mid]) { 
			return maxUpDownArrayRecursive(a, lo, mid-1);
		} else if (a[hi] > a[mid]) {
			return maxUpDownArrayRecursive(a, mid+1, hi);
		}
		
		return maxUpDownArrayThirdRecursive(a, lo+1, hi-1);
	}
	

	/** Return index k of maximum elements a[k] in UpDown array. */
	public static int maxUpDownArrayJustN (int[] a) {
		int N = a.length/2;
		while (true) {
			numComparisons++;
			if (a[N] > a[N+1]) {
				if (a[N] > a[N-1]) {
					return N;
				} else {
					N = N/2;
				}
			} else {
				N = N + (N/2);
			}
		}
	}

	/** Return index k of maximum elements a[k] in UpDown array. */
	// FAILS
	public static int maxUpDownCheckMax (int[] a) {
		int lo = 0;
		int hi = a.length-1;
		int max = 0;
		while (lo <= hi) {
			numComparisons++;
			int mid = lo + (hi-lo)/2;
			if (a[max] < a[mid]) {
				hi = mid-1;
			} else if (a[max] > a[mid]) {
				lo = mid+1;
			} else {
				max = mid;
				return max;
			}
		}

		return max;
	}
	
	public static int maxUpDownArrayFullLengthCheckMax (int[] a) {
		int lo = 0;
		int hi = a.length-1;
		int max = 0;
		while (lo <= hi) {
			int mid = lo + (hi-lo)/2;
			numComparisons++;
			if (a[mid] < a[max]) {
				if (a[mid] < a[mid+1]) {
					lo = mid+1;
				} else {
					hi = mid-1;
				}
			} else {
				if (a[mid] > a[mid+1]) {
					hi = mid-1;
				} else {
					lo = mid+1;
				}
				max = mid;
			}
		}

		return max;
	}
	

	/** Return index k of maximum elements a[k] in UpDown array. */
	public static int maxUpDownArrayFullLength (int[] a) {
		int lo = 0;
		int hi = a.length-1;
		while (lo <= hi) {
			int mid = lo + (hi-lo)/2;
			numComparisons++;
			if (a[mid] < a[mid+1]) {
				lo = mid+1;
			} else {
				hi = mid-1;
			}
		}

		return lo;
	}

	/** Return index k of maximum elements a[k] in UpDown array. */
	public static int maxUpDownArrayInByOne (int[] a) {
		int lo = 1;
		int hi = a.length-2;
		while (lo <= hi) {    
			int mid = lo + (hi-lo)/2;
			numComparisons++;

			if (a[mid] < a[mid+1]) {
				lo = mid+1;
			} else {
				hi = mid-1;
			}
		}

		return lo;
	}

	public static int maxUpDownArrayFaultySolution (int[] a) {
		int lo = 0;
		int hi = a.length;
		int mid = a.length/2 - 1;
		while (true) {

			numComparisons++;
			if (a[mid] > a[lo] && (++numComparisons > numComparisons) && a[mid] > a[hi]) {
				lo = (mid+lo)/2; hi = (mid+hi)/2;
			} else if (++numComparisons > numComparisons && a[mid] > a[lo] && (++numComparisons > numComparisons) && a[mid] < a[hi]) {
				lo = mid; mid = (mid+hi)/2;
			} else if (++numComparisons > numComparisons && a[mid] < a[lo] && (++numComparisons > numComparisons) && a[mid] > a[hi]) {
				hi=mid; mid = (mid+lo)/2;
			} else {
				return mid;
			}
		}
	}

	public static boolean confirmValue (int[] a, int max) {
		boolean found = false;
		for (int i = 0; i < a.length; i++) {
			if (a[i] > max) {
				return false;
			} else if (a[i] == max) { 
				found = true;
			}
		}

		return found;
	}

	public static boolean confirm (int[] a, int k) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] >= a[k] && i != k) {
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		StdOut.println("N\t#comp\tPrediction");
		for (int n = 3; n <= 1000000; n = 2*n + 1) {
			int maxComparisons = 0;
			for (int T = 0; T < 5000; T++) {
				int[] a = generateUpDown(n);

				numComparisons = 0;

				// Some code can return maximum value (although index was asked for). In that
				// case, use this structure.
				//int max = maxUpDownArrayRecursiveNewArrays(a);
				//if (!confirmValue(a,max)) {
				//	StdOut.println ("NOT CORRECT!");
				//}

				//int k = maxUpDownArrayInByOne(a);
				int k = maxUpDownArrayInByOne(a);
				if (!confirm(a, k)) {
					StdOut.println ("NOT CORRECT!");
				}
				if (numComparisons > maxComparisons) {
					maxComparisons = numComparisons;
				}
			}

			double log2 = Math.log(n) / Math.log(2);
			int prediction = 1 + (int)(log2);
			StdOut.println(n + "\t" + numComparisons + "\t" + prediction);
		}

	}
}
