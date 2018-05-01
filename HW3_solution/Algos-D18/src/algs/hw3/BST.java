

public class BST<Key extends Comparable<Key>, Value> {
	static int numComparison;
	
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
	}

	public boolean isEmpty() { return size() == 0; }

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
		
		numComparison++;
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
	
	/**
	 * To compute the internal path length, you need the sum total of the depths of all nodes 
	 * in the tree. This recursive method does it most efficiently, without requiring any 
	 * additional storage.
	 */
	public int sumDepths() {
		if (root == null) { return 0; }
		
		// call helper method by computing depth of sub-tree assuming its root has depth of 0
		return sumDepth(root, 0);
	}
	
	/**
	 * Assuming subtree rooted at parent has given depth, returns the sum of all depths of the
	 * descendant nodes.
	 *  
	 * @param parent
	 * @param depth
	 * @return
	 */
	private int sumDepth(Node parent, int depth) {
		if (parent == null) return 0;  // nothing
		
		// return whatever parent's depth is assumed to be, plus the sum of the depths of left and right
		// sub-trees, whose respective parents are at the given depth+1.
		return depth + sumDepth(parent.left, depth+1) + sumDepth(parent.right, depth+1);
	}
}
