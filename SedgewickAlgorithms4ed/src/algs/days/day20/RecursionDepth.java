package algs.days.day20;

// This fails on my computer at 6291 (2015) and 11402 (2018).
public class RecursionDepth {
	static int count = 0;
	
	public void depth() {
		count++;
		System.out.println(count);
		depth();
	}

	public static void main(String[] args) {
		new RecursionDepth().depth();
	}
}
