package algs.days;

import algs.sedgewick.StdRandom;
import algs.sedgewick.Stopwatch;

public class Day02 {
	public static void main(String[] args) {
		// timing comparison of addition vs. multiplication vs. square root
		
		int N = Integer.parseInt(args[0]);
		
		// only here so the code won't be detected as having no external effect.
		long sum;
		int[] numbers = new int[N];
		for (int i = 0; i < N; i++) {
			numbers[i] = StdRandom.uniform(-1000000, 1000000);
		}
		
		// SWITCH THESE AROUND AND SEE WHAT HAPPENS!
		
		
		Stopwatch addsw = new Stopwatch();
		sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += (numbers[i]+numbers[j]);
			}
		}
		System.out.println("Add:" + addsw.elapsedTime() + " seconds");
		System.out.println("For the record, sum was " + sum);
		
		Stopwatch multsw = new Stopwatch();
		sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += (numbers[i]*numbers[j]);
			}
		}
		System.out.println("Mult:" + multsw.elapsedTime() + " seconds");
		System.out.println("For the record, sum was " + sum);
	}
}
