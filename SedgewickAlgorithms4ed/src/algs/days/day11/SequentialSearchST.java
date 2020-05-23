package algs.days.day11;

public class SequentialSearchST<Key, Value> {
	int N;           // number of key-value pairs
	Node first;      // the linked list of key-value pairs

	// Nodes now store (key and value)
	class Node {
		Key   key;
		Value value;
		Node  next;

		public Node (Key key, Value val, Node next)  {
			this.key  = key;
			this.value  = val;
			this.next = next;
		}
	}

	public int size()                 { return N;  }
	public boolean isEmpty()          { return size() == 0; }
	public boolean contains(Key key)  { return get(key) != null; }

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
					prev.next = n.next;   // have previous one linke around
				}
				
				return;
			}

			prev = n;                     // don't forget to update!
			n = n.next;
		}
	}
}