package algs.experiment.rank;

import algs.experiment.Indexable;

/**
 * Naive solution. 
 */
public class MinMaxFinder {
	
	/**
	 * Return the location of the min and max in the collection.
	 * 
	 * @param col         Collection containing the items.
	 * @return            index positions in collection of largest and smallest items
	 * @throws Exception
	 */
	public static int[] minMax(Indexable<?> col) throws Exception {
		return minMax(col, 0, col.size());
	}
	
	/**
	 * Return the min and max in the collection which is expected to have at least two elements. 
	 * 
	 * @param col         Collection containing the items
	 * @param left        left edge of range (inclusive)
	 * @param right       right edge of range (exclusive)
	 * @return            index positions in collection of largest and smallest items
	 * @throws Exception
	 */
	public static int[] minMax(Indexable<?> col, int left, int right) throws Exception {
		int i = left+2;
		int smallest = left;
		int largest = left+1;

		if ((right-left) % 2 == 1) {
			largest = left; // assume left-most is the smallest and largest
			i = left+1;
		} else if (col.compare(left, left+1) > 0) {
			// put in proper order.
			largest = left;
			smallest = left+1;
		}
		
		// now check each and compare only to the one it could be compared with.
		while (i < right) {
			if (col.compare(i, i+1) > 0) {
				if (col.compare(i, largest) > 0) {
					largest = i;
				}
				if (col.compare(i+1, smallest) < 0) {
					smallest = i+1;
				}
			} else {
				if (col.compare(i+1, largest) > 0) {
					largest = i+1;
				}
				if (col.compare(i, smallest) < 0) {
					smallest = i;
				}
			}
			
			i += 2;
		}
		
		return new int[] { largest, smallest };
	}
	
	/** Demonstrate that it works. */
	public static void main(String[] args) throws Exception {
		
		for (int n = 4; n <= 1024; n*= 2) {
			Selection collection = new Selection(n);
			
			int[] minMax = minMax (collection);
				
			collection.assertKthRank(n-1, collection.get(minMax[0]));
			collection.assertKthRank(0, collection.get(minMax[1]));
			
			String ticket = collection.getValidation();
			System.out.println(n + "\t" + ticket);
		}
	}
}
