package algs.sedgewick;

/**
 * Return time in seconds
 * 
 * @author Laptop
 */
public class Stopwatch {
	long start;
	
	public Stopwatch() {
		start = System.currentTimeMillis();
	}
	
	public double elapsedTime() {
		long now = System.currentTimeMillis();
		return (now - start) / 1000.0;
	}
}
