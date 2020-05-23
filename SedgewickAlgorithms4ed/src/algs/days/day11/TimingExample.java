package algs.days.day11;

import edu.princeton.cs.algs4.Stopwatch;

public class TimingExample {
	public static void main(String[] args) {
		long sum = 0;
		Stopwatch sw = new Stopwatch();
		for (int i = 0; i < 1000000; i++) {
			sum += i;
		}
		System.out.println("this often reports 0.0");
		System.out.println(sum + " took " + sw.elapsedTime());
		
		sum = 0;
		sw = new Stopwatch();
		for (int k = 0; k < 100; k++) {
			for (int i = 0; i < 1000000; i++) {
				sum += i;
			}
		}
		System.out.println("This might show a non-zero value");
		System.out.println(sum + " took " + sw.elapsedTime());
	}
}
