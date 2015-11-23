package algs.solutions.hw3;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// 3way merge
public class MergeThreeWay {
    
	static Comparable aux[];
	static int lessCount = 0;
	static int exchCount = 0;
	
    public static void sort(Comparable[] a) {
    	lessCount = exchCount = 0;
    	aux = new Comparable[a.length];
    	sort (a, 0, a.length-1);
    }
    
    // recursive helper function
    static void sort (Comparable[] a, int lo, int hi) {
    	if (hi <= lo) return;
    	
    	int third = (hi - lo + 1)/3;
    	sort(a, lo, lo + third);
		sort(a, lo +third+1, lo + 2*third);
		sort(a, lo +2*third+1, hi);
		merge(a, lo , lo+third, lo+2*third, hi);
    }
    
    // 3way-merge sorted results a[lo..left] with a[left+1..right]  and a[right+1..hi] back into a
    static void merge (Comparable[] a, int lo, int left, int right, int hi) {
    	int i = lo;        // starting index into left1 sorted sub-array
    	int j = left+1;    // starting index into left2 sorted sub-array
    	int m = right+1;   // starting index into left3 sorted sub-array
    	
    	// copy a[lo..hi] into aux[lo..hi]
    	for (int k = lo; k <= hi; k++) {
    		aux[k] = a[k];
    		exchCount++;
    	}
    	
    	// now comes the merge. Something you might simulate with flashcards
    	// drawn from two stack piles. This is the heart of mergesort. 
    	for (int k = lo; k <= hi; k++) {
    		exchCount++;
    		if       (i > left && j > right)               { a[k] = aux[m++]; }
    		else if  (            j > right && m > hi)     { a[k] = aux[i++]; }
    		else if  (i > left &&              m > hi)     { a[k] = aux[j++]; } 
    		
    		else if  (m > hi) {
    			if (less(aux[j], aux[i]))      { a[k] = aux[j++]; }
    			else                           { a[k] = aux[i++]; }}
    		
    		else if  (i > left) {
    			if (less(aux[m],aux[j]))       { a[k] = aux[m++]; }
    			else                           { a[k] = aux[j++]; }}
    		
    		else if  (j > right) {
    			if (less(aux[m],aux[i]))       { a[k] = aux[m++]; }
    			else                           { a[k] = aux[i++]; }}
    		else {
    			// when we get here, all three are available! 
    			Comparable aj = aux[j];
    			Comparable ai = aux[i];
    			if (less(aj, ai)) {
    				if (less(aj, aux[m]))      { a[k] = aux[j++]; }
    				else                       { a[k] = aux[m++]; }
    			} else {
    				if (less(ai, aux[m]))      { a[k] = aux[i++]; }
    				else                       { a[k] = aux[m++]; }
    			}
    		}
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
        MergeThreeWay.sort(a);
        show(a);
    }
}