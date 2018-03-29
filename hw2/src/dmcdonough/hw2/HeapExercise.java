package dmcdonough.hw2;

import edu.princeton.cs.algs4.*;

// Useful template to construct a heap from a collection of random values.
public class HeapExercise {

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
	private static void updateEntry(int trial, int n, int delComparisons, int insertComparisons) {
		int comparisons = (delComparisons+insertComparisons);
		if(results[trial][0] >= comparisons || results[trial][0] == 0){
			results[trial][0] = comparisons;
		}
		if(results[trial][1] <= comparisons ) {
			results[trial][1] = comparisons;
		}
	}
	
	
	public static int delMin(Double[] data) {
		return 0;
	}
	
	public static int insert(Double[] data) {
		return 0;
	}
	

	public static void main(String[] args) {

		int T = 10;
		Double[] data;
		//10 trials 
		for (int t = 0; t < T; t++) {
			//of n 4 to 8192 size heap
			for (int n = 4, idx = 0; n <= 8192; n*= 2, idx++) {
				// Randomly construct a Heap priority queue containing N values using StdRandom.
				data = generateData(n);
				int delComparisons = 0;
				int insComparisons = 0;
				//Once constructed, perform 1000 iterations of the following sequence
				for(int i=0;i<=999;i++) {
					//Remove minimum priority value in priority queue (during which you should count the number of comparisons)
					delComparisons = delMin(data);
					// Insert random value
					insComparisons = insert(data);					
					
					//update min, max of 
					updateEntry(idx,n,delComparisons,insComparisons);

				}
			}
		}
		generateReport();
	}

}
