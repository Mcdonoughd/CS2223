package algs.solutions.hw4;

import edu.princeton.cs.algs4.StdOut;

public class Q3 {
	public static void main(String[] args) {
		int N = 31;

		Integer[] vals = new Integer[N];
		for (int i = 1; i <= N; i++) {
			vals[i-1] = i;
		}

		StraightBST<Integer> bst = new StraightBST<Integer>(vals);
		StraightBST<Integer> copy = bst.copy();

		// a more complicated example of inorder traversal.
		copy.inorder(new FormatTree());

		// now do exact same structure. Remove max in all cases, to validate that same structure 
		// appears in both.
		while (!bst.isEmpty()) {
			bst.deleteMax();
			copy.deleteMax();

			if (!bst.identicalStructure(copy)) {
				StdOut.println ("SOMEHOW NOT SAME STRUCTURE");
				break;
			}
		}

		// now do mirror image by deleteMin and deleteMax on the copy.
		bst = new StraightBST<Integer>(vals);
		copy = bst.copy();
		
		// now do exact same structure. Remove max in one, min in the other, and validate mirror image
		// appears in both.
		while (!bst.isEmpty()) {
			bst.deleteMax();
			copy.deleteMin();

			if (!bst.mirrorImage(copy)) {
				StdOut.println ("SOMEHOW NOT SAME STRUCTURE IN MIRROR IMAGE");
				break;
			}
		}
	}
}
