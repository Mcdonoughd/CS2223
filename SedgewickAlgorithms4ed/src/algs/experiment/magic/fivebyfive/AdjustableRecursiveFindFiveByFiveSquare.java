package algs.experiment.magic.fivebyfive;

import algs.experiment.magic.Cell;

/**
 * A 5x5 latin magic square has 25 digits from 1-25. There are 25! possible arrangements
 * or 15,511,210,043,331,000,000,000,000 possible.
 * 
 * Reputable websites list the number of 5x5 magic squares (ignoring rotation/flips) as being
 * 275,305,224. With rotate/flip multiple by 8. Even so, the vast majority are non magic.
 * 
 * Any algorithmic solution must attempt to avoid incredibly wasted searches. Most magic squares
 * that you see start with '13' in the center.
 * 
 * A  B  C  D  x1
 * E  H  .  x3
 * F  I  13
 * G  J
 * x2 x4
 * 
 * 11 choices, or C(25,10) [https://www.hackmath.net/en/calculator/n-choose-k?n=25&k=10&order=0&repeat=0]
 * Gives us only 3,268,760 possibilities. Try each of these and record these partial results.
 * 
 * But there are 5by5 without 13 in the center:
 * 
 * 24  5 16  8 12
 * 18  7 14 25  1
 * 15 21  3 17  9
 *  2 19 10 11 23
 *  6 13 22  4 20
 * 
 * https://www.dadsworksheets.com/puzzles/magic-square/5x5-normal-1-v1.html
 *
 * Here are the computed results. Note that you can assume the existence of
 * the "symmetry" boards by replacing every cell with (n*n-1)-cell. I 
 * therefore only computed boards with centers 1 .. 13; here are the 
 * results of the counts
 
  34926336      1 in center
  43717728      2 in center
  61279488      3 in center
  62682784      4 in center
  77817792      5 in center
  83228128      6 in center
  96540192      7 in center
  99589152      8 in center
 111121280      9 in center
 107009088      10 in center
 125882176      11 in center
 121107776      12 in center
 152637952      13 in center (my original computation)
 121107776      14 in center (symmetry)
 125882176      15 in center (symmetry)
 107009088      16 in center (symmetry)
 111121280      17 in center (symmetry)
  99589152      18 in center (symmetry)
  96540192      19 in center (symmetry)
  83228128      20 in center (symmetry)
  77817792      21 in center (symmetry)
  62682784      22 in center (symmetry)
  61279488      23 in center (symmetry)
  43717728      24 in center (symmetry)
  34926336      25 in center (symmetry)

2202441792 in total, which is 8 x the number of unique (omitting rotation/symmetry)

 275305224 unique boards

 * Full computation lasted from (roughly) Feb-24 through Mar-5, so almost eight days of 
 * compute time. 
 * 
 * @author heineman
 */
public class AdjustableRecursiveFindFiveByFiveSquare {

	static int[][] board = new int[][] { 
		{ 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0 },
		{ 0, 0, 0, 0, 0 }
	};

	// one more than needed, for simplicity, to offset by 1. */
	static boolean used[] = new boolean[] { false, /* burn this one. */ 
		false, false, false, false, false, false, false, false, false, false, false, false,
		false, /* start in middle and SKIP 13. */
		false, false, false, false, false, false, false, false, false, false, false, false
	};

	// 25 indices, now, with T being acceptable
	static final int T   = 0;
	static final int A   = 1;
	static final int B   = 2;
	static final int C   = 3;
	static final int D   = 4;
	static final int x1  = 5;
	static final int E   = 6;
	static final int F   = 7;
	static final int G   = 8;
	static final int x2  = 9;
	static final int H   = 10;
	static final int I   = 11;
	static final int J   = 12;
	static final int x3  = 13;
	static final int x4  = 14;
	static final int K   = 15;
	static final int x5  = 16;
	static final int L   = 17;
	static final int x6  = 18;
	static final int x7  = 19;
	static final int x8  = 20;
	static final int M   = 21;
	static final int x9  = 22;
	static final int x10 = 23;
	static final int x11 = 24;

	// places to investigate
	/*
	 *  A  B  C   D  x1
	 *  E  H  x11 x4 x10  
	 *  F  I  T   K  x5
	 *  G  J  M   L  x9   
	 * x2  x3 x8  x7 x6
	 * 
	 * Order of these positions is T A B C D (x1) E F G (x2) H I J (x3,x4) K (x5) L (x6,x7,x8) M (x9,x10,x11).
	 * Note that cell (2,2) determines the initial starting point, which can be parallelized amongst multiple
	 * computations.
	 * 
	 * Assuming calling explore_0, then total number of computed positions is:
	 * 
	 * 1*24*23*22*21*19*18*17*15*14*13*10*8*4 = 1,295,295,050,649,600
	 * 
	 * Note we can skip the computed values x1 .. x11, which reduces the search space to manageable time; also, 
	 * the total number of computed possibilities (above) is never actually checked, because the algorithm can
	 * stop in short-circuit mode, once a computed digit (x1.x11) is already used.
	 * 
	 */
	static Cell[] positions = new Cell[] { new Cell(2,2),
		new Cell(0,0), new Cell(0,1), new Cell(0,2), new Cell(0,3), /*x1*/ null,
		new Cell(1,0), new Cell(2,0), new Cell(3,0), /*x2*/ null, 
		new Cell(1,1), new Cell(2,1), new Cell(3,1), /*x3*/ null, /*x4*/ null,
		new Cell(2,3), /*x5*/ null,
		new Cell(3,3), /*x6*/null, /*x7*/null, /*x8*/ null,
		new Cell(3,2), /*x9*/null, /*x10*/null, /*x11*/ null};

