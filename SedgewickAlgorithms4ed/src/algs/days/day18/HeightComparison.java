package algs.days.day18;

import edu.princeton.cs.algs4.StdOut;

// this code inserts values into the binary tree IN ASCENDING ORDER. Without balancing,
// this would lead to incredibly skewed trees. As it is, the trees self-balance, and we can
// keep track of its height and number of rotations. See if you can figure out a closed
// formula for the number of rotations, for all three varieties.
public class HeightComparison {

	public static void main(String[] args) {
		
		int maxIdx = 16;
		StdOut.println("N\tA-Ht.\tRB-Ht.\tTM-Ht.\tAVL R\tRB R\tTM R");
		for (int n = 8; n <= Math.pow(2, maxIdx); n *= 2) {

			AVL<Integer> avl = new AVL<Integer>();
			RedBlackBST<Integer,Integer> redBlack = new RedBlackBST<Integer,Integer>();
			TreeMap<Integer,Integer> treeMap = new TreeMap<Integer,Integer>();

			// up to powers of 2 minus 1
			for (int i = 1; i < n; i++) {
				if (!avl.assertAVLProperty(avl.root)) {
					StdOut.println ("BAD AVL");
				}
				if (!redBlack.check()) {
					StdOut.println ("BAD RedBlack Tree");
				}


				avl.insert(i);
				redBlack.put(i, i);
				treeMap.put(i, i);
			}
			
			// guaranteed to have Num unique integers... Get Height Now!
			int heightAvl = 1+avl.height();
			int heightRedBlack = 1+redBlack.height();
			int heightTreeMap = 1+treeMap.height();
			
			int rotatAvl = avl.rotations;
			int rotateRB = redBlack.rotations;
			int rotateTreeMap = treeMap.rotations;
			
			StdOut.println((n-1) + "\t" + heightAvl + "\t" + heightRedBlack + "\t" + heightTreeMap +
					"\t" + rotatAvl + "\t" + rotateRB + "\t" + rotateTreeMap);
		}
	}
}


/** Sample output. Observe: Height for RedBlack TreeMap is no more than twice optimal. Number of Rotations ~ same
 
N		A-Ht. RB-Ht. TM-Ht.	AVL R	RB R	TM R
7		3	  3	     4		4		4		3
15		4	  4	     6		11		11		9
31		5	  5  	 8		26		26		23
63		6	  6  	 10		57		57		53
127		7	  7	     12		120		120		115
255		8	  8	     14		247		247		241
511		9	  9	     16		502		502		495
1023	10    10	 18		1013	1013	1005
2047	11    11	 20		2036	2036	2027
4095	12	  12	 22		4083	4083	4073
8191	13	  13	 24		8178	8178	8167
16383	14	  14	 26		16369	16369	16357
32767	15	  15	 28		32752	32752	32739
65535	16	  16	 30		65519	65519	65505
131071	17	  17	 32		131054	131054	131039

*/