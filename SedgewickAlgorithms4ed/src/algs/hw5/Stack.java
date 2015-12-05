package algs.hw5;

import java.util.Iterator;
import java.util.NoSuchElementException;


// Standard implementation of Stack using linked lists.
public class Stack<Item> implements Iterable<Item> {
	Node first;     // top of stack
	int N = 0;      // size of the stack

	// helper linked list class
	class Node {
		Item item;
		Node next;
	}

	/** Initializes an empty stack. */
	public Stack() {
		first = null;
	}

	public boolean isEmpty() { return first == null; }
	public int size()        { return N; }

	/**
	 * Adds the item to this stack.
	 *
	 * @param  item the item to add
	 */
	public void push(Item item) {
		Node oldfirst = first;
		first = new Node();
		first.item = item;
		first.next = oldfirst;
		N++;
	}

	/**
	 * Removes and returns the item most recently added to this stack.
	 *
	 * @return the item most recently added
	 * @throws NoSuchElementException if this stack is empty
	 */
	public Item pop() {
		if (isEmpty()) throw new NoSuchElementException("Stack underflow");
		Item item = first.item;        // save item to return
		first = first.next;            // delete first node
		N--;
		return item;                   // return the saved item
	}

	@Override
	public Iterator<Item> iterator() { return new ListIterator(first); }
	
	// an iterator, doesn't implement remove() since it's optional
    class ListIterator implements Iterator<Item> {
        Node current;

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