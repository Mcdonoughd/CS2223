package dmcdonough.hw2;

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
 * @param <Item>
 */
public class MultiSet<Item extends Comparable<Item>> {

	
	/** You must use this Node class as part of a LinkedList to store the MultiSet items. Do not modify this class. */
	class Node {
		private Item   item;
		private int    count;
		private Node   next;		
	}

	//variable for the multiset itself
	
	public int unique = 0;
	public int total = 0;
	public Node head = null;
	
	/** Create an empty MultiSet. */
	public MultiSet () { 
		head = new Node();
	}
	
	
	public void printMS() {
		Node iterate;
		iterate = this.head;
		if(iterate == null) {
			System.out.println("MultiSet is Empty");
			return;
		}
		else {
			for(int i=0;i<=this.unique-1;i++) {
				System.out.println(iterate.item + ": " + iterate.count);
				if(iterate.next == null) {
					return;
				}else {
					iterate = iterate.next;
				}
			}
		}
	}
	
	/**
	 * Initialize the MultiSet to contain the unique elements found in the initial list.
	 * 
	 * Performance is allowed to be dependent on N*N, where N is the number of total items in initial.
	 */
	public MultiSet(Item [] initial) { 
		//initial is an array of items

		for(int i = 0;i<=initial.length-1; i++) {
			this.add(initial[i]);
		}
		
		
	}

	/** 
	 * Return the number of items in the MultiSet.
	 * 
	 * Performance must be independent of the number of items in the MultiSet, or ~ 1.
	 */
	public int size() {
		//we keep track of the size in terms of a variable that is changed through addition
		return this.unique;
	}

	/** 
	 * Determines equality with another MultiSet objects.
	 * 
	 * Assume U=number of unique items in self while UO=number of unique items in other.
	 * 
	 * Performance must be linearly dependent upon min(U1,U2)
	 */
	public boolean identical (MultiSet<Item> other) { 
		//check if same size!
		if(this.size() != other.size() || other == null || this == null) {
			return false;
		}
		else {
			Node this_iterator = this.head;
			Node other_iterator = other.head;
			for(int j =0; j<this.size()-1;j++){
				for(int i =0; i < other.size()-1;i++) {
					if(this_iterator.item == other_iterator.item) {
						break; //this item was in the other
					}
					else{
						//if at the end of the other list and item was not found...
						if(other_iterator.next == null) {
							return false;
						}//otherwise move to the next item
						other_iterator = other_iterator.next;
					}
				}
				this_iterator = this_iterator.next;
			}
		}
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
		if(it == null) {
			System.out.println("I'm sorry, But I can't do that Dave. The item is null...");
			return false;
		}
		//the multiset head
		Node iterator = this.head;
		//if the head is null...
		if(iterator == null) {
			//new item becomes head
			this.head = new Node();
			this.head.item = it;
			this.head.count = 1;
			this.unique = 1;
			this.total = 1;
			return true;
		}
		//if the head is not null...
		else {
			//iterate through each element
			for(int i = 0; i<=this.unique-1; ) {
				//check if element is at the current spot
				if(iterator.item == it) {
					iterator.count++;
					this.unique++;
					this.total++;
					return true;
				}
				//if not check if there is not next spot...
				else if(iterator.next == null) {
					//add to end of list
					iterator.next = new Node();
					iterator = iterator.next;
					iterator.count = 1;
					iterator.item = it;
					iterator.next = null;
					this.unique++;
					this.total++;
					return true;
				}
				//if there is a next spot move.
				else {
					//move through the list
					iterator = iterator.next;
				}
			}
		}
		return false;
	}
	
	/** 
	 * Remove an item from the MultiSet; return false if not in the MultiSet to
	 * begin with, otherwise returns true on success.
	 *  
	 * Performance must be no worse than linearly dependent on N.
	 */
	public boolean remove (Item it) {
		//if given value is null then dont do anything
		if(it == null) {
			System.out.println("I'm sorry, But I can't do that Dave. The item is null...");
			return false;
		}
		//the multiset head
		Node iterator = this.head;
		//this iterator is one step behind the iterator
		Node iterator_behind = this.head;
		//if the head is null...
		if(iterator == null) {
			//error
			System.out.println("Multiset is Empty");
			return false;
		}
		//if the head is not null...
		else {
			//iterate through each element
			for(int i = 0; i<=this.unique-1; ) {
				//check if element is at the current spot
				if(iterator.item == it) {
					iterator.count--;
					this.unique--;
					this.total--;
					//if iterator count is 0 then remove it from the set (aka rewire previous node's next to the current node's next).
					if(iterator.count == 0) {
						
						//if iterator and iterator_behind are the same they are both the head. So set them to null. 
						if(iterator_behind == iterator) {
							this.head = null;
						}
						else {
							iterator_behind.next = iterator.next;
						}
						return true;
					}
					
					return true;
				}
				//if not check if there is not next spot...
				else if(iterator.next == null) {
					return false;
				}
				//if there is a next spot move.
				else {
					//save current spot
					iterator_behind = iterator;
					//move the iterator
					iterator = iterator.next;
					
				}
			}
		}
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
		if(it == null) {
			System.out.println("I'm sorry, But I can't do that Dave. The item is null...");
			return 0;
		}
		//the multiset head
		Node iterator = this.head;
		//if the head is null...
		if(iterator == null) {
			//item is not in the set
			return 0;
		}
		//if the head is not null...
		else {
			//iterate through each element
			for(int i = 0; i<=this.unique-1; ) {
				//check if element is at the current spot
				if(iterator.item == it) {
					//return the count
					return iterator.count;
				}
				//if not check if there is not next spot...
				else if(iterator.next == null) {
					//end of list
					return 0;
				}
				//if there is a next spot move.
				else {
					//move through the list
					iterator = iterator.next;
				}
			}
		}		
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
		return other;
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
