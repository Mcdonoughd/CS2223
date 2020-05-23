package algs.solutions.hw2;

public class MathAnalysis {
	
	public static void testAvg(int N) {
		double log2n = Math.log(N)/Math.log(2);
		System.out.println(Math.pow(3.0/2.0,log2n) * (1 - (log2n-1)/9.0));
	}
}
