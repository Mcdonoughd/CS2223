package algs.days.day23;

import edu.princeton.cs.algs4.StdOut;

/**
 * Add the following numbers to an AVL tree in this order: 1, 2, 3, 4, 5
 * 
 * What is its structure?
 */
public class SampleExamQuestionAVL {
	public static void main(String[] args) {
		AVL<Integer> tree = new AVL<Integer>();
		for (int i = 1; i <= 5; i++) {
			tree.insert(i);
		}
		
		StdOut.println("root is:" + tree.root.key);
		StdOut.println("root.left is:" + tree.root.left.key);
		StdOut.println("root.right is:" + tree.root.right.key);
		StdOut.println("root.right.left is:" + tree.root.right.left.key);
		StdOut.println("root.right.right is:" + tree.root.right.right.key);
		
		
		// now ask a harder question. Insert each of the first N numbers, and inspect 
		// the root when N is 2^k-1. What are the values of root for each of these given
		// trees.
		tree = new AVL<Integer>();
		int pwr = 1;
		for (int i = 1; i <= 10000; i++) {
			tree.insert(i);
			if (i == pwr) {
				StdOut.printf("For T(%d), the root is %d\n", pwr, tree.root.key);
				pwr = pwr*2 + 1;  // get to next value.
			}
		}
		
		// Check if there is a sequence match for first N values of root.
		tree = new AVL<Integer>();
		int lastRoot = -1;
		for (int i = 1; i <= 10000; i++) {
			tree.insert(i);
			if (tree.root.key != lastRoot) {
				StdOut.printf("%d,", tree.root.key);
				lastRoot = tree.root.key;
			}
		}
		StdOut.println();
		
		// Confirm number of times that a given value is the root value.
		// Note this is for the first time interesting:
		// 1 (2),2 (3),4 (6),8 (12),16 (24),32 (48),64 (96),128 (192),256 (384)...
		//
		// Thus each value is root for twice as long as it predecessor (once you get to 4)
		tree = new AVL<Integer>();
		lastRoot = -1;
		int ct = 0;
		for (int i = 1; i <= 10000; i++) {
			tree.insert(i);
			if (tree.root.key != lastRoot) {
				if (ct != 0) {
					StdOut.printf("%d (%d),", lastRoot, ct);
				}
				lastRoot = tree.root.key;
				ct = 1;
			} else {
				ct++;
			}
		}
		StdOut.println();
	}
}
