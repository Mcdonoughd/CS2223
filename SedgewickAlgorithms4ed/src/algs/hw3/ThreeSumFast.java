package algs.hw3;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class ThreeSumFast {

	static int rank (Comparable[] collection, Comparable target) {
		int low = 0;
		int high = collection.length-1;

		while (low <= high) {
			int mid = (low+high)/2;

			int rc = collection[mid].compareTo(target);
			if (rc < 0) {
				low = mid+1;
			} else if (rc > 0) {
				high = mid-1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	public static int count(Integer[] a) {
		int N = a.length;
		Quick.sort(a);

		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				int k = rank (a, -(a[i] + a[j]));
				if (k > j) cnt++;
			}
		}
		return cnt;
	} 

	public static void main(String[] args) {
		for (int N = 256; N <= 16384; N *= 2) {
			Integer[] sample = new Integer[N];
			for (int i = 0; i < sample.length; i++) {
				sample[i] = StdRandom.uniform(-10000,10000);
			}

			Stopwatch sw = new Stopwatch();
			int num = count(sample);
			double time = sw.elapsedTime();
					
			StdOut.println(N + "\t" + time);
		}
	}
}
