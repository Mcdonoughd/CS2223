package dmcdonough.hw2;

import dmcdonough.hw2.MultiSet;
import dmcdonough.hw2.MultiSet.Node;

public class MultiSet<Item extends Comparable<Item>> {

	Node head;
	int N;

	/**
	 * You must use this Node class as part of a LinkedList to store the MultiSet
	 * items. Do not modify this class.
	 */
	class Node {
		private Item item;
		private int count;
		private Node next;
	}

	/** Create an empty MultiSet. */
	public MultiSet() {
		head = null;
	}
	// Create another constructor for MultiSet ???

	/**
	 * Initialize the MultiSet to contain the unique elements found in the initial
	 * list.
	 * 
	 * Performance is allowed to be dependent on N*N, where N is the number of total
	 * items in initial.
	 */
	public MultiSet(Item[] initial) {

		for (int i = 0; i < initial.length; i++) {
			this.add(initial[i]); // OR USE this (INSTEAD OF "set")? -- (USE ADD FUNCTION CREATED BELOW!)
		}

	}

	/**
	 * Return the number of items in the MultiSet.
	 * 
	 * Performance must be independent of the number of items in the MultiSet, or ~
	 * 1.
	 */
	public int size() {
		return N;
	}

	/**
	 * Determines equality with another MultiSet objects.
	 * 
	 * Assume U=number of unique items in self while UO=number of unique items in
	 * other.
	 * 
	 * Performance must be linearly dependent upon min(U1,U2)
	 */
	public boolean identical(MultiSet<Item> other) {
		if ((other == null) && (this != null))
			return false;

		if ((other != null) && (this == null))
			return false;

		Node a = head;
		Node b = other.head;

		if ((a.item != b.item) || (a.count != b.count))
			return false;

		while ((a != null) && (b != null)) {

			/*
			 * Move on to next nodes if previous nodes hold the same item and the same count
			 */
			if (a.item == b.item && a.count == b.count) {
				a = a.next;
				b = b.next;
			} else {
				return false;
			}
		}

		/*
		 * If linked lists are identical, then 'a' and 'b' must be null here
		 */
		return (a == null && b == null);
	}

	/**
	 * Return an array that contains the items from the MultiSet.
	 * 
	 * Performance must be linearly dependent on N.
	 */
	public Item[] toArray() {
		Node here = head;

		Comparable[] arr = new Comparable[N];

		int i = 0;
		while (here != null && i < N) {
			for (int j = 0; j < here.count; j++) {
				arr[i] = here.item;
				i++;
			}
			here = here.next;
		}

		return (Item[]) arr;
	}

	/**
	 * Add an item to the MultiSet.
	 * 
	 * Performance must be no worse than linearly dependent on N.
	 */
	public boolean add(Item it) {
		// student fills in
		N++;

		// is empty?
		if (head == null) {
			head = new Node();
			head.item = it;
			head.count = 1;
			head.next = null;
			return true;
		}

		// is the new item smaller than the head
		int rc = head.item.compareTo(it);

		if (rc > 0) {
			Node node = new Node();
			node.item = it;
			node.count = 1;
			node.next = head;
			head = node;
			return true;
		}

		Node here = head;
		Node next = here.next;
		while (here != null) {
			rc = here.item.compareTo(it); // check each time

			if (rc == 0) {
				here.count++;
				return true;
			}

			if (next == null) { // it > last one
				Node node = new Node();
				node.item = it;
				node.count = 1;
				node.next = null;
				here.next = node;
				return true;
			}

			// it > here, let me know if it < next
			int nrc = next.item.compareTo(it);
			if (nrc > 0) {
				// here ... it ... next
				Node node = new Node();
				node.item = it;
				node.count = 1;
				node.next = next;
				here.next = node;
				return true;
			}

			// it > next
			next = next.next;
			here = here.next;
		}

		return true;
	}

