package algs.days.day08;

// set breakpoint in partition to see this work.
public class PartitionExample {
	public static void main(String[] args) {
		String[] a = new String [] { "egg", "fly", "ant", "cat", "dog", "get", "bat" };
        Quick.partition(a, 0, 6);
        Quick.show(a);
	}
}
