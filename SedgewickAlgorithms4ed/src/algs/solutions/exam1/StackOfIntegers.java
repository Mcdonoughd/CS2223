package algs.solutions.exam1;

/** Slightly modified from p. 141 implementation. */
public class StackOfIntegers {
	private Integer[] a;  // holds the items
	private int N;        // number of items in stack

	// create an empty stack with given capacity
	public StackOfIntegers() {
		a = new Integer[99];
		N = 0;
	}

	public boolean isEmpty()            {  return N == 0;        }
	public boolean isFull()             {  return N == a.length; }
	public void push(Integer item)      {  a[N++] = item;        }
	public Integer pop()                {  return a[--N];        }
}
