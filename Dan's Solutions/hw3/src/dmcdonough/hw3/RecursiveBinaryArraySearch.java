package dmcdonough.hw3;



public class RecursiveBinaryArraySearch {
	
	/** For comparison. The original iterative Binary Array Search with while loop. */
	int straightRank (int[] a, int target, int lo, int hi) {
		while (lo <= hi) {
			int mid = (lo+hi)/2;

			int rc = a[mid] - target;
			if (rc < 0) {
				lo = mid+1;
			} else if (rc > 0) {
				hi = mid-1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	/** Recursive BInary Array Search. */
	int rank (int[] a, int target, int lo, int hi) {
		// Base Case
		if (lo > hi) { return -1; }
		
		// Choose Sub-problem. Key operation: compare target with a[mid]
		int mid = (lo+hi)/2;
		int rc = a[mid] - target;
		if (rc < 0) {
			return rank (a, target, mid+1, hi);
		} else if (rc > 0) {
			return rank (a, target, lo, mid-1);
		} else {
			return mid;
		}
	}

	/** Exercise on small array to demonstrate works. */
	public static void main(String[] args) {
		RecursiveBinaryArraySearch bis = new RecursiveBinaryArraySearch();
		
		int[] values = new int[] { 2, 4, 5, 6, 10, 12, 18 };
		for (int i = 0; i < 20; i++) {
			System.out.printf("%d\t%d\n", i, bis.rank(values, i, 0, values.length-1));
		}
	}
}
