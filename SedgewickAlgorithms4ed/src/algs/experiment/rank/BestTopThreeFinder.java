package algs.experiment.rank;

/**
 * Recursive solution. 
 *
 * Extend BestTopTwo with logic. Do you see a pattern?
 */
public class BestTopThreeFinder {
	
	/**
	 * Return the top three largest items in the collection. 
	 * 
	 * @param col         Collection containing the items.
	 * @return            top three largest items.
	 * @throws Exception
	 */
	public static Selection.Item[] topThree(Selection col) throws Exception {
		int size = col.size();
		Selection.Item items[] = new Selection.Item[size];
		for (int i = 0; i < size; i++) {
			items[i] = (Selection.Item) col.get(i);
		}
		
		return topThree(items, col.smallest());
	}
	
	/**
	 * Given an array containing n>1 elements, return the three largest items.
	 * 
	 * 0th item is largest and 1st is second largest and 2nd is third largest.
	 * 
	 * @param smallpad    smallest possible value, which is to be used to pad odd-length arrays.
	 * @throws Exception 
	 */
	public static Selection.Item[] topThree (Selection.Item items[], Selection.Item smallpad) throws Exception {
		// Base Case
		int n = items.length;
		if (n <= 3) {
			Selection.Item[] ret = new Selection.Item[3];
			
			if (items[0].compareTo(items[1]) > 0) {
				ret[0] = items[0];
				ret[1] = items[1];
			} else {
				ret[0] = items[1];
				ret[1] = items[0];
			}
			
			if (n == 2) { 
				ret[2] = smallpad;
				return ret;
			}
			
			// where does third fit in? (no more than) two comparisons determines all
			if (ret[1].compareTo(items[2]) > 0) {
				ret[2] = items[2];
			} else if (ret[0].compareTo(items[2]) > 0) {
				ret[2] = ret[1];
				ret[1] = items[2];
			} else {
				ret[2] = ret[1];
				ret[1] = ret[0];
				ret[0] = items[2];
			}

			return ret;
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
		
		// recursively determine largest THREE of the largest, called A, B and C, where A > B > C.
		// Each of these is larger than respective a, b, c in the smaller array. Know that A is largest, 
		// but must find largest two that remain.
		// in smaller that HAD ORIGINALLY BEEN COMPARED WITH LARGEST.
		// Largest[0] is largest and you must determine how Largest[1] and this smaller one compare.
		Selection.Item [] largest = topThree(larger, smallpad);
		
		// Need to perform this logic to find location of original largest elements. 
		// This loop can be avoided with a better data structure.
		Selection.Item a = smallpad, A = smallpad;
		Selection.Item b = smallpad, B = smallpad;
		Selection.Item               C = smallpad;
		for (int i = 0; i < n/2 + padding; i++) {
			if (largest[0] == larger[i]) { a = smaller[i]; A = larger[i]; }
			if (largest[1] == larger[i]) { b = smaller[i]; B = larger[i]; }
			if (largest[2] == larger[i]) {                 C = larger[i]; }
		}
		
		// Know that A > B > C.
		//    1. if a > C, then 
		//         1a. if B > a then we have A>B>a
		//         1b. else we have A>a>B
		//    2. else if b > C, then we have A>B>b (since a < C)
		//    3. otherwise A>B>C
		//
		// so at most need just two comparisons.
		if (a.compareTo(C) > 0) {
			if (B.compareTo(a) > 0) {
				return new Selection.Item[] { A, B, a };
			} else {
				return new Selection.Item[] { A, a, B };
			}
		} else if (b.compareTo(C) > 0) {
			return new Selection.Item[] { A, B, b };
		} else {
			return new Selection.Item[] { A, B , C };
		}		
	}
	
	/** Demonstrate that it works. */
	public static void main(String[] args) throws Exception {
		
		for (int n = 2; n <= 4096; n *= 2) {
			Selection collection = new Selection(n);
					
			Selection.Item[] topTwo = topThree (collection);
				
			collection.assertKthRank(n-1, topTwo[0]);
			collection.assertKthRank(n-2, topTwo[1]);
			
			String ticket = collection.getValidation();
			System.out.println(n + "\t" + ticket);
		}
	}
}
