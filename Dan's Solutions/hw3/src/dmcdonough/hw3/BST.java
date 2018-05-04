package dmcdonough.hw3;

import edu.princeton.cs.algs4.StdRandom;

/**
 * You will modify this class to support Q4 on the homework assignment.
 * 
 * @author heineman
 *
 * @param <Key>
 * @param <Value>
 */
public class BST<Key extends Comparable<Key>, Value> {

	Node root;               // root of the tree
	
	class Node {
		Key    key;          
		Value  val;      
		int height;
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
		
		int cmp = key.compareTo(parent.key);
		
		if      (cmp < 0) return get(parent.left, key);
		else if (cmp > 0) return get(parent.right, key);
		else              return parent.val;
	}
	
	public boolean is_in(Key key)  { return is_in(root, key); }
	
	private boolean is_in(Node parent, Key key) {
		if (parent == null) return false;
		
		int cmp = key.compareTo(parent.key);
		
		if      (cmp < 0) return is_in(parent.left, key);
		else if (cmp > 0) return is_in(parent.right, key);
		else              return true;
	}
	
	/** Search parent. */
	public int get_comp(Key key) { return get_comp(root, key,0); }
	
	private int get_comp(Node parent, Key key,int comp) {
		if (parent == null) return comp;
		
		int cmp = key.compareTo(parent.key);
		
		if      (cmp < 0) {
			return get_comp(parent.left, key,comp+=1); 
				}
		else if (cmp > 0) {
			return get_comp(parent.right, key,comp+=2);
		}
		else {
			//Value i = comp;
			return comp+=1;
		}
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
	
	
	
	public static int totalHeightSum = 0;

    int getCN(Node node) {
        if (node == null)
            return -1;
        if (node.left == null && node.right == null) {
            return totalHeightSum++;
        }
        else
            return getCN(node.left) + getCN(node.right);
    }

	
	
}
