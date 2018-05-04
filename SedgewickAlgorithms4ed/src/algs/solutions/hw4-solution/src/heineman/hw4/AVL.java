package heineman.hw4;

import edu.princeton.cs.algs4.StdOut;

/**
 * Minimum implementation of AVL balanced binary tree.
 * 
 * Note this does not offer SymbolTable behavior, but rather just stores Key objects.
 *
 * Duplicate values can be inserted into this tree. This was done to make the homework
 * a bit more complicated. In the general case, you would likely not be inserting duplicate
 * values into a tree, but rather, if this is important, use a count for the number of keys
 * in the tree to reduce storage.
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

	public boolean isEmpty() { return root != null; }
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
		// Option 1, which works directly with nodes
		// root = load(ordered, 0, ordered.length-1);
		
		// Option 2, which works iteratively. HOWEVER, with duplicate values, this
		// will force some rotations because of the nature of the left-leaning insert
		loadIterative(ordered);   
		
		// Option 3, which recursively builds trees
		//AVL<Key> newTree = loadAVL(ordered, 0, ordered.length-1);
		//root = newTree.root;
		StdOut.println ("number of rotations:" + rotations);
	}
	
	/**
	 * For case with 2^k-1 nodes, can use simple iteration, based on following observation:
	 * 
	 * 8:          8
	 * 4:     4       12
	 * 2:   2 6     10 14
	 * 1:  1 3 5 7 9 11 13 15
	 * 
	 * Only works for 2^k-1 nodes.
	 * 
	 * @param v
	 * @return
	 */
	void loadIterative (Key v[]) {
		// compute ROOT
		int n = (v.length+1)/2;
		int numToAdd = 1;
		while (n >= 1) {
			int added = 0;
			int val = n;
			while (added < numToAdd) {
				insert(v[val-1]);      // index based arithmetic from 0
				added++;
				val += 2*n;            // advance to next one, if it exists.
			}
			n = n / 2;
			numToAdd *= 2;
		}
		
	}
	
	/** 
	 * Recursively loads trees in order. Doesn't even call insert.
	 * 
	 * Note when called with size not equal to 2^k-1, then tree still works, but it is not heap-shaped
	 * 
	 *              6
	 *          /      \
	 *         3        9
	 *        / \     /  \ 
 	 *       1   4   7    11
	 *        \   \   \   / \
	 *         2   5   8 10  12
	 */
	Node load (Key v[], int lo, int hi) {
		// base case is nonexistent. stop now
		if (hi < lo) { return null; }
		
		// low each half, using the median as the root.
		int mid = (lo + hi)/2;
		Node parent = new Node(v[mid], 1);
		parent.left =  load(v, lo, mid-1);
		parent.right = load(v, mid+1, hi);
		
		// must make sure to update statistics properly
		computeHeight(parent);
		return parent;
	}
	
	/**
	 * In this solution, you construct an AVL tree and perform surgery on the 
	 * left and right children links.
	 * 
	 * @param v
	 * @param lo
	 * @param hi
	 * @return
	 */
	AVL<Key> loadAVL (Key v[], int lo, int hi) {
		// null case: This is only here for collections whose length are not 2^k-1
		if (hi < lo) { return new AVL<Key>(); }
		
		// base case has single value. No rotations.
		if (hi == lo) { 
			AVL<Key> tree = new AVL<Key>();
			tree.insert(v[lo]);
			return tree;
		}
		
		// low each half, using the median as the root.
		int mid = (lo + hi)/2;
		AVL<Key> tree = new AVL<Key>();
		tree.insert(v[mid]);
		
		AVL<Key> left = loadAVL(v, lo, mid-1);
		AVL<Key> right = loadAVL(v, mid+1, hi);
		
		tree.root.left = left.root;
		tree.root.right = right.root;
		
		// must make sure to update statistics properly
		computeHeight(tree.root);
		return tree;
	}
	
	/** 
	 * Inserts into AVL tree all keys found in 'other'.
	 * 
	 * Upon completion, the 'other' AVL will be untouched.
	 * 
	 * @param other
	 */
	public void merge(AVL<Key> other) {
		for (Key k : other.keys()) {
			this.insert(k);
		}
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
