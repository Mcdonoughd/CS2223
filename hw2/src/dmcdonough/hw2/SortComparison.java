package dmcdonough.hw2;

import algs.days.day06.Insertion;
import edu.princeton.cs.algs4.*;

// Template to use for Question 2 on Homework 2. Fix/update as needed.
public class SortComparison {

	// These are the proper values to use for HW2. if you want to try your code on smaller values,
	// then reduce these, say, to lowSize=5 and highSize=11
	static final int lowSize = 13;   // 2^13 = 8192
	static final int highSize = 19;	 // 2^19 = 524288
	
	
	/** Copied from StdRandom.shuffle(). Bringing here to avoid counting exchanges. */
    public static void shuffle(Object[] a) {
        if (a == null) throw new NullPointerException("argument array is null");
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = i + StdRandom.uniform(N-i);     // between i and N-1
            Object swap = a[i];
            a[i] = a[r];
            a[r] = swap;
        }
    }
	
    /**
     * Generates an array of N=2^k unique integers in random arrangement. 
     * 
     * @param k  power of 2 unique integers to be returned
     * @return array is shuffled.
     */
    static Integer[] generateUniqueData(int k) {
    	// Students must fill this in. This code is here to make sure we can run right from initial start of HW2
    	Integer[] vals = new Integer[k];
    	for (int i = 0; i < vals.length; i++) { vals[i] = 0; }
    	return vals;
    }
    
    /**
     * Write a method that returns a shuffled integer array which has the following properties.
     * 
     *   + The size of the array is N=2^k and it contains (k+1) distinct values.
     *   
     *   + 1 appears one time
     *   + 2 appears two times
     *   + 3 appears four times
     *   + ...
     *   + k-1  appears 2^(k-2) times
     *   + k appears 2^(k-1) times
     *   + k+1 appears one time
     *  
     * The following is a valid pre-shuffled array for k=5:
     * 
     * [5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,1,5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,6] 
     *
     * @param k  power of 2 unique integers to be returned
     * @return array is shuffled
     */
    //I luckily already coded this is the previous problem so I'll just copy this
    
    

	//computes log base 2 of input
	public static double log2(double num){
	return (Math.log(num)/Math.log(2));
	}
	
	
	//fills array Siblings on any N^2 size array such that:
	public static void Sibling(int r, Integer[] ar, int level, int N, int max_level){
		//r is the pos of the 1st val of the current level
		
		int num = (int) Math.pow(2, level-1) ;
		int pattern = (int) Math.floor(N/num);
		//int offset = (int) Math.pow(2, max_level-level-1)-1;
		
		for(int i = r; i <= N-1; i+=pattern) {
			ar[i] = level; //place each element on the level with it's level number
		}
	}
		
	
	//init the structure
	public static void Parent(int r, Integer[] ar, int level, int N, int max_level) {
		//check if current level of tree is greater than the greatest possible level
		if(max_level-1 < level) {
			return;
		}
		else {
		//calculate origin to check for edges
		int origin = (int) Math.floor((N-1)/2);
		//calculate the left most child
		int left_child = (int) (r-(Math.floor(r/2))-1);
		//calc right child
		int right_child = (int) (r+(Math.floor(r/2))+1);
		
		//check edges
		if(left_child >= 0 && left_child < N && ar[left_child] == null && left_child < origin) {
			//get sibling which exist on level the current level (min 2) and beyond
			if (level>=2 && level<max_level ) {
				Sibling(left_child,ar,level,N,max_level);
			}
    		ar[left_child] = level; //set child
    		
    		Parent(left_child,ar,level+1,N,max_level); //Recursive to left children
    		Parent(right_child,ar,level+1,N,max_level); //recursive to right children
    		 
			}
		}
		//end
		//return;
	}
	
	public static Integer[] randomArray(int k) {
		if(k % 2 != 0) {
		return null;
		}
		int N = (int) Math.pow(2, k);
		//Make array
    	Integer[] ar = new Integer[N];
    	
    	//determine max level of tree
    	int max_level = (int) (log2(N)+1);
    	
    	//if array is greater than size 2
    	if(N > 2) {
			int origin = (int) Math.floor((N-1)/2);
			ar[origin] = 1;
        	Parent(origin, ar, 2, N, max_level );
    	}
    	
    	//else set 1st element as 1
    	else {
    		ar[0] = 1;
    	}
    	//add last value to array (note on 1x1 array one is filled twice due to this attribute
    	ar[N-1] = max_level;
    	
    	//shuffle the array
    	shuffle(ar);
    	
		return ar;
		
	}
	
	
    
    
    
	static Integer[] generateHighDuplicateData(int k) {
		// Students must fill this in. This code is here to make sure we can run right from initial start of HW2
		return randomArray(k);
	}
	
	// These have been placed here so you can double check that each of the sorting algorithms
	// did indeed sort the data. Note: DO NOT CALL THIS FUNCTION WITHIN YOUR Stopwatch time period
	// when you are checking for the total elapsed time for the respective algorithm to complete
	static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
	static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }
	
	// You are responsible for writing code that updates these values. There are three values for s: 0,1,2
	//
	// unique[n][s][0] = best time for algorithm #s on a dataset of size n with n unique values
	// unique[n][s][1] = fewest number of exch operations for algorithm #s on a dataset of size n with n unique values
	// unique[n][s][2] = fewest number of less operations for algorithm #s on a dataset of size n with n unique values
	static double[][][] unique = new double[8][highSize-lowSize+1][3];
	
	// duplicates[n][s][0] = best time for algorithm #s on a dataset of size n with mostly duplicate values
	// duplicates[n][s][1] = fewest number of exch operations for algorithm #s on a dataset of size n with mostly duplicate values
	// duplicates[n][s][2] = fewest number of less operations for algorithm #s on a dataset of size n with mostly duplicate values
	static double[][][] duplicates = new double[8][highSize-lowSize+1][3];
	
	// Generates the table with the proper formatting. Use as is. DO NOT MODIFY THIS METHOD.
	public static void generateReport() {
		StdOut.println("Time Trials");
		StdOut.println("N\tMergeU\tQuickU\tQuickAU\t|\tMergeD\tQuickD\tQuickAD");
		for (int k = lowSize, idx = 0; k <= highSize; k++, idx++) {
			int n = (int) Math.pow(2, k);
			StdOut.printf("%2d\t%.3f\t%.3f\t%.3f\t|\t%.3f\t%.3f\t%.3f%n", 
					n,
					unique[idx][0][0], unique[idx][1][0], unique[idx][2][0], 
					duplicates[idx][0][0], duplicates[idx][1][0], duplicates[idx][2][0]);
		}
		
		StdOut.println();
		StdOut.println("Exch Results");
		StdOut.println("N\tMergeU\t\tQuickU\t\tQuickAU\t\t|\tMergeD\t\tQuickD\t\tQuickAD");
		for (int k = lowSize, idx = 0; k <= highSize; k++, idx++) {
			int n = (int) Math.pow(2, k);
			StdOut.printf("%2d\t%.3e\t%.3e\t%.3e\t|\t%.3e\t%.3e\t%.3e%n", 
					n,
					unique[idx][0][1], unique[idx][1][1], unique[idx][2][1],
					duplicates[idx][0][1], duplicates[idx][1][1], duplicates[idx][2][1]);
			
		}
		
		StdOut.println();
		StdOut.println("Less Results");
		StdOut.println("N\tMergeU\t\tQuickU\t\tQuickAU\t\t|\tMergeD\t\tQuickD\t\tQuickAD");
		for (int k = lowSize, idx = 0; k <= highSize; k++, idx++) {
			int n = (int) Math.pow(2, k);
			StdOut.printf("%2d\t%.3e\t%.3e\t%.3e\t|\t%.3e\t%.3e\t%.3e%n", 
					n,
					unique[idx][0][2], unique[idx][1][2], unique[idx][2][2],
					duplicates[idx][0][2], duplicates[idx][1][2], duplicates[idx][2][2]);
		}
	}

	// I suggest it might be useful to have a helper method such as this, which properly updates
	// the results[n][entry][xxx] values where xxx is 0 for time, 1 for exchange, and 2 for less.
	// 
	// The purpose of this method is to ensure you record the minimum time, the fewest number of
	// exchanges, and the fewest number of comparison operations, for the unique data set
	static void updateUniqueEntry(int trial, int n, int entry, long exch, long less, double time) {
		// Student fills in...
		switch (trial) {
		case 0:
			if(unique[n][entry][trial] == 0.0 || time < unique[n][entry][trial]) {
				unique[n][entry][trial] = time;
			}
			break;
		case 1:
			if(unique[n][entry][trial] == 0.0 || exch < unique[n][entry][trial]) {
				unique[n][entry][trial] = exch;
			}
			break;
		case 2:
			if(unique[n][entry][trial] == 0 || less < unique[n][entry][trial]) {
				unique[n][entry][trial] = less;
			}
			break;
		default:
			System.out.println("ERROR!");
			return;
		}
				
	}
	
	static void updateDuplicatesEntry(int trial, int n, int entry, long exch, long less, double time) {
		// Student fills in
		switch (trial) {
		case 0:
			if(duplicates[n][entry][trial] == 0.0 || time < duplicates[n][entry][trial]) {
				duplicates[n][entry][trial] = time;
			}
			break;
		case 1:
			if(duplicates[n][entry][trial] == 0.0 || exch < duplicates[n][entry][trial]) {
				duplicates[n][entry][trial] = exch;
			}
			break;
		case 2:
			if(duplicates[n][entry][trial] == 0 || less < duplicates[n][entry][trial]) {
				duplicates[n][entry][trial] = less;
			}
			break;
		default:
			System.out.println("ERROR!");
			return;
		}
	}
	
