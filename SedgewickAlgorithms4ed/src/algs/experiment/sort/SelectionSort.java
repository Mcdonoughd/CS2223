package algs.experiment.sort;

public class SelectionSort {
	
	public static void main(String[] args) throws Exception {
		
		int n = 1000;
		Sorter collection = new Sorter(n);
		
		for (int i = n-1; i >= 1; i--) { 
			int maxPos = selectMax(collection, 0, i);
			if (maxPos != i) {
				collection.swap(i, maxPos);
			}
		}
		
		collection.assertSorted();
		String ticket = collection.getValidation();
		System.out.println(ticket);
	}

	/** select max in collection[left,right]. */
	private static int selectMax(Sorter collection, int left, int right) throws Exception {
		int maxPos = left;
		int i = left;
		while (++i <= right) {
			if (collection.compare(i, maxPos) > 0) {
				maxPos = i;
			}
		}
		
		return maxPos;
	}
}
