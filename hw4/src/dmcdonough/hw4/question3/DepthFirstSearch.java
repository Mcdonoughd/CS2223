package dmcdonough.hw4.question3;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import algs.days.day20.Stack;
import dmcdonough.hw4.question3.Graph;

/**
 * Run in Shell as follows:
 * 
 * % java algs.days.day20.DepthFirstSearch tinyG.txt
 */
public class DepthFirstSearch {
	int[] edgeTo; 
	boolean marked[];	// which vertices have been seen already
	int count;			// how many connected
	Graph g;			// graph being searched
	String indent = "";
	final int s;
	
	

	public DepthFirstSearch(Graph g, int s) {
		this.s = s;
		this.g = g;
		edgeTo = new int[g.V()];
		marked = new boolean[g.V()];
		dfs(s);
	}


	public boolean hasPathTo(int v) { return marked[v]; }

	/** recover path from s to v. */
	public Iterable<Integer> pathTo(int target) {
		if (!hasPathTo(target)) return null;
		
		Stack<Integer> path = new Stack<Integer>();
		int v = target;
		while (v != s) {
			path.push(v);
			v = edgeTo[v];
		}

		// last one to push is the source, which makes it
		// the first one to be retrieved
		path.push(s);
		return path;
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
	public boolean[] getMarked(){
    	return this.marked;
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
