package algs.days.day16;

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
	
	// new methods for discussion
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
    	if (n == null) { return; }
    	
		inorder (n.left);
		StdOut.println (n.key);
		inorder (n.right);
    }
    
    // traversal ideas
    // invoke a pre-order traversal of the tree
    public void preorder() { preorder(root); }
    private void preorder(Node n) {
    	if (n == null) { return; }
		StdOut.println (n.key);
		
		preorder (n.left);
		preorder (n.right);
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
}
