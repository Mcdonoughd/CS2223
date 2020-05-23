package algs.hw3;

import edu.princeton.cs.algs4.StdOut;

/**
 * Proposition C (From Page 403 in Sedgewick)
 *
 *	 The number of compares used for a search hit ending at a given node is 1 plus the depth.
 *	 Adding the depths of all nodes, we get a quantity known as the internal path length of the tree.
 *	 
 *	 Thus the desired quantity (i.e., average # of compares on random BST) is 1 plus the average
 *	 internal path length of the BST. Let Cn be the internal path length of a BST built from 
 *	 inserting N randomly ordered distinct keys, so the average cost of a search hit is 1 + Cn/N.
 *	 
 *	 Compute that CN ~ 2N ln N, thus average is ~ 2 ln N, where ln is natural logarithm. Or as I
 *	 prefer to state it, average is ~ 1.39 log N in base 2.
 *
 * Proposition D (From page 404 in Sedgewick)
 *   
 *   Insertions and search misses in a BST built from N random keys require ~2 ln N (about 1.39 log N)
 *   
 * Note: you will change nearly everything in this class.
 */
public class PropositionCD {
	
	static void propc() {
		// this is how to construct a BST with Double values as Keys. Note that the value is a Boolean 
		// object, and for this assignment, simply use True as the value, since it will be ignored.
		BST<Double,Boolean> tree = new BST<Double,Boolean>();
		
		double d = 1.4;
		tree.put(d, true);
		
		StdOut.println("value associated with 1.4:" + tree.get(1.4));
		
		StdOut.println("value associated with 2.3:" + tree.get(2.3));
	}
	
	static void propd() {
		// this is how to construct a BST with Double values as Keys. Note that the value is a Boolean 
		// object, and for this assignment, simply use True as the value, since it will be ignored.
		BST<Double,Boolean> tree = new BST<Double,Boolean>();
		
		double d = 1.4;
		tree.put(d, true);
		
		StdOut.println("value associated with 1.4:" + tree.get(1.4));
		
		StdOut.println("value associated with 2.3:" + tree.get(2.3));
	}
	
	public static void main(String[] args) {
		propc();
		propd();
	}
}
