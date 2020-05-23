package algs.days.day05;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.*;

/**
 *  @param <Item>    items stored by the Queue are defined using JDK Generics
 */
public class Queue<Item>  {
	private Node<Item> first;    // beginning of queue
	private Node<Item> last;     // end of queue
	private int N;               // number of elements on queue

	// helper linked list class
	class Node<Item> {
		private Item item;
		private Node<Item> next;
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
		Node<Item> oldlast = last;

		last = new Node<Item>();
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