	/**
	 * Remove an item from the MultiSet; return false if not in the MultiSet to
	 * begin with, otherwise returns true on success.
	 * 
	 * Performance must be no worse than linearly dependent on N.
	 */
	public boolean remove(Item it) {
		N--;
		Node tmp = head;
		Node prev = null;

		while (tmp != null) {
			// check if "it" is same item that tmp points to
			if (tmp.item.equals(it)) { // if it is found

				if (prev == null) {
					if (tmp.count == 1)
						head = tmp.next; // acting like element does not have count !
					else
						tmp.count = tmp.count - 1;
				} else {
					if (tmp.count == 1)
						prev.next = tmp.next;
					else
						tmp.count = tmp.count - 1;
				}

				return true;
			}

			prev = tmp;
			tmp = tmp.next; // advance to next one (if it exists)
		}

		N++;
		return false; // list is empty or element not found in the list
	}

	/**
	 * Returns the number of times item appears in the MultiSet.
	 * 
	 * If returns 0, then the item is not contained within this MultiSet.
	 * 
	 * Performance must be no worse than linearly dependent on U.
	 */
	public int multiplicity(Item it) {
		// student fills in
		Node tmp = head;

		while (tmp != null) {
			if (tmp.item.equals(it)) {
				return tmp.count;
			}

			tmp = tmp.next; // go to the next one (if it exists)
		}

		return 0; // return 0 if the item is not contained in the MultiSet
	}

	/**
	 * Determine whether this MultiSet includes other MultiSet.
	 * 
	 * A MultiSet A includes a MultiSet B when: for all elements x in B with
	 * multiplicity mB(x), the multiplicity mA(x) in A is >= mB(x).
	 * 
	 * In degenerate case: 1. If this is empty, false is always returned. 2. If this
	 * is non-empty and other is empty, true is returned.
	 * 
	 * Performance must be linearly dependent on U + UO where U is the number of
	 * unique items in this and UO is the number of unique items in other.
	 */
	public boolean includes(MultiSet<Item> other) {
		Node tmp = head;
		Node otherTmp = other.head;

		// This is empty, return false
		if (tmp == null)
			return false;

		// This not empty, but other is empty
		if (tmp != null && other.head == null)
			return true;

		int count = 0; // Count the number of occurrences
		while (tmp != null && otherTmp != null) {
			int rc = tmp.item.compareTo(otherTmp.item);

			// This is greater than other, increment other
			if (rc > 0) {
				otherTmp = otherTmp.next;

				if (otherTmp == null)
					break;
			}

			// Other is greater that this, increment this
			if (rc < 0) {
				tmp = tmp.next;
			}

			// Items are identical
			if (rc == 0) {
				tmp = tmp.next;
				otherTmp = otherTmp.next;
				count++;
			}
		}

		if (count == other.N)
			return true;

		// Other is not included in this
		return false;
	}
	
	//join two sets together by the head and tail
	public MultiSet<Item> Join(MultiSet<Item> A, MultiSet<Item> B){
		Node B_node = new Node();
		B_node = B.head;
		Node A_node = new Node();
		A_node = A.head;

		while(A_node!=null){
			if(A_node.next==null) {
				A_node.next = B_node;
				A.N += B.size();
				break;
			}
			A_node = A_node.next;
		}

		return setUnique(A);
	}
	
	//collapses duplicates within the join 
	public MultiSet<Item> setUnique(MultiSet<Item> A) {
		//System.out.println("hi");
		
		MultiSet<Item> unique = new MultiSet<Item>();
		//find the repeated element and update it's count
		Node U_node = new Node();

		while(A.head != null) {
			if(unique.multiplicity(A.head.item)<=0) {
				//element is not in unique
				unique.add(A.head.item);
				U_node = unique.head;
			}
			else { //element is in unique
				
				while(U_node != null) {
					//System.out.println("HELP");
					if(U_node.item == A.head.item) {
						U_node.count += A.head.count+1;
						//U_node.count = Math.abs(U_node.count);
						break;
					}
					U_node = U_node.next;
				}
			}
			//System.out.println("HELP");
			A.head = A.head.next;
		}

		//System.out.println("\nUnique");
		//unique.printMS();
		return unique;
	}
	
	
	
	
	public void printMS() {
		Node A = this.head;
		while(A != null) {
			System.out.println(A.item + ": " + A.count);
			A = A.next;
		}
	}
	public boolean contains(Item it) {
		boolean contains = false;
		MultiSet<Item> u = new MultiSet<Item>();
		while(this.head!=null){
			if(head.item == it){
				contains = true;
			}
			u.add(head.item);
			this.remove(head.item);
		}
		this.head = u.head;
		return contains;
}

