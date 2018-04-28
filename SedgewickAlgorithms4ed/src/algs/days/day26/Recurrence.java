package algs.days.day26;

public class Recurrence {
	public static int compute (int N, int k) {
		if (k <= 1) { return 0; }

		int count = 0;
		for (int j = 0; j < Math.sqrt(N); j++) {
			count++;
		}

		return count + compute (N, k/2);
	}

	public static void main(String[] args) {
		for (int N = 2; N < 65536*16; N*= 2) {
			// Model is technically Ceiling(Sqrt(n)) * Ceiling(log_2(N))
			double model = Math.ceil(Math.sqrt(N))* Math.ceil(Math.log(N)/Math.log(2));
			System.out.printf("%d\t%d\t%.2f\n", N, compute(N, N), model);
		}
	}
	
}
