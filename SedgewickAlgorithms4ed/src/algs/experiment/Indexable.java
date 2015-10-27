package algs.experiment;

/**
 * Fundamental interface used during experiments for providing methods for manipulating 
 * collections which can be indexed by integer [0, n) where n is the size of the collection.
 *
 * @param <E>
 */
public interface Indexable<E> {
	/**
	 * Returns the ith element in the collection. 
	 * 
	 * @param i           desired index position into the collection
	 * @throws Exception  should i be an invalid index number
	 */
	Comparable<E> get(int i) throws Exception;


	/** 
	 * Set the ith element to be a previously retrieved object. Counts as an assignment.
	 * 
	 * @param i
	 * @param o
	 * @throws Exception
	 */
	void set (int i, Comparable<E> o) throws Exception;
	
	/**
	 * Compare the two comparable elements at positions i and j, and return result.
	 * 
	 * @param i
	 * @param j
	 * @return   -1 if c[i] < c[j], +1 if c[i] > c[j], 0 if c[i] = c[j]
	 * @throws Exception  should i or j be an invalid index number
	 */
	int compare(int i, int j) throws Exception;
	
	/**
	 * Gets the size of the collection.
	 */
	int size();
}