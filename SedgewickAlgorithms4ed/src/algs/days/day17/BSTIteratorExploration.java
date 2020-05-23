package algs.days.day17;

import edu.princeton.cs.algs4.StdOut;

public class BSTIteratorExploration {
	public static void main(String[] args) {
		// construct binary tree on p. 401
		BST<String> bst = new BST<String>();

		bst.insert("R");
		bst.insert("U");
		bst.insert("A");
		bst.insert("T");
		bst.insert("W");
		bst.insert("P");
		bst.insert("I");

		// This is very different from the traversal, since we are able
		// to act on the keys, whereas the traversal simply printed the
		// values out to the screen.
		for (String s : bst.keys()) {
			StdOut.println(s);
		}
		
		System.out.println("Has three leaves:" + bst.countLeaves());
	}
}
