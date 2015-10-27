package algs.experiment.sort;

/**
 * A useful pivot algorithm that selects a random element within the [left, right] range of a collection.
 */
public class RightMost implements ISelectPivot {

	@Override
	public int select(Sorter collection, int left, int right) {
		return right;
	}

}
