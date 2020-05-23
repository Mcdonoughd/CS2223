package dmcdonough.hw5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import algs.days.day12.SeparateChainingHashST;


/**
 * Modify this class for problem 1 on HW5.
 */
public class WordLadder {

	/**
	 * Represent the mapping of (uniqueID, 4-letter word)
	 */
	static SeparateChainingHashST<String,Integer> table = new SeparateChainingHashST<String,Integer>();
	static SeparateChainingHashST<Integer,String> reverse = new SeparateChainingHashST<Integer,String>();

	/**
	 * Determine if the two same-sized words are off by just a single character.
	 */
	public static boolean offByOne(String w1, String w2) {
		int offby = 0;
		char[] word1 = w1.toCharArray();
		char[] word2 = w2.toCharArray();
		//length of string is always 4
		for(int i=0;i<4;i++) {
			if(word1[i]!=word2[i]) {
				offby++; //if char are off by 1 then 
			}
		}
		//if not offbyone return false
		if(offby!=1) {
			return false;
		}
		else {
		return true;
		}
	}


	/**
	 * Main method to execute.
	 * 
	 * From console, enter the start and end of the word ladder.
	 */
	public static void main(String[] args) throws FileNotFoundException {

		// Use this to contain all four-letter words that you find in dictionary
		AVL<String> avl = new AVL<String>();

		// create a graph where each node represents a four-letter word.
		// Also construct avl tree of all four-letter words.
		// Note: you will have to copy this file into your project to access it, unless you
		// are already writing your code within the SedgewickAlgorithms4ed project.
		Scanner sc = new Scanner(new File ("words.english.txt"));
		while (sc.hasNext()) {
			String s = sc.next();
			if (s.length() == 4) {
				avl.insert(s);
			}
		}
		sc.close();

		
		Queue<String> queue = avl.keys();
		int Qsize = queue.size();
		//init graph
		Graph graph = new Graph(Qsize);
		
		//array of strings added to the table
		String[] checked = new String[Qsize];
		
		//for all values in the tree...
		for(int i =0;i<Qsize;i++) {
			String value = queue.dequeue(); //get the value from the avl
			//for all past values..
			for(int j = 0;j< i ;j++) {
				if(checked[j] != null) {
					//check if they are off by one
					if(offByOne(checked[j],value)) {
						//if so add an edge
						graph.addEdge(i, j);
					}
				}
			}
			
			//add values to the checked array, table and reverse table
			checked[i] = value;
			table.put(value, i);
			reverse.put(i, value);
			
		}
		
		
		StdOut.println("Enter word to start from (all in lower case):");
		String start = StdIn.readString().toLowerCase();
		StdOut.println("Enter word to end at (all in lower case):");
		String end = StdIn.readString().toLowerCase();

		// need to validate that these are both actual four-letter words in the dictionary
		if (!avl.contains(start)) {
			StdOut.println (start + " is not a valid word in the dictionary.");
			System.exit(-1);
		}
		if (!avl.contains(end)) {
			StdOut.println (end + " is not a valid word in the dictionary.");
			System.exit(-1);
		}

		// Once both words are known to exist in the dictionary, then create a search
		// that finds shortest distance (should it exist) between start and end.
		// be sure to output the words in the word ladder, IN ORDER, from the start to end.

		//Breath first search -> guarenteed to find the shortest path
		int str = table.get(start);
		int fin = table.get(end);
		BreathFirstSearch Search = new BreathFirstSearch(graph,str,fin);
		//Seach.bfs
		//Iterable<Integer> //Path =  Search.shortestPathTo(fin, str);
		System.out.println(Search.hasPathTo(fin));
		System.out.println(Search.distTo(fin));
		
		
		//boolean[] list_of_nodes = Search.getMarked();
		//int qsize  Seach.;
		//System.out.println(start);
//		for(int i = 0; i<qsize; i++) {
//			System.out.println();
//		}
		 if (Search.hasPathTo(fin)) {
             System.out.print(start + " ");
             for (int v : Search.shortestPathTo(fin,str)) {
                 System.out.print(reverse.get(v) + " " );
                 //System.out.print("hello ");
             }
             System.out.println();
         }
	
		//reverse.get(Search.forEach(action));
	}
}

