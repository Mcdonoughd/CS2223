package algs.days.day18;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class AVLTest {

	static String[] headers = { "Total", "Max", "Avg.", "Stdev" };
	static final int TOTAL = 0;
	static final int MAX   = 1;
	static final int AVG   = 2;
	static final int STDV  = 3;

	static double val[][][];

	static void record (int logn, int type, double avl, double sedgewick, double treemap) {
		val[type][logn][0] = avl;
		val[type][logn][1] = sedgewick;
		val[type][logn][2] = treemap;
	}

	public static void main(String[] args) {
		int idx = 3;
		int maxIdx = 15;
		val = new double[4][maxIdx+1][3];
		
		for (int n = 8; n <= Math.pow(2, maxIdx); n *= 2, idx++) {
			StdOut.println ("Computing for " + n);
			runTrial(n, idx);
		}


		for (int t = 0; t < 4; t++) {
			StdOut.println ("N\t" + headers[t] + "\tAVL\tRedBLack\tTreeMap");
			for (int logn = 3; logn < idx; logn++) {
				int N = (int) Math.pow(2,  logn);
				System.out.println(N + "\t\t" + val[t][logn][0] + "\t" + val[t][logn][1] + "\t" + val[t][logn][2]);
			}
			StdOut.println();
		}
	}

	static void runTrial (int Num, int logn) {
		// AVL is original self-balancing structure. RedBlackBST is Sedgewick's implementation
		// which is optimized for human readability and understanding. TreeMap is Java's built
		// in RedBlack tree which is optimized for performance.
		AVL<Integer> avl = new AVL<Integer>();
		RedBlackBST<Integer,Integer> redBlack = new RedBlackBST<Integer,Integer>();
		TreeMap<Integer,Integer> treeMap = new TreeMap<Integer,Integer>();

		int i = 0;
		while (i < Num) {
			if (!avl.assertAVLProperty(avl.root)) {
				StdOut.println ("BAD AVL");
			}
			if (!redBlack.check()) {
				StdOut.println ("BAD RedBlack Tree");
			}

			int rnd = StdRandom.uniform(0, Num*10);
			if (!avl.contains(rnd)) {
				avl.insert(rnd);
				redBlack.put(rnd, rnd);
				treeMap.put(rnd, rnd);
				i++;
			}
		}

		// guaranteed to have Num unique integers...

		// randomly delete elements, one at a time.
		Queue<Integer> queue = (Queue<Integer>) avl.keys();
		Integer[] keys = new Integer[queue.size()];
		for (int idx = 0; idx < keys.length; idx++) {
			keys[idx] = queue.dequeue();
		}

		// complete until empty
		int count = keys.length;
		Queue<Integer> avlRotations = new Queue<Integer>();
		Queue<Integer> redBlackRotations = new Queue<Integer>();
		Queue<Integer> treeMapRotations = new Queue<Integer>();
		while (count > 0) {
			int idx = StdRandom.uniform(0, count);

			int numRotations = avl.rotations;
			avl.fastDelete(keys[idx]);
			avlRotations.enqueue(avl.rotations - numRotations);

			numRotations = redBlack.rotations;
			redBlack.delete(keys[idx]);
			redBlackRotations.enqueue(redBlack.rotations - numRotations);

			numRotations = treeMap.rotations;
			treeMap.remove(keys[idx]);
			treeMapRotations.enqueue(treeMap.rotations - numRotations);

			if (!avl.assertAVLProperty(avl.root)) {
				StdOut.println ("BAD AVL");
			}

			keys[idx] = keys[--count];
		}

		// done with both: queues maintain the 
		int[] avlStats = new int[avlRotations.size()];
		int[] redBlackStats = new int[redBlackRotations.size()];
		int[] treeMapStats = new int[treeMapRotations.size()];

		int idx = 0;
		int totalAvl = 0, totalRedBlack = 0, totalTreeMap = 0;
		int maxAvl = 0, maxRedBlack = 0, maxTreeMap = 0;
		while (!avlRotations.isEmpty()) {
			avlStats[idx] = avlRotations.dequeue();
			totalAvl += avlStats[idx];
			if (avlStats[idx] > maxAvl) {
				maxAvl = avlStats[idx];
			}

			redBlackStats[idx] = redBlackRotations.dequeue();
			totalRedBlack += redBlackStats[idx];
			if (redBlackStats[idx] > maxAvl) {
				maxRedBlack = redBlackStats[idx];
			}

			treeMapStats[idx] = treeMapRotations.dequeue();
			totalTreeMap += treeMapStats[idx];
			if (treeMapStats[idx] > maxAvl) {
				maxTreeMap = treeMapStats[idx];
			}

			idx++;
		}

		// Output Stats:
		record (logn, TOTAL, totalAvl, totalRedBlack, totalTreeMap);
		record (logn, MAX, maxAvl, maxRedBlack, maxTreeMap);
		record (logn, AVG, StdStats.mean(avlStats),  StdStats.mean(redBlackStats), StdStats.mean(treeMapStats));
		record (logn, STDV, StdStats.stddev(avlStats),  StdStats.stddev(redBlackStats), StdStats.stddev(treeMapStats));
	}
}

