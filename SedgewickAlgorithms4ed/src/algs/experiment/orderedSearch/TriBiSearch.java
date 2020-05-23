package algs.experiment.orderedSearch;

public class TriBiSearch implements ISearchOrdered {
	int comparisons = 0;

	float f;
	
	public TriBiSearch(float f) {
		this.f = f;
	}
	
	@Override
	public boolean contains(OrderedCollection collection, Object target) {
		try {
			comparisons = 0;
			
			// FIRST do a three-way partition
			int low = 0;
			int high = collection.size()-1;
			if (low <= high) {
				int delta = (int)((low+high)/f);
				if (delta > high-low) { delta = high-low; }
				int mid1 = low + delta;
				int mid2 = high - delta;
				comparisons++;
				int rc1 = collection.compare(mid1, target);
				if (rc1 > 0) {
					high=mid1-1;
					
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
					
					
				} else if (rc1 == 0) {
					return true;
				} else {
					comparisons++;
					int rc2 = collection.compare(mid2, target);
					if (rc2 < 0) {
						low=mid2+1;

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
						
					} else if (rc2 == 0) {
						return true;
					} else {
						low = mid1+1;
						high = mid2-1;
						
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
