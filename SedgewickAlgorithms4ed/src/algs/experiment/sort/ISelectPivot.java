package algs.experiment.sort;

public interface ISelectPivot {
	/**
	 * Return an index position within collection[left,right] to use when partitioning
	 * the elements.
	 * 
	 * @param collection    Collection of comparable elements 
	 * @param left          leftmost range
	 * @param right         rightmost range
	 * @return              integer index >= left and <= right.
	 */
	int select(Sorter collection, int left, int right);
}
