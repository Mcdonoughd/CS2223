package algs.hw5;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

// Proper Merge Sort from Sedgewick, 4ed
// Standard implementation.
public class Merge {

	static Comparable aux[];

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


	/**
	 * Reads in a sequence of strings from standard input; selection sorts them; 
	 * and prints them to standard output in ascending order. 
	 */
	public static void main(String[] args) {
		
		// choose some k value
		int k = 50;  
		
		StdOut.println("N\tTime");
		for (int N = 4096; N <= 524288; N *= 2) {
			Integer[] ar = new Integer[N];
			for (int i = 0; i < ar.length; i++) {
				ar[i] = StdRandom.uniform(k);
			}

			Stopwatch sw = new Stopwatch();
			sort(ar);
			StdOut.printf("%d\t%f\n", N, sw.elapsedTime());
		}
	}
}