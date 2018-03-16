package algs.days.day03;

import java.util.Iterator;


/** Slightly modified from p. 141 implementation. */
public class FixedCapacityStackOfState implements Iterable<State> {
	private State[] a;  // holds the items
	private int N;        // number of items in stack

	// create an empty stack with given capacity
	public FixedCapacityStackOfState(int capacity) {
		a = new State[capacity];
		N = 0;
	}

	/** Returns values in order that you would get them if you pop'd them one at a time. */
	class ReverseArrayIterator implements Iterator<State> {
		private int i = N;
		public boolean hasNext() { return i > 0;  }
		public State next()    { return a[--i]; }
	}

	public boolean isEmpty()          {  return N == 0;        }
	public boolean isFull()           {  return N == a.length; }
	public void push(State item)      {  a[N++] = item;        }
	public State pop()                {  State ret = a[--N]; a[N] = null; return ret; }
	public Iterator<State> iterator() {  return new ReverseArrayIterator(); } 

}