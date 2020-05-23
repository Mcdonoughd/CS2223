package algs.days.day11;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Put here for analysis, not for use!
public class Bubble {

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * @param a the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		int N = a.length;

		boolean swapped;
		do {
			swapped = false;
			for (int idx = 1; idx < N; idx++) {
				if (less(a[idx], a[idx-1])) {
					exch(a, idx, idx-1);
					swapped = true;
				}
			}

			N--;
		} while (swapped);
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

	// print array to standard output
	private static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			StdOut.println(a[i]);
		}
	}

	/**
	 * Reads in a sequence of strings from standard input; selection sorts them; 
	 * and prints them to standard output in ascending order. 
	 */
	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		Bubble.sort(a);
		show(a);
	}
}