package algs.days.day11;

import edu.princeton.cs.algs4.StdRandom;

public class LyingBinarySearch {
	
	static boolean hasLied = false;
	static double lyingPercentageTrigger = 0.05;
	static int countCompare = 0;
	
	static void resetLyingState() {
		hasLied = false;
		countCompare = 0;
	}
	
	/**
	 * Return comparison. Will lie exactly once during rank invocation, based upon
	 * the lying percentage trigger.
	 */
	static int compareTo(int[] collection, int idx, int target) {
		countCompare++;
		
		if (!hasLied && StdRandom.uniform() < lyingPercentageTrigger) {
			hasLied = true;
			// make sure different from expected
			int rc = collection[idx] - target;
			while (true) {
				int lie = StdRandom.uniform(-1, 2);
				if (rc == 0 && lie != 0) { return lie; }
				if (rc < 0 && lie >= 0) { return lie; }
				if (rc > 0 && lie <= 0) { return lie; }
				
				// must keep going and eventually we will exit this loop.
			}
		}
		return collection[idx] - target;
	}
	
	
	static int rank (int[] collection, int target) {
		resetLyingState();
		int low = 0;
		int high = collection.length-1;
		while (low <= high) {
			int mid = (low+high)/2;

			// might lie, so check again...
			int rc = compareTo(collection, mid, target);
			int rc2 = compareTo(collection, mid, target);
			
			// Hah! Found the liar!  Goal is to make rc the proper value
			if (rc != rc2) {
				int rc3 = compareTo(collection, mid, target);
				if (rc3 == rc2) {
					// if rc3 agrees with rc2 then rc was the lie, so reset
					rc = rc3;
				} else {
					// otherwise, rc2 was the lie and we can let rc stand as is
				}
			}
			
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
		System.out.println("N\tMaxGuess\t2*LogN+3");

		for (int n = 4, idx=2; n <= 1048576; n*=2, idx++) {
			int a[] = new int[n];
			
			// only even numbers
			for (int i = 0; i < 2*n; i+=2) { a[i/2] = i; }
			
			int mostGuesses = 0;
			
			// 10K trials
			for (int t = -1; t < 2*n; t++) {
				// valid numbers are even, invalid numbers are odd.
				int rank = rank(a, t);
				if (t % 2 == 0) {
					if (rank < 0) { 
						System.err.println("ERROR! WE'VE BEEN FOOLED!"); 
					}
					else { 
						if (countCompare > mostGuesses) {
							mostGuesses = countCompare;
						}
					}
				} else {
					if (rank > 0) { 
						System.err.println("ERROR! WE'VE BEEN FOOLED!"); 
					}
					else { 
						if (countCompare > mostGuesses) {
							mostGuesses = countCompare;
						}						
					}
				}
			}
			
			System.out.println(n + "\t" + mostGuesses + "\t" + (idx*2+3));
		}
	}
}