	// call this instead to simply generate all magic squares sequentially. Takes too long.
	public static void explore_0 () {
		Cell c = positions[0];
		for (int i = 1; i <= 25; i++) {
			if (i == 13) { continue; }  // skip 13 since those were all computed for day04

			// every time we get here, all of the used should be reset
			board[c.row][c.col] = i;
			used[i] = true;
			explore(1);
			used[i] = false;
		}
	}

	/** Call this one using different processes to parallelize effort. */
	public static void explore_from (int start) {
		Cell c = positions[0];
		// every time we get here, all of the used should be reset
		board[c.row][c.col] = start;
		used[start] = true;
		explore(1);
		used[start] = false;
	}
	
	public static int explore (int pos) {

		if (pos == 25) {
			// DONE
			System.out.printf("%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d,%2d%n",
					board[0][0], board[0][1], board[0][2], board[0][3], board[0][4],
					board[1][0], board[1][1], board[1][2], board[1][3], board[1][4],
					board[2][0], board[2][1], board[2][2], board[2][3], board[2][4],
					board[3][0], board[3][1], board[3][2], board[3][3], board[3][4],
					board[4][0], board[4][1], board[4][2], board[4][3], board[4][4]
					);
			return 0;
		}

		Cell c = positions[pos];
		if  (c.type == 0) {
			// try each value. If not already used, add to board at current position and then
			// mark the number as being used. When explore returns, the number is no longer 
			// being used, but we continue the for loop until all 25 are found.
			// Note in the first implementation, I had only i<25 which (of course, omitted the
			// 25th one).
			for (int i = 1; i <= 25; i++) {
				if (!used[i]) {
					board[c.row][c.col] = i;
					used[i] = true;
					int delta = explore(pos+1);
					used[i] = false;
					if (delta > 0) {
						i += (delta-1);
					}
				}
			}
			return 0;
		} else if (c.type == 1) {
			// COMPUTED
			Cell[] bases = ((Computed)c).bases;
			int dig = 65 - (
					board[bases[0].row][bases[0].col] +
					board[bases[1].row][bases[1].col] +
					board[bases[2].row][bases[2].col] +
					board[bases[3].row][bases[3].col]);
			// note: in an earlier implementation, I had used dig >= 0, which is nonsensical.
			if (dig >= 1 && dig <= 25) {
				if (!used[dig]) {
					used[dig] = true;
					board[c.row][c.col] = dig;
					explore(pos+1);
					used[dig] = false;
				}
			} else {
				return dig-25; // if we get here, how far can we accelerate loop calling us?
			}
		}

		return 0; // never gets here.
	}

	public static void main(String[] args) {

		// fill in the computed values
		positions[5]  = new Computed(0,4).from(positions[A], positions[B], positions[C], positions[D]);     // x1=65-(A+B+C+D)
		positions[9]  = new Computed(4,0).from(positions[A], positions[E], positions[F], positions[G]);     // x2=65-(A+E+F+G)
		positions[13] = new Computed(4,1).from(positions[B], positions[H], positions[I], positions[J]);     // x3=65-(B+H+I+J)
		positions[14] = new Computed(1,3).from(positions[x1], positions[T], positions[J], positions[x2]);   // x4=65-(x1+T+J+x2)

		positions[16] = new Computed(2,4).from(positions[F], positions[I], positions[T], positions[K]);     // x5=65-(F+I+T+K)
		positions[18] = new Computed(4,4).from(positions[A], positions[H], positions[T], positions[L]);     // x6=65-(A+H+T+L)
		positions[19] = new Computed(4,3).from(positions[D], positions[x4], positions[K], positions[L]);    // x7=65-(D+x4+K+L)
		positions[20] = new Computed(4,2).from(positions[x2], positions[x3], positions[x7], positions[x6]); // x8=65-(x2+x3+x7+x6)

		positions[22] = new Computed(3,4).from(positions[G], positions[J], positions[M], positions[L]);     // x9=65-(G+J+M+L)
		positions[23] = new Computed(1,4).from(positions[x1], positions[x5], positions[x6], positions[x9]); // x10=65-(x1+x5+x6+x9)
		positions[24] = new Computed(1,2).from(positions[E], positions[H], positions[x4], positions[x10]);  // x11=65-(E+H+x4+x10)

		// handle the 1st one specially; grab from the command line.
		explore_from(13);
	}
}
