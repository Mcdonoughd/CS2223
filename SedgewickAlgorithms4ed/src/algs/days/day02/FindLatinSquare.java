package algs.days.day02;

/**
 * A 4x4 magic square has 16 digits from 1-16. There are 16! possible arrangements
 * or 20,922,789,888,000 possible.
 * 
 *  A  B  C  D
 *  E  Q  K  S
 *  F  H  N  P 
 *  G  R  O  T
 * 
 * Note: there are obvious improvements to the performance of this code. For example,
 * consider the for-loops for D (and G); these are redundant, since the proper value
 * can be computed based on the other three values in the row (and column).
 * 
 * Ultimately this finds 7,040 unique squares, but with symmetry and rotation, this evaluates
 * to just 880 (divide by 8).
 * 
 * The 880 squares of order four were enumerated by Frénicle de Bessy in 1693.
 * http://mathworld.wolfram.com/MagicSquare.html
 * 
 */
public class FindLatinSquare {
	public static void main(String[] args) {
		int count = 0;
		
		// compute known target value=34 for 4x4 square
		int M=34;
		
		// top row
		for (int a = 1; a <= 16; a++) {
			for (int b = 1; b <= 16; b++) {
				if (b == a) { continue; }
				for (int c = 1; c <= 16; c++) {
					if (c == b || c == a) { continue; }
					int total = a+b+c;
					for (int d = 1; d <= 16; d++) {
						if (d == a || d == b || d == c) { continue; }
						// C(16,4) ways of getting here=43680
						if (total+d != M) { continue; }

						// first column
						for (int e = 1; e <= 16; e++) {
							if (e == a || e == b || e ==c || e == d) { continue; }
							for (int f = 1; f <= 16; f++) {
								if (f == a || f == b || f == c || f == d || f == e) { continue; }
								int vert = a+e+f;
								for (int g = 1; g <= 16; g++) {
									if (g == a || g == b || g == c || g == d || g == e || g == f) { continue; }
									// C(12,3) ways of getting here=1320
									if (vert+g != M) { continue; }

									// long diagonal Northeast
									int diag = d + g;
									for (int h = 1; h <= 16; h++) {
										if (h == a || h == b || h == c || h == d || h == e || h == f || h == g) { continue; }
										int part3 = b+h;
										
										for (int k = 1; k <= 16; k++) {
											if (k == a || k == b || k == c || k == d || k == e || k == f || k == g || k == h) { continue; }

											if (h+k+diag != M) { continue; }

											// third column
											int part = c+k;
											for (int n = 1; n <= 16; n++) {
												if (n == a || n == b || n == c || n == d || n == e || n == f || n == g || n == h || n == k) { continue; }
												for (int o = 1; o <= 16; o++) {
													if (o == a || o == b || o == c || o == d || o == e || o == f || o == g || o == h || o == k || o == n) { continue; }
													
													if (part + n + o != M) { continue; }
													
													// third row
													int part2 = f+h;
													for (int p = 1; p <= 16; p++) {
														if (p == a || p == b || p == c || p == d || p == e || p == f || p == g || p == h || p == k || p == n || p == o) { continue; }
														if (part2 +n+p != M) { continue; }
														
														// second column
														for (int q = 1; q <= 16; q++) {
															if (q == a || q == b || q == c || q == d || q == e || q == f || q == g || q == h || q == k || q== n || q == o || q == p) { continue; }
															
															int part4 = e+q+k;
															for (int r = 1; r <= 16; r++) {
																if (r == a || r == b || r == c || r == d || r == e || r == f || r == g || r == h || r == k || r== n || r == o || r == p || r == q) { continue; }
																
																
																if (part3+q+r != M) { continue; }
																
																// second row
																for (int s = 1; s <= 16; s++) {
																	if (s == a || s == b || s == c || s == d || s == e || s == f || s == g || s == h || s == k || s == n || s == o || s == p || s == q || s == r) { continue; }
																	
																	if (part4 + s != M) { continue; }
																	
																	int r1 = a+q+n;
																	int r2 = g+r+o;
																	if (r1 != r2) { continue; }
																	int r3 = d+s+p;
																	if (r3 != r2) { continue; }
																	
																	int t = M-r3; // compute last digit
																	
																	if (count == 0) {
																		System.out.println("First one found...");
																		System.out.printf("%2d %2d %2d %2d %n%2d %2d %2d %2d %n%2d %2d %2d %2d %n%2d %2d %2d %2d %n%n",
																				a,b,c,d,e,q,k,s,f,h,n,p,g,r,o,t);
																	}
																	
																	count++;
																	
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		System.out.println("total found:" + count);
		System.out.println("total unique (ignoring symmetry):" + count/8);
	}
}
