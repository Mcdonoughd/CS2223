package algs.experiment.rank;

/**
 * Recursive solution. 
 *
 * Note that this was the result of two hours' work. Most mis-steps occurred because I hastened to the
 * implementation before working out "all of the details." This code contains the "clean version" of what
 * I was trying to develop.
 * 
 * There are a lot of details to fill in when taking an "algorithm sketch" from paper to code. It helps 
 * to be a really good programmer!
 */
public class BestTopTwoFinderExtraSpace {
	
	/**
	 * Return the top two largest items in the collection. 
	 * 
	 * @param col         Collection containing the items.
	 * @return            top two largest items.
	 * @throws Exception
	 */
	public static Selection.Item[] topTwo(Selection col) throws Exception {
		int size = col.size();
		Selection.Item items[] = new Selection.Item[size];
		for (int i = 0; i < size; i++) {
			items[i] = (Selection.Item) col.get(i);
		}
		
		return topTwo(items, col.smallest());
	}
	
	/**
	 * Given an array containing n>1 elements, return the two largest items.
	 * 
	 * 0th item is largest and 1st is second largest.
	 * 
	 * @param smallpad    smallest possible value, which is to be used to pad odd-length arrays.
	 * @throws Exception 
	 */
	public static Selection.Item[] topTwo (Selection.Item items[], Selection.Item smallpad) throws Exception {
		// Base Case
		int n = items.length;
		if (n == 2) {
			if (items[0].compareTo(items[1]) > 0) {
				return new Selection.Item[] { items[0], items[1] };
			} else {
				return new Selection.Item[] { items[1], items[0] };
			}
		}
		
		// Recursive case
		// separate into lower and upper. If odd number of elements, 
		// pad smaller with smallpad
		int padding = 0;
		if (n%2 == 1) { padding = 1; }
		Selection.Item[] smaller = new Selection.Item[n/2+padding];
		Selection.Item[] larger = new Selection.Item[n/2+padding];
		
		// handle odd case specially
		if (padding == 1) { larger[n/2] = items[n-1]; smaller[n/2] = smallpad; }
		
		// (n/2) comparisons.
		for (int i = 0; i < n-padding; i+=2) {
			if (items[i].compareTo(items[i+1]) > 0) {
				larger[i/2] = items[i];
				smaller[i/2] = items[i+1];
			} else {
				smaller[i/2] = items[i];
				larger[i/2] = items[i+1];
			}
		}
		
		// recursively determine largest TWO of the largest, A and B. Then must compare B with a, the 
		// element that was smaller than A when originally compared.
		Selection.Item [] largest = topTwo(larger, smallpad);
		
		// Need to perform this logic to find location of original largest elements. 
		// This loop can be avoided with a better data structure.
		Selection.Item a = smallpad, A = smallpad;
		Selection.Item               B = smallpad;
		for (int i = 0; i < n/2 + padding; i++) {
			if (largest[0] == larger[i]) { a = smaller[i]; A = larger[i]; }
			if (largest[1] == larger[i]) {                 B = larger[i]; }
		}
		
		// if a > B then we have A>a else we have A>B
		//
		// only need one comparison to find ordering of three remaining elements.
		if (a.compareTo(B) > 0) {
			return new Selection.Item[] { A, a };
		} else {
			return new Selection.Item[] { A, B }; 
		}
	}
	
	/** Demonstrate it works. */
	public static void main(String[] args) throws Exception {
		
		for (int n = 4; n <= 1024; n*= 2) {
			Selection collection = new Selection(n);
					
			Selection.Item[] topTwo = topTwo (collection);
				
			collection.assertKthRank(n-1, topTwo[0]);
			collection.assertKthRank(n-2, topTwo[1]);
			
			String ticket = collection.getValidation();
			System.out.println(n + "\t" + ticket);
		}
	}
}
