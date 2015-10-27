package algs.experiment.orderedSearch;

/**
 * A randomized search over a sorted array seeks to find a value by randomly picking some value within
 * its range instead of the middle element of the range.
 */

public class RandomSearch implements ISearchOrdered {
	int comparisons = 0;

	@Override
	public boolean contains(OrderedCollection collection, Object target) { 
		try {
			comparisons = 0;
			int low = 0;
			int high = collection.size()-1;
			while (low <= high) {
				int mid = low + (int)(Math.random()*(high-low));
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
