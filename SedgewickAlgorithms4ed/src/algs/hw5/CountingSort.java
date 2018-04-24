package algs.hw5;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

// Proper Merge Sort from Sedgewick, 4ed
public class CountingSort {

	public static void sort(Integer[] values, int k) {
		// fill in...
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
			sort(ar, k);
			StdOut.printf("%d\t%f\n", N, sw.elapsedTime());
		}
	}
}