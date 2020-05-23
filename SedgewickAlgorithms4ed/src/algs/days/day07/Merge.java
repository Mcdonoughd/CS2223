package algs.days.day07;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Proper Merge Sort from Sedgewick, 4ed
public class Merge {

	static Comparable aux[];
	static long numCopies;

	public static void sort(Comparable[] a) {
		aux = new Comparable[a.length];
		sort (a, 0, a.length-1);
	}

	// recursive helper function
	static void sort (Comparable[] a, int lo, int hi) {
		if (hi <= lo) return;

		int mid = lo + (hi - lo)/2;

		sort(a, lo, mid);
		sort(a, mid+1, hi);
		merge(a, lo, mid, hi);
	}

	// merge sorted results a[lo..mid] with a[mid+1..hi] back into a
	static void merge (Comparable[] a, int lo, int mid, int hi) {
		int i = lo;     // starting index into left sorted sub-array
		int j = mid+1;  // starting index into right sorted sub-array

		// copy a[lo..hi] into aux[lo..hi]
		for (int k = lo; k <= hi; k++) {
			aux[k] = a[k];
			numCopies++;
		}

		// now comes the merge. Something you might simulate with flashcards
		// drawn from two stack piles. This is the heart of mergesort. 
		for (int k = lo; k <= hi; k++) {
			if       (i > mid)               { a[k] = aux[j++]; }
			else if  (j > hi)                { a[k] = aux[i++]; }
			else if  (less(aux[j], aux[i]))  { a[k] = aux[j++]; }
			else                             { a[k] = aux[i++]; }
		}
	}


	/***************************************************************************
	 *  Helper sorting functions.
	 ***************************************************************************/

	// is v < w ?
	private static boolean less(Comparable v, Comparable w) {
		return v.compareTo(w) < 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	// print array to standard output a[lo..hi]
	private static void show(Comparable[] a, int lo, int hi) {
		for (int i = lo; i <= hi; i++) {
			StdOut.println (a[i]);
		}
	}

	/**
	 * Reads in a sequence of strings from standard input; selection sorts them; 
	 * and prints them to standard output in ascending order. 
	 */
	public static void main(String[] args) {
		//        String[] a = StdIn.readAllStrings();
		//        Merge.sort(a);
		//        show(a, 0, a.length-1);

		StdOut.println("N\t#Copies\tEstimate = 2*N*log N - 2*N");
		for (int N = 4; N <= 512; N *= 2) {
			Double[] ar = new Double[N];
			for (int i = 0; i < ar.length; i++) {
				ar[i] = Math.random();
			}

			sort(ar);
			int k = (int) (Math.log(N) / Math.log(2));
			double estimate = N*(k*2 - 2);
			StdOut.printf("%2d\t%d\t%.2f%n", N, numCopies, estimate);
		}
	}
}