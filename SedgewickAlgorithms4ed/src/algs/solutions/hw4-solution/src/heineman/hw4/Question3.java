package heineman.hw4;

import algs.hw4.question3.Graph;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Question3 {
	/** Maximum number of places. */
	public static final int MaxLocations = 16;
	
	/** Each location gets its own unique id, mapped using the symbol table. */
	static SeparateChainingHashST<String, Integer> table = new SeparateChainingHashST<>();
	
	/** Each unique ID gets mapped to a location. This is the inverse map of table. */
	static SeparateChainingHashST<Integer,String> reverse = new SeparateChainingHashST<>();

	/** Next ID to use. */
	static int uniqueID = 0;
	
	static boolean marked[];		// which vertices have been seen already
	static Graph graph;			// graph being searched
	
	/** Continue DFS search over graph by visiting vertex v. */  
	static void dfs (int v) {
		marked[v] = true;    // we have now seen this vertex 
		
		// look into neighbors
		for (int w : graph.adj(v)) {
			if (!marked[w]) {
				dfs (w);
			}
		}
	}
	
	public static void main(String[] args) {
		
		StdOut.println ("Enter locations on a single line, separated by spaces. Note 'home' is special and does not need to be included in list.");
		String[] locations = StdIn.readLine().split(" ");
		int n = locations.length + 1; // don't forget home!
		graph = new Graph(n);
		marked = new boolean[n];
		
		// Home is the 0 vertex in the graph.
		table.put("home", 0);
		
		StdOut.println ("Enter source and target separated by a space. Once done, enter 0");
		
		// Build up a collection of edges
		while (StdIn.hasNextLine()) {
			String from = StdIn.readString();
			if (from.equals("0")) { break; }
			String to = StdIn.readString();
			
			int fromID;
			if (!table.contains(from)) {
				fromID = uniqueID++;
				table.put(from, fromID);
				reverse.put(fromID, from);
			} else {
				fromID = table.get(from);
			}
			
			int toID;
			if (!table.contains(to)) {
				toID = uniqueID++;
				table.put(to, toID);
				reverse.put(toID, to);
			} else {
				toID = table.get(to);
			}
			
			graph.addEdge(fromID, toID);
		}
		
		// make sure you can always get from "home" to any desired location. For all those
		// locations which are not reachable, output them all:
		dfs(0);  // start from home and try to reach everyone.
		
		boolean allConnected = true;
		for (int i = 0; i < uniqueID; i++) {
			if (!marked[i]) {
				System.out.println("Can't get from 'home' to " + reverse.get(i));
				allConnected = false;
			}
		}		
		
		if (allConnected) {
			System.out.println("Can get everywhere");
		}
	}
}
