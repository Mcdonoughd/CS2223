package dmcdonough.hw2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import dmcdonough.hw2.QuickSort;

/**
 *  The <tt>Quick</tt> class provides static methods for sorting an
 *  array and selecting the ith smallest element in an array using quicksort.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/21elementary">Section 2.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  Provides an alternate partition method to use. 
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class QuickSort {

	public static long exchCount;
	public static long lessCount;

	// This class should not be instantiated.
	private QuickSort() { }

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * @param a the array to be sorted
	 */
	public static void sort(Comparable[] a) {
		StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
	}

	// quicksort the subarray from a[lo] to a[hi]
	private static void sort(Comparable[] a, int lo, int hi) { 
		if (hi <= lo) return;
		int j = partition(a, lo, hi);
		sort(a, lo, j-1);
		sort(a, j+1, hi);
	}

	private static int partition(Comparable[] a, int lo, int hi)
	{ // Partition into a[lo..i-1], a[i], a[i+1..hi].
	 int i = lo, j = hi+1; // left and right scan indices
	 Comparable v = a[lo]; // partitioning item
	 while (true)
	 { // Scan right, scan left, check for scan complete, and exchange.
	 while (less(a[++i], v)) if (i == hi) break;
	 while (less(v, a[--j])) if (j == lo) break;
	 if (i >= j) break;
	 exch(a, i, j);
	 }
	 exch(a, lo, j); // Put v = a[j] into position
	 return j; // with a[lo..j-1] <= a[j] <= a[j+1..hi].
	}



	/***************************************************************************
	 *  Helper sorting functions.
	 ***************************************************************************/

	// is v < w ?
	private static boolean less(Comparable v, Comparable w) {
		lessCount++;
		return v.compareTo(w) < 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j) {
		exchCount++;
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}


	/***************************************************************************
	 *  Check if array is sorted - useful for debugging.
	 ***************************************************************************/
	private static boolean isSorted(Comparable[] a) {
		return isSorted(a, 0, a.length - 1);
	}

	private static boolean isSorted(Comparable[] a, int lo, int hi) {
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i-1])) return false;
		return true;
	}


	// print array to standard output
	private static void show(Comparable[] a) {
		for (int i = 0; i < a.length; i++) {
			StdOut.println(a[i]);
		}
	}

	/**
	 * Reads in a sequence of strings from standard input; quicksorts them; 
	 * and prints them to standard output in ascending order. 
	 * Shuffles the array and then prints the strings again to
	 * standard output, but this time, using the select method.
	 */
	public static void main(String[] args) {
		String[] a = StdIn.readAllStrings();
		QuickSort.sort(a);
		show(a);

	}

}

