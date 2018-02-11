package algs.days.day01;

import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.*;

public class TimingComparison {

	/** Values to be searched. Will be in sorted order and contain unique values. */
	static int vals[];
	
	/** Values to be searched. WIll also be in sorted order and contain unique values. */
	static int targets[];
	
	/**
	 * Returns a unique, sorted array 
	 * 
	 * @param number -- number of unique values to be present
	 * @param range  -- +/- range is the range of numbers to generate 
	 * @return
	 */
	static int[] randomUniqueArray(int total, int range) {
		// Range of numbers to choose from [-range, range]
		if (2*range + 1 < total) {
			throw new IllegalArgumentException ("Range is too small given the required total: " + total);
		}
		
		ArrayList<Integer> uniq = new ArrayList<>();
		while (uniq.size() < total) {
			int val = StdRandom.uniform(-range, range);
			if (!uniq.contains(val)) {
				uniq.add(val);
			}
		}
		Collections.sort(uniq); // sorts properly
		int[] values = new int[uniq.size()];
		for (int i = 0; i < values.length; i++) { values[i] = uniq.get(i); }
		
		return values;
	}

	/** Execute BinaryArraySearch using DIV for midpoint, and compare LT, GT, then EQ. */
	boolean contains_lt_gt_eq_div(int[] collection, int target) {
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


	/** Execute BinaryArraySearch using SHIFT for midpoint, and compare LT, GT, then EQ. */
	boolean contains_lt_gt_eq_shift(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high) >> 1;

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


	/** Execute BinaryArraySearch using SHIFT for midpoint, and compare EQ, LT, then GT. */
	boolean contains_eq_lt_gt_shift(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high) >> 1;

		int rc = collection[mid] - target;
		if (rc == 0) {
			return true;
		} else if (rc < 0) {
			low = mid+1;
		} else {
			high = mid-1;
		} 
		}
		return false;
	}

	/** Execute BinaryArraySearch using DIV for midpoint, and compare EQ, LT, then GT. */
	boolean contains_eq_lt_gt_div(int[] collection, int target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid] - target;
			if (rc == 0) {
				return true;
			} else if (rc < 0) {
				low = mid+1;
			} else {
				high = mid-1;
			} 
		}
		return false;
	}

	/** Compute timing results of SHIFT/LT/GT/EQ. */
	double timing_lt_gt_eq_shift(int numRuns) {
		// time a base run
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_lt_gt_eq_shift(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}
	
	/** Compute timing results of DIV/LT/GT/EQ. */
	double timing_lt_gt_eq_div(int numRuns) {
		// time a base run
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_lt_gt_eq_div(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}
	
	/** Compute timing results of DIV/EQ/LT/GT. */
	double timing_eq_lt_gt_div(int numRuns) {
		// time a base run
		StopwatchCPU base = new StopwatchCPU();
		for (int t = 0; t < numRuns; t++) {
			for (int i = 0; i < targets.length; i++) {
				contains_eq_lt_gt_div(vals, targets[i]);
			}
		}
		return base.elapsedTime();
	}
	
	/** Launch everything. */
	public static void main(String[] args) {
		
		System.out.println("Generating values. This may take a few seconds...");
		// range of numbers is +/- 2^24 or [-16777216, 16777216]. Generate a total of 2^17 or 131072
		vals = randomUniqueArray((int) Math.pow(2, 17), (int) Math.pow(2, 24));
		
		// These are the items to be searched: 131072 within range +/- 2^25 of [-33554432, 33554432]
		// to ensure there are some numbers that are not found.
		targets = randomUniqueArray((int) Math.pow(2, 17), (int) Math.pow(2, 25));
		
		// quick test
		TimingComparison tc = new TimingComparison();
		for (int t : targets) {
			boolean found1 = tc.contains_eq_lt_gt_div(vals, t);
			boolean found2 = tc.contains_lt_gt_eq_shift(vals, t);
			boolean found3 = tc.contains_lt_gt_eq_div(vals, t);
			
			if (found1 == found2 && found2 == found3) {
				// good
			} else {
				System.err.println("Defect found on " + t + ", " + found1 + "," + found2 + "," + found3);
				System.exit(-1);
			}
		}
		
		System.out.println("timing_eq_lt_gt_div");
		for (int i = 0; i < 10; i++) {
			System.gc();
			double t = new TimingComparison().timing_eq_lt_gt_div(512);	
			System.out.printf("%.3f%n", t);
		}
		System.out.println("----");
		System.out.println("timing_lt_gt_eq_shift");
		for (int i = 0; i < 10; i++) {
			System.gc();
			double t = new TimingComparison().timing_lt_gt_eq_shift(512);	
			System.out.printf("%.3f%n", t);
		}
		System.out.println("----");
		System.out.println("timing_lt_gt_eq_div");
		for (int i = 0; i < 10; i++) {
			System.gc();
			double t = new TimingComparison().timing_lt_gt_eq_div(512);	
			System.out.printf("%.3f%n", t);
		}
		
	}
}
