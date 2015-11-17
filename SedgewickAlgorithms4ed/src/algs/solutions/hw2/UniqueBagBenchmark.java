package algs.solutions.hw2;

import edu.princeton.cs.algs4.*;

public class UniqueBagBenchmark {
	
	// if you go to larger n, then you will run out of Java Heap space
	public static void main(String[] args) {
		for (int n = 4; n <= 2097152; n *= 2) {
			
			// create two bags of the integers [1, n]. Bag 'one' has all numbers
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
			
			Stopwatch sw = new Stopwatch();
			one.size();
			double sizeTime = sw.elapsedTime();
			
			sw = new Stopwatch();
			one.identical(one);
			double identicalTime = sw.elapsedTime();
			
			sw = new Stopwatch();
			one.toArray();
			double arrayTime = sw.elapsedTime();
			
			sw = new Stopwatch();
			one.contains(n+1);
			double containsTime = sw.elapsedTime();
			
			sw = new Stopwatch();
			one.remove(n/2);
			double removeTime = sw.elapsedTime();
			
			sw = new Stopwatch();
			one.add(n/2);
			double addTime = sw.elapsedTime();

			sw = new Stopwatch();
			one.intersects(two);
			//StdOut.println(one.intersects(two).size());  Check size of intersect if you need to be convinced
			double intersectsTime = sw.elapsedTime();
			
			sw = new Stopwatch();
			one.union(two);
			//StdOut.println(one.union(two).size());       Check size of union if you need to be convinced
			double unionTime = sw.elapsedTime();
			
			// report times.
			StdOut.println(n + "\t" + sizeTime + "\t" + identicalTime + "\t" +  arrayTime + "\t" + containsTime
					+ "\t" +  removeTime + "\t" + addTime + "\t" + intersectsTime + "\t" + unionTime);
		}
	}
}
