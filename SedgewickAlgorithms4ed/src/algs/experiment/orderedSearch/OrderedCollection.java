package algs.experiment.orderedSearch;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * You are to use this class and write an algorithm to search for item in ordered collection.
 * 
 * Purpose of this class is to support experiments on this problem.
 * 
 * You are not to modify this code.
 * 
 * Use this code for experiments designed to evaluate algorithms that operate over collections that
 * are already ordered in ascending order. Note that the items in this collection are "opaque" to
 * the outside. That is, the external classes can only compare the relative ordering of two items in 
 * the collection
 * 
 * @author George Heineman
 */
public class OrderedCollection {
	private int numComparisons = 0;
	private boolean done = false;
	
	private List<Item> items;
	private boolean exception = false;
	
	/** Each item is internal and cannot be accessed until assertion is made. */
	protected class Item implements Comparable<Item> {
		double val; 
		Item (double val) {
			this.val = val;
		}
		
		@Override
		public int compareTo(Item o) {
			if (!done) { numComparisons++; }
			if (this.val > o.val) {
				return +1;
			}
			if (this.val < o.val) {
				return -1;
			}
			return 0;
		}
		
		public String toString() {
			return "" + this.val;
		}
	}
	
	/** Retrieve opaque item. */
	public Object get(int i) throws Exception {
		assertClean();
		return items.get(i);
	}

	/** Return size of the collection. */
	public int size() { return items.size(); }

	
	/** Returns a random element in the collection. */
	public Object randomElement() throws Exception {
		assertClean();

		int pos = (int) (items.size()*Math.random());
		return items.get(pos);
	}
	
	/** Returns a random element not in the collection. */
	public Object randomElementNotInCollection() throws Exception {
		assertClean();

		Item target = new Item(Math.random());
		while (true) {
			int found = Collections.binarySearch(items, target);
			if (found < 0) {
				return target;
			}
		
			target = new Item(Math.random());
		}
	}
	
	/**
	 * Initialize with n random elements with no duplication. This is achieved probabilistically
	 * rather than guaranteed. List is initially sorted.
	 * 
	 * @param n    size of the collection
	 */
	public OrderedCollection (int n) {
		items = new ArrayList<Item>();
		for (int i = 0; i < n; i++) {
			items.add(new Item(Math.random()));
		}
		
		Collections.sort(items);
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
			return items.get(i).compareTo(items.get(j));
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
			return items.get(i).compareTo((Item) o);
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
	public OrderedCollection (int n, int range) {
		items = new ArrayList<Item>();
		for (int i = 0; i < n; i++) {
			items.add(new Item((int)(range*Math.random())));
		}
		
		Collections.sort(items);
	}
	
	/** Once Collection has thrown an exception, we are done. */
	private void assertClean() throws Exception {
		if (exception) { throw new Exception ("Collection has already thrown an exception."); }
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
		String val = "" + numComparisons + ",SECRET";
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
		
		return "[numCompare=" + numComparisons + ",key=" + key();
	}

	/** Return number of comparisons made. */
	public int numComparisons() { return numComparisons; }

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Item i : items) {
			sb.append(i).append(",");
		}
		
		return sb.toString();
	}
}
