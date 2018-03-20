package algs.days.day04;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.*;

/**
 *  The <tt>ResizingArrayStack</tt> class represents a last-in-first-out (LIFO) stack
 *  of generic items.
 *  It supports the usual <em>push</em> and <em>pop</em> operations, along with methods
 *  for peeking at the top item, testing if the stack is empty, and iterating through
 *  the items in LIFO order.
 *  <p>
 *  This implementation uses a resizing array, which double the underlying array
 *  when it is full and halves the underlying array when it is one-quarter full.
 *  The <em>push</em> and <em>pop</em> operations take constant amortized time.
 *  The <em>size</em>, <em>peek</em>, and <em>is-empty</em> operations takes
 *  constant time in the worst case. 
 *  <p>
 *  For additional documentation,
 *  see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class ResizingArrayStackExperiment<Item>  {
    private Item[] a;         // array of items
    private int N;            // number of elements on stack

    private double growthFactor = 2;  // multiplier for growing stack
    private double shrinkFactor = 2;
    int numGrowths = 0;
    int numShrinks = 0;
    
    /**
     * Initializes an empty stack.
     */
    public ResizingArrayStackExperiment() {
        a = (Item[]) new Object[2];
        N = 0;
    }

    /**
     * Is this stack empty?
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of items in the stack.
     * @return the number of items in the stack
     */
    public int size() {
        return N;
    }


    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    /**
     * Adds the item to this stack.
     * @param item the item to add
     */
    public void push(Item item) {
        if (N == a.length) {
        	resize((int)(growthFactor*a.length));    // grow size of array if necessary
        	numGrowths++;
        }
        a[N++] = item;                           					// add item
    }

    /**
     * Removes and returns the item most recently added to this stack.
     * @return the item most recently added
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = a[N-1];
        a[N-1] = null;                              // to avoid loitering
        N--;
        // shrink size of array if necessary
        if (N > 0 && N == a.length/(growthFactor+shrinkFactor)) {
        	resize((int)(a.length/growthFactor));
        	numShrinks++;
        }
        return item;
    }

    /**
     * Unit tests the <tt>Stack</tt> data type.
     */
    public static void main(String[] args) {
        int numTrials = 50;
    	
        for (double growth = 1.5; growth <= 8.0; growth += 0.5) {
	        float totalSize = 0;
	        float totalGrowth = 0;
	        float totalShrink = 0;
	        float totalTime = 0;
	        for (int trial = 0; trial < numTrials; trial++) {
	        	ResizingArrayStackExperiment<Integer> stack = new ResizingArrayStackExperiment<Integer>();
	        	stack.growthFactor = growth;
		        System.gc();
		        Stopwatch sw = new Stopwatch();
	            // start with a stack of 100 random elements
	            for (int i = 0; i < 100; i++) {
	            	stack.push(StdRandom.uniform(1000));
	            }
		        for (int i = 0; i < 50000; i++) {
		        	if (StdRandom.uniform() < 0.40) {
		        		stack.push(StdRandom.uniform(1000));
		        	} else {
		        		if (!stack.isEmpty()) {
		        			stack.pop();
		        		}
		        	}
		        }
		        totalTime += sw.elapsedTime();
		        totalSize += stack.size();
		        totalGrowth += stack.numGrowths;
		        totalShrink += stack.numShrinks;
	        }
	        System.out.printf("%2.1f	%2.4f,%2.1f,%2.1f,%2.1f%n", growth, (totalTime/numTrials), (totalSize/numTrials), (totalGrowth/numTrials), (totalShrink/numTrials));
        }

    }
}