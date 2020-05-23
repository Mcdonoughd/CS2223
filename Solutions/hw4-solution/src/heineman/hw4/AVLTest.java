package heineman.hw4;


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class AVLTest {

	public static void main(String[] args) {
		for (int n = 2; n <= 128; n *= 2) {
			StdOut.println ("Computing for " + n);
			runTrial(n);
		}
	}

	static void runTrial (int n) {
		AVL<Integer> avl = new AVL<Integer>();
		
		// create random AVL tree with N nodes (note duplicates can exist).
		for (int i = 0; i < n; i++) {
			int rnd = StdRandom.uniform(0, n);
			avl.insert(rnd);
		}

		// merge into single array.
		// then reestablish
		for (int t : avl.keys()) {
			System.out.print(t + " ");
		}
		System.out.println();
	}
}