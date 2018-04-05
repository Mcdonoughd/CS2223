package algs.solutions.exam1;

public class FindGap {
	// record number of array inspections
	static int ai = 0;
	
	// my sample solution
	public static int findGapStraight (int[] a) {
		int lo = 1;		// can avoid 0th and last one since they are known in advance
		int hi = a.length-2;
		while (lo <= hi) {		// must be <= otherwise strange case happens
			int mid = lo + (hi-lo)/2;       // or any suitable midpoint computation
			ai++;
			if (a[mid] > mid) {	// gap is on the left….
				hi = mid - 1;		// so close in on it
			} else {
				lo = mid + 1;	// gap is on the right
			} 
		}
		return lo;                         

	}

	public static int findGap (int[] a) { 
		ai = 0;
		//return recursiveFindGap(a, 0, a.length-1);
		return findGapStraight(a);
	}
	
	// student's sample recursive solution
	public static int recursiveFindGap (int[] a, int lo, int hi) {
		if (lo == hi) return hi;
		
		int mid = (lo + hi)/2;
		ai++; 
		if (a[mid] == mid) {
			return recursiveFindGap(a, mid+1, hi);
		}
		return recursiveFindGap(a,lo,mid);
	}

	
	public static void main(String[] args) {
		int g, m=0;
		
		g = findGap(new int[] { 0, 2, 3, 4, 5, 6}); System.out.printf("%d\t%d\n", g, ai); m = Math.max(ai,m);
		g = findGap(new int[] { 0, 1, 3, 4, 5, 6}); System.out.printf("%d\t%d\n", g, ai); m = Math.max(ai,m);
		g = findGap(new int[] { 0, 1, 2, 4, 5, 6}); System.out.printf("%d\t%d\n", g, ai); m = Math.max(ai,m);
		g = findGap(new int[] { 0, 1, 2, 3, 5, 6}); System.out.printf("%d\t%d\n", g, ai); m = Math.max(ai,m);
		g = findGap(new int[] { 0, 1, 2, 3, 4, 6}); System.out.printf("%d\t%d\n", g, ai); m = Math.max(ai,m);
		
		System.out.println("max: " + m);
		
		// try to find first four max (should be 10, since 1 + floor (log n-2)
		int[] a = new int[10];
		
		// where gap should be
		m = 0;
		for (int gap = 1; gap < a.length-1; gap++) {
			for (int i = 0, val=0; i < a.length; i++) {
				if (i == gap) { val++; }
				a[i] = val;
				val++; // advance
			}
			
			g = findGap(a);
			m = Math.max(ai,m);
		}
		System.out.printf("for array size %d most number ai to find gap is %d\n", a.length, m);
	}
}
