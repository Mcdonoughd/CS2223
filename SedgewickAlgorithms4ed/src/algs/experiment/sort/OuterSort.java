package algs.experiment.sort;

import algs.experiment.rank.MinMaxFinder;

/**
 * A sorting method, which I call "OuterSort", that sorts an array by (a) locating its smallest and largest
 * elements; (b) swapping these into place at the left position and right position; (c) then recursively
 * sorting the inner array with two fewer elements.
 * 
 * This will be one of the sorting algorithms investigated by the students, not because it is a good sorting
 * algorithm but because students can analyze its behavior mathematically. 
 */
public class OuterSort {
	public static void main(String[] args) throws Exception {
		
		for (int n = 4; n <= 512; n*= 2) {
			Sorter collection = new Sorter(n);
			outersort(collection, 0, n-1);
			
			collection.assertSorted();
			String ticket = collection.getValidation();
			System.out.println(ticket);
		}
	}

	/**
	 * 
	 * @param collection
	 * @param left       inclusive in the range to be sorted
	 * @param right      inclusive in the range to be sorted
	 * @throws Exception
	 */
	static void outersort(Sorter collection, int left, int right) throws Exception {
		// base case: single element or no element...
		if (left >= right) {
			return;
		}
		
		int minmax[] = MinMaxFinder.minMax(collection, left, right+1);
		
		// all the same value! no need to continue! 
		// if (minmax[0] == minmax[1]) { return; }

		if (minmax[0] == right && minmax[1] == left) {
			// do nothing. already in place
		} else if (minmax[0] == left && minmax[1] == right) {
			// one swap 
			collection.swap(left, right);
		} else if (minmax[0] == left) {
			// largest is in the leftmost spot.
			Comparable<Sorter.Item> largest = collection.get(left);
			collection.set(left, collection.get(minmax[1]));
			collection.set(minmax[1], collection.get(right));
			collection.set(right, largest);
		} else if (minmax[1] == right) {
			// smallest is in the rightmost spot.
			Comparable<Sorter.Item> smallest = collection.get(right);
			collection.set(right, collection.get(minmax[0]));
			collection.set(minmax[0], collection.get(left));
			collection.set(left, smallest);
		} else {
			collection.swap(left, minmax[1]);
			collection.swap(right, minmax[0]);
		}
		
		outersort(collection, left+1, right-1);
	}
}
