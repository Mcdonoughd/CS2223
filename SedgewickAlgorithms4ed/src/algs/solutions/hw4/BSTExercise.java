package algs.solutions.hw4;

import edu.princeton.cs.algs4.StdRandom;

public class BSTExercise {
	public static void main(String[] args) {
		
		// construct a straight BST and output its contents in order
		
		StraightBST<Integer> bst = new StraightBST<Integer>();
		for (int i = 0; i < 15; i++) {
			bst.add(StdRandom.uniform(0,100));
		}
		
		bst.inorder(new FormatTree<Integer>());
	}
}
