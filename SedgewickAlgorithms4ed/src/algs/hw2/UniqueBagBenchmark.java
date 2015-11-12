package algs.hw2;

import edu.princeton.cs.algs4.*;

public class UniqueBagBenchmark {
	
	// if you go to larger n, then you will run out of Java Heap space
	public static void main(String[] args) {
		for (int n = 4; n <= 2097152; n *= 2) {
			
			// Create two bags of the integers [1, n]. Bag 'one' has all numbers
			// while bag two has odd numbers only.
			// Add the numbers in REVERSE ORDER otherwise this will take a LOOOOONG time.
			UniqueBag<Integer> one = new UniqueBag<Integer>();
			UniqueBag<Integer> two = new UniqueBag<Integer>();
			for (int k = n; k >=1; k--) {
				one.add(k);
				if (k % 2 == 1) { two.add(k); }
			}
			
			// at this point you can benchmark some operations:
			// size, identical, add, toArray, contains, remove, intersects, union
			// use StopWatch as you did before.
			
		}
	}
}
