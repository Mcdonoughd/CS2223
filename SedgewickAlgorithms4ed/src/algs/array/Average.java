package algs.array;

import edu.princeton.cs.algs4.StdRandom;

public class Average {
	
	/**
	 * Recursive implementation of average. 
	 * 
	 * @param vals    values over which to compute average
	 * @return        return computed average
	 */
	public static double compute(double[] vals) {
		return computeRecursive(vals, 0, vals.length);
	}
		
	/**
	 * Recursive implementation that computes average for a given range [left, right) of vals.
	 * 
	 * @param vals
	 * @param left
	 * @param right
	 * @return
	 */
	public static double computeRecursive(double[] vals, int left, int right) {
		// base case
		if (right - left == 1) {
			return vals[left];
		}
		
		// recursive
		int mid = (left + right) / 2;
		
		double average1 = computeRecursive(vals, left, mid);
		double average2 = computeRecursive(vals, mid, right);
		
		return (average1+average2)/2;
	}
	
	/**
	 * Compute straightforward average computation
	 * 
	 * @param vals      array of values
	 * @return          average of the values.
	 */
	public static double straightAverage(double vals[]) {
		double average = vals[0];
		for (int i = 1; i < vals.length; i++) {
			average += vals[i];
		}
		
		return average/vals.length;
	}
	
	/**
	 * Test client.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		for (int t = 0; t < 1000; t++) {
			double vals[] = new double[512];
			for (int i = 0; i < vals.length; i++) {
				vals[i] = StdRandom.uniform();
			}
			
			double a1 = straightAverage(vals);
			double a2 = compute(vals);
			
			assert (a1 == a2);
		}
	}
}
