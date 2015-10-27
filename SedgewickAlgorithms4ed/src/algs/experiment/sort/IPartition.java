package algs.experiment.sort;

public interface IPartition {
	
	/**
	 * Given a collection[left,right] and a potential pivotIndex, partition the collection
	 * and return an index k such that all elements collection[left,k-1] <= collection[k] and 
	 * collection[k-1,right] >= collection[k]
	 * 
	 * @param collection
	 * @param left
	 * @param right
	 * @param pivotIndex
	 * @return
	 * @throws Exception
	 */
	int partition(Sorter collection, int left, int right, int pivotIndex) throws Exception;
}
