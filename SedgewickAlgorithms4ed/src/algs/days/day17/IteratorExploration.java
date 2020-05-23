package algs.days.day17;

import edu.princeton.cs.algs4.StdOut;
import algs.days.day04.ResizingArrayStack;

public class IteratorExploration {
	public static void main(String[] args) {
		ResizingArrayStack<Integer> stack = new ResizingArrayStack<Integer>();
		
		stack.push(95);
		stack.push(13);
		stack.push(101);
		stack.push(15);
		
		// outputs numbers in LIFO order
		for (int x : stack) {
			StdOut.println(x);
		}
	}
}
