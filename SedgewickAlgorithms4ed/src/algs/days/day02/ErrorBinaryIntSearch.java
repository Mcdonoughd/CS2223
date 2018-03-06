package algs.days.day02;

import java.util.Arrays;

import edu.princeton.cs.algs4.*;

public class ErrorBinaryIntSearch {
	boolean bad_contains(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low < high) {
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

	boolean bad_contains_2(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc < 0) {
				low = mid;
			} else if (rc > 0) {
				high = mid;
			} else {
				return true;
			}
		}
		return false;
	}


	public static void main(String[] args) {
		ErrorBinaryIntSearch bis = new ErrorBinaryIntSearch();

		for (int i = 4; i < 1048576; i *= 2) {
			// create values[] array of sizes 2^n for n = 2 .. 20
			int[] values = new int[i];
			for (int j = 0; j < i; j++) { values[j] = j+1; }

			// check each one and count how many missed.
			int found = 0, founde = 0, foundo = 0;
			for (int j = 1; j < i; j++) { 
				if (bis.bad_contains(values, j)) {
					found++;
					if (j % 2 == 0) { founde++; }
					if (j % 2 == 1) { 
						foundo++;
					}
				}
			}
			System.out.println(i + "," + found + "," + founde + "," + foundo);
		}
	}
}