package algs.solutions.hw2.quincunx;

import java.util.Random;

/**
 * Used to display properties of a normal distribution.
 * 
 * Has the following structure:
 * 
 * N = Number of Levels
 * 
 *	               0       <- head
 *	              / \
 *	             1   2
 *	            / \ / \
 *	           3   4   5
 *	          / \ / \ / \
 * 	         6   7   8   9
 *	        / \ / \ / \ / \
 *  last-> A ->B-> C-> D-> E 
 *  
 * Each node in the first N levels has a left and right link to a node below it, as shown above.
 * And last has its right pointer point horizontally (A->B, B->C, C->D, D->E)
 *         
 * Here N=4 and this divides into FIVE possible bins.
 * 
 * There is an 'add' method that adds an entry by starting at the head node and randomly falling down
 * one level below (50% probability of going left, 50% of going right). Once this process repeats N
 * times, then one of the final N+1 nodes is the final destination.
 * 
 * This final layer can be "read" from left to right by traversing the 'right' link of the last node,
 * which is the lowest-left-most node.
 * 
 * A sample run with 1000 additions is
 * 
 * 36,148,314,310,156,36,
 * 
 * Here is a sample run with 10000 additions:
 * 
 * 0,0,0,0,0,0,2,11,28,58,155,325,495,780,1135,1331,1422,1275,1115,814,512,306,141,55,30,9,1,0,0,0,0,0,0,
 * 
 */
public class Quincunx {

	/** Represents a node in the structure. */
	class Node {
		Node left;
		Node right;
		int count;
	}

	/** N actual layers, with one final layer that contains the counts of N+1 values. */
	final int N;
	
	/** Topmost node in the structure. */
	final Node head;
	
	/** Leftmost node on the N+1 layer. */
	final Node last;
	
	/** Random number generator to be able to replicate a run as desired. */
	final Random rnd;

	/**
	 * Construct a Quincunx with n+1 layers using the given seed for the random number generator. 
	 */
	public Quincunx(int n, int seed) {
		this.N = n;

		if (n > 0) {
			Node list = list(n+1);
			
			// The key idea is to find the recursive structure. A Quincunx of size n has a sub-quincunx of 
			// size n-1 inside of it, if you can find it.
			//
			//	               0       <- head
			//	              / \
			//	             1   2
			//	            / \ / \
			//	           3   4   5
			//	          / \ / \ / \
			// 	         6   7   8   9
			//	        / \ / \ / \ / \
			//  last-> A ->B-> C-> D-> E 
			//
			// Imagine a sub-quincunx rooted at '2'. Its 'head' is 2, its "last" is B.
			//
			// Now imagine a left-leaning linear list 0 - 1 - 3 - 6 - A.
			//
			// once these are both created, write a 'merge' function that grafts them together to make
			// the final quincunx
			head = merge (list, new Quincunx(n-1));
			last = lastInList(list);
		} else {
			head = last = new Node();
			// head is a fixed exit point, and we must mark it as such.
		}
		rnd = new Random(seed);
	}

	/** Construct a Quincunx of n+1 layers without regard for the actual random number seed. */
	public Quincunx(int n) {
		this(n, (int) System.currentTimeMillis());
	}

	/** 
	 * Merge the long straight left-leaning list of k+1 nodes with quincunx of one smaller size, k to
	 * create a new Quincunx structure of k+1 layers.  
	 */
	Node merge (Node list, Quincunx smaller) {
		Node left = list;
		Node right = smaller.head;

		// like a zipper.
		Node prev = right;
		while (right != null) {
			left.right = right;
			left = left.left;
			prev = right;
			right = right.left;
		}

		// at this point, left is a fixed exit point and we mark it as such.
		// prev is the final one
		left.right = prev; // this is "horizontal" for ease of access.

		return list;
	}

	/** Create a left-leaning list of size n. No .right links are set in this list. */
	Node list(int n) {
		Node head = new Node();
		while (--n > 0) {
			Node tmp = new Node();
			tmp.left = head;
			head = tmp;
		}

		return head;
	}

	/** Helper method to find last node in left-leaning list. */
	Node lastInList(Node lst) {
		if (lst == null) { return null; }
		while (lst.left != null) {
			lst = lst.left;
		}

		return lst;
	}

	/** 
	 * Add an entry, and process it randomly throughout.
	 * 
	 * Starting with the head, randomly choose left or right (i.e., "if (rnd.nextDouble() <= 0.5)").
	 * and move to that node in the level below. Once you have reached the final level, stop.
	 * 
	 * At each node, increment the count associated with that node. The counts in the final layer
	 * will be normally distributed, assuming a "perfect pseudo-random number generator."
	 */
	void add() { 
		Node top = head;
		int num = N;
		top.count++;
		while (num-- > 0) {
			if (rnd.nextDouble() <= 0.5) {
				top = top.left;
			} else {
				top = top.right;
			}
			top.count++;
		}

	}

	/** For simplicity return array containing N+1 final counts. */
	public int[] outputResults() {
		int[] output = new int[N+1];
		Node tmp = last;
		int idx = 0;
		while (tmp != null) {
			output[idx++] = tmp.count;
			tmp = tmp.right;
		}
		return output;		
	}
}
