package dmcdonough.hw4;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Bonus1D {

	/**
	 * For bonus points, modify this method to return a new AVL tree which is contains all keys found
	 * in 'one' and 'two'.
	 * 
	 * The only restriction is that you are not allowed to perform any rotations.
	 *  
	 * @param one
	 * @param two
	 */
	static AVL<Integer> merge (AVL<Integer> one, AVL<Integer> two) {
		
		// FIX AND REPLACE
		return new AVL<Integer>();
	}

	public static void main(String[] args) {
		AVL<Integer> one = new AVL<Integer>();
		AVL<Integer> two = new AVL<Integer>();

		for (int i = 0; i < 64; i++) {
			int rnd = StdRandom.uniform(0, 32);
			one.insert(rnd);
			rnd = StdRandom.uniform(0, 32);
			two.insert(rnd);
		}

		for (int i : one.keys()) { StdOut.print(i + " "); } StdOut.println();
		for (int i : two.keys()) { StdOut.print(i + " "); } StdOut.println();

		AVL<Integer> comb = merge(one, two);
		for (int i : comb.keys()) { StdOut.print(i + " "); } StdOut.println();
		System.out.println("num rotations:" + comb.rotations);
	}

}
