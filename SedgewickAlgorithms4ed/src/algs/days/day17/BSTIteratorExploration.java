package algs.days.day17;

import edu.princeton.cs.algs4.StdOut;

public class BSTIteratorExploration {
	public static void main(String[] args) {
		// construct binary tree on p. 401
		BST<String,Integer> bst = new BST<String,Integer>();

		bst.put("R", 1);
		bst.put("U", 2);
		bst.put("A", 3);
		bst.put("T", 4);
		bst.put("W", 5);
		bst.put("P", 6);
		bst.put("I", 7);

		// This is very different from the traversal, since we are able
		// to act on the keys, whereas the traversal simply printed the
		// values out to the screen.
		for (String s : bst.keys()) {
			StdOut.println(s);
		}
	}
}
