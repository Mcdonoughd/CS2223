package algs.hw2;


import java.util.Arrays;

import junit.framework.TestCase;

/**
 * These test cases do a great job in validating the basic functionality of your classes.
 * 
 * You can use this as a guide to help debug some of the more complicated "edge cases" of your algorithms.
 * 
 * To run, you right click on this class in Eclipse and say "Run As JUnit Test."
 * 
 * Note that you must copy this class into your package for it to work properly.
 * 
 * @author Nicholas Krichevsky
 * @author George Heineman
 */
public class MultiSetTestCase extends TestCase {

	private static final boolean STRICT_ARRAY_CHECKING = true;

	private Comparable[] comp(Integer[] a) {
		Comparable[] c = new Comparable[a.length];
		for (int i = 0; i < a.length; i++) {
			c[i] = a[i];
		}
		return c;
	}
	
	public void testEmpty() {
		MultiSet ms = new MultiSet();
		assertTrue (ms.size() == 0);
	}

	// build up all possible multi sets with 0, 1, or 2
	public void testSmall() {
		// work with one node
		MultiSet<Integer> ms = new MultiSet<Integer>();
		ms.add(2);
		assertTrue (ms.size() == 1);
		ms.add(2);
		assertTrue (ms.size() == 2);
		assertTrue(ms.remove(2));
		assertTrue (ms.size() == 1);
		assertTrue(ms.remove(2));
		assertTrue (ms.size() == 0);

		// now do two nodes (same idea) add 3, 4 then add 4, 3
		ms.add(3);
		assertTrue (ms.size() == 1);
		ms.add(4);
		assertTrue (ms.size() == 2);
		MultiSet<Integer> ms2 = new MultiSet<Integer>();
		ms2.add(4);
		assertTrue (ms2.size() == 1);
		ms2.add(3);
		assertTrue (ms2.size() == 2);
		assertTrue (ms.identical(ms2));

		// delete tail and delete head
		assertTrue(ms.remove(3));
		assertTrue (ms.size() == 1);
		assertTrue(ms2.remove(4));
		assertTrue (ms2.size() == 1);
		assertFalse (ms.identical(ms2));

		// delete middle
		ms = new MultiSet<Integer>();
		ms.add(5);
		ms.add(3);
		ms.add(4);  // add in middle
		assertTrue (ms.size() == 3);
		assertTrue(ms.remove(4));
		assertTrue (ms.size() == 2);
		assertTrue(ms.remove(3));
		assertTrue (ms.size() == 1);
		assertTrue(ms.remove(5));
		assertTrue (ms.size() == 0);
	}

	// build up all possible multi sets with 0, 1, or 2
	public void testIdentical() {
		MultiSet<Integer> ms = new MultiSet<Integer>();
		MultiSet<Integer> ms2 = new MultiSet<Integer>();

		ms.add(4);
		ms.add(7);
		ms.add(4);

		ms2.add(7);
		ms2.add(4);
		ms2.add(4);

		assertTrue(ms2.identical(ms));
		assertTrue(ms2.remove(4));
		assertTrue(ms2.size() == 2);
		assertFalse(ms2.identical(ms));
		assertFalse(ms.identical(ms2));

		assertFalse(ms.identical(null));

		// same number of items, different values.
		ms = new MultiSet<Integer>();
		ms2 = new MultiSet<Integer>();
		ms.add(4);
		ms.add(7);
		ms.add(7);

		ms2.add(7);
		ms2.add(4);
		ms2.add(4);
		assertFalse(ms2.identical(ms));
		assertFalse(ms.identical(ms2));
	}

	// make sure to check counts.
	public void testRemove() {
		MultiSet<Integer> ms = new MultiSet<Integer>();
		assertFalse (ms.remove(9));  // empty should deny right away

		ms.add(4);
		ms.add(4);
		ms.add(7);
		ms.add(7);

		assertFalse(ms.remove(1));  // before
		assertFalse(ms.remove(5));  // in middle
		assertFalse(ms.remove(51));  // at end

		assertTrue(ms.remove(4));
		assertTrue(ms.remove(7));
		assertTrue(ms.remove(4));
		assertTrue(ms.remove(7));
		assertTrue(ms.size() == 0);
	}

