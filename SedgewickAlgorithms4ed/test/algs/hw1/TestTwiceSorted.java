package algs.hw1;

import junit.framework.TestCase;

public class TestTwiceSorted extends TestCase {

	static int[][] notUniq= new int[][] {
		{  5, 12, 18, 22},
		{  7, 24, 37, 50},
		{ 26, 27, 38, 57},
		{ 37, 33, 60, 62},
	};
	
	static int[][] notTwiceSorted = new int[][] {
		{  5, 12, 18, 22},
		{ 24, 13, 37, 50},
		{ 26, 27, 38, 57},
		{ 37, 33, 60, 62},
	};
	
	class EmptyAlgorithm extends TwiceSorted {
		public EmptyAlgorithm(int[][] a) {
			super(a);
		}

		@Override
		public int[] locate(int target) {
			return null;
		}

	}

	public void testBadInput() {
		try {
			new EmptyAlgorithm(notTwiceSorted);
			fail ("should detect row not ordered.");
		} catch (Exception e) {
			
		}
		
		try {
			new EmptyAlgorithm(notUniq);
			fail ("should detect duplicate values.");
		} catch (Exception e) {
		}
	}
}
