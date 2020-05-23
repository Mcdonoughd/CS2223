package algs.days.day02;


/**
 * This code contains two different defective binary array search implementations.
 * 
 * In the first one, {@link #bad_contains(int[], int)}, the while loop mistakenly uses
 * the condition (low < high) as the while loop guard. I discuss this issue in lecture.
 * 
 * In the second one, {@link #bad_contains_2(int[], int)}, the variables 'low' and 'high'
 * are incorrectly set to be the mid, instead of (mid+1) and (mid-1). This leads to
 * an infinite loop for some searches.
 */
public class ErrorBinaryIntSearch {
	
	/**
	 * Defective Binary Array Search that causes some searches to fail (can you determine which ones?).
	 */
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

	/**
	 * Defective Binary Array Search that causes infinite loop for some searches.
	 */
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


	/**
	 * Run the trials.
	 * 
	 * Size : Collection Size to search
	 * Found: How many numbers were found (without defect, should have been size)
	 * FoundE: How many even numbers were found with the defect
	 * FoundO: How many odd numbers were found with the defect
	 */
	public static void main(String[] args) {
		ErrorBinaryIntSearch bis = new ErrorBinaryIntSearch();

		System.out.println("Size,Found,FoundE,FoundO");
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