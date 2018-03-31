package dmcdonough.hw2;

import edu.princeton.cs.algs4.StdOut;

public class MaxPQ<Key> {
	private Key[] pq;                    // store items at indices 1 to N (pq[0] is unused)
	private int N;                       // number of items on priority queue

	public MaxPQ(int initCapacity) {
		pq = (Key[]) new Object[initCapacity + 1];
		N = 0;
	}

	public boolean isEmpty() { return N == 0;  }
	public int size() { return N; }

	public void insert(Key x) {
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
	
	private int comp_count  = 0;
	
	public int findMin() {
		double min = 999; //set min the a large num
		int index = N;
		
		//you only need to look at the last 2 levels in the sorted heap wich begins at N/4
		for(int i=(N/4 + 1); i<N+1; i++) {
			if((double)pq[i]<min) {
				min = (Double) pq[i];
				index = i;
			}
			comp_count += i;
		}
		// (N/4 + N/2);
		return index;
	}
	
	public int delMin() {
		int min_index = findMin();
		exch(min_index, N--);
		pq[N+1] = null;     // to avoid loitering and help with garbage collection
		sink_count(min_index);
		return min_index;
	}
	
	
	
	public int getCount() {
		return comp_count;
	}
	public void resetCount() {
		comp_count = 0;
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
	
	private void sink_count(int k) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less_count(j, j+1)) j++;
			if (!less_count(k, j)) break;
			exch(k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for compares and swaps.
	 ***************************************************************************/
	private boolean less(int i, int j) {
		return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
	}
	
	private boolean less_count(int i, int j) {
		comp_count++;
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
	
	/****************************
	 *  Sample main
	 ****************************/
	public static void main(String[] args) {
		MaxPQ<Integer> pq = new MaxPQ<Integer>(16);
		Integer[] a = {0,1,2,3,4,5,6,7,8,9};
		for (int s : a) {
			pq.insert(s);
		}
		
		while (!pq.isEmpty()) {
			StdOut.println(pq.delMax());
		}
	}
}
