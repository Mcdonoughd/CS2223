package algs.experiment.sort;


/**
 * Sliding walls partition.
 * 
 * @author Laptop
 *
 */
public class SlidingWalls implements IPartition {

	@Override
	public int partition(Sorter collection, int left, int right, int pivotIndex) throws Exception {
		int k = right;
		int i = left;
		// http://www.mycstutorials.com/articles/sorting/quicksort
		
		// make sure pivot value is in the leftmost slot.
		if (pivotIndex != left) {
			collection.swap(left, pivotIndex);
		}
		// while the scan indices from left and right have not met,
		while (k > i) {                  

			// from the left, look for the first element greater than the pivot
			while (collection.compare(i, left) <= 0 && i <= right && k > i) { i++; }
			
			// from the right, look for the first element not greater than the pivot
			while (collection.compare(k, left) > 0 && k >= left && k >= i) { k--; }

			// if the left index is still smaller than the right index, swap the corresponding elements
			if (k > i) { collection.swap(i, k); }
		}
		
		// after the indices have crossed, swap the last element in the left partition with the pivot 
		collection.swap (left, k);
		return k;
	}

}
