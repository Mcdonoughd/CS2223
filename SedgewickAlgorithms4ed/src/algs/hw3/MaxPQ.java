package algs.hw3;

public class MaxPQ<Key> {
	private Key[] pq;                    // store items at indices 1 to N (pq[0] is unused)
	private int N;                       // number of items on priority queue
	int lessCount = 0;

	public MaxPQ(int initCapacity) {
		pq = (Key[]) new Object[initCapacity + 1];
		N = 0;
	}

	// resize the underlying array
	public void resize(int max) {
		Key[] temp = (Key[]) new Object[max];
		for (int i = 1; i <= N; i++) {
			temp[i] = pq[i];
		}
		pq = temp;
	}

	public boolean isEmpty() { return N == 0;  }
	public int size() { return N; }

	public void insert(Key x) {
		if (N == pq.length-1) resize(2*pq.length);
		pq[++N] = x;
		swim(N);
	}

	public Key delMax() {
		Key max = pq[1];
		exch(1, N--);
		pq[N+1] = null;     // to avoid loitering and help with garbage collection
		sink(1);
		return max;
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

}
