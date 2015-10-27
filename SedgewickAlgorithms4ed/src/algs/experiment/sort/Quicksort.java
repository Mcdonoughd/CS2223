package algs.experiment.sort;


/**
 * This is the Quicksort implementation. Yes it is as short as it looks (once you have an external partitioning
 * algorithm.
 * 
 *  This will be one of the sorting algorithms investigated by the students. 
 */
public class Quicksort {
	
	public static void main(String[] args) throws Exception {
		
		int n = 1000;
		Sorter collection = new Sorter(n);
		ISelectPivot pivot = new RightMost(); 
		IPartition partition = new SlidingWalls();
		quicksort(collection, pivot, partition, 0, n-1);
		
		collection.assertSorted();
		String ticket = collection.getValidation();
		System.out.println(ticket);
	}

	/**
	 * Use the recursive Quicksort algorithm to sort the collection[left,right] inclusively on both ends.
	 * Use the given ISelectPivot object to determine the pivot value, which is then passed to the partitioning
	 * scheme as defined by an IPartition object.
	 */
	static void quicksort(Sorter collection, ISelectPivot pivot, IPartition partition, int left, int right) throws Exception {
		if (left < right) {
			int pi = pivot.select(collection, left, right);
			pi = partition.partition (collection, left, right, pi);
			quicksort(collection, pivot, partition, left, pi-1);
			quicksort(collection, pivot, partition, pi+1, right);
		}
	}
}
