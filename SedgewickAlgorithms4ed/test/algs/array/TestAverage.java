package algs.array;

import edu.princeton.cs.algs4.StdRandom;
import junit.framework.TestCase;

/** JUnit 3.0 test cases. */
public class TestAverage extends TestCase {

	public void testAverage() {
		Average avg = new Average();
		
		for (int t = 0; t < 1000; t++) {
			double vals[] = new double[512];
			for (int i = 0; i < vals.length; i++) {
				vals[i] = StdRandom.uniform();
			}
			
			double a1 = avg.straightAverage(vals);
			double a2 = avg.compute(vals);
			assertEquals (a1, a2, 0.0000001);   // equal to 1 part in a million
		}
	}
}
