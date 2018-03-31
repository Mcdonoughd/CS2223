package dmcdonough.hw2;

import edu.princeton.cs.algs4.*;

// Useful template to construct a heap from a collection of random values.
public class HeapExercise {
		
	//static Double[] data;
	
	static Double[] generateData(int n) {
		Double[] vals = new Double[n];
		for (int i = 0; i < n; i++) {
			vals[i] = StdRandom.uniform();
		}
		return vals;
	}

	// You are responsible for updating these values.
	//
	// [n][0] = fewest number of comparisons for delMin on heap of size n
	// [n][1] = most number of comparisons for delMin on heap of size n
		
	static int[][] results = new int[12][4];

	public static void generateReport() {
		StdOut.println("Heap Trials");
		StdOut.println("N\tDelMin");
		for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
			StdOut.println(n + "\t" + results[idx][0] + "-" + results[idx][1]);
		}
	}

	
	// Update results, given information for the given trial, data size N, number of comparisons
		// during the delMin operation.
		// NOTE: (3-26-2018) -- IN initial version, there had been a parameter: int insertComparisons
		// but this is no longer needed.
		private static void updateEntry(int trial, int n, int delComparisons) {
			int comparisons = (delComparisons);
			if(results[trial][0] >= comparisons || results[trial][0] == 0){
				results[trial][0] = comparisons;
			}
			if(results[trial][1] <= comparisons ) {
				results[trial][1] = comparisons;
			}
		}

	public static void main(String[] args) {

		int T = 10;
		
		//10 trials 
		for (int t = 0; t<T; t++) {
			//of n 4 to 8192 size heap
			for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
				// Randomly construct a Heap priority queue containing N values using StdRandom.
				MaxPQ<Double> data= new MaxPQ<Double>(n);
				Double[] a = generateData(n);
				for (double s : a) {
					data.insert(s);
				}
				//int delComparisons = 0;
				//Once constructed, perform 1000 iterations of the following sequence
				for(int i=1;i<=1000;i++) {
					//Remove minimum priority value in priority queue (during which you should count the number of comparisons)
					data.delMin();
					int delComparisons = data.getCount();
					
					data.resetCount();
					// Insert random value					
					data.insert(StdRandom.uniform());
					//update min, max of 
					updateEntry(idx,n,delComparisons);
					
				}
			}
		}	
		generateReport();
	}

}
