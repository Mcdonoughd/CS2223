package algs.days.day03;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

// I didn't get chance to present this. Even when you access each of the numbers
// in the array IN ADVANCE, this doesn't seem to eliminate the performance
// benefit of being second.

public class FailToPreventOddBehavior {
	public static void main(String[] args) {
		// timing comparison of addition vs. multiplication vs. square root

		System.out.println("N,ADD,MULT");
		for (int N = 500; N < 50000; N += 500) {
			System.gc();
			
			// create the numbers
			long sum, mult;
			int[] numbers = new int[N];
			for (int i = 0; i < N; i++) {
				numbers[i] = StdRandom.uniform(-1000000, 1000000);
			}
			
			// Access each value once. This doesn't seem to eliminate observation
			sum = 0;
			for (int j = 0; j < N; j++) {
				sum += numbers[j];
			}
			
			// now ready to do actual timing
			Stopwatch addsw = new Stopwatch();
			sum = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					sum = numbers[i]+numbers[j];
				}
			}
			double addT = addsw.elapsedTime();
			
			Stopwatch multsw = new Stopwatch();
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					mult = (numbers[i]*numbers[j]);
				}
			}
			double multT = multsw.elapsedTime();
			
			System.out.println(N + "," + addT + "," + multT);
		}
	}
}
