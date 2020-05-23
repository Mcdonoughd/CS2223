package algs.solutions.hw1;

import algs.hw1.TwiceSorted;

/**
 * Just does brute force search of all cells in order.
 */
public class StraightForward_TwiceSorted extends TwiceSorted {

	public StraightForward_TwiceSorted(int[][] a) {
		super(a);
	}

	public StraightForward_TwiceSorted() {
		super();
	}


	/** 
	 * Naive implementation will incur the most array inspections: 30752
	 */
	@Override
	public int[] locate(int target) {
		for (int r = 0; r < length(); r++) {
			for (int c = 0; c < length(); c++) {
				if (inspect(r,c) == target) {
					return new int[] {r, c};
				}
			}
		}

		return null;
	}

	/** 
	 * Marginal improvement: terminate looking in a row once target is smaller than array value. 
	 * This drops the number of inspections (in the default case) to 29190. Still not good enough.
	 */
	public int[] locateSlightlyBetter(int target) {
		for (int r = 0; r < length(); r++) {
			for (int c = 0; c < length(); c++) {
				int val = inspect(r,c);
				if (val == target) {
					return new int[] {r, c};
				} else if (val > target) {
					break;
				}
			}
		}

		return null;
	}
	
	/** Copy this code to work with the existing built-in trial. */ 
	public static void main (String args[]) {
		System.out.println("Number of inspections:" + new StraightForward_TwiceSorted().trial(512));
	}

}
