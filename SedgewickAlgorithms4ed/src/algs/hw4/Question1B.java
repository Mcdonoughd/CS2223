package algs.hw4;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Question1B {

	/**
	 * Once you have merge working, complete this code so it works.
	 */
	public static void main(String[] args) {
		AVL<Integer> one = new AVL<Integer>();
		AVL<Integer> oneCopy = new AVL<Integer>();
		AVL<Integer> two = new AVL<Integer>();

		// Create two random AVLs consisting of 64 random numbers, all drawn from the
		// range [0, 32]. Note that there will be duplicates, and the AVL tree has 
		// been modified to allow duplicate key values. Note that 'oneCopy' is a full
		// copy of 'one'.
		for (int i = 0; i < 32; i++) {
			int rnd = StdRandom.uniform(0, 32);
			one.insert(rnd);
			oneCopy.insert(rnd);
			rnd = StdRandom.uniform(0, 32);
			two.insert(rnd);
		}
		
		StdOut.println("one contains a total of:" + oneCopy.size());
		StdOut.println("two contains a total of:" + two.size());

		// merge the values from two into 'one'. Now 'one' contains all values from 
		// 'oneCopy' and 'two'.
		one.merge(two);
		
		StdOut.println("combined contains a total of:" + one.size());
		if (one.size() != oneCopy.size() + two.size()) {
			StdOut.println ("  *** NOT PROPERLY MERGED ***");
			System.exit(-1);
		}

		// create a table that will keep track of the number of times you have seen a specific key.
		SeparateChainingHashST<Integer, Integer> table = new SeparateChainingHashST<>();

		// You must use the 'table' object to ensure that all keys appear in 'one', with 
		// the right number of repetitions. Use this table to keep a count of how many times a given 
		// key appears in the original two AVL trees (oneCopy and two). Then reduce the table counts
		// once for every key that appears in the combined AVL tree, one. Eventually, if all goes
		// well, the size of the table will once again be zero, and you will have then confirmed
		// that your 'merge' method works properly.
		
		// Fill in Here. 
		
		// done
		StdOut.println("Number of values remaining in table should be zero:" + table.size());
		if (table.size() != 0) {
			StdOut.println ("  *** UNABLE TO VALIDATE ***");
		}
	}

}
