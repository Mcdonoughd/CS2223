package algs.solutions.exam1;

public class MakeCopy {
	/**
	 * Given a stack of integers, you must return a copy of the stack.
	 * 
	 * When this method returns the original stack must contain its original contents in their original 
	 * positions.
	 */
	public static StackOfIntegers copy (StackOfIntegers stack) {
		StackOfIntegers extra = new StackOfIntegers();
		QueueOfIntegers queue = new QueueOfIntegers();
		
		while (!stack.isEmpty()) {
			extra.push(stack.pop());
		}
		
		// extra is now reverse.
		StackOfIntegers copy = new StackOfIntegers();
		
		// now know the minimum
		while (!extra.isEmpty()) {
			int x = extra.pop();
			copy.push(x);
			stack.push(x);
		}
		
		return copy;
	}
	
	static StackOfIntegers construct() {
		StackOfIntegers st = new StackOfIntegers();
		st.push(2);
		st.push(9);
		st.push(3);
		st.push(2);
		st.push(12);
		st.push(7);
		return st;
	}
	
	public static void main(String[] args) {
		StackOfIntegers st = construct();
		
		StackOfIntegers c = copy(st);
		
		while (!st.isEmpty()) { System.out.print(st.pop() + " "); }
		System.out.println();
		while (!c.isEmpty()) { System.out.print(c.pop() + " "); }
	}
	
}
