package algs.days.day27;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;

public class Fewest {
	static int numComparisons;
	
	static int compareTo(int[] ar, int from, int to) {
		numComparisons++;
		return ar[from] - ar[to];
	}
	
	public static void main(String[] args) {
		int ar[] = new int[32];
		
		int n = 32;
		for (int i = 0; i < n; i++) {
			ar[i] = StdRandom.uniform(1000);
		}
		
		// find min and max in fewest number of comparisons
		numComparisons = 0;
		int min = 0;
		int max = 0;
		
		// naive implementation
		for (int i = 1; i < n; i++) {
			// if greater than max, reset
			if (compareTo(ar, i, max) > 0) {
				max = i;
			}
		}
		for (int i = 1; i < n; i++) {
			// if smaller than min, reset
			if (compareTo(ar, min, i) > 0) {
				min = i;
			}
		}
		
		System.out.println("N:" + n + ", min:" + ar[min] + ", max:" + ar[max] + ", compares:" + numComparisons);
		System.out.println("Estimate: 2*(N-1)");
		
		// efficient by a hair.
		// find min and max in fewest number of comparisons
		numComparisons = 0;
		min = 0;
		max = 0;
			
		// Find maximum
		for (int i = 1; i < n; i++) {
			// if greater than max, reset
			if (compareTo(ar, i, max) > 0) {
				max = i;
			}
		}
		
		// when searching for min, skip the 'max' index, since that
		// index is >= min
		for (int i = 1; i < n; i++) {
			if (i == max) { continue; }
			
			// if smaller than min, reset
			if (compareTo(ar, min, i) > 0) {
				min = i;
			}
		}
		
		System.out.println("N:" + n + ", min:" + ar[min] + ", max:" + ar[max] + ", compares:" + numComparisons);
		System.out.println("Estimate: 2N-3");
		
		// BEST CASE. ALREADY SORTED
		numComparisons = 0;
		min = 0;
		max = 0;
		
		Arrays.sort(ar);
		for (int i = 1; i < n; i++) {
			
			// if smaller than min, reset
			if (compareTo(ar, i, max) > 0) {
				max = i;
			} else if (compareTo(ar, min, i) > 0) {
				min = i;
			}
		}
		
		System.out.println("N:" + n + ", min:" + ar[min] + ", max:" + ar[max] + ", compares:" + numComparisons);
		System.out.println("Upper Estimate: 2N-2, but could be lower, yet never lower than N-1");
		
	}
}
