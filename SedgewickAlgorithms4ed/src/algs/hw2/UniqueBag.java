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
 * @param <Item>
 */
public class UniqueBag<Item extends Comparable<Item>> {

	Node<Item> first = null;
	
	/** You must use this Node class as part of a LinkedList to store the UniqueBag items. */
	class Node<Item> {
		private Item       item;
		private Node<Item> next;
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
	public boolean add(Item it){
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
