package algs.solutions.hw2;

/**
 * This data type offers Bag-like behavior with the added constraint that all elements
 * stored are guaranteed to be unique within the bag, based on the equals() method.
 *
 * In all of the performance specifications, N refers to the number of items in the 
 * UniqueBag.
 * 
 * Once you complete this implementation, you will need to provide empirical evidence 
 * to support the performance specifications of each method.
 * 
 * CHANGE LOG:
 * 1. Added logic that each Item must extend Comparable<Item>. This means that you can
 *    fully compare items with each other
 *    
 * 2. Added default constructor so you can create an empty bag more easily.
 * 
 * @param <Item>
 */
public class UniqueBag<Item extends Comparable<Item>> {

	Node first = null;
	int count = 0;

	/** You must use this Node class as part of a LinkedList to store the UniqueBag items. */
	class Node {
		private Item   item;
		private Node   next;
	}

	/** Default constructor to create an empty initial bag. */
	public UniqueBag() {

	}

	/**
	 * Initialize the bag to contain the unique elements found in the initial list.
	 * 
	 * Performance must be dependent of the number of items in initial, or ~ N.
	 */
	public UniqueBag(Item[] initial) {
		for (Item i : initial) {
			add(i);
		}
	}

	/** 
	 * Return the number of items in the UniqueBag.
	 * 
	 * Performance must be independent of the number of items in the UniqueBag, or ~ 1.
	 */
	public int size() {
		return count;
	}

	/** 
	 * Determines equality with another UniqueBag objects.
	 * 
	 * Performance must be linearly dependent on the number of items in the UniqueBag, or ~ N.
	 */
	public boolean identical (UniqueBag<Item> other) {
		if (other == null) { return false; }
		if (other.count != count) { return false; }

		// check links
		Node me = first;
		Node them = other.first;

		while (me != null) {
			if (me.item.compareTo(them.item) != 0) {
				return false;
			}

			me = me.next;
			them = them.next;
		}

		// must be equal since we have exhausted elements!
		return true;
	}

	/** 
	 * Return an array that contains the items from the UniqueBag.
	 * 
	 * Performance must be linearly dependent on the number of items in the UniqueBag, or ~ N.
	 */
	public Item[] toArray() {
		Item[] items = (Item[]) new Comparable[count];

		Node t = first;
		int idx = 0;
		while (t != null) {
			items[idx++] = t.item;
			t = t.next;
		}

		return items;
	}

	/** 
	 * Add an item to the UniqueBag; return false if already contained.
	 * 
	 * Performance can be linearly dependent on the number of items in the UniqueBag, or ~ N.
	 */
	public boolean add(Item it){
		if (first == null) {
			first = new Node();
			first.item = it;
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
					prev.next.next = node;
				} else {
					first = new Node();
					first.item = it;
					first.next = node;
				}

				count++;
				return true;
			} else if (rc == 0) {
				return false;
			} else {
				// advance
			}

			prev = node;
			node = node.next;
		}

		// if we get here, then must append. Luckily prev points to last one
		prev.next = new Node();
		prev.next.item = it;
		count++;
		return true;
	}

	/** 
	 * Determine whether the item is contained by the UniqueBag.
	 * 
	 * Performance must be linearly dependent on the number of items in the UniqueBag, or ~ N.
	 */
	public boolean contains(Item it) {
		if (first == null) { return false; }

		Node t = first;
		int idx = 0;
		while (t != null) {
			int rc = t.item.compareTo(it);
			if (rc == 0) { 
				return true; 
			} else if (rc > 0) {
				// can't be in list since item in list is greater than it
				return false; 
			}

			// keep checking.
			t = t.next;
		}

		// run off edge. Not a chance now...
		return false;
	}

	/** 
	 * Remove an item from the UniqueBag; return false if not in the list to
	 * begin with, otherwise returns true on success.
	 *  
	 * Performance can be linearly dependent on the number of items in the UniqueBag, or ~ N.
	 */
	public boolean remove (Item it){
		if (count == 0) { return false; }

		int rc = first.item.compareTo(it);
		if (rc == 0) {
			first = first.next;
			count--;
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
				// cleanly cut out
				prev.next = next.next;
				count--;
				return true;
			}

			prev = next;
			next = next.next;
		}

		// none left.
		return false;
	}

	/** 
	 * Return a UniqueBag which represents intersection with existing UniqueBag.
	 * 
	 * Performance must be linearly dependent on the number of items in both UniqueBag 
	 * objects, or in other words ~ M + N where M is the number of items in other and N
	 * is the number of items in this UniqueBag.
	 */
	public UniqueBag<Item> intersects(UniqueBag<Item> other) {

		UniqueBag<Item> retval = new UniqueBag<Item>();

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
				retval.count++;

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
	 * Return a UniqueBag which represents union with existing UniqueBag.
	 * 
	 * Performance must be linearly dependent on the number of items in both UniqueBag 
	 * objects, or in other words ~ M + N where M is the number of items in other and N
	 * is the number of items in this UniqueBag.
	 */
	public UniqueBag<Item> union(UniqueBag<Item> other) {
		UniqueBag<Item> retval = new UniqueBag<Item>();

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
				
				// advance both!
				i = i.next;
				j = j.next;
			} else if (rc < 0) {
				newOne.item = i.item;
				i = i.next;   // advance i
			} else {
				newOne.item = j.item;
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
			retval.count++;
		}
		
		// add those that aren't exhausted
		if (i == null && j == null) { return retval; }
		
		// one of these still has items. Start with those.
		Node n = i;
		if (n == null) { n = j; }
		
		while (n != null) {
			newOne = new Node();
			newOne.item = n.item;
			
			if (lastInRetVal == null) {
				// take care to create node properly AND count.
				retval.first = newOne;
				lastInRetVal = retval.first;
			} else {
				lastInRetVal.next = newOne;
				lastInRetVal = lastInRetVal.next;
			}
			retval.count++;
			
			n = n.next;
		}

		return retval;

	}


}
