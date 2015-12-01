package algs.days.day18;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class AVLTest {

	static String[] headers = { "Total", "Max", "Avg.", "Stdev", "Height" };
	static final int TOTAL  = 0;
	static final int MAX    = 1;
	static final int AVG    = 2;
	static final int STDV   = 3;
	static final int HEIGHT = 4;

	static double val[][][];

	static void record (int logn, int type, double avl, double sedgewick, double treemap) {
		val[type][logn][0] = avl;
		val[type][logn][1] = sedgewick;
		val[type][logn][2] = treemap;
	}

	public static void main(String[] args) {
		int idx = 3;
		int maxIdx = 15;
		val = new double[5][maxIdx+1][3];
		
		for (int n = 8; n <= Math.pow(2, maxIdx); n *= 2, idx++) {
			StdOut.println ("Computing for " + n);
			runTrial(n, idx);
		}

		for (int t = 0; t < val.length; t++) {
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

		// guaranteed to have Num unique integers... Get Height Now!
		int heightAvl = avl.height();
		int heightRedBlack = redBlack.height();
		int heightTreeMap = treeMap.height();

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
		record (logn, HEIGHT, heightAvl, heightRedBlack, heightTreeMap);
	}
}

/**

N	Total	AVL	RedBLack	TreeMap
8		2.0	7.0	1.0
16		0.0	19.0	6.0
32		5.0	75.0	3.0
64		18.0	194.0	32.0
128		39.0	534.0	50.0
256		92.0	1332.0	84.0
512		185.0	3205.0	185.0
1024		392.0	7628.0	377.0
2048		751.0	17669.0	767.0
4096		1477.0	40776.0	1614.0
8192		3067.0	95797.0	3170.0
16384		6183.0	213473.0	6086.0
32768		12062.0	456236.0	12256.0

N	Max	AVL	RedBLack	TreeMap
8		1.0	3.0	0.0
16		0.0	1.0	1.0
32		2.0	3.0	1.0
64		2.0	3.0	3.0
128		2.0	3.0	3.0
256		4.0	5.0	0.0
512		4.0	5.0	3.0
1024		4.0	5.0	0.0
2048		4.0	5.0	3.0
4096		6.0	8.0	1.0
8192		6.0	7.0	0.0
16384		6.0	7.0	0.0
32768		7.0	10.0	1.0

N	Avg.	AVL	RedBLack	TreeMap
8		0.25	0.875	0.125
16		0.0	1.1875	0.375
32		0.15625	2.34375	0.09375
64		0.28125	3.03125	0.5
128		0.3046875	4.171875	0.390625
256		0.359375	5.203125	0.328125
512		0.361328125	6.259765625	0.361328125
1024		0.3828125	7.44921875	0.3681640625
2048		0.36669921875	8.62744140625	0.37451171875
4096		0.360595703125	9.955078125	0.39404296875
8192		0.3743896484375	11.6939697265625	0.386962890625
16384		0.37738037109375	13.02935791015625	0.3714599609375
32768		0.36810302734375	13.9232177734375	0.3740234375

N	Stdev	AVL	RedBLack	TreeMap
8		0.4629100498862757	1.1259916264596033	0.3535533905932738
16		0.0	1.1672617529928753	0.7187952884282609
32		0.447889315243931	1.7525902489301832	0.29614458108029906
64		0.5764904768001549	2.06995705806237	0.816496580927726
128		0.6344269377714653	2.532029079580746	0.7553939890812988
256		0.7590951786146968	2.303822484310735	0.6460665296889428
512		0.6855430473822122	2.448870969486897	0.6826824851197201
1024		0.7645221611551628	2.8054128965339666	0.6950475193762687
2048		0.7322592829116162	2.8604352442419643	0.6889984489014066
4096		0.7547003944242839	3.0429459400217733	0.7109838835350069
8192		0.7582007556174344	3.0413216968675654	0.7051901036253265
16384		0.7621188048253748	3.5059865339647605	0.6927081899148932
32768		0.7504983803911142	3.4430382872426364	0.6918022202184428

N	Height	AVL	RedBLack	TreeMap
8		3.0	3.0	3.0
16		4.0	4.0	4.0
32		5.0	6.0	5.0
64		6.0	7.0	6.0
128		7.0	8.0	8.0
256		9.0	9.0	9.0
512		10.0	12.0	10.0
1024		11.0	13.0	11.0
2048		12.0	14.0	13.0
4096		14.0	16.0	14.0
8192		14.0	17.0	15.0
16384		16.0	18.0	16.0
32768		17.0	20.0	18.0



*/