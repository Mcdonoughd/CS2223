package algs.days.day16;

import edu.princeton.cs.algs4.StdOut;

public class FloorExample {
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
		
		// now what happens with put L
		StdOut.println("Floor of R:" + bst.floor("R"));
		StdOut.println("Floor of S:" + bst.floor("S"));
		
		// show in pre-order traversal
		bst.preorder();
	}
}
