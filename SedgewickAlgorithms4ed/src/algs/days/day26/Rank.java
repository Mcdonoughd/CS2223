package algs.days.day26;

public class Rank {
	public static void main(String[] args) {
		/** Ignore the value (boolean) associated with the key. */
		BST<Integer, Boolean> bst = new BST<>();
		
		int[] vals = new int[] { 6, 2, 9, 3, 5, 8, 1, 4, 7 };
		
		for (int v : vals) {
			bst.put(v, true);
		}
		
		for (int i = 0; i < 10; i++) {
			System.out.println(i + ":" + bst.select(i));
		}
	}
}
