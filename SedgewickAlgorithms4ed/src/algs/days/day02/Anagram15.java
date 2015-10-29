package algs.days.day02;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Anagram15 {
	
	public static int rank(String key, String[] a) {
		int lo = 0;
		int hi = a.length -1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			int cmp = key.compareTo(a[mid]);
			if (cmp < 0) hi = mid - 1;
			else if (cmp > 0) lo = mid + 1;
			else return mid;
		}
		
		return -1;
	}
	
	public static boolean in (int a, int[] ar) {
		for (int i = 0; i < ar.length; i++) {
			if (ar[i] == a) { return true; }
		}
		return false;
	}
	
	public static void main(String[] args) {
		In in = new In ("words.english.txt");
		String[] words = in.readAllStrings();
		
		StdOut.println("Enter 15 letters");
		String word = StdIn.readString().toLowerCase();
		char []letters = word.toCharArray();
		int a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,ctr=0;
		
		for (a = 0; a < 15; a++) 
 		 for (b = 0; b < 15; b++) 
		  if (!in (b, new int[] {a} ))
		   for (c = 0; c < 15; c++) 
			if (!in (c, new int[] {a,b} )) 
			 for (d = 0; d < 15; d++) 
			  if (!in (d, new int[] {a,b,c} ))
			   for (e = 0; e < 15; e++) 
				if (!in (e, new int[] {a,b,c,d} ))
				 for (f = 0; f < 15; f++) 
				  if (!in (f, new int[] {a,b,c,d,e} ))
				   for (g = 0; g < 15; g++) 
					if (!in (g, new int[] {a,b,c,d,e,f} ))
					 for (h = 0; h < 15; h++) 
					  if (!in (h, new int[] {a,b,c,d,e,f,g} ))
					   for (i = 0; i < 15; i++) 
						if (!in (i, new int[] {a,b,c,d,e,f,g,h} ))
						 for (j = 0; j < 15; j++) 
						  if (!in (j, new int[] {a,b,c,d,e,f,g,h,i} ))
						   for (k = 0; k < 15; k++)
						    if (!in (k, new int[] {a,b,c,d,e,f,g,h,i,j} ))
							 for (l = 0; l < 15; l++)
							  if (!in (l, new int[] {a,b,c,d,e,f,g,h,i,j,k} ))
							   for (m = 0; m < 15; m++)
							    if (!in (m, new int[] {a,b,c,d,e,f,g,h,i,j,k,l} ))
							     for (n = 0; n < 15; n++)
								  if (!in (n, new int[] {a,b,c,d,e,f,g,h,i,j,k,l,m} ))
								   for (o = 0; o < 15; o++)
								    if (!in (o, new int[] {a,b,c,d,e,f,g,h,i,j,k,l,m,n} )) {
								    	ctr++;
								    	word = ""+letters[a]+letters[b]+letters[c]+letters[d]+letters[e]+
								    		      letters[f]+letters[g]+letters[h]+letters[i]+letters[j]+
								    		      letters[k]+letters[l]+letters[m]+letters[n]+letters[o];
								    	if (rank(word, words) != -1 ) {
								    		System.out.println(word + " at " + ctr);
								    	}
								    }
	}
}