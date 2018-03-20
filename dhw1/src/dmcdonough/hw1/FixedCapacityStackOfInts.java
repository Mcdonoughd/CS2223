package dmcdonough.hw1;

import java.util.Iterator;

import edu.princeton.cs.algs4.*;

/** Slightly modified from p. 141 implementation. */
public class FixedCapacityStackOfInts implements Iterable<Integer> {
	private Integer[] a;  // holds the items
	private int N;        // number of items in stack

	// create an empty stack with given capacity
	public FixedCapacityStackOfInts(int capacity) {
		a = new Integer[capacity];
		N = 0;
	}

	/** Returns values in order that you would get them if you pop'd them one at a time. */
	class ReverseArrayIterator implements Iterator<Integer> {
		private int i = N;
		public boolean hasNext() { return i > 0;  }
		public Integer next()    { return a[--i]; }
	}

	public boolean isEmpty()            {  return N == 0;        }
	public boolean isFull()             {  return N == a.length; }
	public void push(Integer item)      {  a[N++] = item;        }
	public Integer pop()                {  return a[--N];        }
	public Iterator<Integer> iterator() {  return new ReverseArrayIterator(); } 

	public static void main(String[] args) {
		FixedCapacityStackOfInts stack = new FixedCapacityStackOfInts(100);
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) stack.push(Integer.valueOf(item)); 
			else if (stack.isEmpty())  StdOut.println("BAD INPUT"); 
			else                       StdOut.print(stack.pop() + " ");
		}
		StdOut.println();

		// print what's left on the stack
		StdOut.println("(" + stack.N + " left on the stack)");
		for (Integer i : stack) { StdOut.print(i + " ");
		StdOut.println();
		}
	} 
}