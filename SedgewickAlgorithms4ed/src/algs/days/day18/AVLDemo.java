package algs.days.day18;

public class AVLDemo {
	public static void main(String[] args) {
		AVL<Integer> avl = new AVL<Integer>();
		avl.insert(50);
		avl.insert(30);
		avl.insert(10);   // what happens here? Rotate Right
		
		avl = new AVL<Integer>();
		avl.insert(50);
		avl.insert(10);
		avl.insert(30);   // what happens here? Rotate Left-Right
		
		avl = new AVL<Integer>();
		avl.insert(10);
		avl.insert(50);
		avl.insert(30);   // what happens here? Rotate Right-Left
		
		avl = new AVL<Integer>();
		avl.insert(10);
		avl.insert(30);
		avl.insert(50);   // what happens here? Rotate Left
	}
}
