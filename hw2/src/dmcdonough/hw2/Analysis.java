package dmcdonough.hw2;

import algs.days.day07.WastedSpaceMerge;
import edu.princeton.cs.algs4.StdOut;

/**
 * Your goal is to output a table that looks like this:

N	Avg.	Inspectk
1	1.00	1
2	1.50	1,2
4	2.00	2,1,2,3
8	2.63	3,2,3,1,3,2,3,4
16	3.38	4,3,4,2,4,3,4,1,4,3,4,2,4,3,4,5
32	4.22	5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,1,5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,6
64	5.13	6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,2,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,1,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,2,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,7


Bonus Question (1pt.)

See homework. Come up with a mathematical formula that computes this average value.

 */

public class Analysis {
	

	// My strawman algorithm which wastes space (as I mention in the beginning of Lecture 7).

	    static int extraSpace;
		public static void sort(Comparable[] a) {
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
	    	
	    	// merge a[lo..mid] with a[mid+1..hi] into new storage
	    	Comparable[] aux = new Comparable[hi-lo+1];
	    	extraSpace += aux.length;
	    	
	    	// copy a[lo..hi] into aux[lo..hi]
	    	for (int k = lo; k <= hi; k++) {
	    		aux[k-lo] = a[k];
	    	}
	    	
	    	// now comes the merge. Something you might simulate with flashcards
	    	// drawn from two stack piles. This is the heart of mergesort. 
	    	for (int k = lo; k <= hi; k++) {
	    		if       (i > mid)                     { a[k] = aux[j++ - lo]; }
	    		else if  (j > hi)                      { a[k] = aux[i++ - lo]; }
	    		else if  (less(aux[j-lo], aux[i-lo]))  { a[k] = aux[j++ - lo]; }
	    		else                                   { a[k] = aux[i++ - lo]; }
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

		public static double log2(double num){
		return (Math.log(num)/Math.log(2));
		}
		
		
		public static void Parent(int r, Integer[] ar, int level, int N, int max_level) {
			//check if current level of tree is greater than the greatest possible level
			if(max_level-1 < level) {
				return;
			}
			
			else {
			//carry origin to check for edges
			int origin = (int) Math.floor((N-1)/2);
			//calculate simple children
			int left_child = (int) (r-(Math.floor(r/2))-1);
    		int right_child = (int) (r+(Math.floor(r/2))+1);
    		//recursive call to parents for left nodes
			if(left_child >= 0 && left_child < N && ar[left_child] == null && left_child < origin) {
        		ar[left_child] = level;
        		Parent(left_child,ar,level+1,N,max_level);
    		}
			//recursive call to parents for right nodes
    		if(right_child <= N-1 && right_child >=0 && ar[right_child] == null && right_child < origin) {
        		
        		ar[right_child] = level;
        		Parent(right_child,ar,level+1,N,max_level);
    		}
		}
			//end
    		return;
		}
	
	    /**
	     * Reads in a sequence of strings from standard input; selection sorts them; 
	     * and prints them to standard output in ascending order. 
	     */
	    public static void main(String[] args) {
	    	StdOut.println("N\tAvg\tInspectk");
	    	//Complete Heap Implementation
	        for (int N = 1; N <= 64; N *= 2) {
	        	
	        	//Make array
	        	Integer[] ar = new Integer[N];
	        	//max level of tree
	        	int max_level = (int) (log2(N)+1);
	        	if(N > 2) {
	    			int origin = (int) Math.floor((N-1)/2);

		        	Parent(origin, ar, 2, N, max_level );
	        	}
	        	else {
	        		ar[0] = 1;
	        	}
	        	//add last value to array
	        	ar[N-1] = max_level;
	        	
	        
	        	/**
	        	 *  Parent(r) = (r-1)/2 if r!=0.
					Left child(r) = (N-1)/2 if 2r+1 <=n.
					Right child(r) = 2r-1 if 2r+2 <=n.						
					Left sibling(r) =r-1 if r is even.
					Right sibling(r) =r+1 if r is odd and r+1<=n.
	        	 */
	        	
	        	//populate the array with vals based on binary view order
        	
        		/*ar[(int) Math.floor((N-1)/2)] = 1;
        		Parent_left((int) Math.floor((N-1)/2),ar,2,N);	
        		Parent_right((int) Math.floor((N-1)/2),ar,2,N);*/
	        	
	        		
	        	//determine the Max search item at end of array
	        	//ar[N-1] = (int) (log2(N)+1);
	        	double estimate = 0;
	        	StdOut.printf("%d\t%d\t%.2f%n", N, extraSpace, estimate);
	    }
	        }
	    }
	