/**
N	Total	AVL		RedBLack	TreeMap
8			1		7			1
16			7		23			4
32			12		60			15
64			24		199			31
128			33		583			32
256			88		1418		83
512			184		3190		207
1024		409		7496		380
2048		778		18848		746
4096		1529	43783		1597
8192		2990	93777		3126
16384		5938	200068		6198
32768		12207	459614		12340

N	Max		AVL		RedBLack	TreeMap
8			1.0		2.0			0.0
16			2.0		3.0			0.0
32			3.0		5.0			0.0
64			2.0		4.0			1.0
128			4.0		6.0			2.0
256			4.0		5.0			2.0
512			4.0		6.0			0.0
1024		6.0		8.0			2.0
2048		5.0		6.0			0.0
4096		5.0		6.0			3.0
8192		6.0		7.0			0.0
16384		7.0		8.0			3.0
32768		6.0		7.0			0.0

N	Avg.	AVL					RedBLack			TreeMap
8			0.125				0.875				0.125
16			0.4375				1.4375				0.25
32			0.375				1.875				0.46875
64			0.375				3.109375			0.484375
128			0.2578125			4.5546875			0.25
256			0.34375				5.5390625			0.32421875
512			0.359375			6.23046875			0.404296875
1024		0.3994140625		7.3203125			0.37109375
2048		0.3798828125		9.203125			0.3642578125
4096		0.373291015625		10.689208984375		0.389892578125
8192		0.364990234375		11.4473876953125	0.381591796875
16384		0.3624267578125		12.211181640625		0.3782958984375
32768		0.372528076171875	14.02630615234375	0.3765869140625

N	Stdev	AVL					RedBLack			TreeMap
8			0.3535533905932738	1.1259916264596033	0.3535533905932738
16			0.7274384280931732	1.4127396551853895	0.6831300510639732
32			0.7513428837969107	1.6214290789499601	0.802591568487018
64			0.6546536707079771	1.8267604033995732	0.7344620642412539
128			0.7126096406869612	2.3803943091280346	0.5886057194025436
256			0.7077996836371593	2.742268543506613	0.6265055396215349
512			0.7400679611252934	2.590364762676909	0.7178334260425822
1024		0.7857725889355539	2.68643773477719	0.6998005406374318
2048		0.7587468043938117	2.8974914609018305	0.6849144490934191
4096		0.7585472325808152	3.081290305701827	0.7127551579593441
8192		0.7548062901265108	3.3512356862314148	0.7010068315112983
16384		0.7424120173448838	3.108010175206545	0.6971012792872064
32768		0.7538175549971001	3.373845591282224	0.6939813251576841

*/