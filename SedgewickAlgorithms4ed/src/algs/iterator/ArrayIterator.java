package algs.iterator;

import java.util.Iterator;

/**
 * Iterator over an array.
 * 
 * @param <T>
 */
public class ArrayIterator<T> implements Iterator<T> {
	T[] array;
	int idx = 0;
	
	public ArrayIterator(T[] array) { this.array = array; }
	
	public boolean hasNext() { return idx < array.length; }
	
	public T next() { T ret = array[idx++]; return ret; }
	
	
	public void remove() { throw new UnsupportedOperationException("No remove possible."); }

}
