package dmcdonough.hw2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
/**
 * Your goal is to output a table that looks like this:

N	Avg.	Inspectk
1	1.00	1
2	1.50	1,2
4	2.00	2,1,2,3
8	2.63	3,2,3,1,3,2,3,4
16	3.38	4,3,4,2,4,3,4,1,4,3,4,2,4,3,4,5
32	4.22	5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,1,5,4,5,3,5,4,5,2,5,4,5,3,5,4,5,6
64	5.13	6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,2,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,1,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,2,6,5,6,4,6,5,6,3,6,5,6,4,6,5,6,7


Bonus Question (1pt.)

See homework. Come up with a mathematical formula that computes this average value.

 */

public class Analysis {
	

		//computes log base 2 of input
		public static double log2(double num){
		return (Math.log(num)/Math.log(2));
		}
		
		
		//Siblings on any N^2 size array such that:
		//offset to the current level being searched is 2^(maxlevel-level-1) - 1
		//number of occurences = 2^(level-1)
		//Pattern of occurences = size/num
		public static void Sibling(int r, Integer[] ar, int level, int N, int max_level){
			//r is the pos of the 1st val of the current level
			
			int num = (int) Math.pow(2, level-1) ;
			int pattern = (int) Math.floor(N/num);
			//int offset = (int) Math.pow(2, max_level-level-1)-1;
			
			for(int i = r; i <= N-1; i+=pattern) {
				ar[i] = level; //place each element on the level with it's level number
			}
		}
			
		
		//init the structure
		public static void Parent(int r, Integer[] ar, int level, int N, int max_level) {
			//check if current level of tree is greater than the greatest possible level
			if(max_level-1 < level) {
				return;
			}
			else {
			//calculate origin to check for edges
			int origin = (int) Math.floor((N-1)/2);
			//calculate the left most child
			int left_child = (int) (r-(Math.floor(r/2))-1);
			//calc right child
			int right_child = (int) (r+(Math.floor(r/2))+1);
			
    		//check edges
			if(left_child >= 0 && left_child < N && ar[left_child] == null && left_child < origin) {
				//get sibling which exist on level the current level (min 2) and beyond
				if (level>=2 && level<max_level ) {
					Sibling(left_child,ar,level,N,max_level);
				}
        		ar[left_child] = level; //set child
        		
        		Parent(left_child,ar,level+1,N,max_level); //Recursive to left children
        		Parent(right_child,ar,level+1,N,max_level); //recursive to right children
        		 
    			}
			}
			//end
    		//return;
		}
		
		
		//print the given array
		public static void printBCTH(Integer[] ar) {
			for(int i=0; i<=ar.length-1;i++) {
				if(i<ar.length-1) {
				StdOut.print(ar[i]+",");
				}
				else {
					StdOut.print(ar[i]);
				}
			}
			StdOut.println();
		}
		
		
		//sum the given array
		public static float sumBCTH(Integer[] ar) {
			float sum =0;
			for(int i=0; i<=ar.length-1;i++) {
				sum+=ar[i];
			}
			return sum;
		}
		
	    /**
	     * Reads in a sequence of strings from standard input; selection sorts them; 
	     * and prints them to standard output in ascending order. 
	     */
	    public static void main(String[] args) {
	    	StdOut.println("N\tAvg\t\tInspectk");
	    	//Binary Complete Tree-Heap Implementation
	    	//Introducing the possibly most useless data structure ever
	    	
	    	//for each size array....
	        for (int N = 1; N <= 64; N *= 2) {
	        	
	        	//Make array
	        	Integer[] ar = new Integer[N];
	        	
	        	//determine max level of tree
	        	int max_level = (int) (log2(N)+1);
	        	
	        	//if array is greater than size 2
	        	if(N > 2) {
	    			int origin = (int) Math.floor((N-1)/2);
	    			ar[origin] = 1;
		        	Parent(origin, ar, 2, N, max_level );
	        	}
	        	
	        	//else set 1st element as 1
	        	else {
	        		ar[0] = 1;
	        	}
	        	//add last value to array (note on 1x1 array one is filled twice due to this attribute
	        	ar[N-1] = max_level;
	        	
	        
	        	StdOut.printf("%d\t%f\t", N, (sumBCTH(ar)/N));
	        	printBCTH(ar);
	    }
	        }
	    }
	

