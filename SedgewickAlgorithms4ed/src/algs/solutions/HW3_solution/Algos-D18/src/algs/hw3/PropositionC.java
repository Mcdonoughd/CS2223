package algs.hw3;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * From Page 403 in Sedgewick:
 *
 * The number of compares used for a search hit ending at a given node is 1 plus the depth.
 * Adding the depths of all nodes, we get a quantity known as the internal path length of the tree.
 * 
 * Thus the desired quantity (i.e., average # of compares on random BST) is 1 plus the average
 * internal path length of the BST. Let Cn be the internal path length of a BST built from 
 * inserting N randomly ordered distinct keys, so the average cost of a search hit is 1 + Cn/N.
 * 
 * Compute that CN ~ 2N ln N, thus average is ~ 2 ln N
 * 
 * This code only runs ONE trial, but with successive trials, the results more clearly match the model.
 */
public class PropositionC {
	static Double[] generateData(int n) {
		Double[] vals = new Double[n];
		for (int i = 0; i < n; i++) {
			vals[i] = StdRandom.uniform();
		}
		return vals;
	}
	
	public static void main(String[] args) {
		StdOut.println("N\tCn\tH-Ave\tM-Ave\tModel");
		for (int n = 64; n <= 4096; n*= 2) {
			double fix = 2*Math.log(n); // in natural logarithm.
			
			BST tree = new BST();
			Double[] data = generateData(n);
			for (double d : data) {
				tree.put(d,  true);
			}
			
			int Cn = tree.sumDepths();
			
			// search hits
			BST.numComparison = 0;
			for (double d : data) {
				tree.get(d);
			}
			int hitMaxNumComparison = BST.numComparison;
			
			// These are search-misses, since we search using different random data.
			
			Double[] newData = generateData(n);
			BST.numComparison = 0;
			for (double d : newData) {
				if (tree.get(d) != null) {
					// these should all fail
					System.err.println ("Unexpectedly clashed on random numbers");
				}
			}
			int missMaxNumComparison = BST.numComparison;
			
			double model = 1.39* Math.log(n)/Math.log(2); //1 + (1.0*Cn) / n;
			double missAverage = (1.0*missMaxNumComparison) / n;
			double hitAverage = (1.0*hitMaxNumComparison) / n;
			
			
			StdOut.printf("%d\t%d\t%.2f\t%.2f\t%.2f%n", n, Cn, hitAverage, missAverage, model);
			
		}
		
	}
}
