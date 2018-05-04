package dmcdonough.hw1;

import algs.hw1.TwiceSorted;


/**
 * Copy this class into your package, which must be USERID.hw1
 */
public class TwiceSorted_Solution extends TwiceSorted {
	
	/** Small example to use. */
	public static final int[][] test = new int[][] {
		{  1, 2, 3, 4},
		{  5, 6, 7, 8},
		{ 9, 10, 11, 12},
		{ 13, 14, 15, 16},
	};

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
		//System.out.println(inspect(length()-1, length()-1));
		//search diagonal from bottom up until you find an int smaller than the target
		int dia = length()-1;
		for(; 0<=dia;dia--) {
			if(inspect(dia,dia)<target) {
				//check end of row to see if they are greater than target
				if(inspect(dia,length()-1)>=target) {
					for(int j = dia; j<=length()-1;j++) {
						//System.out.println(j);
						//System.out.println(i);
						//if int is greater than target no use checking rest of row
						if(inspect(dia,j)>target) {
							break;
						}
						//if not check if it is target
						else if (inspect(dia, j) == target) {
							 return new int[] {dia, j}; 
						}
					}
				}
				//check end of column to see if they are greater than target
				if(inspect(length()-1,dia)>=target) {
					for(int j = dia; j<=length()-1;j++) {
						
						//if int is greater than target no use checking rest of col
						if(inspect(j,dia)>target) {
							break;
						}
						//if not check if it is target
						else if (inspect(j, dia) == target){
							  return new int[] {j, dia}; 
						}
					}
				}
				
				//if not, check more diagonals
			}
			//
			else if(inspect(dia,dia)==target) {
				//SAME!
				return new int[] {dia, dia}; 
			}
		}
		return null;
	}
	/** Be sure that you call your class constructor. Do not modify this method. */ 
	public static void main (String args[]) {
		System.out.println("Number of inspections:" + new TwiceSorted_Solution(big).trial(512));
	}

}
