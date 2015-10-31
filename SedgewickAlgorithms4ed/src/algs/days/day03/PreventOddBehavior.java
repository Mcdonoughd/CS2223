package algs.days.day03;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

// It is only when I generate all possible computations does it go away, and even
// then it still seems to have some odd behavior.

public class PreventOddBehavior {
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
			
			// Access each possible computation. For some reason, this eliminates 
			// many timing variations. To see this, plot the resulting graphs.
			sum = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					sum = numbers[i]+numbers[j];
					sum = numbers[i]*numbers[j];
				}
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
