package heineman.hw4;

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

		// Fill in Here. You must use the 'table' object to ensure that all keys appear in 'one', with 
		// the right number of repetitions. Use this table to keep a count of how many times a given 
		// key appears in the original two AVL trees (oneCopy and two).
		for (Integer key : oneCopy.keys()) {
			if (table.contains(key)) {
				table.put(key,  table.get(key) + 1);
			} else {
				table.put(key,  1);
			}
		}

		for (Integer key : two.keys()) {
			if (table.contains(key)) {
				table.put(key,  table.get(key) + 1);
			} else {
				table.put(key,  1);
			}
		}

		// now go through and remove all keys, one by one, as found in 'one' which shoudl contain the 
		// combined set of keys from both AVL trees.
		for (Integer key : one.keys()) {
			if (table.contains(key)) {
				int count = table.get(key) - 1;
				if (count == 0) {
					table.delete(key);
				} else {
					table.put(key,  count);
				}
			}
		}
		
		// done
		StdOut.println("Number of values remaining in table should be zero:" + table.size());
		if (table.size() != 0) {
			StdOut.println ("  *** UNABLE TO VALIDATE ***");
		}
	}

}
