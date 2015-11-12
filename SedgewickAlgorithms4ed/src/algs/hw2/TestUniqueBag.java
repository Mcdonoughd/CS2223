package algs.hw2;

import java.util.Arrays;

import junit.framework.TestCase;

// Select file and choose Run As ... | JUnit Test Case within eclipse
//
// I may add some more test cases but will finalize by Wednesday.
public class TestUniqueBag extends TestCase {

	public void testEmpty() {
		UniqueBag<String> ub = new UniqueBag<String>();
		assertEquals (0, ub.size());
	}
	
	public void testOrderedAdd() {
		UniqueBag<String> ub = new UniqueBag<String>();
		ub.add("boy");
		ub.add("add");
		
		assertTrue (Arrays.equals(new String[] { "add", "boy"}, ub.toArray()));
	}
	
	public void testOrderedMultiple() {
		UniqueBag<String> ub = new UniqueBag<String>();
		ub.add("boy");
		ub.add("cat");
		assertTrue (ub.contains("boy"));
		assertTrue (ub.contains("cat"));
		
		
		assertTrue (Arrays.equals(new String[] { "boy", "cat"}, ub.toArray()));
		
		ub.add("bus");
		assertTrue (Arrays.equals(new String[] { "boy", "bus", "cat"}, ub.toArray()));
		assertTrue (ub.contains("bus"));
		assertTrue (ub.contains("cat"));
		assertFalse (ub.contains("any"));
		assertFalse (ub.contains("dog"));
		assertFalse (ub.contains("but"));
		
		ub.add("ant");
		assertTrue (Arrays.equals(new String[] { "ant", "boy", "bus", "cat"}, ub.toArray()));

		ub.add("dog");
		assertTrue (Arrays.equals(new String[] { "ant", "boy", "bus", "cat", "dog"}, ub.toArray()));
	
		assertTrue (ub.remove("ant"));
		assertFalse (ub.contains("ant"));
		assertEquals (4, ub.size());
		assertFalse (ub.remove("ant"));
		assertTrue (ub.remove("boy"));    // remove first
		assertEquals (3, ub.size());
		
		assertTrue (ub.remove("dog"));   // remove last
		assertEquals (2, ub.size());
	}
	
	public void testDuplicates() {
		UniqueBag<String> ub = new UniqueBag<String>();
		assertTrue (ub.add("test"));
		assertFalse (ub.add("test"));
	}
	
	public void testIntersect() {
		UniqueBag<String> ub = new UniqueBag<String>();
		assertTrue (ub.add("bob"));
		assertTrue (ub.add("cat"));
		assertTrue (ub.add("dog"));
		
		UniqueBag<String> other = new UniqueBag<String>();
		assertTrue (other.add("any"));
		assertTrue (other.add("cat"));
		assertTrue (other.add("elf"));
		
		UniqueBag<String> third = ub.intersects(other);
		assertEquals (1, third.size());
		assertTrue (Arrays.equals(new String[] { "cat"}, third.toArray()));
	}

	public void testUnion() {
		UniqueBag<String> ub = new UniqueBag<String>();
		assertTrue (ub.add("bob"));
		assertTrue (ub.add("dog"));
		assertTrue (ub.add("cat"));

		UniqueBag<String> other = new UniqueBag<String>();
		UniqueBag<String> third = other.union(ub);
		assertEquals (3, third.size());
		assertTrue (Arrays.equals(new String[] { "bob", "cat", "dog"}, third.toArray()));
		
		assertTrue (other.add ("elf"));
		assertTrue (other.add ("bob"));
		
		third = other.union(ub);
		assertEquals (4, third.size());
		assertTrue (Arrays.equals(new String[] { "bob", "cat", "dog", "elf"}, third.toArray()));
		
		
	}
	
	public void testNonIntersect() {
		UniqueBag<String> ub = new UniqueBag<String>();
		assertTrue (ub.add("bob"));
		assertTrue (ub.add("cat"));
		assertTrue (ub.add("dog"));
		
		UniqueBag<String> other = new UniqueBag<String>();
		assertTrue (other.add("any"));
		assertTrue (other.add("cry"));
		assertTrue (other.add("elf"));
		
		UniqueBag<String> third = ub.intersects(other);
		assertEquals (0, third.size());
		assertTrue (Arrays.equals(new String[] {}, third.toArray()));

	}

}
