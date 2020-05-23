package algs.hw4;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.*;

/**
 * From Lecture. Leave unchanged.
 * 
 *  @param <Item>    items stored by the Queue are defined using JDK Generics
 */
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

	/**
	 * Unit tests the <tt>Queue</tt> data type.
	 */
	public static void main(String[] args) {
		Queue<String> q = new Queue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) q.enqueue(item);
			else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
		}
		StdOut.println("(" + q.size() + " left on queue)");
	}
}