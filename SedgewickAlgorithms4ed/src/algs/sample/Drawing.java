package algs.sample;

import algs.sedgewick.StdDraw;

/**
 * More examples can be found here:
 * 
 * http://introcs.cs.princeton.edu/java/32class/Trajectory.java.html
 * 
 * @author George Heineman
 *
 */
public class Drawing {
	
	static boolean isrelativePrime(int i, int j) {
		if (i == 1 || j == 1) { return false; }
		if (i == j) { return false; }
		
		return (gcd(i,j) == 1); 
	}
	
	static int gcd (int p, int q) {
		if (q == 0) { return p; }
		int r = p % q;
		return gcd(q, r);
	}
	
	public static void main(String[] args) {
		int N = 100;
		StdDraw.setXscale(0, N);
		StdDraw.setYscale(0, N);
		StdDraw.setPenRadius(.01);
		
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (isrelativePrime(i,j)) {
					StdDraw.filledRectangle(i, j, .5, .5);
				}
			}
		}		
	}
}
