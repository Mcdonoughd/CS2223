package algs.days.day17;

import edu.princeton.cs.algs4.StdOut;

public class BST<Key extends Comparable<Key>, Value> {

	Node root;               // root of the tree

	class Node {
		Key    key;          
		Value  val;         
		Node   left, right;  // left and right subtrees
		int    N;            // number of nodes in subtree

		public Node(Key key, Value val, int N) {
			this.key = key;
			this.val = val;
			this.N = N;
		}

		public String toString() { return "[" + key + "]"; }
	}

	public boolean isEmpty() { return size() == 0; }

	public String toString() { return "<bst: root=" + root +", N=" + size(root) + ">"; }

	/** Return number of key-value pairs in ST. */
	public int size()                { return size(root); }

	// Helper method that deals with "empty nodes"
	private int size(Node node) {
		if (node == null) return 0;

		return node.N;
	}

	// One-line method for containment. 
	public boolean contains(Key key) { return get(key) != null; }

	/** Search parent. */
	public Value get(Key key)        { return get(root, key); }

	private Value get(Node parent, Key key) {
		if (parent == null) return null;

		int cmp = key.compareTo(parent.key);

		if      (cmp < 0) return get(parent.left, key);
		else if (cmp > 0) return get(parent.right, key);
		else              return parent.val;
	}

	/** Invoke put on parent, should it exist. */
	public void put(Key key, Value val) {
		root = put(root, key, val);
	}

	private Node put(Node parent, Key key, Value val) {
		if (parent == null) return new Node(key, val, 1);

		int cmp = key.compareTo(parent.key);
		if      (cmp < 0) parent.left  = put(parent.left,  key, val);
		else if (cmp > 0) parent.right = put(parent.right, key, val);
		else              parent.val   = val;

		parent.N = 1 + size(parent.left) + size(parent.right);
		return parent;
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

	public Key floor(Key key) {
		Node rc = floor(root, key);
		if (rc == null) return null;
		return rc.key;
	} 

	private Node floor(Node parent, Key key) {
		if (parent == null) return null;

		int cmp = key.compareTo(parent.key);
		if (cmp == 0) return parent;                   // found? Then this is floor
				if (cmp <  0) return floor(parent.left, key);  // smaller? must be in left subtree

				Node t = floor(parent.right, key);             // greater? we might be floor, but
				if (t != null) return t;                       // only if 
				else return parent; 
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
	public void preorder() { preorder(root); }
	private void preorder(Node n) {
		if (n != null) {
			StdOut.println (n.key);

			preorder (n.left);
			preorder (n.right);
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
		parent.N = size(parent.left) + size(parent.right) + 1;
		return parent;
	}

	// new methods for discussion
	public Key max() { return max(root).key; }

	private Node max (Node parent) {
		if (parent.right == null) { return parent; }
		return max(parent.right);
	}
	
	public void delete(Key key) { root = delete(root, key); }

	private Node delete(Node parent, Key key) {
		if (parent == null) return null;

		// recurse until you find parent with this key.
		int cmp = key.compareTo(parent.key);
		if      (cmp < 0) parent.left  = delete(parent.left,  key);
		else if (cmp > 0) parent.right = delete(parent.right, key);
		else { 
			// handle easy cases first:
			if (parent.right == null) return parent.left;
			if (parent.left  == null) return parent.right;

			// has two children: Plan on returning min of our right child
			Node old = parent;
			parent = min(old.right);     // will eventually be "new parent"

			// Note this is a simpler case: Delete min from right subtree
			// and DON'T FORGET to stitch back in the original left child
			parent.right = deleteMin(old.right);   
			parent.left = old.left;
		} 

		// as recursions unwind, update size appropriately
		parent.N = size(parent.left) + size(parent.right) + 1;
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

}
