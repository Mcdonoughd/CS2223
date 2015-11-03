package algs.days.day01;

import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class BinaryIntSearch {
	boolean contains(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc < 0) {
				low = mid+1;
			} else if (rc > 0) {
				high = mid-1;
			} else {
				return true;
			}
		}
		return false;
	}
	
	int rank (int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
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
		BinaryIntSearch bis = new BinaryIntSearch();
		
		int[] values = new int[] { 2, 4, 5, 6, 10, 12, 18 };
		StdOut.println ("Here are your values: " + Arrays.toString(values));
		StdOut.println ("What are you looking for? ");
		// invoke method on object
		int val = StdIn.readInt();
		System.out.println("Rank of " + val + " is " + bis.rank(values, val));
	}
}
