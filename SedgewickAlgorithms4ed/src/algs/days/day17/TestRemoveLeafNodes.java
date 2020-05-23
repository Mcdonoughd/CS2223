package algs.days.day17;

public class TestRemoveLeafNodes {
	public static void main(String[] args) {
		BST<String> bst = new BST<String>();
		
		bst.insert("M");
		bst.insert("A");
		bst.insert("T");
		bst.insert("W");
		
		bst.removeAllLeafNodes();
		
		for (String key : bst.keys()) {
			System.out.println(key);
		}
	}
}
