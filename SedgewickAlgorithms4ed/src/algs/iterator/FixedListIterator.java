package algs.iterator;

import java.util.ListIterator;

/**
 * Example demonstrating backward and forward iteration over an array.
 * 
 * A more complicated iterator than 
 * 
 * @author George Heineman
 * @param <E>
 */
public class FixedListIterator<E> implements ListIterator<E> {

	/** Storage. */
	E[] array;
	int index = -1;
	int orientation = 0;
	
	public FixedListIterator(E[] array) {
		this.array = array;
	}
	
	@Override
	public boolean hasNext() {
		return index <= array.length-1;
	}

	@Override
	public E next() {
		index++;
		orientation = 1;
		return array[index];
	}

	@Override
	public boolean hasPrevious() {
		return index > -1;
	}

	@Override
	public E previous() {
		index--;
		orientation = -1;
		return array[index+1];
	}

	@Override
	public int nextIndex() {
		return index+1;
	}

	@Override
	public int previousIndex() {
		return index-1;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove not supported");		
	}

	@Override
	public void set(E e) {
		if (orientation == 0) { 
			throw new IllegalStateException("Have not called prev or next yet.");
		} else if (orientation == 1) {
			array[index] = e;
		} else if (orientation == -1) {
			array[index+1] = e;
		}
	}

	@Override
	public void add(E e) {
		throw new UnsupportedOperationException("Remove not supported");		
	}
}
