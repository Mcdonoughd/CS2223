package algs.problems.binaryTree;

/**
 * A collection that ensures uniqueness of the elements added to the collection.
 *
 * The collection supports the {@link #iterator()} interface.
 * 
 * @param <T>    fundamental type of values in the collection.
 */
public interface ISet<T> extends Iterable<T> {
	
	/**
	 * Add the value to the collection and return true if the structure of the collection changed.
	 * 
	 * @param value   value to add
	 * 
	 * @return true if value was added, false otherwise
	 */
	boolean add(T value);

	/**
	 * Remove the value from the collection, should it exist; return true if the collection changes,
	 * false otherwise.
	 * 
	 * @param value   value to be removed
	 * 
	 * @return true if value was removed, false otherwise
	 */
	boolean remove(T value);

	/**
	 * Determine whether collection contains the given value.
	 * 
	 * @param value   value to be searched for 
	 * 
	 * @return true if value is contained, false otherwise
	 */
	boolean contains (T value);
}
