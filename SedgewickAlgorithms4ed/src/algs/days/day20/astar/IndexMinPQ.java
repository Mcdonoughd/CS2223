package algs.days.day20.astar;

import java.util.NoSuchElementException;

// put here for AStar solver only
public class IndexMinPQ<Key extends Comparable<Key>> {
	int maxN;        // maximum number of elements on PQ
	int N;           // number of elements on PQ
	int[] pq;        // binary heap using 1-based indexing
	int[] qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
	Key[] keys;      // keys[i] = priority of i

	/**
	 * Initializes an empty indexed priority queue with indices between <tt>0</tt>
	 * and <tt>maxN - 1</tt>.
	 * @param  maxN the keys on this priority queue are index from <tt>0</tt>
	 *         <tt>maxN - 1</tt>
	 * @throws IllegalArgumentException if <tt>maxN</tt> &lt; <tt>0</tt>
	 */
	public IndexMinPQ(int maxN) {
		if (maxN < 0) throw new IllegalArgumentException();
		this.maxN = maxN;
		keys = (Key[]) new Comparable[maxN];        // make this of length maxN??
		pq   = new int[maxN + 1];
		qp   = new int[maxN + 1];                   // make this of length maxN??
		for (int i = 0; i <= maxN; i++)
			qp[i] = -1;
	}

	public boolean isEmpty() { return N == 0; }
	public int size() { return N; }


    /** Is i an index on this priority queue? NOT TRADITIONAL HEAP OPERATION */
    public boolean contains(int i) {
        if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
        return qp[i] != -1;
    }
	
	/** Insert key and record position. */
	public void insert(int i, Key key) {
		if (i < 0 || i >= maxN) throw new IndexOutOfBoundsException();
		N++;
		qp[i] = N;
		pq[N] = i;
		keys[i] = key;
		swim(N);
	}

	/** Removes a minimum key and returns its associated index. */
	public int delMin() { 
		if (N == 0) throw new NoSuchElementException("Priority queue underflow");
		int min = pq[1];        
		exch(1, N--); 
		sink(1);
		qp[min] = -1;            // delete
		keys[pq[N+1]] = null;    // to help with garbage collection
		pq[N+1] = -1;            // not needed
		return min; 
	}

	/**
	 * Decrease the key associated with index <tt>i</tt> to the specified value.
	 *
	 * @param  i the index of the key to decrease
	 * @param  key decrease the key assocated with index <tt>i</tt> to this key
	 * @throws IndexOutOfBoundsException unless 0 &le; <tt>i</tt> &lt; <tt>maxN</tt>
	 * @throws IllegalArgumentException if key &ge; key associated with index <tt>i</tt>
	 * @throws NoSuchElementException no key is associated with index <tt>i</tt>
	 */
	public void decreaseKey(int i, Key key) {
		keys[i] = key;
		swim(qp[i]);
	}

	/***************************************************************************
	 * General helper functions.
	 ***************************************************************************/
	private boolean greater(int i, int j) {
		return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
	}

	/** Note that we need to record location within priority queue when swapping. */
	private void exch(int i, int j) {
		int swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}


	/***************************************************************************
	 * Heap helper functions.
	 ***************************************************************************/
	private void swim(int k)  {
		while (k > 1 && greater(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}

	private void sink(int k) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && greater(j, j+1)) j++;
			if (!greater(k, j)) break;
			exch(k, j);
			k = j;
		}
	}
}
