package algs.iterator;

import junit.framework.TestCase;

/** Simple test case for iterator. */
public class TestFixedIterator extends TestCase {
	public void testOne() {
		String[] val = new String[] { "simple" };
		
		FixedListIterator<String> fi = new FixedListIterator<String>(val);
		assertTrue (fi.hasNext());
		assertFalse (fi.hasPrevious());
		
		assertEquals ("simple", fi.next());
		assertTrue (fi.hasPrevious());
		assertEquals ("simple", fi.previous());
		assertFalse (fi.hasPrevious());
		fi.set("best");
		assertEquals ("best", val[0]);
		fi.set("lastChange");
		assertEquals ("lastChange", val[0]);	
	}
}
