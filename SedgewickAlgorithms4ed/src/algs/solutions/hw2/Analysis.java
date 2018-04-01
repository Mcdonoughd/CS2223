package algs.solutions.hw2;

import edu.princeton.cs.algs4.StdOut;

public class Analysis {
	
	/**
	 * Based on a deep understanding of the number of Binary Array Searches to find ALL values in a 2^k array.
	 * 
	 * # inspections to locate item
	 * [1]                                      A1=  avg: 1         // 1/1       S1 = 1
	 * [1,2]                                    A2=  avg: 1.5       // 3/2       S2 = 3
	 * [2,1,2,3]                                A4=  avg: 2         // 8/4       S4 = 8
	 * [3,2,3,1,3,2,3,4]                        A8=  avg: 2.625     // 21/8      S8 = 21
	 * [4,3,4,2,4,3,4,1,4,3,4,2,4,3,4,5]        A16= avg: 3.375     // 54/16     S16 = 54
	 * [5,4,5,3,...]                            A32= avg: 4.21875   // 135/32    S32 = 135
	 * [6,5,6,... ]                             A64= avg: 5.12500   // 328/64    S64 = 328   ** So CLOSE! expected it to be 324.
	 * 
	 * [5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,1,5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,6] 
	 * [6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,2,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,1,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,2,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,7]
	 * 
	 * For N=power of 2, the above formula looked like it had recursive structure. Observe that denominators in fraction
	 * are powers of two, and the numerators appear to be computed by multiplying by 3 and subtracting a power of 3.
	 *  
	 * S1 = 1
	 * S2 = 3*S1 - 0 = 3
	 * S4 = 3*S2 - 1 = 8
	 * S8 = 3*S4 - 3 = 21
	 * S16 = 3*S8 - 9 = 54
	 * S32 = 3*S16 - 27 = 135
	 * S64 = 3*S32 - 81 = 324    ** Argh. Breaks down here. So something else needs to be developed.
	 * 
	 * Here is another approach.
	 * 
	 * For V8, count each number that appears in the array
	 *   + one 1
	 *   + two 2s
	 *   + four 3s
	 *   + finally 1 4
	 * For V16, count each number that appears in the array
	 *   + one 1
	 *   + two 2s
	 *   + four 3s
	 *   + eight 4s
	 *   + finally 1 5
	 * 
	 * OK, this is something we can work with. The average will simply be the sum/N
	 * 
	 * The sum is computed as follows:
	  
	                           k
                             -----         
                              \            (i-1) 
                               )      i * 2   
                              /        
                             -----
                             i = 1

	 
	 * If you have never worked with 'maple' you should try it out sometime. It has some really useful capabilities,
	 * like trying to compute a formula for a fixed sum.
	 * 
	 * Run maple on CCC machines (/usr/local/maple16/bin/maple) and type in the following:
	 * 
	 * > sum(i*2^(i-1),i=1..k);
	 
	 
	                     (k + 1)
                        2        (k + 1)    (k + 1)
                        ---------------- - 2        + 1
                               2

	 * Thus, this is the closed-form formula for the summary.
	 * 
	 * @param N
	 */
	static void averageCase(int N) {
		
		// populate sorted array with no gaps.
		int[] ar = new int[N];
		int[] inspects = new int[N];
		int idx = 0;
		for (int i = 0; i < N; i++) { ar[i] = i; }
		
		float sum = 0;
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
			
			sum += numInspects;
			inspects[idx++] = numInspects;
		}
		
		StdOut.printf("%2.5f\t", sum/N);
		for (int i = 0; i < N; i++) {
			StdOut.print(inspects[i]);
			if (i != N-1) { StdOut.print(","); }
		}
	}

	/**
	 * For non-powers of 2, this is more complicated:
	 * 
	 *  N                                                    k  diff
	 *  2: [1,2]              1,1                            1  1                
	 *  3: [2,1,2]            1,2                            1  2
	 *  4: [2,1,2,3]                1,2,1                    2  1
	 *  5: [2,3,1,2,3]              1,2,2                    2  2
	 *  6: [2,3,1,3,2,3]            1,2,3                    2  3
	 *  7: [3,2,3,1,3,2,3]          1,2,4                    2  4
	 *  8: [3,2,3,1,3,2,3,4]                1,2,4,1          3  1
	 *  9: [3,2,3,4,1,3,2,3,4]              1,2,4,2          3  2
	 * 10: [3,2,3,4,1,3,4,2,3,4]            1,2,4,3          3  3
	 * 11: [3,4,2,3,4,1,3,4,2,3,4]          1,2,4,4          3  4
	 * 12: [3,4,2,3,4,1,3,4,2,4,3,4]        1,2,4,5          3  5
	 * 13: [3,4,2,4,3,4,1,3,4,2,4,3,4]      1,2,4,6          3  6
	 * ....
	 * 
	 * for N = power of 2, sum is (log N + 1) + SUM(i*2^(i-1)) for i=[1,log N] inclusive
	 * 
	 * Using maple (cheat!) I find following:
	 * 
	 * sum(i*2^(i-1),i=1..k);
	 * 
	 * 
	 * 
     *                    (k + 1)
     *                   2        (k + 1)    (k + 1)
     *                   ---------------- - 2        + 1
     *                          2
	 *
	 * 
	 * 
	 * 
	 * In the case of 13 elements, for example, you find:
	 *    1 element in 1 inspection
	 *    2 elements in 2 inspections
	 *    4 elements in 3 inspections [these 1/2/4/... are highly stylized, based on powers of two]
	 *    6 elements in 4 inspections [these are based on difference from prior power of 2]
	 * 
	 * k=floor(log2(N))
	 * SUM (i=1 to k) [ 2^(i-1)*i] + (k+1)*diff, where diff = (N+1)-2^k
	 * 
	 * As N gets higher and higher, then you can validate that:
	 * 
	 * avg(N) -> log2(N)-1.
	 * 
	 * For example, for N=511, avg(N) = 8.017612524, while log2(N)-1=7.997179481
	 *
	 * @param N
	 */
	static double avg(int N) {
		int k = (int)(Math.floor(Math.log(N)/Math.log(2)));  // This is floor(log2_(N))
		long diff = N + 1 - (int)Math.pow(2,k);
		double sum = 0;  
		for (int i = 1; i <= k; i++) {
			sum += Math.pow(2, i-1)*i;   // some (powers of 2 from 1..k-1)*i
		}

		// add diff*(k+1)
		double av = (sum + diff*(k+1))/N;
		return av;
	}
	
	public static void main(String[] args) {
		
		
		StdOut.println("N\tEst.\tAvg.\tInspectk");
		for (int N = 1; N <= 64; N *= 2) {
			int k = (int)(Math.log(N)/Math.log(2));
			double SN = (k+1) + (Math.pow(2,k+1)*(k+1)/2.0) - Math.pow(2,k+1) + 1;
			double AN=SN/N;
			
			StdOut.printf("%d\t%2.5f\t", N, AN);
			averageCase(N);
			System.out.println();
		}
		
	}
}
