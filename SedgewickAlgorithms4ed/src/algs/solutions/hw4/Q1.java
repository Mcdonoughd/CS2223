package algs.solutions.hw4;

import edu.princeton.cs.algs4.StdOut;

public class Q1 {
	public static void main(String[] args) {
		for (int k = 2; k <= 10; k++) {
			int N = (int) (Math.pow(2,k)-1);
			
			Integer[] vals = new Integer[N];
			for (int i = 1; i <= N; i++) {
				vals[i-1] = i;
			}
			
			StraightBST<Integer> bst = new StraightBST<Integer>(vals);
			StdOut.println(N + "\t" + bst.height());
		}
	}
}
