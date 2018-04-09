package algs.days.day18;


public class BSTIteratorExploration {
	public static void main(String[] args) {
		// construct binary tree on p. 401
		AVL<String> bst = new AVL<String>();

		// woah. this is very funky Java, which you won't normally
		// use, but I am trying to hack a BST.
		bst.insert("K");
		bst.root.left = bst.new Node("C");
		bst.root.right = bst.new Node("C");
		bst.root.left.left = bst.new Node("E");
		bst.root.left.left.right = bst.new Node("W");
		bst.root.left.right = bst.new Node("L");
		bst.root.right.left = bst.new Node("E");
		bst.root.right.left.left = bst.new Node("O");
		bst.root.right.left.right = bst.new Node("M");
		bst.root.right.right = bst.new Node("A");
		bst.root.right.right.right = bst.new Node("B");
		
		bst.postorder();
	}
}
