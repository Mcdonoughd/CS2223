package algs.hw3;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// 
public class Merge {
    
	static Comparable aux[];
	static long lessCount = 0;
	static long exchCount = 0;
	static long advanceI = 0;
	static long advanceJ = 0;
	
    public static void sort(Comparable[] a) {
    	lessCount = exchCount = advanceI = advanceJ = 0;
    	aux = new Comparable[a.length];
    	sort (a, 0, a.length-1);
    }
    
    // recursive helper function
    static void sort (Comparable[] a, int lo, int hi) {
    	// Base Case
    	if (hi <= lo) return;
    	
    	int mid = lo + (hi - lo)/2;
    	
    	// Choose Sub-problem(s). Key operation: compare two values in a.
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
    		else if  (less(aux[j], aux[i]))  { a[k] = aux[j++]; advanceJ++; }
    		else                             { a[k] = aux[i++]; advanceI++; }
    	}
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
        Merge.sort(a);
        show(a);
    }
}