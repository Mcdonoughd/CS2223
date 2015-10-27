package algs.experiment.orderedSearch;

/**
 * Implements BinaryArraySearch against the OrderedCollection data set.
 * 
 * Keeps track of number of comparisons made to date, allowing external retrieval of this count.
 */
public class BinaryArraySearch implements ISearchOrdered {
	int comparisons = 0;
	
	@Override
	public boolean contains(OrderedCollection collection, Object target) { 
		try {
			comparisons = 0;
			int low = 0;
			int high = collection.size()-1;
			while (low <= high) {
				int mid = (low+high)/2;
				comparisons++;
				int rc = collection.compare(mid, target);
				if (rc < 0) {
					low = mid+1;
				} else if (rc > 0) {
					high = mid-1;
				} else {
					return true;
				}
			}
		} catch (Exception e) {
			
		}
		return false;
	}

	@Override
	public int numComparisons() {
		return comparisons;
	}

}
