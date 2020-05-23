package algs.days.day21;

import java.text.NumberFormat;

public class Point implements Comparable<Point> {
	double x;
	double y;
	int id;				  // this will be set externally by guided algorithm
	static Point target;  // set externally by guided algorithm.
	static NumberFormat fmt = NumberFormat.getInstance();
	
	public Point (double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals (Object o) {
		if (o == null) { return false; }
		if (o instanceof Point) {
			Point other = (Point) o;
			return x == other.x && y == other.y;
		}
		
		return false;
	}
	
	public double distance(Point p) {
		return Math.sqrt((p.x-x)*(p.x-x) + (p.y-y)*(p.y-y));
	}

	/** compare by distance to target. */
	@Override
	public int compareTo(Point other) {
		double myDist = distance(target);
		double otherDist = other.distance(target);
		double dist = myDist - otherDist;
		if (dist < 0) { return -1; }
		if (dist > 0) { return +1; }
		return 0;
	}
	
	public String toString() {
		return "(" + fmt.format(x) + "," + fmt.format(y) + ")";
	}
}
