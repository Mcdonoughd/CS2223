package algs.hw3;

import java.util.Iterator;

// USE THIS CLASS AS IS.
public class SequentialSearchST<Key,Value> implements Iterable<Key> {
	int N;           // number of key-value pairs
	Node first;      // the linked list of key-value pairs

	// Nodes now store (key and value)
	class Node {
		Key      key;
		Value    value;
		Node     next;

		public Node (Key key, Value val, Node next)  {
			this.key  = key;
			this.value  = val;
			this.next = next;
		}
	}
	
	// Iterator to return the keys in the Symbol Table.
	class LinkedListIterator implements Iterator<Key> {

		Node item;   // current item for the iterator to return.
		
		LinkedListIterator(Node first) {
			item = first;
		}
		
		/** Are there any more items to return? */
		public boolean hasNext() {
			return item != null;
		}

		/** Return and advance. */
		public Key next() {
			Key key = item.key;
			item = item.next;
			return key;
		}

		@Override
		public void remove() {}
		
	}
	

	public int size()                    { return N;  }
	public boolean isEmpty()             { return size() == 0; }
	public boolean contains(Key key)     { return get(key) != null; }

	public Value get(Key key) {
		Node n = first;
		while (n != null) {
			if (key.equals (n.key)) {
				return n.value;
			}

			n = n.next;
		}

		return null;  // not present
	}

	public void put(Key key, Value val) {
		if (val == null) {
			delete (key);
			return;
		}

		Node n = first;
		while (n != null) {
			if (key.equals (n.key)) {
				n.value = val;
				return;
			}

			n = n.next;
		}

		// add as new node at beginning
		first = new Node (key, val, first);
		N++;
	}
	
	public void delete(Key key) {
		Node prev = null;
		Node n    = first;
		while (n != null) {
			if (key.equals (n.key)) {
				if (prev == null) {       // no previous? Must have been first
					first = n.next;
				} else {
					prev.next = n.next;   // have previous one link around
				}
				
				return;
			}

			prev = n;                     // don't forget to update!
			n = n.next;
		}
	}
	
	
	/**
	 * Use this method to retrieve an iterator containing all keys in the Symbol Table
	 */
	public Iterator<Key> iterator() {
		return new LinkedListIterator(first);
	}
}