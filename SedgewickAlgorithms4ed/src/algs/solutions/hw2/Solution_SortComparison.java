package algs.solutions.hw2;

import edu.princeton.cs.algs4.*;

// Q1 on Homeework 2
public class Solution_SortComparison {
	
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
     * @param k
     * @return
     */
    static Integer[] generateUniqueData(int k) {
    	int N = (int) Math.pow(2, k);
		Integer[] ar = new Integer[N];
		for (int i = 0; i < N; i++) {
			ar[i] = i;
		}
		
		shuffle(ar);
		return (ar);
    }
    
    /**
     * Write a method that returns an integer which has the following properties.
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
     * @param N
     * @return
     */
	static Integer[] generateHighDuplicateData(int k) {
		
		// populate sorted array with no gaps.
		int N = (int) Math.pow(2, k);
		int[] ar = new int[N];
		Integer[] inspects = new Integer[N];
		int idx = 0;
		for (int i = 0; i < N; i++) { ar[i] = i; }
		
		for (int target = 0; target < N; target++) {
			int lo = 0;
			int hi = N-1;
			int numInspects = 0;
			
			// Within this Row [0..rightCol] use Binary search for target.
			while (lo <= hi) {
				int mid = (lo+hi)/2; //lo + (hi - lo)/2;
				
				numInspects++;
				int rc = ar[mid]- target;
				if (rc < 0) {
					lo = mid+1;
				} else if (rc > 0) {
					hi = mid-1;
				} else {
					break;
				}
			}
			
			inspects[idx++] = numInspects;
		}
		
		shuffle(inspects);
		return inspects;
	}
	
	// replicated here so we don't inadvertently increment counts of less/exch needed for the experiment.
	static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
	static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }
	
	// You are responsible for updating these values. There are four values for s: 0,1,2
	//
	// unique[n][s][0] = best time for algorithm #s on a dataset of size n with n unique values
	// unique[n][s][1] = fewest number of exch operations for algorithm #s on a dataset of size n with n unique values
	// unique[n][s][2] = fewest number of less operations for algorithm #s on a dataset of size n with n unique values
	static double[][][] unique = new double[8][highSize-lowSize+1][3];
	
	// duplicates[n][s][0] = best time for algorithm #s on a dataset of size n with mostly duplicate values
	// duplicates[n][s][1] = fewest number of exch operations for algorithm #s on a dataset of size n with mostly duplicate values
	// duplicates[n][s][2] = fewest number of less operations for algorithm #s on a dataset of size n with mostly duplicate values
	static double[][][] duplicates = new double[8][highSize-lowSize+1][3];
	
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

	private static void updateUniqueEntry(int trial, int n, int entry, long exch, long less, double time) {
		if (trial == 0) {
			unique[n][entry][0] = time;
			unique[n][entry][1] = exch;
			unique[n][entry][2] = less;
			return;
		}

		if (time < unique[n][entry][0]) {
			unique[n][entry][0] = time;
		}
		
		if (exch < unique[n][entry][1]) {
			unique[n][entry][1] = exch; 
		}
		
		if (less < unique[n][entry][2]) {
			unique[n][entry][2] = less;
		}
	}
	
	private static void updateDuplicatesEntry(int trial, int n, int entry, long exch, long less, double time) {
		if (trial == 0) {
			duplicates[n][entry][0] = time;
			duplicates[n][entry][1] = exch;
			duplicates[n][entry][2] = less;
			return;
		}

		if (time < duplicates[n][entry][0]) {
			duplicates[n][entry][0] = time;
		}
		
		if (exch < duplicates[n][entry][1]) {
			duplicates[n][entry][1] = exch;
		}
		
		if (less < duplicates[n][entry][2]) {
			duplicates[n][entry][2] = less;
		}
	}
	
	// use as is...
	public static void main(String[] args) {
		
		int T = 3;
		StopwatchCPU watch;
		Integer[] data;
		double time;
		
		for (int t = 0; t < T; t++) {
			System.out.printf("Trial %d ...%n", t);
			for (int k = lowSize, idx = 0; k <= highSize; k++, idx++) {
				
				
				data = generateUniqueData(k);
				watch = new StopwatchCPU();
				try {
					Merge.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Merge sort");
					}
					updateUniqueEntry (t, idx, 0, Merge.exchCount, Merge.lessCount, time);
					System.out.println("Uniq\t" + t +"\t" + idx + "\t" + Merge.advanceI + "\t" + Merge.advanceJ);
				} catch (StackOverflowError e) {
					// stack overflow!
					System.out.printf("Stack Overflow on Merge (%d)!%n", k);
				}
				
				data = generateUniqueData(k);
				watch = new StopwatchCPU();
				try {
					Quick.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Quick sort");
					}
					updateUniqueEntry (t, idx, 1, Quick.exchCount, Quick.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow!
					System.out.printf("Stack Overflow on Quick (%d)!%n", k);
				}
				
				data = generateUniqueData(k);
				watch = new StopwatchCPU();
				try {
					QuickAlternate.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with QuickAlternate sort");
					}
					updateUniqueEntry (t, idx, 2, QuickAlternate.exchCount, QuickAlternate.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow!
					System.out.printf("Stack Overflow on QuickAlternate (%d)!%n", k);
				}
			
				data = generateHighDuplicateData(k);
				watch = new StopwatchCPU();
				try {
					Merge.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Merge sort");
					}
					updateDuplicatesEntry (t, idx, 0, Merge.exchCount, Merge.lessCount, time);
					System.out.println("Dup\t" + t +"\t" + idx + "\t" + Merge.advanceI + "\t" + Merge.advanceJ);
				} catch (StackOverflowError e) {
					// stack overflow!
					System.out.printf("Stack Overflow on Merge (%d)!%n", k);
				}
				
				data = generateHighDuplicateData(k);
				watch = new StopwatchCPU();
				try {
					Quick.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with Quick sort");
					}
					updateDuplicatesEntry (t, idx, 1, Quick.exchCount, Quick.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow!
					System.out.printf("Stack Overflow on Quick (%d)!%n", k);
				}
				
				data = generateHighDuplicateData(k);
				watch = new StopwatchCPU();
				try {
					QuickAlternate.sort(data);
					time = watch.elapsedTime();
					if (!isSorted(data)) {
						System.out.println("ERROR with QuickAlternate sort");
					}
					updateDuplicatesEntry (t, idx, 2, QuickAlternate.exchCount, QuickAlternate.lessCount, time);
				} catch (StackOverflowError e) {
					// stack overflow!
					System.out.printf("Stack Overflow on QuickAlternate (%d)!%n", k);
				}
				
			}
		}
		
		generateReport();
	}

}
