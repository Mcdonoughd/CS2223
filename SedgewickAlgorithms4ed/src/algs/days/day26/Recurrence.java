package algs.days.day26;

public class Recurrence {
	public static int compute (int N, int k) {
		if (k < 1) { return 0; }

		int count = 0;
		for (int j = 0; j < Math.sqrt(N); j++) {
			count++;
		}

		return count + compute (N, k/2);
	}

	public static void main(String[] args) {
		for (int N = 2; N < 65536*16; N*= 2) {
			System.out.printf("%d\t%d\n", N, compute(N, N));
		}
	}
	
}
