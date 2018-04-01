package algs.solutions.hw2;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// Sedgewick, 4ed
public class Insertion {
	static long lessCount;
	static long exchCount;
	
	/**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
	public static void sort(Comparable[] a) {
		lessCount = exchCount = 0;
        int N = a.length;
													// Truth: 
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
													// Truth: 
        }
													// Truth: 
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
        Insertion.sort(a);
        show(a);
    }
}