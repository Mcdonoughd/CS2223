package algs.days.day26.search;

import edu.princeton.cs.algs4.StdOut;


// Minimum implementation of AVL balanced binary tree.
//
// worth comparing against Red-Black BST.

public class AVL<Key extends Comparable<Key>,Value> {

	Node root;               // root of the tree

	class Node {
		Key    key;  
		Value  value;
		Node   left, right;  // left and right subtrees
		int    height;       // need to know how TALL this subtree is (from leaf, not root).

		public Node(Key key, Value value) {
			this.key = key;
			this.value = value;
		}

		public String toString() { return "[" + key + ", " + value + "]"; }
	}

	public boolean isEmpty() { return root != null; }

	public String toString() { return "<bst: root=" + root +">"; }

	/** Compute height of node in BST from leaf. */
	int dynamicHeight(Node parent) {
		int height = -1;
		if (parent == null) { return -1; }

		height = Math.max(height, dynamicHeight(parent.left));
		height = Math.max(height, dynamicHeight(parent.right));
		return height + 1;
	}

	/** Compute and check height difference of node's children in BST. */
	int dynamicHeightDifference(Node parent) {
		int leftTarget = 0;
		int rightTarget = 0;
		leftTarget = 1 + dynamicHeight(parent.left);
		rightTarget = 1 + dynamicHeight(parent.right);

		return leftTarget - rightTarget;
	}

	/** Validate AVL property for BST node. */
	boolean assertAVLProperty(Node parent) {
		if (parent == null) { return true; }

		if (Math.abs(dynamicHeightDifference(parent)) > 1) {
			return false;
		}
		if (!assertAVLProperty(parent.left)) { return false; }
		if (!assertAVLProperty(parent.right)) { return false; }

		return true;
	}

	/**
	 * Determine height difference for a given node by subtracting leftH - rightH
	 */
	int heightDifference(Node node) {
		int leftTarget = 0;
		int rightTarget = 0;
		if (node.left != null) {
			leftTarget = 1 + node.left.height;
		}
		if (node.right != null) {
			rightTarget = 1 + node.right.height;
		}

		return leftTarget - rightTarget;
	}

	/** Update height for node. */
	void computeHeight(Node node) {
		int height = -1;
		if (node.left != null) {
			height = Math.max(height, node.left.height);
		}
		if (node.right != null) {
			height = Math.max(height, node.right.height);
		}

		node.height = height + 1;
	}

	// One-line method for containment. 
	public boolean contains(Key key) { return get(root, key) != null; }
	public Value get(Key key) { return get(root, key); }

	private Value get(Node parent, Key key) {
		if (parent == null) return null;

		int cmp = key.compareTo(parent.key);

		if      (cmp < 0) return get(parent.left, key);
		else if (cmp > 0) return get(parent.right, key);
		else              return parent.value;
	}

	/** Invoke put on parent, should it exist. */
	public void insert(Key key, Value value) {
		root = insert(root, key, value);
	}

	private Node insert(Node parent, Key key, Value value) {
		if (parent == null) return new Node(key, value);

		int cmp = key.compareTo(parent.key);
		if (cmp <= 0) {
			parent.left  = insert(parent.left,  key, value);
			if (heightDifference(parent) == 2) {
				if (key.compareTo(parent.left.key) <= 0) {
					parent = rotateRight(parent);
				} else {
					parent = rotateLeftRight(parent);
				}
			}
		} else {
			parent.right = insert(parent.right, key, value);
			if (heightDifference(parent) == -2) {
				if (key.compareTo(parent.right.key) > 0) {
					parent = rotateLeft(parent);
				} else {
					parent = rotateRightLeft(parent);
				}
			}
		}

		computeHeight(parent);
		return parent;
	}

	/** Perform right rotation around given node. */
	private Node rotateRight(Node parent) {
		Node newRoot = parent.left;
		Node grandson = newRoot.right;
		parent.left = grandson;
		newRoot.right = parent;

		computeHeight(parent);
		return newRoot;
	}

