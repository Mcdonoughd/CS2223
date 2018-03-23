package algs.days.day08;

public class HeapBuster {

	static void dive (int n) {
		System.out.println(n);
		
		dive(n+1);
	}
	
	public static void main(String[] args) {
		dive(0);
	}
}
