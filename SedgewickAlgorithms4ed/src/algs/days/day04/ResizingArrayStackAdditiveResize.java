package algs.days.day04;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.*;

// A Copy of resize that extends size of array by 100 items with each new one, rather
// than doubling it in size.

public class ResizingArrayStackAdditiveResize<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int N;            // number of elements on stack

    static int numResizes = 0;
    
    /**
     * Initializes an empty stack.
     */
    public ResizingArrayStackAdditiveResize() {
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
        numResizes++;
    }

    /**
     * Adds the item to this stack.
     * @param item the item to add
     */
    public void push(Item item) {
        if (N == a.length) resize(100+a.length);  // only extend by 100
        a[N++] = item;                            // add item
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
        if (N > 0 && N == a.length/4) resize(a.length/2);
        return item;
    }

    /**
     * Returns (but does not remove) the item most recently added to this stack.
     * @return the item most recently added to this stack
     * @throws java.util.NoSuchElementException if this stack is empty
     */
    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[N-1];
    }

    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     * @return an iterator to this stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;

        public ReverseArrayIterator() {
            i = N-1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }

    /**
     * Unit tests the <tt>Stack</tt> data type.
     */
    public static void main(String[] args) {
        ResizingArrayStackAdditiveResize<Integer> stack = new ResizingArrayStackAdditiveResize<Integer>();
      
        // start with a stack of 100 random elements
        for (int i = 0; i < 100; i++) {
        	stack.push(StdRandom.uniform(1000));
        }
        
        // for 10,000 operations randomly push and pop with .80 probability of push
        Stopwatch sw = new Stopwatch();
        double results[] = new double[70000];
        for (int i = 0; i < 70000; i++) { results[i] = 0; }  // eliminate timing issues
        
        for (int i = 0; i < 70000; i++) {
        	if (StdRandom.uniform() < 0.80) {
        		stack.push(StdRandom.uniform(1000));
        	} else {
        		stack.pop();
        	}
        	results[i] = sw.elapsedTime();
        }
        System.out.println("Final Time: " + sw.elapsedTime() + " (size = " + stack.size() + ", #Resize = " + numResizes + ")");
        System.out.println("Stats: " + StdStats.mean(results) + ", +/-" + StdStats.stddev(results));
        
        // Final Time: 0.06 sometimes 0.07
        // Stats: 0.027894999999977542, +/-0.01669653814606029
        // Stats: 0.0278348571428348, +/-0.016968600784974114
        // Stats: 0.02109185714284204, +/-0.01744786265522115
        // Stats: 0.024319957142858208, +/-0.017627892533654304
    }
}