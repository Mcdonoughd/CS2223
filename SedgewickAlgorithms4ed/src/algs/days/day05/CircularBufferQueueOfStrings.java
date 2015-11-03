package algs.days.day05;

import edu.princeton.cs.algs4.*;

public class CircularBufferQueueOfStrings  {
	private String[] a;  // holds the items
	private int N;       // number of items in queue
	private int first;   // start of the queue
	private int last;    // end of the queue

	// create an empty queue with given capacity
	public CircularBufferQueueOfStrings(int capacity) {
		a = new String[capacity];
		N = 0;
	}

	public boolean isEmpty()            {  return N == 0;                    }
	public boolean isFull()             {  return N == a.length;             }
	
	public void enqueue(String item) {  
		if (isFull()) { throw new IllegalStateException("Queue is Full."); }
		
		a[last] = item;
		N++;
		last = (last + 1) % a.length;
	}
	
	public String dequeue() { 
		if (isEmpty()) { throw new IllegalStateException("Queue is Empty."); }
		
		String val = a[first];
		N--;
		first = (first + 1) % a.length;
		return val;
	}

	public static void main(String[] args) {
		CircularBufferQueueOfStrings queue = new CircularBufferQueueOfStrings(8);
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (item.equals("-")) {
				if (queue.isEmpty()) {
					StdOut.println ("BAD INPUT: Queue is empty");
				} else {
					StdOut.println ("Dequeued " + queue.dequeue());
				}
			} else {
				if (queue.isFull()) {
					StdOut.println("BAD INPUT: Queue is Full");
				} else {
					queue.enqueue(item);
				}
			}
		}
		StdOut.println();

		// print what's left on the queue
		StdOut.println("(" + queue.N + " left on the queue)");
	} 
} 