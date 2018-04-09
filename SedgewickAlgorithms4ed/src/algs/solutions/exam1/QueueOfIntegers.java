package algs.solutions.exam1;


// Strawman implementation. This is not the way a queue should be implemented....
/**
 * 
 * @param <Item>
 */
public class QueueOfIntegers  {
	private Integer[] a;  // holds the items
	private int N;       // number of items in queue

	// create an empty queue with given capacity
	public QueueOfIntegers() {
		a = new Integer[99];
		N = 0;
	}

	public boolean isEmpty()            {  return N == 0;                    }
	public boolean isFull()             {  return N == a.length;             }
	public void enqueue(Integer item)   {  a[N++] = item;                    }
	
	
	public Integer dequeue()             { 
		Integer val = a[0];
		for (int i = 0; i < N; i++) {
			a[i] = a[i+1];
		}
		a[--N] = null;
		return val;
	}

	
} 