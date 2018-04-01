package algs.solutions.hw2;

import edu.princeton.cs.algs4.StdRandom;

// taken as is from section 2.4 of Sedgewick 4ed. Simplified a bit.
public class MaxPQ<Key> {
	private Key[] pq;                    // store items at indices 1 to N (pq[0] is unused)
	private int N;                       // number of items on priority queue
	int lessCount = 0;

	static Double[] generateData(int n) {
		Double[] vals = new Double[n];
		for (int i = 0; i < n; i++) {
			vals[i] = StdRandom.uniform();
		}
		return vals;
	}
	
	public MaxPQ(int initCapacity) {
		pq = (Key[]) new Object[initCapacity + 1];
		N = 0;
	}

	public boolean isEmpty() { return N == 0;  }
	public int size() { return N; }

	// resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= N;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 0; i <= N; i++) {   // have to be <=N because 0th element is burned... CAREFUL
            temp[i] = pq[i];
        }
        pq = temp;
    }
	
	public void insert(Key x) {
		if (N == pq.length-1) resize(2*pq.length);    // double size of array if necessary. Note: Burn first cell...
		pq[++N] = x;
		swim(N);
	}

	public Key delMax() {
		Key max = pq[1];
		exch(1, N--);
		pq[N+1] = null;     // to avoid loitering and help with garbage collection
		sink(1);
		
		// shrink size of array if necessary
        if (N > 0 && N == pq.length/4) resize(pq.length/2);
		return max;
	}

	
	// This is going to require some work. Since we do 1-based indexing (as suggested by book).
	// the first node without children is in m=(N/2)+1 or floor(N/2)+1. Then you need to find
	// lowest in next N/2 entries, from m to N. Then you swap with last one and swim.
	public Key delMin() {
		int m = (N/2)+1;
		for (int i = m+1; i <= N; i++) {
			if (less(i, m)) {
				m = i;
			}
		}
		
		// we now have the smallest one we could find. swap with last, unless already last, in which case done!
		Key ret = pq[m];
		if (m == N) {
			pq[N--] = null;  // avoid loitering. NO need to swim at all!
		} else {
			exch(m, N);
			pq[N--] = null; // avoid loitering
			swim (m);   // re-heapify
		}
		
		// shrink size of array if necessary
        if (N > 0 && N == pq.length/4) {
        	resize(pq.length/2);
        }
		return ret;
	}
	
	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/
	private void swim(int k) {
		while (k > 1 && less(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}

	private void sink(int k) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less(j, j+1)) j++;
			if (!less(k, j)) break;
			exch(k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for compares and swaps.
	 ***************************************************************************/
	private boolean less(int i, int j) {
		lessCount++;
		return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
	}

	private void exch(int i, int j) {
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}

	// is pq[1..N] a max heap?
	private boolean isMaxHeap() {
		return isMaxHeap(1);
	}

	// is subtree of pq[1..N] rooted at k a max heap?
	private boolean isMaxHeap(int k) {
		if (k > N) return true;
		int left = 2*k, right = 2*k + 1;
		if (left  <= N && less(k, left))  return false;
		if (right <= N && less(k, right)) return false;
		return isMaxHeap(left) && isMaxHeap(right);
	}
}
