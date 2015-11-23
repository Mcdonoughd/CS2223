package algs.solutions.hw3;

import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Stack<Item> {
	private Node<Item> first;     // top of stack
	private int N;                // size of the stack

	// helper linked list class
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}

	/**
	 * Initializes an empty stack.
	 */
	public Stack() {
		first = null;
		N = 0;
	}

	public boolean isEmpty() { return first == null; }
	public int size()        { return N; }

	/**
	 * Adds the item to this stack.
	 *
	 * @param  item the item to add
	 */
	public void push(Item item) {
		Node<Item> oldfirst = first;
		first = new Node<Item>();
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



	/**
	 * Unit tests the <tt>Stack</tt> data type.
	 */
	public static void main(String[] args) {
		Stack<String> s = new Stack<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) s.push(item);
			else if (!s.isEmpty()) StdOut.print(s.pop() + " ");
		}
		StdOut.println("(" + s.size() + " left on stack)");
	}
}