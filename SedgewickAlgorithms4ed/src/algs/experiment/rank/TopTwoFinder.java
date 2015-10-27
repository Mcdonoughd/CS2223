package algs.experiment.rank;

/**
 * Naive algorithm for locating top two elements in an array.
 * 
 */
public class TopTwoFinder {
	public static void main(String[] args) throws Exception {
		
		int n = 512;
		Selection collection = new Selection(n);
		collection.orderDescending();
		int largest = 0;
		int smaller = 1;
				
		if (collection.compare(largest, smaller) < 0) {
			int tmp = largest;
			largest = smaller;
			smaller = tmp;
		}
		
		// largest is greater than or equal to smaller
		// find next, swapping as needed.
		
		for (int i = 2; i < n; i++) {
			if (collection.compare(i, largest) >= 0) {
				smaller = largest;
				largest = i;
			} else if (collection.compare(i, smaller) > 0) {
				smaller = i;
			}
		}
		
		collection.assertKthRank(n-1, collection.get(largest));
		collection.assertKthRank(n-2, collection.get(smaller));
		
		String ticket = collection.getValidation();
		System.out.println(ticket);
	}
}
