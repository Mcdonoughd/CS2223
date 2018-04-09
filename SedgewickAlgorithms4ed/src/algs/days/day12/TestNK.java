package algs.days.day12;

import edu.princeton.cs.algs4.StdRandom;
import junit.framework.TestCase;

public class TestNK extends TestCase {
	
	public void testUpToK() {	
		NK nk = new NK(6);
		
		nk.add(3);
		nk.add(7);
		nk.add(1);
		nk.add(4);
		nk.add(2);
		nk.add(5);
		
		assertTrue (nk.contains(4));
		
		nk.add(9);
	
		assertTrue (nk.contains(9));
		nk.add(8);
		assertTrue (nk.contains(8));
		
		nk.remove(5);
		assertTrue (nk.contains(8));
		nk.remove(3);
		assertTrue (nk.contains(8));
		assertTrue (nk.contains(9));
	}
	
	public void testAddSeq() {
		NK nk = new NK(6);
		int[] toAdd = new int[50];
		for (int i = 0; i < toAdd.length; i++) {
			toAdd[i] = i+1;
		}
		for (int i : toAdd) {
			nk.add(i);
		}
		
		for (int i : toAdd) {
			assertTrue (nk.contains(i));
		}
		
		for (int i : toAdd) {
			System.out.println(nk.N);
			assertTrue (nk.remove(i));
		}
		
		assertTrue (nk.isEmpty());
	}
	
	public void testAddSeqInReverse() {
		NK nk = new NK(6);
		
		for (int i = 1; i <= 50; i++) {
			nk.add(i);
		}
		for (int i = 50; i >= 1; i--) {
			assertTrue (nk.remove(i));
		}
		
		assertTrue (nk.isEmpty());
	}
	
	public void testAddRemoveJosephus() {
		NK nk = new NK(6);
		int val = 1;
		
		// eventually will stop
		while (!nk.contains(val)) {
			nk.add(val);
			val = (val + 5) % 197;
		}
		
		assertEquals (197, nk.size());
		
		for (int i = 0; i < 197; i++) {
			if (nk.contains(i)) {
				nk.remove(i);
			}
		}
		
		assertTrue (nk.isEmpty());
	}
	
}
