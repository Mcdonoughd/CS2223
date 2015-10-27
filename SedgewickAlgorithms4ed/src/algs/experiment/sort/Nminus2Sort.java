package algs.experiment.sort;

public class Nminus2Sort {
	public static void main(String[] args) throws Exception {
		
		int n = 128;
		Sorter collection = new Sorter(n);
		collection.orderDescending();
		nminus2(collection, 0, n-1);
		
		collection.assertSorted();
		String ticket = collection.getValidation();
		System.out.println(ticket);
	}

	static void nminus2(Sorter collection, int left, int right) throws Exception {
		if (right - left < 1) { 
			return; 
		}
		
		// find min,max in no worse than 2(n-2)+1
		int largest = left;
		int smallest = left+1;
				
		if (collection.compare(largest, smallest) < 0) {
			int tmp = largest;
			largest = smallest;
			smallest = tmp;
		}
		
		// largest is greater than or equal to smaller
		// find next, swapping as needed.
		
		for (int i = left+2; i <= right; i++) {
			if (collection.compare(i, largest) > 0) {
				largest = i;
			} else if (collection.compare(i, smallest) < 0) {
				smallest = i;
			}
		}
		if (left != smallest) { 
			collection.swap(left, smallest); 
		}
		if (smallest != right) { // have to prevent double swapping same elements.
			if (right != largest) { 
				collection.swap(right, largest);
			}
		}
		nminus2(collection, left+1, right-1);
	}
}
