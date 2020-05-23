package algs.days.day08;


public class PartitionWeakness {
	public static void main(String[] args) {
		String[] a = new String [] { "the", "the", "the", "the", "the", "the", "the" };
		Quick.partition(a, 0, a.length-1);
		Quick.show(a);
	}
}
