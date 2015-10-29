package algs.days.day02;

import java.util.Stack;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class AnagramAnother15 {

	static char[] letters;
	static String[] words;

	static int ctr = 0;

	public static int rank(String key, String[] a) {
		int lo = 0;
		int hi = a.length -1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = key.compareTo(a[mid]);
			if (cmp < 0) hi = mid - 1;
			else if (cmp > 0) lo = mid + 1;
			else return mid;
		}

		return -1;
	}

	public static boolean in (int a, int[] ar) {
		for (int i = 0; i < ar.length; i++) {
			if (ar[i] == a) { return true; }
		}
		return false;
	}

	static void next(Stack<Integer> already) {
		if (already.size() == 15) {
			ctr++;
			StringBuilder word = new StringBuilder();
			for (int pos : already) {
				word.append(letters[pos]);
			}
			if (rank(word.toString(), words) != -1) {
				System.out.println(word + " at " + ctr);	
			}
		} else {
			for (int i = 0; i < 15; i++) {
				if (!already.contains(i)) {
					already.push(i);
					next(already);
					already.pop();
				}
			}
		}
	}

	public static void main(String[] args) {
		In in = new In ("words.english.txt");
		words = in.readAllStrings();

		StdOut.println("Enter 15 letters");
		String word = StdIn.readString().toLowerCase();
		letters = word.toCharArray();
		Stack<Integer> seen = new Stack<Integer>();
		next(seen);
	}
}