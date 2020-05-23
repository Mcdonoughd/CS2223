package algs.solutions.hw1;

/**
 * 
    This table is result of consecutive numbers...
     
	1,10,10,10,9,10
	2,136,136,104,58,82
	3,2080,2080,1064,339,528
	4,32896,32896,10464,1828,2938
	5,524800,524800,99552,9285,15020
	6,8390656,8390656,924160,45190,72910
	7,134225920,134225920,8422528,213255,342160
	8,2147516416,2147516416,75663872,983560,1568530
	9,34359869440,34359869440,671880704,4457481,7068180
	
	// this table is the result of even numbers.
	
	1,26,24,24,22,24
	2,392,308,264,154,196
	3,6176,4440,2840,956,1278
	4,98432,67952,28800,5318,7208
	5,1573376,1066464,279328,27432,37290
	6,25167872,16914368,2626176,134538,182796
	7,402661376,269508480,24143232,637324,864654
	8,-2147450880,8486656,218237952,2945038,3989008
	
	Note that the values are different because the input is different. How would you go about
	determining worst case behavior?
	
	Note: To get proper counts for the 
	final two rows, you need to have 'numChecked' variable
	in TwiceSorted be a long (and then modify the trial() return type).

 */
public class TwiceSorted_Comparison {
	
	/** Will contain numbers from 0 .. 2^(k+1)-1. Starts from 0 because that is what trial() expects. */
	static int[][] generateTwiceSorted(int k) {
		int N = (int) Math.pow(2, k);
		
		int[][] ar = new int[N][N];
		int max = 0;
		
		// need to ensure twice sorted property (all rows sorted left to right) and
		// all columns sorted (top to bottom). Simply place increasing elements 
		// within each row, and spill-over into next row.
		for (int r = 0; r < N; r++) { 
			for (int c = 0; c < N; c++) {
				ar[r][c] = max++;
				max++;  // only put in even numbers, so we can generate misses inside.
			}
		}
		
		return ar;
	}
	
	public static void main(String[] args) {
		long naive = new StraightForward_TwiceSorted().trial(512);
		
		long sb = new StraightForward_TwiceSortedSlightlyBetter().trial(512);
		long rbs = new Solution_RepeatedBinarySearch().trial(512);
		long ccf = new Solution_TwiceSorted_CheckColumnFirst().trial(512);
		long crf = new Solution_TwiceSorted_CheckRowFirst().trial(512);
		long crd = new Solution_TwiceSorted_Diagonal().trial(512);
		long crdpc = new Solution_TwiceSorted_Diagonal_pre_check().trial(512);
		
		System.out.printf("%d,%d,%d,%d,%d,%d,%d%n", naive, sb, rbs, ccf, crf, crd, crdpc);

		
		for (int k = 1; k < 10; k++) {
			int [][]ar = generateTwiceSorted(k);
			int num = (int)(2*Math.pow(2, k*2));
			
			 naive = new StraightForward_TwiceSorted(ar).trial(num);
			 sb = new StraightForward_TwiceSortedSlightlyBetter(ar).trial(num);
			 rbs = new Solution_RepeatedBinarySearch(ar).trial(num);
			 ccf = new Solution_TwiceSorted_CheckColumnFirst(ar).trial(num);
			 crf = new Solution_TwiceSorted_CheckRowFirst(ar).trial(num);
			 crd = new Solution_TwiceSorted_Diagonal(ar).trial(num);
			 crdpc = new Solution_TwiceSorted_Diagonal_pre_check(ar).trial(num);
			
			System.out.printf("%d,%d,%d,%d,%d,%d,%d,%d%n", k, naive, sb, rbs, ccf, crf, crd, crdpc);
		}
	}
}
