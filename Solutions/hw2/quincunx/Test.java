package algs.solutions.hw2.quincunx;

public class Test {
	public static void main(String[] args) {
		Quincunx struct = new Quincunx(32);
		System.out.println(struct);
		
		for (int i = 0; i < 10000; i++) {
			struct.add();
		}
		
		int[] vals = struct.outputResults();
		for (int v : vals) {
			System.out.print(v + ",");
		}
		System.out.println();
	}
}
