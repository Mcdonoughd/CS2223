package algs.days.day08;

import edu.princeton.cs.algs4.StdRandom;

// how would you evaluate the number of comparisons used by this algorithm
// to locate the maximum value
public class RecursiveMaxFinder {
	static int count = 0;
	
	static Comparable largest (Comparable[] a, int lo, int hi) {
		if (hi <= lo) { return a[lo]; }
		
		int mid = lo + (hi - lo)/2;
		
		Comparable maxLeft = largest (a, lo, mid);
		Comparable maxRight = largest (a, mid+1, hi);
		
		if (less(maxLeft, maxRight)) { 
			return maxRight;
		} else {
			return maxLeft;
		}	
	}
	
	static boolean less(Comparable v, Comparable w) {
		count++;
        return v.compareTo(w) < 0;
    }
        
	public static void main(String[] args) {
		int N = 2000;
		
		Integer[] ar = new Integer[N];
		for (int i = 0; i < N; i++) {
			ar[i] = StdRandom.uniform(-1000000, 1000000);
		}
		
		System.out.println(largest(ar, 0, ar.length-1) + " in " + count + " compares");
	}
}
