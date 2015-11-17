package algs.days.day13;


/**
 * This data type offers to store items in a linked list, where each node in the
 * linked list stores up to k individual items.
 *
 * In all of the performance specifications, N refers to the number of items in the 
 * UniqueBag.
 * 
 * Duplicate values may exist within this collection.
 * 
 * @param <Item>
 */
public class Collection<Item extends Comparable<Item>> {
	/** Number of values to be found within each MultiNode. */
    final int k;
	MultiNode first = null;
	
	/** You must use this MultiNode class as part of a LinkedList to store items. */
	class MultiNode {
		private Item[]        items;   // up to k items in sorted order in the array
		private int           count;   // number of items in this node
		private MultiNode     next;
	}

	/** Default constructor to create an empty collection whose default multinode stores 15 items. */
	public Collection() {
		this (15);
	}
	
	/**
	 * Initialize the bag to contain the unique elements found in the initial list.
	 * 
	 * NOTE: there are no performance requirements on this constructor.
	 */
	public Collection (int k) {
		this.k = k;
	}
	
	/** 
	 * Return the number of items in the collection.
	 * 
	 * You are to rate the performance.
	 */
	public int size() {
		// Replace with your implementation
		return -999;
	}

	/** 
	 * Add an item to the Collection.
	 * 
	 * You are to rate the performance.
	 */
	public void add (Item it){
		// Replace with your implementation
	}
	
	/** 
	 * Remove an item from the Collection; return true on success, false if not present
	 * 
	 * You are to rate the performance.
	 */
	public boolean remove (Item it){
		// Replace with your implementation
		return false;
	}

	/** 
	 * Determine whether the item is contained by the UniqueBag.
	 * 
	 * You are to rate the performance.
	 */
	public boolean contains(Item it) {
		// Replace with your implementation
		return false;
	}
}

	
