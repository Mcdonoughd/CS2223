package algs.days.day17;

import edu.princeton.cs.algs4.StdOut;

public class Quiz1 {
	public static void main(String[] args) {
		BST<String> bst = new BST<String>();
		
		bst.insert("D");
		bst.insert("S");
		bst.insert("B");
		bst.insert("T");
		bst.insert("C");
		bst.insert("L");
		
		
		bst.delete("S");
		
		
		StdOut.println(bst.root);
	}
}