	/**
	 * Return a MultiSet which represents intersection with existing MultiSet.
	 * 
	 * Performance must be linearly dependent on the number of unique items in both
	 * MultiSet objects, or in other words ~ U + UO where U is the number of items
	 * in this and MO is the number of items in other.
	 * 
	 * Consider definition of intersect on wikipedia page as to be the correct
	 * logic:
	 * 
	 * This is challenging.
	 */
	public MultiSet<Item> intersects(MultiSet<Item> other) {
		// student fills in
/*
		MultiSet<Item> interSet = new MultiSet<Item>();
		Node A = this.head;
		Node B = other.head;
		//this.printMS();
		//System.out.println("\n");
		//other.printMS();
		//System.out.println("\nJoin");
		MultiSet<Item> AB = Join(this,other); //join of both this and other sets
		AB.printMS();

		while ((A != null)) {
			if(!AB.remove(A.item)) {
				AB.add(A.item);
			}
			else {
				//AB.remove(A.item);
			}
			A = A.next;
		}
		while ((B != null)) {
			if(!AB.remove(B.item)) {
				AB.add(B.item);
			}else {
				AB.remove(B.item);
			}
			B = B.next;
		}
		while ((A != null)) {
			if(!AB.remove(A.item)) {
				//AB.add(A.item);
			}
			else {
				AB.remove(A.item);
			}
			A = A.next;
		}
		
		return setUnique(interSet);*/
		MultiSet<Item> intersect = new MultiSet<Item>();
		MultiSet<Item> u = new MultiSet<Item>();
		while(this.head!=null){
			if(this.contains(head.item)&&other.contains(head.item)){
				if(u.contains(head.item)) {
					//do nothing
				}
				else {
					int min = other.multiplicity(head.item);
					if(this.multiplicity(head.item)<other.multiplicity(head.item)) {
						min = this.multiplicity(head.item);
					}
					
					for(int i = 0; i <min;i++) {
						intersect.add(head.item);
					}
				}
			}
			u.add(head.item);
			remove(head.item);
		}
		this.head = u.head;
		return intersect;
	}
	

	/**
	 * Return a MultiSet which represents union with existing MultiSet.
	 * 
	 * Performance must be linearly dependent on the number of items in both
	 * MultiSet objects, or in other words ~ UO + U where UO is the number of unique
	 * items in other and U is the number of unique items in this MultiSet.
	 * 
	 * Consider definition of intersect on wikipedia page as to be the correct
	 * logic:
	 * 
	 * This is challenging.
	 */
	public MultiSet<Item> union(MultiSet<Item> other) {

		MultiSet<Item> union = new MultiSet<Item>();
		MultiSet<Item> u = new MultiSet<Item>();
		MultiSet<Item> AB = new MultiSet<Item>();
		u = this.intersects(other);
		AB = Join(this, other);
		union = this.Difference(other);
		//check for items in other that weren't in this UniqueBag
		
		return union;
	}
	
	//gets the difference between two sets
	public MultiSet<Item> Difference(MultiSet<Item> other){
		MultiSet<Item> diff = new MultiSet<Item>();
		Node B_node = new Node(); //intersection
		B_node = other.head;
		Node A_node = new Node(); //AB
		A_node = this.head;
		int contains;
		if(A_node == null) {
			return other;
		}
		if(B_node == null) {
			return this;
		}
		while(A_node != null) {
			contains = 0;
			while(B_node != null) {
				if(B_node.item == A_node.item) {
					contains = 1;
				}
				
				B_node = B_node.next;
			}
			if(contains == 0) {
				int min = other.multiplicity(B_node.item);
				if(this.multiplicity(A_node.item)<other.multiplicity(B_node.item)) {
					min = this.multiplicity(A_node.item);
				}
				
				for(int i = 0; i <min;i++) {
					diff.add(A_node.item);
				}
				
			}
			contains = 0;
			A_node = A_node.next;
		}
		return diff;
	}
	
}

