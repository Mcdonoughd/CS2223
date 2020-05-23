package algs.days.day02;

import java.util.Arrays;
import edu.princeton.cs.algs4.*;    // MUST ADD THIS AT START!

/**
 * code from p. 47 of the 4ed Sedgewick
 *
 * % java algs.days.day02.BinarySearch tinyW.txt < tinyT.txt
 * suitable for Linux but not for in-class demonstration
 *
 * Run the Shell class which is in the default package. Then type the
 * same command above:
 *
 * java algs.days.day02.BinarySearch tinyW.txt < tinyT.txt
 * 
 * and it will run properly.
 */
public class BinarySearch {
	
	public static int rank(int key, int[] a) {
		int lo = 0;
		int hi = a.length -1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			if (key < a[mid]) hi = mid - 1;
			else if (key > a[mid]) lo = mid + 1;
			else return mid;
		}
		
		return -1;
	}
	
	public static void main(String[] args) {
		In in = new In (args[0]);
		int[] whitelist = in.readAllInts();
		Arrays.sort(whitelist);
		
		while (!StdIn.isEmpty()) {
			int key = StdIn.readInt();
			if (rank(key, whitelist) == -1) 
				StdOut.println(key);
		}
		
	}
}
