package algs.hw1;

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
	 * For this homework assignment, you need to complete the implementation of this
	 * method.
	 */
	@Override
	public int[] locate(int target) {
		// Only look at the value in the upper right corner of the TwiceSorted array.
		if (inspect(0,length()-1) == target) {
			return new int[] {0, length()-1};
		}
		
		return null;
	}

	/** Be sure that you call your class constructor. Do not modify this method. */ 
	public static void main (String args[]) {
		System.out.println("Number of inspections:" + new TwiceSorted_Solution(sample).trial(512));
	}

}
