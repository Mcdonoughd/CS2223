package algs.experiment.rank;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Iterator;

import algs.experiment.Indexable;
import algs.iterator.ArrayIterator;

/**
 * You are to use this class and write an algorithm to select the kth largest element from a collection.
 * Purpose of this class is to support experiments on this problem.
 * 
 * You are not to modify this class.
 * 
 * @author George Heineman
 */
public class Selection implements Iterable<Selection.Item>, Indexable<Selection.Item> {
	private int numComparisons = 0;
	private int numSwaps = 0;
	private int numAssignments = 0;
	private boolean done = false;
	
	private Item items[];
	private boolean exception = false;
	private long setid;
	
	/** Return the size of the Selection. */
	public int size() {
		return items.length;
	}

	@Override
	public Iterator<Item> iterator() {
		return new ArrayIterator<Item>(items);
	}
	
	/** Return a value equivalent to smallest possible (i.e., negative infinity). */
	public Item smallest() { 
		return new Item(Double.MIN_VALUE, setid);
	}
	
	/** Return a value equivalent to largest possible (i.e., positive infinity). */
	public Item largest() { 
		return new Item(Double.MIN_VALUE, setid);
	}
	
	/** Each item is internal and cannot be accessed until assertion is made. */
	protected class Item implements Comparable<Item> {
		final double val; 
		final long id;
		Item (double val, long id) {
			this.val = val;
			this.id = id;
		}
		
		Item (Item it) {
			this.val = it.val;
			this.id = it.id;
		}
		
		@Override
		public int compareTo(Item o) {
			if (this.id != o.id) {
				throw new RuntimeException ("It is illegal to compare Items from different Selections");
			}
			if (!done) { numComparisons++; }
			if (this.val > o.val) {
				return +1;
			}
			if (this.val < o.val) {
				return -1;
			}
			return 0;
		}
	}
	
	/** Retrieve opaque item. */
	public Comparable<Selection.Item> get(int i) throws Exception {
		assertClean();
		return items[i];
	}
	
	/** Set the ith element to be a previously retrieved object. Counts as an assignment. */
	public void set(int i, Comparable<Selection.Item> o) throws Exception {
		assertClean();
		if (o instanceof Item) {
			numAssignments++;
			items[i] = (Item) o;
			return;
		}
		
		exception = true;
		throw new Exception ("Invalid object being set!");
	}
	
	/**
	 * Initialize with n random elements with no duplication. This is achieved probabilistically
	 * rather than guaranteed.
	 * 
	 * @param n    size of the collection
	 */
	public Selection (int n) {
		items = new Item[n];
		setid = (long) (Math.random()*Long.MAX_VALUE);
		for (int i = 0; i < n; i++) {
			items[i] = new Item(Math.random(), setid);
		}
	}
	
	public void orderAscending() {
		if (numComparisons != 0 || numSwaps != 0 || numAssignments != 0) {
			throw new RuntimeException ("Cannot sort after actions on Selection");
		}
		Arrays.sort(items);
		numComparisons = 0;
		numSwaps = 0;
		numAssignments = 0;
	}
	
	public void orderDescending() {
		if (numComparisons != 0 || numSwaps != 0 || numAssignments != 0) {
			throw new RuntimeException ("Cannot sort after actions on Selection");
		}
		Arrays.sort(items);
		Item[] reverse = new Item[items.length];
		int n = items.length-1;
		for (int i = 0; i <= n; i++) {
			reverse[i] = items[n-i];
		}
		items = reverse;
		numComparisons = 0;
		numSwaps = 0;
		numAssignments = 0;
	}
	
	
	/**
	 * Compares the two items in Selection.
	 * 
	 * Returns +1 if item i is larger than item j, 0 if items are the same, -1 if item i is smaller than 
	 * item j.
	 * 
	 * Increases numComparisons by 1
	 * 
	 * @param i   given item to compare against jth item
	 * @param j   target of the comparison
	 * @return 0 if same, +1 if item[i] > item[j]; -1 if item[i] < item[j]
	 */
	public int compare (int i, int j) throws Exception {
		assertClean();
		try {
			return items[i].compareTo(items[j]);
		} catch (Exception e) {
			exception = true;
			throw (e);
		}
	}
	
	/**
	 * Compares item i against the previously returned object o.
	 * 
	 * @param i   given item to compare against o
	 * @param o   previously retrieved object
	 * @return 0 if same, +1 if item[i] > o; -1 if item[i] < o
	 */
	public int compare (int i, Object o)  throws Exception {
		assertClean();
		try {
			return items[i].compareTo((Item) o);
		} catch (Exception e) {
			exception = true;
			throw (e);
		}
	}
	
	/**
	 * Swaps the two items in Selection.
	 * 
	 * @param i
	 * @param j
	 */
	public void swap (int i, int j) throws Exception {
		try {
			Item t = items[i];
			items[i] = items[j];
			items[j] = t;
			numSwaps++;
		} catch (Exception e) {
			exception = true;
			throw (e); 
		}
	}
	
	/**
	 * Initialize with n random elements which might allow for duplicates.
	 * 
	 * @param n        size of the collection
	 * @param range    values will be drawn from the range [0, range).
	 */
	public Selection (int n, int range) {
		items = new Item[n];
		long setid = (long) (Math.random()*Long.MAX_VALUE);
		for (int i = 0; i < n; i++) {
			items[i] = new Item((int)(range*Math.random()), setid);
		}
	}
	
	/** Once Selection has thrown an exception, we are done. */
	private void assertClean() throws Exception {
		if (exception) { throw new Exception ("Collection has already thrown an exception."); }
	}
	
	/**
	 * Assert that the given item would be at sorted position k if the collection were
	 * to be sorted in ascending order.
	 * 
	 * This internally sorts the items in the collection in sorted order, thus the 0th one is 
	 * smallest and the n-1th is largest.
	 * 
	 * @param k       ordinal position (k is greater than or equal to 0 and less than n where n is size of collection)
	 * @param item    the item which was previously retrieved from the collection.
	 * @exception     If incorrect, an exception is thrown
	 */
	public void assertKthRank(int k, Object item) throws Exception {
		assertClean();
		done = true;
		
		Item[] sorted = new Item[items.length];
		for (int i = 0; i < sorted.length; i++) {
			sorted[i] = new Item(items[i]);
		}
		Arrays.sort(sorted);
		
		if (sorted[k].compareTo((Item)item) != 0) {
			exception = true;
			throw new Exception ("That is not the kth (" + k + ") ranked item, where 0 is smallest.");
		}
	}
	
	// http://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	/**
	 * Return validating key used to authenticate results.
	 * 
	 * @return
	 */
	private String key() {
		String val = "" + numComparisons + "," + numSwaps + "," + numAssignments + ",SECRET";
		byte[] bytesOfMessage;
		try {
			bytesOfMessage = val.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			return bytesToHex(thedigest);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Only call this method when you believe the collection is sorted.
	 * 
	 * @return      String that encodes information to prove your claim.
	 *  
	 * @throws Exception
	 */
	public String getValidation() throws Exception {
		assertClean();
		
		return "[numCompare=" + numComparisons + ",numSwap=" + numSwaps + ",numAssignments = " + numAssignments + ",key=" + key();
	}

}
