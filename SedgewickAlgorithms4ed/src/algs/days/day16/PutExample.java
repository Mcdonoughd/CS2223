package algs.days.day16;

public class PutExample {
	public static void main(String[] args) {
		// construct binary tree on p. 401
		BST<String,Integer> bst = new BST<String,Integer>();
		
		bst.put("S", 1);
		bst.put("E", 2);
		bst.put("A", 3);
		bst.put("C", 4);
		bst.put("R", 5);
		bst.put("H", 6);
		bst.put("M", 7);
		bst.put("P", 8);
		bst.put("X", 9);
		
		// now what happens with put L
		bst.put("L", 10);
		
		// output keys in ascending order.
		bst.inorder();
		
	}
}
