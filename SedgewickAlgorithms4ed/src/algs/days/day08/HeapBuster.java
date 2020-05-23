package algs.days.day08;

public class HeapBuster {

	static void dive (int n, int cn) {
		System.out.println(n);
		
		dive(n+1, n+1);
	}
	
	public static void main(String[] args) {
		dive(0, 0);
	}
}