	/** Perform left rotation around given node. */
	private Node rotateLeft(Node parent) {
		Node newRoot = parent.right;
		Node grandson = newRoot.left;
		parent.right = grandson;
		newRoot.left = parent;

		computeHeight(parent);
		return newRoot;
	}

	/** Perform left, then right rotation around given node. */
	private Node rotateLeftRight(Node parent) {
		Node child = parent.left;
		Node newRoot = child.right;
		Node grand1  = newRoot.left;
		Node grand2  = newRoot.right;
		child.right = grand1;
		parent.left = grand2;

		newRoot.left = child;
		newRoot.right = parent;

		computeHeight(child);
		computeHeight(parent);
		return newRoot;
	}

	/** Perform right, then left rotation around given node. */
	private Node rotateRightLeft(Node parent) {
		Node child = parent.right;
		Node newRoot = child.left;
		Node grand1  = newRoot.left;
		Node grand2  = newRoot.right;
		child.left = grand2;
		parent.right = grand1;

		newRoot.left = parent;
		newRoot.right = child;

		computeHeight(child);
		computeHeight(parent);
		return newRoot;
	}

	public Key minimum() {
		Node n = root;
		if (root == null) { return null; }

		while (n.left != null) {
			n = n.left;
		}

		return n.key;
	}

	/** Implement method to return Value when removing largest element. */
	public void deleteMin() {
		if (root == null) { return; }

		Node n = root;
		while (n.left != null) {
			n = n.left;
		}

		fastDelete (n.key);
	}

	// new methods for discussion
	public Key max() { return max(root).key; }

	private Node max (Node parent) {
		if (parent.right == null) { return parent; }
		return max(parent.right);
	}

	public void fastDelete (Key key) { root = fastDelete(root, key); }

	Node fastDelete(Node parent, Key key) {
		if (parent == null) { return null; }

		int rc = key.compareTo(parent.key);
		if (rc < 0) {
			parent.left = fastDelete (parent.left, key);
			if (heightDifference(parent) == -2) {
				if (heightDifference(parent.right) <= 0) {
					parent = rotateLeft(parent);
				} else {
					parent = rotateRightLeft(parent);
				}
			}
		} else if (rc > 0) {
			parent.right = fastDelete (parent.right, key);
			if (heightDifference(parent) == 2) {
				if (heightDifference(parent.left) >= 0) {
					parent = rotateRight(parent);
				} else {
					parent = rotateLeftRight(parent);
				}
			}
		} else {
			if (parent.right == null) { return parent.left; }
			Node child = parent.right;
			while (child.left != null) {
				child = child.left;
			}

			Key childKey = child.key;
			Value childValue = child.value;
			parent.right = fastDelete (parent.right, childKey);
			parent.key = childKey;
			parent.value = childValue;

			if (heightDifference(parent) == 2) {
				if (heightDifference(parent.left) >= 0) {
					parent = rotateRight(parent);
				} else {
					parent = rotateLeftRight(parent);
				}
			}
		}

		computeHeight(parent);
		return parent;
	}


	// traversal ideas
	// invoke an inorder traversal of the tree
	public void hasZeroLength() { hasZeroLength(root); }
	private void hasZeroLength(Node n) {
		if (n != null) {
			LinkedList ll = (LinkedList) n.value;
			if (ll.isEmpty()) {
				System.out.println("EMPTY VALUE.");
			}
			
			hasZeroLength (n.left);
			
			hasZeroLength (n.right);
		}
	}

	// traversal ideas
	// invoke an inorder traversal of the tree
	public void inorder() { inorder(root); }
	private void inorder(Node n) {
		if (n != null) {
			inorder (n.left);
			StdOut.println (n.key + ":" + n.value.toString());
			inorder (n.right);
		}
	}

	public int height() {
		return height(root);
	}
	private int height(Node x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

}
