package dmcdonough.hw3;


import java.text.DecimalFormat;

import dmcdonough.hw3.BST.Node;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/*
 * 
 * 	You will generate N random keys from the uniform distribution of using StdRandom.uniform(). As you
have seen in past homeworks, your job is to evaluate random BSTs of size of (N=64, 128, …, 1024). For a
given problem size N, you are to compute an array of N random double values. Use these N keys to
construct a BST by inserting these keys into the BST. It doesn’t matter what values are to be stored with
the keys, so for convenience, just use Boolean as I show in the sample code, and always store the value
“true” with each key.
To confirm proposition C, invoke get(key) for each of these values in the BST and you will guarantee a
search hit. Compute the necessary statistics and output a table showing the average number of
compares for a problem of size N.
To confirm proposition D, construct a new array of N different double values (assuming the unlikelihood
of generating a perfect match) and then invoke get(key) for these values, and each one should miss.
Compute the necessary statistics and output a table showing the average number of compares for a
problem of size N.
 *
 */



/**
 * Proposition C (From Page 403 in Sedgewick)
 *
 *	 The number of compares used for a search hit ending at a given node is 1 plus the depth.
 *	 Adding the depths of all nodes, we get a quantity known as the internal path length of the tree.
 *	 
 *	 Thus the desired quantity (i.e., average # of compares on random BST) is 1 plus the average
 *	 internal path length of the BST. Let Cn be the internal path length of a BST built from 
 *	 inserting N randomly ordered distinct keys, so the average cost of a search hit is 1 + Cn/N.
 *	 
 *	 Compute that CN ~ 2N ln N, thus average is ~ 2 ln N, where ln is natural logarithm. Or as I
 *	 prefer to state it, average is ~ 1.39 log N in base 2.
 *
 * Proposition D (From page 404 in Sedgewick)
 *   
 *   Insertions and search misses in a BST built from N random keys require ~2 ln N (about 1.39 log N)
 *   
 * Note: you will change nearly everything in this class.
 */
public class PropositionCD {
	//Cn = 0 the total internal path length of a BST built from inserting N randomly ordered distinct keys, 
	//so that the average cost of a search hit is 1 + CN / N. 
	//
	//The number of compares used for a search hit ending at a given node is 1 plus the depth of that node. 
	//Adding the depths of all nodes, we get a quantity known as the internal path length of the tree
	
	
	//H-Ave = 1 = avg number of comparisons on binary hits
	//M-Ave = 2 = avg number of comparisons on binary tree misses
	//Model = 3 = the avg height of a tree dictated by 1.39 *log(N)
	static double[][] results = new double[12][5]; //array of max comp and exchanges
	
	//generates log 2 base of input
	public static double log2(double num){
		return (Math.log(num)/Math.log(2));
		}
	
	//gets the number of comparisons required for given keys
	public static double getKeys(BST<Double,Boolean> tree, Double[] Keys) {
		double H_ave = 0;
		int sumcomp = 0;
		double size = Keys.length;
		//System.out.println(size);
		for(int i=0;i<size;i++) {
			sumcomp += tree.get_comp(Keys[i]);
		}
		H_ave = sumcomp / size;
		//System.out.println(H_ave);
		return H_ave;
	}
	
	//gets the number of comparisons required for invalid keys
	public static double getUnknownKeys(BST<Double,Boolean> tree, Double[] Keys) {
		double m_ave = 0;
		int sumcomp = 0;
		double size = Keys.length;
		//System.out.println(size);
		for(int i=0;i<size;i++) {
			sumcomp += tree.get_comp(Keys[i]);
		}
		m_ave = sumcomp / size;
		//System.out.println(H_ave);
		return m_ave;
	}
	
	
	//generate the report
	public static void generateReport(int n,int idx) {
		DecimalFormat df = new DecimalFormat("###.##");
		double model = 1.39 * log2(n); //make model
		StdOut.println(n + "\t" + df.format(results[idx][0]) + "\t" + df.format(results[idx][1]) + "\t" + df.format(results[idx][2])+ "\t" + df.format(model));
	}

	 
	//generates valid data for the TREE returns array of doubles of values in the tree
		static Double[] generateData(BST<Double,Boolean> tree,int n) {
			Double[] Keys = new Double[n];
			for (int i = 0; i < n; i++) {
				double randomnumber = StdRandom.uniform();
				tree.put(randomnumber, true);//generate uniform data
				Keys[i] = randomnumber;
				//System.out.println(Keys[i]);
			}
			return Keys;
		}
		
		//generate unknownset of number (assuming no duplicates)
		static Double[] generateUnkownData(BST<Double,Boolean> tree,int n) {
			Double[] Keys = new Double[n];
			for (int i = 0; i < n; i++) {
				double randomnumber = StdRandom.uniform();
				tree.put(randomnumber, true);//generate uniform data
				//Keys[i] = randomnumber;
				//System.out.println(Keys[i]);
			}
			for (int i = 0; i < n; i++) {
				double randomnumber = StdRandom.uniform();
				//generate uniform data
				Keys[i] = randomnumber;
				//System.out.println(Keys[i]);
			}
			return Keys;
		}
		
		
		
	//CN be the total internal path length of a BST built from inserting N randomly ordered distinct keys, so that the average cost of a search hit is 1 CN / N. 

	
	static void Cn(int n,int idx) {
		BST<Double,Boolean> tree = new BST<Double,Boolean>();
		generateData(tree,n);
		//System.out.println(tree.FindHeight(tree.root));
		results[idx][0] = tree.getCN(tree.root);
		BST.totalHeightSum = 0;
	}	
	
	
	static void H_avg(int n,int idx) {
		BST<Double,Boolean> tree = new BST<Double,Boolean>();
		Double[] Keys = generateData(tree,n);
		results[idx][1] = getKeys(tree,Keys);
	}
	
	static void M_avg(int n,int idx) {
		BST<Double,Boolean> tree = new BST<Double,Boolean>();
		//Double[] real = generateData(tree,n);
		Double[] Keys = generateUnkownData(tree,n);
		results[idx][2] = getKeys(tree,Keys);
		
	}
	
	public static void main(String[] args) {

		System.out.println("N\tCn\tH-Ave\tM-Ave\tModel");
		for(int n = 64, idx=0; n<=1024;n*=2,idx++) {
			//generate random input to tree
			//It doesn’t matter what values are to be stored with
			//the keys, so for convenience, just use Boolean as I show in the sample code, and always store the value
			//“true” with each key.
			Cn(n,idx);
			H_avg(n,idx);
			M_avg(n,idx);
			generateReport(n,idx);
		}
	}
}
