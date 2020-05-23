package algs.days.day23;

import algs.days.day18.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * Minimum implementation of AVL balanced binary tree.
 * 
 * Note this does not offer SymbolTable behavior, but rather just stores Key objects.
 *
 * Worth comparing against Red-Black BST.
 * 
 * @param <Key>
 */

public class AVL<Key extends Comparable<Key>> {

	Node root;               // root of the tree
	int rotations;           // for instrumentation
	class Node {
		Key    key;        
		Node   left, right;  // left and right subtrees
		int    height;       // need to know how TALL this subtree is (from leaf, not root).

		public Node(Key key) {
			this.key = key;
		}

		public String toString() {
			String leftS = "";
			if (left != null) leftS = "left:" + left.key;
			String rightS = "";
			if (right != null) rightS = "right:" + right.key;
			
			return "[" + key + " "+ leftS + " " + rightS + "]";
		}
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
	public boolean contains(Key key) { return get(root, key); }
	private boolean get(Node parent, Key key) {
		if (parent == null) return false;

		int cmp = key.compareTo(parent.key);

		if      (cmp < 0) return get(parent.left, key);
		else if (cmp > 0) return get(parent.right, key);
		else              return true;
	}

	/** Invoke put on parent, should it exist. */
	public void insert(Key key) {
		root = insert(root, key);
	}

	private Node insert(Node parent, Key key) {
		if (parent == null) return new Node(key);

		int cmp = key.compareTo(parent.key);
		if (cmp <= 0) {
			parent.left  = insert(parent.left,  key);
			if (heightDifference(parent) == 2) {
				if (key.compareTo(parent.left.key) <= 0) {
					parent = rotateRight(parent);
				} else {
					parent = rotateLeftRight(parent);
				}
			}
		} else {
			parent.right = insert(parent.right, key);
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
		rotations++;
		Node newRoot = parent.left;
		Node grandson = newRoot.right;
		parent.left = grandson;
		newRoot.right = parent;

		computeHeight(parent);
		return newRoot;
	}

	/** Perform left rotation around given node. */
	private Node rotateLeft(Node parent) {
		rotations++;
		Node newRoot = parent.right;
		Node grandson = newRoot.left;
		parent.right = grandson;
		newRoot.left = parent;

		computeHeight(parent);
		return newRoot;
	}

	/** Perform left, then right rotation around given node. */
	private Node rotateLeftRight(Node parent) {
		rotations +=2;
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
		rotations +=2;
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

	public Key min() { return min(root).key; }

	private Node min (Node parent) {
		if (parent.left == null) { return parent; }
		return min(parent.left);
	}

	public Key nonRecursiveMin() {
		Node n = root;

		while (n.left != null) {
			n = n.left;
		}

		return n.key;
	}


	// traversal ideas
	// invoke an inorder traversal of the tree
	public void inorder() { inorder(root); }
	private void inorder(Node n) {
		if (n != null) {
			inorder (n.left);
			StdOut.println (n.key);
			inorder (n.right);
		}
	}

	// traversal ideas
	// invoke a pre-order traversal of the tree
	public void postorder() { postorder(root); }
	private void postorder(Node n) {
		if (n != null) {
			postorder (n.left);
			postorder (n.right);
			StdOut.println (n.key);
		}
	}

	/** Implement method to return Value when removing largest element. */
	public void deleteMin() {
		if (root != null) { root = deleteMin(root);	}
	}

	Node deleteMin(Node parent) {
		if (parent.left == null) {
			return parent.right;
		}

		parent.left = deleteMin(parent.left);
		return parent;
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
			parent.right = fastDelete (parent.right, childKey);
			parent.key = childKey;

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

	/**
	 * Returns all keys in the symbol table as an <tt>Iterable</tt>.
	 * To iterate over all of the keys in the symbol table named <tt>st</tt>,
	 * use the foreach notation: <tt>for (Key key : st.keys())</tt>.
	 *
	 * @return all keys in the symbol table
	 */
	public Iterable<Key> keys() { return keys(min(), max()); }

	public Iterable<Key> keys(Key lo, Key hi) {
		Queue<Key> queue = new Queue<Key>();
		keys(root, queue, lo, hi);
		return queue;
	} 

	private void keys(Node node, Queue<Key> queue, Key lo, Key hi) { 
		if (node == null) return; 

		// check if contained within this range
		int cmplo = lo.compareTo(node.key); 
		int cmphi = hi.compareTo(node.key);

		// much like a traversal; builds up state in the queue.
		if (cmplo < 0)                 keys(node.left, queue, lo, hi); 
		if (cmplo <= 0 && cmphi >= 0)  queue.enqueue(node.key); 
		if (cmphi > 0)                 keys(node.right, queue, lo, hi); 
	}

	public int height() {
		return height(root);
	}
	private int height(Node x) {
		if (x == null) return -1;
		return 1 + Math.max(height(x.left), height(x.right));
	}

}
