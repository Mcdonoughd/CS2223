package algs.days.day17;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class TraverseBST {
	public static void main(String[] args) {
		// construct binary tree on p. 401
		BST<String> bst = new BST<String>();

		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			bst.insert(item);
		}

		System.out.println("Preorder Traversal:");
		bst.preorder();

		System.out.println("Inorder Traversal:");
		bst.inorder();
		
		System.out.println("Postorder Traversal:");
		bst.postorder();

	}
}
