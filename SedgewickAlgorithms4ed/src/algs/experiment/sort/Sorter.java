package algs.experiment.sort;

import java.security.MessageDigest;
import java.util.Arrays;

import algs.experiment.Indexable;


/**
 * You are to use this class and write an algorithm to sort a (uniform) random collection. Once done
 * you can get the number of comparisons and the number of swaps.
 * 
 * Purpose of this class is to support experiments on this problem.
 * 
 * Sometimes your goal is to sort with fewest number of comparisons. Sometimes with the fewest number
 * of swaps. You decide which algorithm to use, and this class will keep track of your statistics 
 * and report truthfully. It isn't possible to spoof its results. 
 *  
 * You are not to modify this class.
 *  
 * @author George Heineman
 */
public class Sorter implements Indexable<Sorter.Item> {
	private int numComparisons = 0;
	private int numSwaps = 0;
	private int numAssignments = 0;
	
	private Item items[];
	private boolean exception = false;
	
	/** Each item is internal and cannot be accessed until assertion is made. */
	protected class Item implements Comparable<Item> {
		double val; 
		Item (double val) {
			this.val = val;
		}
		
		@Override
		public int compareTo(Item o) {
			numComparisons++;
			if (this.val > o.val) {
				return +1;
			}
			if (this.val < o.val) {
				return -1;
			}
			return 0;
		}
	}
	
	/** Return the size of the Selection. */
	public int size() {
		return items.length;
	}
	
	/** Retrieve opaque item. */
	public Comparable<Sorter.Item> get(int i) throws Exception {
		assertClean();
		return items[i];
	}
	
	/** Set the ith element to be a previously retrieved object. Counts as an assignment! */
	public void set(int i, Comparable<Sorter.Item> o) throws Exception {
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
	public Sorter (int n) {
		items = new Item[n];
		for (int i = 0; i < n; i++) {
			items[i] = new Item(Math.random());
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
	 * Compares the two items in Sorter.
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
	 * Swaps the two items in Sorter.
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
	 * Initialze with n random elements which might allow for duplicates.
	 * 
	 * @param n        size of the collection
	 * @param range    values will be drawn from the range [0, range).
	 */
	public Sorter (int n, int range) {
		items = new Item[n];
		for (int i = 0; i < n; i++) {
			items[i] = new Item((int)(range*Math.random()));
		}
	}
	
	/** Once Sorter has thrown an exception, we are done. */
	private void assertClean() throws Exception {
		if (exception) { throw new Exception ("Collection has already thrown an exception."); }
	}
	
	/**
	 * Assert that the collection is sorted.
	 * 
	 * If the collection is NOT sorted, then exception is thrown.
	 */
	public void assertSorted() throws Exception {
		assertClean();
		
		for (int i = 0; i < items.length-1; i++) { 
			if (items[i].compareTo(items[i+1]) == 1) {
				exception = true;
				throw new Exception ("Collection is not sorted at item location " + i);
			}
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
		assertSorted();
		
		return "[numCompare=" + numComparisons + ",numSwap=" + numSwaps + ",numAssignments = " + numAssignments + ",key=" + key();
	}
}
