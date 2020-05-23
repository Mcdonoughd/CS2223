package algs.days.day23;

import java.util.Iterator;


// modified Bag implementation to support simple deletion of an edge.
// follows sedgewick standard implementation

public class Bag<Item> implements Iterable<Item> {

	Node first;     // first node in the list (may be null)
	int size;
	
	class Node {
		Item    item;
		Node    next;
	}

	public void add (Item item) {
		Node oldfirst = first;

		first = new Node();
		first.item = item;
		first.next = oldfirst;
		size++;
	}
	
	public void remove (Item item) {
		if (first == null) { return; }
		
		Node prev = null;
		Node n = first;
		while (n != null) {
			if (n.item.equals(item)) {
				if (prev == null) {
					first = n.next;
				} else {
					prev.next = n.next;
				}
				size--;
				return;
			}
			
			prev = n;
			n = n.next;
		}		
	}


    /** Returns iterator over items in bag. */
    public Iterator<Item> iterator()  { return new ListIterator(first); }
    public int size() { return size; }
    
    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current;

        public ListIterator(Node first)  { current = first;        }
        public boolean hasNext()         { return current != null; }
        public void remove()             {                         }

        public Item next() {
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
	
}
