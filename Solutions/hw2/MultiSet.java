package algs.solutions.hw2;

/**
 * This data type offers Bag-like behavior with the added constraint that it tries
 * to minimize space by keeping track of the count of each item in the bag.
 *
 * Find the definition of MultiSet on Wikipedia (https://en.wikipedia.org/wiki/Multiset)
 *
 * In all of the performance specifications, N refers to the total number of items 
 * in the MultiSet while U refers to the total number of unique items.
 * 
 * Note that you will only have U nodes, one for each distinct item, and so U <= N; however, 
 * you can't know in advance HOW many duplicates will exist, so in the worst case, 
 * some computations will still depend upon N. 
 * 
 * Heineman Solution.
 * 
 * @param <Item>
 */
public class MultiSet<Item extends Comparable<Item>> {

	int count;
	Node first = null;
	
	/** You must use this Node class as part of a LinkedList to store the MultiSet items. Do not modify this class. */
	class Node {
		private Item   item;
		private int    count;
		private Node   next;
	}

	/** Create an empty MultiSet. */
	public MultiSet () { 
		count = 0;
	}

	/**
	 * Initialize the MultiSet to contain the unique elements found in the initial list.
	 * 
	 * Performance is allowed to be dependent on N*N, where N is the number of total items in initial.
	 */
	public MultiSet(Item [] initial) { 
		for (Item i : initial) {
			add(i);
		}
	}

	/** 
	 * Return the number of items in the MultiSet.
	 * 
	 * Performance must be independent of the number of items in the MultiSet, or ~ 1.
	 */
	public int size() { return count; }

	/** 
	 * Determines equality with another MultiSet objects.
	 * 
	 * Assume U1=number of unique items in self while U2=number of unique items in other.
	 * 
	 * Performance must be linearly dependent upon max(U1,U2)
	 */
	public boolean identical (MultiSet<Item> other) { 
		if (other == null) { return false; }
		if (other.count != count) { return false; }

		// check links
		Node me = first;
		Node them = other.first;

		while (me != null) {
			if (me.item.compareTo(them.item) != 0) {
				return false;
			}

			if (me.count != them.count) { 
				return false;
			}
			
			me = me.next;
			them = them.next;
		}

		// must be equal since we have exhausted elements!
		return true;
	}
	
	/** 
	 * Return an array that contains the items from the MultiSet.
	 * 
	 * Performance must be linearly dependent on N.
	 */
	public Item[] toArray() {
		Item[] items = (Item[]) new Comparable[count];

		Node t = first;
		int idx = 0;
		while (t != null) {
			for (int i = 0; i < t.count; i++) {
				items[idx++] = t.item;
			}
			t = t.next;
		}

		return items;
	}
	
	/** 
	 * Add an item to the MultiSet.
	 * 
	 * Performance must be no worse than linearly dependent on N.
	 */
	public boolean add(Item it) {
		if (first == null) {
			first = new Node();
			first.item = it;
			first.count = 1;
			count = 1;
			return true;
		}

		Node prev = null;
		Node node = first;

		while (node != null) {
			int rc = node.item.compareTo(it);

			// we have gone past and can insert before
			if (rc > 0) {
				if (prev != null) {
					prev.next = new Node();
					prev.next.item = it;
					prev.next.count = 1;
					prev.next.next = node;
				} else {
					first = new Node();
					first.item = it;
					first.count = 1;
					first.next = node;
				}

				count++;
				return true;
			} else if (rc == 0) {
				node.count++;  // add one to our running count
				count++;
				return true;
			} else {
				// advance
			}

			prev = node;
			node = node.next;
		}

		// if we get here, then must append. Luckily prev points to last one
		prev.next = new Node();
		prev.next.item = it;
		prev.next.count = 1;
		count++;
		return true;
	}
	
	/** 
	 * Remove an item from the MultiSet; return false if not in the MultiSet to
	 * begin with, otherwise returns true on success.
	 *  
	 * Performance must be no worse than linearly dependent on N.
	 */
	public boolean remove (Item it) {
		if (count == 0) { return false; }

		int rc = first.item.compareTo(it);
		if (rc == 0) {
			count--;
			if (first.count > 1) {
				first.count--;
				return true;
			}
			
			// otherwise can throw away.
			first = first.next;
			return true;
		}

		// now remove at first glance.
		Node prev = first;
		Node next = first.next;
		while (next != null) {
			rc = next.item.compareTo(it);

			// not present
			if (rc > 0) { return false; }
			else if (rc == 0) {
				count--;
				// cleanly cut out
				if (next.count > 1) {
					next.count--;
					return true;
				}
				prev.next = next.next;
				return true;
			}

			prev = next;
			next = next.next;
		}

		// none left.
		return false;
	}
	
	/** 
	 * Returns the number of times item appears in the MultiSet.
	 * 
	 * If returns 0, then the item is not contained within this MultiSet.
	 * 
	 * Performance must be no worse than linearly dependent on U.
	 */
	public int multiplicity (Item it) {
		if (first == null) { return 0; }

		Node t = first;
		while (t != null) {
			int rc = t.item.compareTo(it);
			if (rc == 0) { 
				return t.count; 
			} else if (rc > 0) {
				// can't be in list since item in list is greater than it
				return 0; 
			}

			// keep checking.
			t = t.next;
		}

		// run off edge. Not a chance now...
		return 0;
	}

