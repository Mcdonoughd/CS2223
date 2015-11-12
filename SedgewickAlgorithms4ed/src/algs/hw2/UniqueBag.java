package algs.hw2;

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
 * 3. Cleaned up the description of the Node inner class, which doesn't need generics
 *    because the outer class provides this.
 *    
 * 4. Added remove method, which I somehow had forgotten to include in the template. You need
 *    to implement this (as a complement to add).
 * 
 * 
 * Final Comments:
 * 
 * 1. To receive maximum points you are to implement all methods without using the existing
 *    java.util.* classes that would otherwise be useful. The point of this programming exercise
 *    is to gain experience in working with linked-list structures where the focus is on achieving
 *    the highest performance of the code.
 * 
 * @param <Item>
 */
public class UniqueBag<Item extends Comparable<Item>> {

	Node first = null;
	
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
	 * NOTE: there are no performance requirements on this constructor.
	 */
	public UniqueBag(Item[] initial) {
		
	}
	
	/** 
	 * Return the number of items in the UniqueBag.
	 * 
	 * Performance must be independent of the number of items in the UniqueBag, or ~ 1.
	 */
	public int size() {
		// Replace with your implementation
		return -999;
	}

	/** 
	 * Determines equality with another UniqueBag objects.
	 * 
	 * Performance must be linearly dependent on the number of items in the UniqueBag, or ~ N.
	 */
	public boolean identical (UniqueBag other) {
		// Replace with your implementation
		return false;
	}
	
	/** 
	 * Return an array that contains the items from the UniqueBag.
	 * 
	 * Performance must be linearly dependent on the number of items in the UniqueBag, or ~ N.
	 */
	public Item[] toArray() {
		// Replace with your implementation
		return null;
	}

	/** 
	 * Add an item to the UniqueBag; return false if already contained.
	 * 
	 * Performance can be linearly dependent on the number of items in the UniqueBag, or ~ N.
	 */
	public boolean add (Item it){
		// Replace with your implementation
		return false;
	}
	
	/** 
	 * Remove an item to the UniqueBag; return false if not contained within, true on success.
	 * 
	 * Performance can be linearly dependent on the number of items in the UniqueBag, or ~ N.
	 */
	public boolean remove (Item it){
		// Replace with your implementation
		return false;
	}

	/** 
	 * Determine whether the item is contained by the UniqueBag.
	 * 
	 * Performance must be linearly dependent on the number of items in the UniqueBag, or ~ N.
	 */
	public boolean contains(Item it) {
		// Replace with your implementation
		return false;
	}

	/** 
	 * Return a UniqueBag which represents intersection with existing UniqueBag.
	 * 
	 * Performance must be linearly dependent on the number of items in both UniqueBag 
	 * objects, or in otherwords ~ M + N where M is the number of items in other and N
	 * is the number of items in this UniqueBag.
	 */
	public UniqueBag<Item> intersects(UniqueBag<Item> other) {
		
		// Replace with your implementation
		return null;
	}

	/** 
	 * Return a UniqueBag which represents union with existing UniqueBag.
	 * 
	 * Performance must be linearly dependent on the number of items in both UniqueBag 
	 * objects, or in otherwords ~ M + N where M is the number of items in other and N
	 * is the number of items in this UniqueBag.
	 */
	public UniqueBag<Item> union(UniqueBag<Item> other) {
		// Replace with your implementation
		return null;
	}


}
