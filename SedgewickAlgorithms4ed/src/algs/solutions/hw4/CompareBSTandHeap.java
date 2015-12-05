package algs.solutions.hw4;

import edu.princeton.cs.algs4.*;

// For HW4 you are asked to compare the number of comparisons made during 
// successive calls to 'deleteMax'. Note that in a MaxPQ there are also
// exchange operations, which do not occur in a BST; however, these are
// always fewer than the number of comparisons (as you can see from p. 316). 
public class CompareBSTandHeap {
	static Double[] generateData(int n) {
		Double[] vals = new Double[n];
		for (int i = 0; i < n; i++) {
			vals[i] = StdRandom.uniform();
		}
		return vals;
	}

	public static void main(String[] args) {
		Double[] data;
		float T = 512;

		for (int n = 4, idx = 0; n <= 1048576; n*= 2, idx++) {
			float maxRatio = 0;
			for (int t = 0; t <= T; t++) {
				MaxPQ<Double> mpq = new MaxPQ<Double>(n);
				StraightBST<Double> bst = new StraightBST<Double>();

				data = generateData(n);
				for (double d : data) {
					mpq.insert(d);
					bst.add(d);
				}

				// ready for the trial to start:
				mpq.inspectedElementCount = 0;
				bst.inspectedElementCount = 0;
				while (!mpq.isEmpty() && !bst.isEmpty()) {
					double heapMax = mpq.delMax();  // remove the maximum
					double bstMax = bst.max();
					bst.deleteMax();

					if (heapMax != bstMax) {
						StdOut.println ("Error not retrieving same value.");
					}
				}

				float ratio = (1.0f*mpq.inspectedElementCount)/bst.inspectedElementCount;
				if (ratio > maxRatio) {
					maxRatio = ratio;
				}
			}
			System.out.println(n + "\t" + maxRatio);
		}
	}
}