public static void main(String[] args) {
	
		int T = 3;
		StopwatchCPU watch;
		Integer[] data;
		double time;
		
		for (int t = 0; t < T; t++) {
			System.out.printf("Trial %d ...%n", t+1);
			for (int k = lowSize, idx = 0; k <= highSize; k++, idx++) {
				
				// For each algorithm to be compared (MergeSort,QuickSort,QuickSortAlternate)
				// you must generate the data set to use, create a Stopwatch to measure the time, launch the 
				// sort request, record the elapsed time, validate that the array was actually sorted, and 
				// then update the appropriate entry (whether Unique or Duplicates) with the recorded new values...
				
				// Here is a sample using Insertion Sort. You will have SIX such blocks: THREE for unique data,
				// and THREE for highly duplicated data
//Mergesort - Unique Test
				data = generateUniqueData(k);
				watch = new StopwatchCPU();
				try {
					Insertion.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Merge sort");
					}
					watch = null;
					updateUniqueEntry (t, idx, 0, Insertion.exchCount, Insertion.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow! Be sure to put this in just in case (hint....)
					System.out.printf("Stack Overflow (%d)!%n", k);
				}
				

//Mergesort - Unique Test
data = generateUniqueData(k);
watch = new StopwatchCPU();
try {
	Insertion.sort(data);
	time = watch.elapsedTime();
	if (!isSorted(data)) {
		System.out.println("ERROR with Merge sort");
	}
	watch = null;
	updateUniqueEntry (t, idx, 0, Insertion.exchCount, Insertion.lessCount, time);
} catch (StackOverflowError e) {
	// stack overflow! Be sure to put this in just in case (hint....)
	System.out.printf("Stack Overflow (%d)!%n", k);
}

			}
		}
		
		generateReport();
	}

}
