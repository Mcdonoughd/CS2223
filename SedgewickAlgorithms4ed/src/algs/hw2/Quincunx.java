package algs.hw2;

import java.util.Random;

/**
 * THIS IS A BONUS QUESTION. DO NOT ATTEMPT UNTIL ENTIRE HOMEWORK IS DONE.
 * 
 * Used to display properties of a normal distribution.
 * 
 * Has the following structure:
 * 
 * N = Number of Levels, here N=4
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
 * Each node in the first N levels has a left and right link to the nodes below it, as shown above.
 * For example, the left link of 1 points to 3 while the right link of 1 points to 4. The quincunx
 * knows the 'head' node, and also the 'last' node, which is the left-most node on the n+1 layer of
 * the quincunx. All of the nodes on the N+1 layer have their right links pointing to the node to its
 * right (with the exception of the rightmost node, E, above). The quincunx knows the 'last' node,
 * which is the leftmost node on the N+1 layer.
 *         
 * Here N=4 and this divides into FIVE possible bins: A, B, C, D, and E.
 *
 * The purpose of a quincunx is to simulate the normal distribution.
 *
 * See http://mathworld.wolfram.com/GaltonBoard.html for more details
 * 
 * There is a 'trial' method that starts a trial at the head node and randomly falling down
 * one level below (50% probability of going left, 50% of going right). Once this process repeats N
 * times, then one of the final N+1 nodes is the final destination.
 * 
 * This final layer can be "read" from left to right by traversing the 'right' link of the last node,
 * which is the lowest-left-most node.
 * 
 * When you complete this assignment, then you can replicate results as follows:
 * 
 * A sample run on a Quincunx board of size N=4 (and thus five nodes on layer N+1), with 1000 additions is
 * 
 * 62,263,368,233,74
 * 
 * Here is a sample run on a Quincunx board of size N=32 (and thus 33 nodes on layer N+1), with 10000 additions:
 * 
 * 0,0,0,0,0,0,2,11,28,58,155,325,495,780,1135,1331,1422,1275,1115,814,512,306,141,55,30,9,1,0,0,0,0,0,0
 * 
 * The 'Quitest' code will perform the 
 * 
 * NOTE: THIS IS A BONUS QUESTION AND YOU SHOULD NOT ATTEMPT UNTIL ENTIRE HOMEWORK IS DONE
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
		rnd = new Random(seed);
		
		// You will need to complete this constructor.
		head = null; // FIX ME. DONE JUST TO BE ABLE TO HAVE THIS COMPILE
		last = null; // FIX ME. DONE JUST TO BE ABLE TO HAVE THIS COMPILE
	}

	/** Construct a Quincunx of n+1 layers without regard for the actual random number seed. Leave as is. */
	public Quincunx(int n) {
		this(n, (int) System.currentTimeMillis());
	}

	/** 
	 * Process a trial, starting at the top, and ending at one of the N+1 bins in the final layer.
	 * 
	 * Starting with the head, randomly choose left or right (i.e., "if (rnd.nextDouble() <= 0.5)").
	 * and move to that node in the level below. Once you have reached the final level, stop.
	 * 
	 * At each node, increment the count associated with that node. The counts in the final layer
	 * will be normally distributed, assuming a "perfect pseudo-random number generator."
	 */
	void trial() { 
		// Fix this method
	}

	/** For simplicity return array containing N+1 final counts. */
	public int[] outputResults() {
		// FIX THIS METHOD.
		return new int[] { };		
	}
	
	/** 
	 * Validate the quincunx on a given N. You should be able to leave this as is, 
	 * though you can modify the N argument to set the size. 
	 */
	public static void main(String[] args) {
		int N=32;
		Quincunx struct = new Quincunx(N);
		
		for (int i = 0; i < 1000; i++) {
			struct.trial();
		}
		
		int[] vals = struct.outputResults();
		int idx = 0;
		for (int v : vals) {
			System.out.print(v);
			if (idx++ != N) { System.out.print(","); }
		}
		System.out.println();
	}
}
