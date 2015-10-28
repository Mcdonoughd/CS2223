package algs.hw1;

public class ConfirmCrossingPoint {

	// change this to be a 5x7 array that confirms the maximum number
	// of array locations to be investigated. 
	public static void main(String[] args) {
		
		// CHANGE THIS
		boolean [][] ar =  {
				{ false, false, false, false, true, false, false, false },
				{ false, false, false, false, true, false, false, false },
				{ true,  true,  true,  true,  true, true,  true,  true },
				{ false, false, false, false, true, false, false, false },
		};
		
		// confirm
		new CrossingPoint().locate(ar);
	}
}
