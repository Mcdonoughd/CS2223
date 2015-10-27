package algs.experiment.orderedSearch;

public class TertiarySearch implements ISearchOrdered {
	int comparisons = 0;

	@Override
	public boolean contains(OrderedCollection collection, Object target) {
		try {
			comparisons = 0;
			int low = 0;
			int high = collection.size()-1;
			while (low <= high) {
				int delta = (low+high)/3;
				if (delta > high-low) { delta = high-low; }
				int mid1 = low + delta;
				int mid2 = high - delta;
				comparisons++;
				int rc1 = collection.compare(mid1, target);
				if (rc1 > 0) {
					high = mid1-1;
				} else if (rc1 == 0) {
					return true;
				} else {
					comparisons++;
					int rc2 = collection.compare(mid2, target);
					if (rc2 < 0) {
						low = mid2+1;
					} else if (rc2 == 0) {
						return true;
					} else {
						low = mid1+1;
						high = mid2-1;
					}
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
