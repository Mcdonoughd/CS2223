package algs.days.day12;

/** 
 * This was the problem posed in class today.
 * 
 * Make a small change. All nodes other than the first MUST be full (at K). The first node
 * is allowed to have fewer than K items. Avoids storing the number of items in each node, 
 * since the NK_problem outer class can handle this.

 */
public class NK {
	
	final int K;
	
	// number of array inspections. Reset as needed.
	public long numInspects;
	
	// number of values shifted. Reset as needed.
	public long numExchanges;
	
	public NK (int k) {
		this.K = k;
		numInFirst = 0;
		first = null;
		N = 0;
	}
	
	Node first;
	int  numInFirst;   // may be 0 up to k-1.
	int  N;
	
	public boolean contains(int val) {
		if (first == null) { return false; }
		
		// look in head node specially. If found, great!
		int inFirst = first.rank(val, numInFirst-1);
		if (inFirst >= 0) {
			return true; 
		}
		
		// must check each one
		Node node = first.next;
		while (node != null) {
			int idx = node.rank(val, K-1);
			if (idx >= 0) {
				return true; 
			}
			
			node = node.next;
		}
		
		// didn't find it.
		return false;
	}
	
	/**
	 * Remove by finding where it is. Then swap (arbitrarily) with one of the K elements
	 * from the first node.
	 * 
	 * @param val
	 */
	public boolean remove(int val) {
		if (first == null) { return false; }
		
		// look in head node specially. If found, great!
		int inFirst = first.rank(val, numInFirst-1);
		if (inFirst >= 0) {
			if (numInFirst == 1) {
				first = first.next;    // done! First node is now emptied.
				if (first != null) { numInFirst = K; } else { numInFirst = 0; } // DEBUG        
				N--;                   // debug: address N
				return true;
			}
			
			// copy down, as long as not the last one in this array.
			if (inFirst < numInFirst) {
				first.moveLeft(inFirst+1, numInFirst-1);
				//System.arraycopy(first.array, inFirst+1, first.array, inFirst, numInFirst-inFirst-1);  // debug fixed.
			}
			first.array[--numInFirst] = 0;  // clear out (debug).
			
			// if have emptied out the values from the first node, then adjust first pointer.
			if (numInFirst == 0) {
				first = first.next;
			}
			N--;  // debug: address N
			return true;
		}
		
		// must see if in any node
		Node node = first.next;
		while (node != null) {
			int inNode = node.rank(val, K-1);
			if (inNode < 0) {
				node = node.next;
			} else {
				// found it! 
				// first remove from node
				node.moveLeft(inNode+1, K-1);
				//System.arraycopy(node.array, inNode+1, node.array, inNode, K-inNode-1);   // one less (detect in debugger).
				
				// Replace (arbitrarily) with a value from first node. now find where a value from first can be inserted, and adjust
				int replace = first.array[--numInFirst];
				first.array[numInFirst] = 0; /// debug remove
				if (numInFirst == 0) {
					first = first.next;
					numInFirst = K; // debug: reset count.
				}
				
				// now see where this value should be inserted into node
				inNode = node.rank(replace, K-2);
				inNode = -inNode - 1;
				if (inNode != K-1) {
					node.moveRight(inNode,K-1);
				}
				//System.arraycopy(node.array, inNode, node.array, inNode+1, K-inNode-1);   // debug: had numInFirst, not K
				
				// finally place it and we are done
				node.array[inNode] = replace;
				N--;  // address N
				return true;
			}
		}
		
		// got to end and not here?
		return false;
	}
	
	/**
	 * Add value into collection (duplicates allowed).
	 * 
	 * @param val
	 * @return
	 */
	public void add(int val) {
		N++;   // always increment count
		
		if (first == null) {
			first = new Node();
			first.array[0] = val;
			numInFirst = 1;
			return;
		}
		
		// still can add to the first
		if (numInFirst < K) {
			int idx = first.rank(val, numInFirst-1);
			if (idx < 0) {
				// To retrieve insertIndex, simply negate and add 1 to the return of rank.
				idx = -idx - 1;
			} else {
				// this value already exists. Can insert there too!
			}
			if (numInFirst > idx) { // must have something to copy: detected during debug.
				//System.arraycopy(first.array, idx, first.array, idx+1, numInFirst-idx);
				first.moveRight(idx, numInFirst);
			}
			numInFirst++;
			first.array[idx] = val;
			return;
		} 
		
		// first one is full? Create a new one and link in
		Node newFirst = new Node();
		newFirst.array[0] = val;
		numInFirst = 1;
		newFirst.next = first;
		first = newFirst;
	}
	

	public int size() { return N; }
	public boolean isEmpty() { return N == 0; }
	
	class Node {
		int[] array;
		Node next;
		
		Node() {
			array = new int[K];
		}
	
		/**
		 * Search in array[0..high] inclusively.
		 * 
		 * This allows us to search up to a smaller value for first node, and then for all
		 * values if full node.
		 * 
		 * Modified rank from day 1.
		 * 
		 * if found, returns the index location. If not found, return 
		 * -insertIndex-1. insertIndex is defined as the point at which
		 * val WOULD BE INSERTED into the array, or perhaps better as "the
		 * index of the first element greater than val).
		 * 
		 * To retrieve this value, simply negate and add 1 to the return of rank.
		 */
		int rank (int val, int high) {
			int low = 0;

			while (low <= high) {
				int mid = (low+high)/2;

				int rc = array[mid] - val;
				numInspects++;
				if (rc < 0) {
					low = mid+1;
				} else if (rc > 0) {
					high = mid-1;
				} else {
					return mid;
				}
			}
			
			return -(low + 1);  // val not found. This is where it would be inserted
		} 
		
		/**
		 * Move a[left,right] into a[left-1,right-1], obliterating the former value in a[left-1]
		 */
		void moveLeft(int left, int right) {
			while (left <= right) {
				array[left-1] = array[left];
				numExchanges++;
				left++;
			}
		}
		
		/**
		 * Move a[left,right] into a[left,right+1].
		 * 
		 * Note: this will always be called where right < array.length.
		 * @param inNode
		 * @param i
		 */
		public void moveRight(int left, int right) {
			while (right > left) {
				array[right] = array[right-1];
				numExchanges++;
				right--;
			}
		}

	}

}
