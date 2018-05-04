package heineman.hw4;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Note that this bonus bonus question only works with trees that are 
 * @author Laptop
 *
 */
public class Bonus1D {

	/**
	 * For bonus points, modify this method to return a new AVL tree which contains all keys found
	 * in 'one' and 'two'.
	 * 
	 * The only restriction is that you are not allowed to perform any rotations.
	 * 
	 * This code works by merging two arrays containing the sorted keys into a single array, and then
	 * using the earlier code to load up an arbitrary-sized array.
	 * 
	 * @param one
	 * @param two
	 */
	static AVL<Integer> merge (AVL<Integer> one, AVL<Integer> two) {
		Integer[] merged;

		Queue<Integer> oneq = one.keys();
		Queue<Integer> twoq = two.keys();

		if (oneq.size() == 0) {
			merged = fill(twoq);
		} else if (twoq.size() == 0) {
			merged = fill(oneq);
		} else {
			merged = new Integer[oneq.size() + twoq.size()];
			Integer ov = oneq.dequeue();
			Integer tv = twoq.dequeue();
			int idx = 0;
			while (ov != null && tv != null) {
				if (ov < tv) {
					merged[idx++] = ov;
					if (oneq.isEmpty()) { ov = null; } else { ov = oneq.dequeue(); }
				} else {
					merged[idx++] = tv;
					if (twoq.isEmpty()) { tv = null; } else { tv = twoq.dequeue(); }
				}
			}

			if (ov != null) {
				merged[idx++] = ov;
				while (!oneq.isEmpty()) {
					merged[idx++] = oneq.dequeue();
				}
			}

			if (tv != null) {
				merged[idx++] = tv;
				while (!twoq.isEmpty()) {
					merged[idx++] = twoq.dequeue();
				}
			}
		}

		return new AVL<Integer>(merged);
	}

	static Integer[] fill(Queue<Integer> q) {
		Integer[] ret = new Integer[q.size()];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = q.dequeue();
		}
		return ret;
	}

	public static void main(String[] args) {
		AVL<Integer> one = new AVL<Integer>();
		AVL<Integer> two = new AVL<Integer>();

		for (int i = 0; i < 65536; i++) {
			int rnd = StdRandom.uniform(0, 1024);
			one.insert(rnd);
			rnd = StdRandom.uniform(0, 1024);
			two.insert(rnd);
		}

		for (int i : one.keys()) { StdOut.print(i + " "); } StdOut.println();
		for (int i : two.keys()) { StdOut.print(i + " "); } StdOut.println();

		AVL<Integer> comb = merge(one, two);
		//for (int i : comb.keys()) { StdOut.print(i + " "); } StdOut.println();
		System.out.println("num rotations:" + comb.rotations);
		
		// might miss some duplicates, but a quick way to check
		for (int i : one.keys()) {
			if (!comb.contains(i)) { System.err.println ("Combined missing a value:" + i); }
		}
		for (int i : two.keys()) {
			if (!comb.contains(i)) { System.err.println ("Combined missing a value:" + i); }
		}
	}

}
