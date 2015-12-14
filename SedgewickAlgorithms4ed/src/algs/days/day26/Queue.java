package algs.days.day26;

import java.util.*;

// Standard Sedgewick Queue implementation using linked lists.

public class Queue<Item> implements Iterable<Item> {
	private Node first;    // beginning of queue
	private Node last;     // end of queue
	private int N;               // number of elements on queue

	// helper linked list class
	class Node {
		private Item item;
		private Node next;
	}

	public Queue() {
		first = null;
		last  = null;
		N = 0;
	}

	public boolean isEmpty() { return first == null; }
	public int size()        { return N;             }

	/** Adds the item to this queue. */
	public void enqueue(Item item) {
		Node oldlast = last;

		last = new Node();
		last.item = item;
		last.next = null;
		if (isEmpty()) { 
			first = last;
		} else {
			oldlast.next = last;
		}

		N++;
	}

	/**
	 * Removes and returns the item on this queue that was least recently added.
	 *
	 * @return the item on this queue that was least recently added
	 * @throws NoSuchElementException if this queue is empty
	 */
	public Item dequeue() {
		if (isEmpty()) throw new NoSuchElementException("Queue underflow");

		Item item = first.item;
		first = first.next;
		N--;
		if (isEmpty()) last = null;   // to avoid loitering
		return item;
	}

	/**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     *
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
    public Iterator<Item> iterator()  {
        return new ListIterator(first);  
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current;

        public ListIterator(Node first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }

}