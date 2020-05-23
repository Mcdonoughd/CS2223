package algs.experiment.orderedSearch;

public interface ISearchOrdered {
	/**
	 * Search for target, returning true on success; false otherwise.
	 * @param collection      an Ordered collection of items.
	 * @param target          the desired target to confirm is contained in collection.
	 */
	boolean contains(OrderedCollection collection, Object target);
	
	/**
	 * Return the number of comparisons made.
	 * 
	 * This returns a monotonically increasing number that reflects the total number of comparisons
	 * made by the {@link #contains(OrderedCollection, Object)} method. 
	 */
	int numComparisons();
}