	/** 
	 * Determine whether this MultiSet includes other MultiSet.
	 * 
	 * A MultiSet A includes a MultiSet B when: for all elements x in B with multiplicity mB(x), the
	 * multiplicity mA(x) in A is >= mB(x).
	 * 
	 * In degenerate case:
	 *   1. If this is empty, false is always returned.
	 *   2. If this is non-empty and other is empty, true is returned.
	 * 
	 * Performance must be linearly dependent on UA + UO where UA is the number of unique items in A
	 * and UO is the number of unique items in other.
	 */
	public boolean includes(MultiSet<Item> other) {
		if (first == null) { return false; } 
		if (other.first == null) { return true; }
		
		Node t = first;
		Node u = other.first;
		while (u != null) {
			// advance T while its value is < 0
			while (t != null && u.item.compareTo(t.item) > 0) {
				t = t.next;
			}
			
			if (t == null) { return false; } // DONE and not includes.
			
			if (u.item.compareTo(t.item) < 0) {
				return false; // missing this value
			}
			
			// must be equal. 
			// check multiplicity: if too small, non inclusive, otherwise keep going.
			// defect at double advancing t.next: test case discovered (thanks, Nick).)
			if (t.count < u.count) { return false; }
			u = u.next;
			t = t.next;
		}

		// run off edge so we are done with other.
		return true;
	}
	
	/** 
	 * Return a MultiSet which represents intersection with existing MultiSet.
	 * 
	 * Performance must be linearly dependent on the number of items in both MultiSet 
	 * objects, or in other words ~ M + MO where M is the number of items in this and MO
	 * is the number of items in other.
	 * 
	 * This is challenging.
	 */
	public MultiSet<Item> intersects(MultiSet<Item> other) {
		MultiSet<Item> retval = new MultiSet<Item>();

		Node lastInRetVal = null;

		Node i = first;
		Node j = other.first;

		// much like MergeSort, work through both, appending value
		// once you know that it it shared by both nodes. Note that 
		// we can't use ADD since that would result in slowdown
		while (i != null && j != null) {
			// if either one is exhausted, then we are done.
			int rc = i.item.compareTo(j.item);
			if (rc == 0) {
				Node newOne = new Node();
				newOne.item = i.item;
				int numToAdd = Math.min(i.count, j.count);
				newOne.count = numToAdd;
				retval.count += numToAdd;

				if (lastInRetVal == null) {
					// take care to create node properly AND count.
					retval.first = newOne;
					lastInRetVal = retval.first;
				} else {
					lastInRetVal.next = newOne;
					lastInRetVal = lastInRetVal.next;
				}

				// advance both!
				i = i.next;
				j = j.next;
			} else if (rc < 0) {
				i = i.next;   // advance i
			} else {
				j = j.next;   // advance j
			}
		}

		return retval;

	}

	/** 
	 * Return a MultiSet which represents union with existing MultiSet.
	 * 
	 * Performance must be linearly dependent on the number of items in both MultiSet 
	 * objects, or in other words ~ M + N where M is the number of items in other and N
	 * is the number of items in this MultiSet.
	 * 
	 * This is challenging.
	 */
	public MultiSet<Item> union(MultiSet<Item> other) {
		MultiSet<Item> retval = new MultiSet<Item>();

		Node lastInRetVal = null;

		Node i = first;
		Node j = other.first;

		// much like MergeSort, work through both, appending value
		// once you know that it it shared by both nodes. Note that 
		// we can't use ADD since that would result in slowdown
		Node newOne; 
		while (i != null && j != null) {
			// every time through we will be 
			newOne = new Node();
			
			// 
			int rc = i.item.compareTo(j.item);
			if (rc == 0) {
				newOne.item = i.item;   // arbitrarily grab from i
				newOne.count = Math.max(i.count, j.count);
				
				// advance both!
				i = i.next;
				j = j.next;
			} else if (rc < 0) {
				newOne.item = i.item;
				newOne.count = i.count;
				i = i.next;   // advance i
			} else {
				newOne.item = j.item;
				newOne.count = j.count;
				j = j.next;   // advance j
			}
			
			if (lastInRetVal == null) {
				// take care to create node properly AND count.
				retval.first = newOne;
				lastInRetVal = retval.first;
			} else {
				lastInRetVal.next = newOne;
				lastInRetVal = lastInRetVal.next;
			}
			retval.count += newOne.count;
		}
		
		// add those that aren't exhausted
		if (i == null && j == null) { return retval; }
		
		// one of these still has items. Start with those.
		Node n = i;
		if (n == null) { n = j; }
		
		while (n != null) {
			newOne = new Node();
			newOne.item = n.item;
			newOne.count = n.count;
			
			if (lastInRetVal == null) {
				// take care to create node properly AND count.
				retval.first = newOne;
				lastInRetVal = retval.first;
			} else {
				lastInRetVal.next = newOne;
				lastInRetVal = lastInRetVal.next;
			}
			retval.count += newOne.count;
			
			n = n.next;
		}

		return retval;
	}
}