	public void testMultiplicity() {
		final int SIZE = 42;
		MultiSet<Integer> set = new MultiSet<>();
		assertEquals (0, set.multiplicity(34));  // any multiplicity on empty set is zero.
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j <= i; j++) {
				set.add(i);
			}
		}
		for (int i = 0; i < SIZE; i++) {
			assertEquals(i + 1, set.multiplicity(i));
		}
		
		set = new MultiSet<>();
		set.add(1);
		set.add(3);
		set.add(5);
		set.add(3);
		assertEquals (0, set.multiplicity(4));  // missing number
		assertEquals (0, set.multiplicity(8));  // missing number after end
	} 

	public void testUnion() {
		Integer[] a1 = {1, 3, 8, 8, 24};
		MultiSet<Integer> set1 = new MultiSet<>(a1);
		Integer[] a2 = {5, 8, 8, 8};
		MultiSet<Integer> set2 = new MultiSet<>(a2);
		Integer[] a3 = {5, 8};
		MultiSet<Integer> set3 = new MultiSet<>(a3);
		assertArrayCorrect(new Comparable[]{1, 3, 5, 8, 8, 8, 24}, set1.union(set2).toArray());
		assertArrayCorrect(new Comparable[]{1, 3, 5, 8, 8, 8, 24}, set2.union(set1).toArray());
		assertArrayCorrect(new Comparable[]{1, 3, 5, 8, 8, 24}, set1.union(set3).toArray());
		assertArrayCorrect(new Comparable[]{1, 3, 5, 8, 8, 24}, set3.union(set1).toArray());
		assertArrayCorrect(new Comparable[]{5, 8, 8, 8}, set3.union(set2).toArray());
		assertArrayCorrect(new Comparable[]{5, 8, 8, 8}, set2.union(set3).toArray());
		Comparable[] next = (new MultiSet<Integer>()).union(set1).toArray();
		Comparable[] first = set1.toArray();
		assertArrayCorrect(first, next);
		first = set1.toArray();
		next = set1.union(new MultiSet<Integer>()).toArray();
		assertArrayCorrect(first, next);
	}

	public void testIntersects() {
		Integer[] a1 = {1, 3, 8, 8, 24};
		MultiSet<Integer> set1 = new MultiSet<Integer>(a1);
		Integer[] a2 = {5, 8, 8, 8};
		MultiSet<Integer> set2 = new MultiSet<Integer>(a2);
		Integer[] a3 = {5, 8};
		MultiSet<Integer> set3 = new MultiSet<Integer>(a3);
		assertArrayCorrect(new Comparable[]{8, 8}, set1.intersects(set2).toArray());
		assertArrayCorrect(new Comparable[]{8, 8}, set2.intersects(set1).toArray());
		assertArrayCorrect(comp(a1), set1.intersects(set1).toArray());
		assertArrayCorrect(new Comparable[]{8}, set3.intersects(set1).toArray());
		assertArrayCorrect(new Comparable[]{8}, set1.intersects(set3).toArray());
		assertArrayCorrect(new Comparable[]{5, 8}, set2.intersects(set3).toArray());
		assertArrayCorrect(new Comparable[]{5, 8}, set3.intersects(set2).toArray());
		assertArrayCorrect(new Comparable[]{}, set1.intersects(new MultiSet<Integer>()).toArray());
		assertArrayCorrect(new Comparable[]{}, (new MultiSet<Integer>()).intersects(set1).toArray());
	}

	public void testToArray() {
		Integer[] a1 = {1, 3, 8, 8, 24};
		MultiSet<Integer> set1 = new MultiSet<>(a1);
		Comparable[] set1Array = set1.toArray();
		assertArrayCorrect(a1, set1Array);
		Integer[] a2 = {8};
		MultiSet<Integer> set2 = new MultiSet<>(a2);
		Comparable[] set2Array = set2.toArray();
		assertArrayCorrect(a2, set2Array);
		Integer[] a3 = {1, 2, 3, 4, 5, 6, 7, 8};
		MultiSet<Integer> set3 = new MultiSet<>(a3);
		Comparable[] set3Array = set3.toArray();
		assertArrayCorrect(a3, set3Array);
		Integer[] a4 = {8, 8, 8, 8};
		MultiSet<Integer> set4 = new MultiSet<>(a4);
		Comparable[] set4Array = set4.toArray();
		assertArrayCorrect(a4, set4Array);
	}

	private static <T> void assertArrayCorrect(T[] a1, T[] a2) {
		if (STRICT_ARRAY_CHECKING) {
			assertTrue (a1.length == a2.length);
			for (int i = 0; i < a1.length; i++) {
				assertEquals(a1[i], a2[i]);
			}
		} else {
			assertTrue(a1.length == a2.length && Arrays.asList(a1).containsAll(Arrays.asList(a1)) && Arrays.asList(a2).containsAll(Arrays.asList(a1)));
		}
	}

	public void testIncludes() {
		MultiSet<Integer> set1 = new MultiSet<>(new Integer[]{1, 8, 24, 8, 3, 42});
		MultiSet<Integer> set1a = new MultiSet<>(new Integer[]{1, 9, 24, 9, 3, 42});
		MultiSet<Integer> set2 = new MultiSet<>(new Integer[]{8});
		MultiSet<Integer> set3 = new MultiSet<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
		MultiSet<Integer> set4 = new MultiSet<>(new Integer[]{1, 3, 8, 8, 8});
		
		MultiSet<Integer> set5 = new MultiSet<>(new Integer[]{1, 24, 1000});
		MultiSet<Integer> set6 = new MultiSet<>(new Integer[]{1, 24});
		MultiSet<Integer> set7 = new MultiSet<>(new Integer[]{100, 150, 120, 8});
		MultiSet<Integer> set8 = new MultiSet<>(new Integer[]{0, 0, 0, 0, 8});
		MultiSet<Integer> set9 = new MultiSet<>(new Integer[]{1000, 1000, 1000, 1000});
		MultiSet<Integer> emptySet = new MultiSet<>(new Integer[]{});
		assertTrue(set1.includes(set2));
		assertFalse(set2.includes(set1));
		assertFalse(set1a.includes(set2));
		assertFalse(set1.includes(set3));
		assertFalse(set3.includes(set1));
		assertFalse(set1.includes(set4));
		assertFalse(set4.includes(set1));
		assertFalse(set1.includes(set5));
		assertFalse(set5.includes(set1));
		assertTrue(set1.includes(set6));
		assertFalse(set6.includes(set1));
		assertFalse(set1.includes(set7));
		assertFalse(set7.includes(set1));
		assertFalse(set1.includes(set8));
		assertFalse(set8.includes(set1));
		assertFalse(set1.includes(set9));
		assertFalse(set9.includes(set1));
		assertTrue(set1.includes(emptySet));
		assertFalse(emptySet.includes(set1));
	}

	public void testRemoveNick() {
		final int SIZE = 16;
		MultiSet<Integer> set = new MultiSet<>();
		for (int i = 0; i < SIZE; i++) {
			if (i % 2 == 0) {
				set.add(i);
			}
			set.add(i);
		}
		assertArrayCorrect(new Comparable[]{0, 0, 1, 2, 2, 3, 4, 4, 5, 6, 6, 7, 8, 8, 9, 10, 10, 11, 12, 12, 13, 14, 14, 15}, set.toArray());
		assertTrue(set.remove(0));
		assertArrayCorrect(new Comparable[]{0, 1, 2, 2, 3, 4, 4, 5, 6, 6, 7, 8, 8, 9, 10, 10, 11, 12, 12, 13, 14, 14, 15}, set.toArray());
		assertTrue(set.remove(0));
		assertArrayCorrect(new Comparable[]{1, 2, 2, 3, 4, 4, 5, 6, 6, 7, 8, 8, 9, 10, 10, 11, 12, 12, 13, 14, 14, 15}, set.toArray());
		assertTrue(set.remove(5));
		assertArrayCorrect(new Comparable[]{1, 2, 2, 3, 4, 4, 6, 6, 7, 8, 8, 9, 10, 10, 11, 12, 12, 13, 14, 14, 15}, set.toArray());
		assertFalse(set.remove(5));
		assertArrayCorrect(new Comparable[]{1, 2, 2, 3, 4, 4, 6, 6, 7, 8, 8, 9, 10, 10, 11, 12, 12, 13, 14, 14, 15}, set.toArray());
		assertFalse(set.remove(SIZE));
		assertArrayCorrect(new Comparable[]{1, 2, 2, 3, 4, 4, 6, 6, 7, 8, 8, 9, 10, 10, 11, 12, 12, 13, 14, 14, 15}, set.toArray());
		for (int i = 0; i < SIZE; i++) {
			set.remove(i);
			set.remove(i);
		}
		assertArrayCorrect(new Comparable[]{}, set.toArray());
	}

	public void testSize() {
		final int SIZE = 42;
		MultiSet<Integer> set = new MultiSet<>();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j <= i; j++) {
				set.add(i);
			}
		}
		assertEquals(SIZE * (SIZE + 1) / 2, set.size());
	}

	public void testIdenticalNick() {
		final int SIZE = 42;
		MultiSet<Integer> set1 = new MultiSet<>();
		MultiSet<Integer> set2 = new MultiSet<>();
		for (int i = 0; i < SIZE; i++) {
			set1.add(i);
		}
		for (int i = 0; i < SIZE - 1; i++) {
			set2.add(i);
		}
		assertFalse(set1.identical(set2));
		assertFalse(set2.identical(set1));
		set2.add(SIZE - 1);
		assertTrue(set2.identical(set1));
		assertTrue(set1.identical(set2));
		set2.add(-23);
		assertFalse(set1.identical(set2));
		assertFalse(set2.identical(set1));
	}

	public void testAdd() {
		MultiSet<Integer> set = new MultiSet<>();
		set.add(5);
		assertArrayCorrect(new Comparable[]{5}, set.toArray());
		set.add(23);
		assertArrayCorrect(new Comparable[]{5, 23}, set.toArray());
		set.add(1);
		assertArrayCorrect(new Comparable[]{1, 5, 23}, set.toArray());
		set.add(5);
		assertArrayCorrect(new Comparable[]{1, 5, 5, 23}, set.toArray());
		set.add(21);
		assertArrayCorrect(new Comparable[]{1, 5, 5, 21, 23}, set.toArray());
		set.add(-20);
		assertArrayCorrect(new Comparable[]{-20, 1, 5, 5, 21, 23}, set.toArray());
		set.add(21);
		assertArrayCorrect(new Comparable[]{-20, 1, 5, 5, 21, 21, 23}, set.toArray());
	}

}

