package algs.days.day02;

import edu.princeton.cs.algs4.*;

public class ThreeSumModified {
	
	public static int count(int[] a) {
		// Count triples that sum to 0
		int N = a.length;
		int ct = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				for (int k = j+1; k < N; k++) {
					if (a[i] + a[j] + a[k] == 0) {
						ct++;
					}
				}
			}
		}
		
		return ct;
	}
		
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		
		int[] ar = new int[N];
		for (int i = 0; i < N; i++) {
			ar[i] = StdRandom.uniform(-10000, 10000);
		}
		
		Stopwatch timer = new Stopwatch();
		int numTriples = ThreeSumModified.count(ar);
		double time = timer.elapsedTime();
		
		StdOut.println(numTriples + " triples + " + time + " seconds");
	}
}
