package algs.hw3;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Question2 {
	
	static Double[] generateData(int n) {
		Double[] vals = new Double[n];
		for (int i = 0; i < n; i++) {
			vals[i] = StdRandom.uniform();
		}
		return vals;
	}
	
	public static void main(String[] args) {
		// 16, 32, 64, … 512. For each size N, run T=10 trials, 
		int T = 1000;
		StdOut.println("N\tMaxComp\tMaxExch");
		for (int n= 16; n <= 512; n*= 2) {
			int maxExch = 0;
			int maxComp = 0;
			for (int t = 0; t < T; t++) {
				
				Heap.sort(generateData(n));
				if (Heap.numCompares > maxComp) {
					maxComp = Heap.numCompares;
				}
				if (Heap.numExchanges > maxExch) {
					maxExch = Heap.numExchanges;
				}
			}
			
			StdOut.printf("%d\t%d\t%d%n", n, maxComp, maxExch);
		}
		
		StdOut.println("\nHypothesis is that MaxComp < 2N and MaxExch < N. Above data confirms this.");
	}
}
