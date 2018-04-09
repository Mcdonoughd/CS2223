package algs.days.day17;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class DeleteExample {
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
		
		
		// now what happens when we remove minimum repeatedly
		while (!bst.isEmpty()) {
			StdOut.println ("-----------");
			bst.inorder();
			StdOut.println ("Key to remove: ");
			String key = StdIn.readString();
			bst.delete(key);
		}
		
		StdOut.println ("Tree is Empty.");
	}
}
