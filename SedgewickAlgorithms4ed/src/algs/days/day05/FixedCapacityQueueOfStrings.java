package algs.days.day05;

import edu.princeton.cs.algs4.*;

// Strawman implementation. This is not the way a queue should be implemented....
public class FixedCapacityQueueOfStrings  {
	private String[] a;  // holds the items
	private int N;       // number of items in queue

	// create an empty queue with given capacity
	public FixedCapacityQueueOfStrings(int capacity) {
		a = new String[capacity];
		N = 0;
	}

	public boolean isEmpty()            {  return N == 0;                    }
	public boolean isFull()             {  return N == a.length;             }
	public void enqueue(String item)    {  a[N++] = item;                    }
	
	
	public String dequeue()             { 
		String val = a[0];
		for (int i = 0; i < N; i++) {
			a[i] = a[i+1];
		}
		a[--N] = null;
		return val;
	}

	public static void main(String[] args) {
		FixedCapacityQueueOfStrings queue = new FixedCapacityQueueOfStrings(10);
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (item.equals("-")) {
				if (queue.isEmpty()) {
					StdOut.println ("BAD INPUT");
				} else {
					StdOut.println ("Dequeued " + queue.dequeue());
				}
			} else {
				queue.enqueue(item);
			}
		}
		StdOut.println();

		// print what's left on the queue
		StdOut.println("(" + queue.N + " left on the queue)");
	} 
} 