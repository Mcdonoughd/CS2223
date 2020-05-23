package algs.problems.binaryTree;

import java.util.Iterator;

public class BinaryTree<T extends Comparable<T>> implements ISet<T> {
	
	T             value;
	BinaryTree<T> left;
	BinaryTree<T> right;
	
	public BinaryTree(T value) {
		this.value = value;
		this.left  = null;
		this.right = null;
	}
	
	/** Determine if set contains the given value. */
	public boolean contains (T val) {
		int rc = value.compareTo(val);
		if (rc < 0) {
			if (left == null) { return false; }
			return left.contains(val);
		} else if (rc > 0) {
			if (right == null) { return false; }
			return right.contains(val);
		} else {
			return true;
		}
	}
	
	public boolean add(T val) {
		int rc = value.compareTo(val);
		if (rc > 0) {
			if (left == null) {
				left = new BinaryTree<T>(val);
				return true;
			} else {
				return left.add(val);
			}
		} else if (rc < 0) {
			if (right == null) {
				right = new BinaryTree<T>(val);
				return true;
			} else {
				return right.add(val);
			}
		} else {
			return false;
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		// TODO Fill in when appropriate
		return null;
	}

	@Override
	public boolean remove(T value) {
		// TODO Fill in when appropriate
		return false;
	}
}
