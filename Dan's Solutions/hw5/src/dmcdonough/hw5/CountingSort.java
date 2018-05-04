package dmcdonough.hw5;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

// Proper Merge Sort from Sedgewick, 4ed
public class CountingSort {

	public static void sort(Integer[] values, int k) {
		// fill in...
		int numCounts[] = new int[k + 1]; //len k +1

	    // populate numCounts
	    for (int num : values) {
	        numCounts[num]++;
	    }

	    // populate the final sorted array
	    int[] sortedArray = new int[values.length]; //len N
	    for(int i=0;i<values.length;i++) {
	    	sortedArray[i] = 0;
	    }
	    int currentSortedIndex = 0;

	    // for each num in numCounts
	    for (int num = 0; num < numCounts.length; num++) { //to k+1
	        int count = numCounts[num]; //frequency of number

	        // for the number of times the item occurs
	        for (int i = 0; i < count; i++) {  //becomes some constant <= k

	            // add it to the sorted array
	            sortedArray[currentSortedIndex] = num;
	            currentSortedIndex++;
	        }
	    }

	    //return sortedArray;
	}

	/**
	 * Reads in a sequence of strings from standard input; selection sorts them; 
	 * and prints them to standard output in ascending order. 
	 */
	public static void main(String[] args) {
		
		// choose some k value
		int k = 100;  
		
		StdOut.println("N\tCounting Sort\t");
		for (int N = 4096; N <= 524288; N *= 2) {
			Integer[] ar = new Integer[N];
			for (int i = 0; i < ar.length; i++) {
				ar[i] = StdRandom.uniform(k);
			}
			
			Stopwatch sw = new Stopwatch();
			sort(ar, k);
			//reshuffle
			//double time = sw.elapsedTime();

			StdOut.printf("%d\t%f\t\n", N, sw.elapsedTime());
			
		}
		StdOut.println("N\tMerge Sort");
		for (int N = 4096; N <= 524288; N *= 2) {
			Integer[] ar = new Integer[N];
			//reshuffle
			for (int i = 0; i < ar.length; i++) {
				ar[i] = StdRandom.uniform(k);
			}
			Stopwatch sw_merge = new Stopwatch();
			Merge.sort(ar);
			StdOut.printf("%d\t%f\t\n", N,sw_merge.elapsedTime());
			
		}
	}
}