package dmcdonough.hw3;


import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

// Sedgewick, 4ed
public class Selection {

	
	
	

	public static int numcomp; //count the comparisons
	public static int numexch; //count the exchanges
	static int[][] results = new int[12][2]; //array of max comp and exchanges
	
	static String[] generateData(int n) {
		String[] vals = new String[n];
		for (int i = 0; i < n; i++) {
			vals[i] = Double.toString(StdRandom.uniform());//generate uniform data
		}
		return vals;
	}

	
    /**
     * Rearranges the array in ascending order, using the natural order.
     * @param a the array to be sorted
     */
    public static void sort(Comparable[] a) {
        int N = a.length;
        							
        // Iterative solution.
        // C(n) is the total number of times you compare two values in the array
        for (int i = 0; i < N; i++) {
            int min = i;
													
            // for each problem you do (N-i) comparisons
            for (int j = i+1; j < N; j++) {
                if (less(a[j], a[min])) {
                	min = j;
                }
            }
													
            exch(a, i, min);
            				
            // when you get here, you have made advanced the problem one set
        }
        											 
    }

   /***************************************************************************
    *  Helper sorting functions.
    ***************************************************************************/
  
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
    	numcomp++;
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    // print array to standard output
    private static void show(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }
  //update entry int the results
  	 private static void updateEntry(int n) {
  			if(results[n][0] <= numcomp){
  				results[n][0] = numcomp;
  			}
  			if(results[n][1] <= numexch ) {
  				results[n][1] = numexch;
  			}
  		}
  		public static void generateReport(int n,int idx) {
  				StdOut.println(n + "\t" + results[idx][0] + "\t" + results[idx][1]);
  		}
    /**
     * Reads in a sequence of strings from standard input; selection sorts them; 
     * and prints them to standard output in ascending order. 
     */
    public static void main(String[] args) {
    	
    	System.out.println("N\tMaxComp\tMaxExch");
		 //for all sizes of arrays test num of array
		 for(int n = 16,idx = 0; n<=512; n*=2,idx++) {
			 //do 10 trials
			 for(int t = 0; t<10;t++) {
				 String[] a = generateData(n);
				 Heap.sort(a);
				 //show(a);
				 updateEntry(idx);
			//reset comp and exch count
				 numcomp = 0;
				 numexch = 0; 
		 }
			 generateReport(n,idx);
	 }
    }
}