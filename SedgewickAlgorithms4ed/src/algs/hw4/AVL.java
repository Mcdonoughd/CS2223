package algs.hw4;

import edu.princeton.cs.algs4.StdOut;

/**
 * Minimum implementation of AVL balanced binary tree.
 * 
 * Note this does not offer SymbolTable behavior, but rather just stores Key objects.
 *
 * Duplicate values can be inserted into this tree.
 * 
 * @param <Key>
 */
public class AVL<Key extends Comparable<Key>> {

	Node root;               // root of the tree
	public int rotations;           // for instrumentation
	class Node {
		Key    key;        
		Node   left, right;  // left and right subtrees
		int    height;       // need to know how TALL this subtree is (from leaf, not root).
		int    N;            // total number of nodes in sub-tree rooted at this node (includes self)

		public Node(Key key, int n) {
			this.key = key;
			this.N = n;
		}

		public String toString() {
			String leftS = "";
			if (left != null) leftS = "left:" + left.key;
			String rightS = "";
			if (right != null) rightS = "right:" + right.key;
			
			return "[" + key + " "+ leftS + " " + rightS + "]";
		}
	}

	public boolean isEmpty() { return root == null; }
	public int size() { return size(root); }
	
	// Helper method that deals with "empty nodes"
	private int size(Node node) {
        if (node == null) return 0;
        
        return node.N;
    }
	public String toString() { return "<bst: root=" + root +">"; }
	
	/** Default constructor. */
	public AVL() { }
	
	/**
	 * Complete this constructor which takes in an ordered array of 2^k - 1 elements and results in the
	 * construction of an AVL tree that is complete
	 * @param ordered
	 */
	public AVL(Key[] ordered) {
		/** FILL IN HERE. **/
		rotations = 0;
		//StdOut.println ("number of rotations:" + rotations);
	}
	
	/** 
	 * Inserts into AVL tree all keys found in 'other'.
	 * 
	 * Upon completion, the 'other' AVL will be untouched.
	 * 
	 * @param other
	 */
	public void merge(AVL<Key> other) {
		/** FILL IN HERE. **/
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

	/** Update height for node AS WELL AS N. */
	void computeHeight(Node node) {
		int height = -1;
		if (node.left != null) {
			height = Math.max(height, node.left.height);
		}
		if (node.right != null) {
			height = Math.max(height, node.right.height);
		}

		node.height = height + 1;
		node.N = 1 + size(node.left) + size(node.right);
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
		if (parent == null) return new Node(key, 1);

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
	
	/** Return queue (linked list) of all keys in order. */
	public Queue<Key> keys() {
		Queue<Key> queue = new Queue<Key>();
		keys(root, queue);
		return queue;
	} 

	/** Helper method for iterator. */
	private void keys(Node node, Queue<Key> queue) { 
		if (node == null) return; 

		// much like a traversal; builds up state in the queue.
		keys(node.left, queue); 
		queue.enqueue(node.key); 
		keys(node.right, queue); 
	}
}
