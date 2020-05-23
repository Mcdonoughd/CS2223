package algs.days.day20;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import algs.days.day19.Graph;

/**
 * Run in Shell as follows:
 * 
 * % java algs.days.day20.DepthFirstSearch tinyG.txt
 */
public class DepthFirstSearch {
	
	boolean marked[];	// which vertices have been seen already
	int count;			// how many connected
	Graph g;			// graph being searched
	String indent = "";
	public DepthFirstSearch (Graph g, int s) {
		marked = new boolean[g.V()];
		this.g = g;
		dfs(s);
	}
	
	public int count() { return count; }    			 // number of vertices connected to s
	public boolean marked(int v) { return marked[v]; }
	
	/** Continue DFS search over graph by visiting vertex v. */  
	void dfs (int v) {
																indent += "  "; 
																StdOut.println(indent + "dfs(" + v + ")");
		marked[v] = true;    // we have now seen this vertex 
		count++;
		
		// look into neighbors
		for (int w : g.adj(v)) {								StdOut.println(indent + "  check" + w);
			if (!marked[w]) {
				dfs (w);
			}
		}
		indent = indent.substring(2);  // truncate indentation
	}
	
	public static void main(String[] args) {
		In in = new In(args[0]);
		Graph g = new Graph(in);
		
		// conduct a DFS over entire graph
		DepthFirstSearch dfs = new DepthFirstSearch(g, 0);
		
		// see who was connected
		for (int i = 0; i < g.V(); i++) {
			StdOut.println(i + "," + dfs.marked[i]);
		}
		StdOut.println(dfs.count() + " connected vertices.");
	}
}
