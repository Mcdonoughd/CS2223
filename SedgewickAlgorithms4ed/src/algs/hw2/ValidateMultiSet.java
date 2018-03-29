package algs.hw2;

import edu.princeton.cs.algs4.*;


/**
 * Use this code "as is" to validate the logic and performance of your multiset. Based on the performance
 * of your computer, you may have to adjust the (low,high) values to be able to see meaningful results.
 * 
 * Your output will look something like this:
 
 
N		inter	ident	union	mult_a		add_a		rem_a
8192	0.003	0.003	0.028	0.000008	0.000017	0.000006
16384	0.006	0.005	0.042	0.000038	0.000056	0.000019
32768	0.003	0.002	0.013	0.000108	0.000160	0.000055
65536	0.000	0.000	0.000	0.000062	0.000105	0.000032  

 */
public class ValidateMultiSet {
	public static final int low = 8192;
	public static final int high = 65536;
	
	/** Copied from StdRandom.shuffle(). Bringing here so you can see the exchanges... */
    public static void shuffle(Object[] a) {
        if (a == null) throw new NullPointerException("argument array is null");
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + StdRandom.uniform(N-i);     // between i and N-1
            Object tmp = a[i];
            a[i] = a[r];
            a[r] = tmp;
        }
    }
    
    /**
     * Return an array of size N*multiplicity/2. It contains each odd number from 1..N a fixed number of times.
     * The returned array should be shuffled.
     * 
     * @param N              high end of range of odd numbers from 1..N, where N is a power of 2.
     * @param multiplicity   how many times each odd number should appear in final array
     */
    static Integer[] generateOddData(int N, int multiplicity) {
		
		Integer[] vals = new Integer[N*multiplicity/2];
		int number = 1;
		for (int i = 0; i < vals.length; i += multiplicity) {
			for (int k = 0; k < multiplicity; k++) {
				vals[i+k] = number; 
			}
			
			number += 2;
		}
		
		shuffle(vals);
		return vals;
	}
    
    /**
     * Return an array of size N*multiplicity/2. It contains each even number from 1..N a fixed number of times.
     * The returned array should be shuffled.
     * 
     * @param N              high end of range of even numbers from 1..N, where N is a power of 2.
     * @param multiplicity   how many times each even number should appear in final array
     */
    static Integer[] generateEvenData(int N, int multiplicity) {
		Integer[] vals = new Integer[N*multiplicity/2];
		int number = 2;
		for (int i = 0; i < vals.length; i += multiplicity) {
			for (int k = 0; k < multiplicity; k++) {
				vals[i+k] = number; 
			}
			
			number += 2;
		}
		
		shuffle(vals);
		return vals;
	}
	
    /**
     * Return an array of size N*multiplicity. It contains each number from 1..N a fixed number of times.
     * The returned array should be shuffled.
     * 
     * @param N              high end of range of numbers from 1..N, where N is a power of 2.
     * @param multiplicity   how many times each number should appear in final array
     */
	static Integer[] generateAllData(int N, int multiplicity) {
		Integer[] vals = new Integer[N*multiplicity];
		for (int i = 0; i < vals.length; i += multiplicity) {
			for (int k = 0; k < multiplicity; k++) {
				vals[i+k] = i+1; 
			}
		}
		
		shuffle(vals);
		return vals;
	}
	
	/** Helper method to exit if anything is wrong. */
	static void ensureIdentical (MultiSet<Integer> one, MultiSet<Integer> two) {
		if (!one.identical(two)) {
			System.out.println("Identical failed");
			System.exit(-1);
		}
	}
	
	/**
	 * Run this method to generate final table. You should not have to modify this method.
	 * @param args
	 */
	public static void main(String[] args) {
		
		// GOAL:
		// s1: contains odd  numbers from 1..n, each once
		// s2: contains odd  numbers from 1..n, each twice
		// s3: contains even numbers from 1..n, each once
		// s4: contains all numbers from 1..n, each once
		
		// will show that:
		//  * s1 intersect s2 == s1
		//  * s1 union s3 == s4
		//  * add all odd numbers to s1 and you will get s2
		//  * Remove all odd numbers from s2 and you will get s1
		//  * Can drain an entire multiset to be empty.
		

		StdOut.println("N\tinter\tident\tunion\tmult_a\t\tadd_a\t\trem_a");
		int trials = 5;
		for (int n = low; n <= high; n *= 2) {
			
			MultiSet<Integer> s1      = new MultiSet<Integer>(generateOddData (n, 1));
			MultiSet<Integer> s1_copy = new MultiSet<Integer>(generateOddData (n, 1));
			MultiSet<Integer> s2      = new MultiSet<Integer>(generateOddData (n, 2));
			MultiSet<Integer> s3      = new MultiSet<Integer>(generateEvenData(n, 1));
			MultiSet<Integer> s4      = new MultiSet<Integer>(generateAllData (n, 1));
			
			// use single stopwatch for all timing results
			MultiSet<Integer> result = null;
			Stopwatch sw = new Stopwatch();
			for (int t = 0; t < trials; t++) {
				result = s2.intersects(s1);
			}
			double intersect = sw.elapsedTime()/trials;

			// this ensures s1 intersect s2 == s1
			for (int t = 0; t < trials; t++) {
				ensureIdentical(s1, result);
			}
			double identical = (sw.elapsedTime() - intersect)/trials;
			
			// compute s1 union s3
			MultiSet<Integer> result2 = null;
			for (int t = 0; t < trials; t++) {
				result2 = s1.union(s3);
			}
			double union = sw.elapsedTime() - identical;
			
			// ensures s1 union s3 == s4
			ensureIdentical(s4, result2);			
			
			// Each multiplicity check is proportional to N, and these are 
			// done 1000 times, so we average by dividing by 1000.
			double pre_multiplicity = sw.elapsedTime();
			for (int i = 1; i <= n; i++) {
				int mi = s2.multiplicity(i);
				if (i %2 == 0 && mi != 0) {
					System.out.println("Fails multiplicity check on even " + i);
				} else if (i %2 == 1 && mi != 2) {
					System.out.println("Fails multiplicity check on odd " + i);
					System.exit(-1);
				}
			}
			double multiplicity_average = (sw.elapsedTime() - pre_multiplicity)/n;
		
			// convert s1 into s2 by adding 1..N step 2. These n operations
			// must be averaged by dividing by n/2.
			for (int i = 1; i <=n; i+= 2) {
				s1.add(i);
			}
			double average_add = (sw.elapsedTime() - multiplicity_average)/(n/2.0); 
			
			// ensures s1 can be converted into s2 by adding n numbers
			ensureIdentical(s1, s2);
			
			// now take s2 and remove each n value, to confirm same as s1_copy.
			double pre_remove_average = sw.elapsedTime();
			// must be averaged by dividing by n/2.
			for (int i = 1; i <=n; i+= 2) {
				s2.remove(i);
			}
			double average_remove = (sw.elapsedTime() - pre_remove_average)/(n/2.0);
			
			// ensures s2 can be converted into s1_copy by removing n numbers
			ensureIdentical(s1_copy, s2);
					
			// verify we can empty out a multiset completely
			for (int i = 1; i <=n; i+= 2) {
				s2.remove(i);
			}
			if (s2.size() != 0) {
				System.out.println("s2 should have been empty");
				System.exit(-1);
			}
			
			StdOut.printf("%d\t%.3f\t%.3f\t%.3f\t%.6f\t%.6f\t%.6f%n" ,n, 
					intersect,identical,union,multiplicity_average,average_add,average_remove);
		}
	}
}
