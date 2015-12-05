package algs.solutions.hw4;

/**
 * Straight BST which can allow duplicated. Instead of being a Symbol table that associated
 * a given value with a key, this stores only values that are comparable.
 * 
 * @param <Value>
 */
public class StraightBST<Value extends Comparable<Value>> {
	int inspectedElementCount = 0;
	
	Node root;                 // root of the tree
	
	class Node {
		Value    val;          
		Node     left, right;  // left and right subtrees

		public Node(Value val) {
			this.val = val;
		}
	}
	
	public boolean mirrorImage(StraightBST<Value> other) {
		return mirrorImage(root, other.root);
	}
	boolean mirrorImage (Node one, Node two) {
		// both missing? Acceptable
		if (one == null && two == null) { return true; }
		
		// handle case where one is missing
		if (one == null || two == null) { return false; }
		
		boolean oneMissingL = true;
		boolean twoMissingL = true;
		boolean oneMissingR = true;
		boolean twoMissingR = true;
		
		if (one.left != null) { oneMissingL = false; }
		if (one.right != null) { oneMissingR = false; }
		if (two.left != null) { twoMissingL = false; }
		if (two.right != null) { twoMissingR = false; }
		
		// determine structural mismatch here...
		if (oneMissingL != twoMissingR || oneMissingR != twoMissingL) { return false; }
		
		return mirrorImage (one.left, two.right) && mirrorImage (one.right, two.left);
	}
	
	public boolean identicalStructure(StraightBST<Value> other) {
		return identicalStructure(root, other.root);
	}
	boolean identicalStructure (Node one, Node two) {
		// both missing? Acceptable
		if (one == null && two == null) { return true; }
		
		// handle case where one is missing
		if (one == null || two == null) { return false; }
		
		boolean oneMissingL = true;
		boolean twoMissingL = true;
		boolean oneMissingR = true;
		boolean twoMissingR = true;
		
		if (one.left != null) { oneMissingL = false; }
		if (one.right != null) { oneMissingR = false; }
		if (two.left != null) { twoMissingL = false; }
		if (two.right != null) { twoMissingR = false; }
		
		// determine structural mismatch here...
		if (oneMissingL != twoMissingL || oneMissingR != twoMissingR) { return false; }
		
		return identicalStructure (one.left, two.left) && identicalStructure (one.right, two.right);
	}
	
	public StraightBST<Value> copy() {
		StraightBST<Value> second = new StraightBST<Value>();
		
		second.root = copy(root);
		return second;
	}
	
	Node copy (Node parent) {
		if (parent == null) { return null; }
		
		Node copy = new Node(parent.val);
		copy.left = copy(parent.left);
		copy.right = copy(parent.right);
		
		return copy;
	}

	/** Need this once you start defining alternate constructors. */
	public StraightBST() { }
	
	/** 
	 * Assuming v is sorted array of size 2^k-1, then this will construct balanced binary
	 * search tree. 
	 */
	public StraightBST(Value v[]) {
		//root = load (v, 0, v.length-1);
		
		imperative (v, 0, v.length-1);
	}

	void imperative (Value v[], int lo, int hi) {
		int mid = (lo + hi)/2;
		add(v[mid]);
		
		// now level by level add proper values from left to right. Observe
		// the relationship between the values. Start with offset of 2^(k-2)
		// and distance of 2^(k-1).
		int offset = (v.length+1)/4;
		int distance = (v.length+1)/2;
		
		while (distance > 1) {
			for (int i = offset; i <= v.length; i += distance) {
				Value k = v[i-1];
				add(k);
			}
			distance /= 2;
			offset /= 2;
		}
	}
	
	Node load (Value v[], int lo, int hi) {
		if (hi < lo) { return null; }
		
		int mid = (lo + hi)/2;
		Node parent = new Node(v[mid]);
		parent.left =  load(v, lo, mid-1);
		parent.right = load(v, mid+1, hi);
		return parent;
	}
	
	
	
	
	public boolean isEmpty() { return root == null; }

	// One-line method for containment. 
	public boolean contains(Value v) { return get(v) != null; }

	/** Search parent. */
	public Value get(Value v)        { return get(root, v); }
	
	Value get(Node parent, Value v) {
		if (parent == null) return null;
		
		int cmp = v.compareTo(parent.val);
		
		if      (cmp < 0) return get(parent.left, v);
		else if (cmp > 0) return get(parent.right, v);
		else              return parent.val;
	}

	/** Invoke put on parent, should it exist. */
	public void add(Value val) {
		root = add(root, val);
	}

	private Node add(Node parent, Value v) {
		if (parent == null) return new Node(v);
		
		int cmp = v.compareTo(parent.val);
		if      (cmp <= 0) parent.left  = add(parent.left, v);
		else if (cmp > 0) parent.right = add(parent.right, v);
		
		return parent;
	}
	
	/**
	 * During an IN-order traversal, you first visit each node in the left subtree (should
	 * one exist), then the current node, then the nodes in the right subtree.
	 * 
	 * @param v    contains logic for processing each value.
	 */
	public void inorder(IVisitor<Value> v) {
		inorder(root, v, 0);
	}
	
	/** 
	 * Helper method for inorder traversal.
	 * 
	 * Note how with each traversal, the distance from root increases (i.e., the level).
	 */
	void inorder(Node node, IVisitor<Value> v, int level) {
		if (node == null) { return; }
		
		inorder(node.left, v, level+1);
		
		// process value of this node, on the given level.
		v.process(node.val, level);
		
		inorder(node.right, v, level+1);
	}

	public int height() { return height(root); }
	private int height(Node n) {
		if (n == null) { return -1; }
		
		return 1+Math.max(height(n.left), height(n.right));
	}
	
	public Value max() {
		if (root == null) { return null; }
		Node n = root;
		while (n.right != null) {
			n = n.right;
		}
		
		return n.val;
	}
	
	/** Implement method to return Value when removing smallest element. */
	public void deleteMin() {
		if (root == null) { return; }
		root = deleteMin(root);
	}
	
	Node deleteMin(Node parent) {
		if (parent.left == null) {
			return parent.right;
		}
		
		parent.left = deleteMin(parent.left);
		return parent;
	}
	
	/** Implement method to return Value when removing largest element. */
	public void deleteMax() {
		if (root == null) { return; }
		root = deleteMax(root);
	}
	
	Node deleteMax(Node parent) {
		inspectedElementCount++;
		if (parent.right == null) {
			return parent.left;
		}
		
		parent.right = deleteMax(parent.right);
		return parent;
	}
}
