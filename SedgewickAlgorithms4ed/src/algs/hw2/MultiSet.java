package algs.hw2;

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
 * Once you complete this implementation, you will need to provide empirical evidence 
 * to support the performance specifications of each method.
 * 
 * @param <Item>
 */
public class MultiSet<Item extends Comparable<Item>> {

	
	/** You must use this Node class as part of a LinkedList to store the MultiSet items. Do not modify this class. */
	class Node {
		private Item   item;
		private int    count;
		private Node   next;
	}

	/** Create an empty MultiSet. */
	public MultiSet () { 
		
	}

	/**
	 * Initialize the MultiSet to contain the unique elements found in the initial list.
	 * 
	 * Performance is allowed to be dependent on N*N, where N is the number of total items in initial.
	 */
	public MultiSet(Item [] initial) { 
		
	}

	/** 
	 * Return the number of items in the MultiSet.
	 * 
	 * Performance must be independent of the number of items in the MultiSet, or ~ 1.
	 */
	public int size() {
		// student fills in
		return -1;
	}

	/** 
	 * Determines equality with another MultiSet objects.
	 * 
	 * Assume U=number of unique items in self while UO=number of unique items in other.
	 * 
	 * Performance must be linearly dependent upon min(U1,U2)
	 */
	public boolean identical (MultiSet<Item> other) { 
		// student fills in 
		return false;
	}
	
	/** 
	 * Return an array that contains the items from the MultiSet.
	 * 
	 * Performance must be linearly dependent on N.
	 */
	public Item[] toArray() {
		// student fills in
		return null;
	}
	
	/** 
	 * Add an item to the MultiSet.
	 * 
	 * Performance must be no worse than linearly dependent on N.
	 */
	public boolean add(Item it) {
		// student fills in
		return false;
	}
	
	/** 
	 * Remove an item from the MultiSet; return false if not in the MultiSet to
	 * begin with, otherwise returns true on success.
	 *  
	 * Performance must be no worse than linearly dependent on N.
	 */
	public boolean remove (Item it) {
		// student fills in
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
		// student fills in
		return -1;
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
	 * Performance must be linearly dependent on U + UO where U is the number of unique items in this
	 * and UO is the number of unique items in other.
	 */
	public boolean includes(MultiSet<Item> other) {
		// student fills in
		return false;
	}
	
	/** 
	 * Return a MultiSet which represents intersection with existing MultiSet.
	 * 
	 * Performance must be linearly dependent on the number of unique items in both MultiSet 
	 * objects, or in other words ~ U + UO where U is the number of items in this and MO
	 * is the number of items in other.
	 * 
	 * Consider definition of intersect on wikipedia page as to be the correct logic:
	 * 
	 * This is challenging.
	 */
	public MultiSet<Item> intersects(MultiSet<Item> other) {
		// student fills in
		return null;
	}

	/** 
	 * Return a MultiSet which represents union with existing MultiSet.
	 * 
	 * Performance must be linearly dependent on the number of items in both MultiSet 
	 * objects, or in other words ~ UO + U where UO is the number of unique items in other and U
	 * is the number of unique items in this MultiSet.
	 * 
	 * Consider definition of intersect on wikipedia page as to be the correct logic:
	 * 
	 * This is challenging.
	 */
	public MultiSet<Item> union(MultiSet<Item> other) {
		// student fills in
		return null;
	}
}
