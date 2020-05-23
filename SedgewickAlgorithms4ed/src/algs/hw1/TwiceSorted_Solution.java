package algs.hw1;

import algs.hw1.TwiceSorted;

/**
 * Copy this class into your package, which must be USERID.hw1
 */
public class TwiceSorted_Solution extends TwiceSorted {

	/** Construct problem solution for given array. Do not modify this method. */
	public TwiceSorted_Solution(int[][] a) {
		super(a);
	}

	/** Construct problem solution using default sample array. Do not modify this method. */
	public TwiceSorted_Solution() {
		super();
	}

	
	/** 
	 * Dumb Locate that checks everything in row col fashion in a check every option pattern
	 */
	
	public int[] dumbLocate(int target) {
		for (int row = 0; row < length(); row++) {
			for (int col = 0; col < length(); col++) {
				if (inspect(row, col) == target) { return new int[] {row, col}; }
			}
		}
		// if target value is not found in array, then return null 
		return null;
}
	
	
	
	/** 
	 * For this homework assignment, you need to complete the implementation of this
	 * method.
	 */
	@Override
	public int[] locate(int target) {
		if(inspect(length()/2, length()/2) > target) { //if greater, check lower diagonal
			
			return null;
		}
		else if (inspect(length()/2, length()/2) < target) { //if less than, check upper diagonal
			
			return null;
		}
		else{
			if (inspect(length()/2, length()/2) == target) { return new int[] {length()/2, length()/2}; } //if equal lucky!
			//if not greater, less than or equal something is wrong return null
			return null;
		}
	}
		
		

	/** Be sure that you call your class constructor. Do not modify this method. */ 
	public static void main (String args[]) {
		System.out.println("Number of inspections:" + new TwiceSorted_Solution(sample).trial(512));
	}

}
