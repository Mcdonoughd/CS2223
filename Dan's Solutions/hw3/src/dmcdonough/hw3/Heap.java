package dmcdonough.hw3;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 *  The {@code Heap} class provides a static methods for heapsorting
 *  an array.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/24pq">Section 2.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Heap {
	
	public static int numcomp; //count the comparisons
	public static int numexch; //count the exchanges
	static int[][] results = new int[12][2]; //array of max comp and exchanges
	
	static String[] generateData(int n) {
		String[] vals = new String[n];
		for (int i = 0; i < n; i++) {
			vals[i] = Double.toString(StdRandom.uniform());//generate uniform data
		}
		return vals;
	}

	
	/**
	  * Rearranges the array in ascending order, using the natural order.
	  * @param a the array to be sorted
	  */
	 public static void sort(Comparable[] a) {
		 int n = a.length;
		 
		 // construct heap from the raw array of which we know nothing.
		 for (int k = n/2; k >= 1; k--) {
			 //a[k] = Double.toString(StdRandom.uniform());
			 init_sink(a, k, n); //copy of sink in which we count comp and exch
		 }
		 
		 //at this point, a has been turned into a heap.
		 while (n > 1) {
			 exch(a, 1, n--);
			 //numexch++;
			 sink(a, 1, n);
		 }
	 }
	 
	 /***************************************************************************
	  * Helper functions to restore the heap invariant.
	  ***************************************************************************/

	 private static void init_sink(Comparable[] pq, int k, int n) {
		 while (2*k <= n) {
			 int j = 2*k;
			 if (j < n && less_count(pq, j, j+1)) {
				// numcomp++;
				 j++;
			 }
			 if (!less_count(pq, k, j)) {
				 //numcomp++;
				 break;
			 }
			 exch(pq, k, j);
			 numexch++;
			 k = j;
		 }
	 }

	 /***************************************************************************
	  * Helper functions to restore the heap invariant.
	  ***************************************************************************/

	 private static void sink(Comparable[] pq, int k, int n) {
		 while (2*k <= n) {
			 int j = 2*k;
			 if (j < n && less(pq, j, j+1)) j++;
			 if (!less(pq, k, j)) break;
			 exch(pq, k, j);
			 k = j;
		 }
	 }

	 /***************************************************************************
	  * Helper functions for comparisons and swaps.
	  * Indices are "off-by-one" to support 1-based indexing.
	  ***************************************************************************/
	 private static boolean less_count(Comparable[] pq, int i, int j) {
		 numcomp++;
		 return pq[i-1].compareTo(pq[j-1]) < 0;
	 }
	 private static boolean less(Comparable[] pq, int i, int j) {
		 //numcomp++;
		 return (pq[i-1].compareTo(pq[j-1]) < 0);
	 }

	 private static void exch(Object[] pq, int i, int j) {
		 Object swap = pq[i-1];
		 pq[i-1] = pq[j-1];
		 pq[j-1] = swap;
	 }

	 // print array to standard output
	 private static void show(Comparable[] a) {
		 for (int i = 0; i < a.length; i++) {
			 StdOut.println(a[i]);
		 }
	 }
	 //update entry int the results
	 private static void updateEntry(int n) {
			if(results[n][0] <= numcomp){
				results[n][0] = numcomp;
			}
			if(results[n][1] <= numexch ) {
				results[n][1] = numexch;
			}
		}
		public static void generateReport(int n,int idx) {
				StdOut.println(n + "\t" + results[idx][0] + "\t" + results[idx][1]);
		}
	 /**
	  * Reads in a sequence of strings from standard input; heapsorts them; 
	  * and prints them to standard output in ascending order. 
	  *
	  * @param args the command-line arguments
	  */
	 public static void main(String[] args) {
		 System.out.println("N\tMaxComp\tMaxExch");
			 //for all sizes of arrays test num of array
			 for(int n = 16,idx = 0; n<=512; n*=2,idx++) {
				 //do 10 trials
				 for(int t = 0; t<10;t++) {
					 String[] a = generateData(n);
					 Heap.sort(a);
					 //show(a);
				 updateEntry(idx);
				//reset comp and exch count
				 numcomp = 0;
				 numexch = 0; 
			 }
				 generateReport(n,idx);
		 }
	 }
 }
