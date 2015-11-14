package algs.days.day10;

import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class BinaryIntSearch {
	int numComparisons = 0;
	int rank (int[] collection, int target) {
		numComparisons = 0;
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			numComparisons++;
			if (rc < 0) {
				low = mid+1;
			} else if (rc > 0) {
				high = mid-1;
			} else {
				return mid;
			}
		}
		return -1;
	}


	public static void main(String[] args) {
		for (int n = 1; n <= 33; n++) {
			BinaryIntSearch bis = new BinaryIntSearch();
			int [] values = new int[n];
			for (int i = 0; i < n; i++) { values[i] = i; }
			
			// exceed on both ends 
			int max = 0;
			for (int i = -1; i <= n; i++) {
				bis.rank(values, i);
				if (bis.numComparisons > max) {
					max = bis.numComparisons;
				}
			}
			
			System.out.println(n + "\t" + max);
		}
		
	}
}
