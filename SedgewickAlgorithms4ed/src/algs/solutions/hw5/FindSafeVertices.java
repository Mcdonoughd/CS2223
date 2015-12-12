package algs.solutions.hw5;

import algs.days.day21.Stack;
import edu.princeton.cs.algs4.StdDraw;


public class FindSafeVertices {

	static boolean isConnected (Graph g) {

		Stack<Integer> stack = new Stack<Integer>();
		boolean marked[] = new boolean[g.V()];

		stack.push(0);
		marked[0] = true;
		while (!stack.isEmpty()) {
			Integer u = stack.pop();

			for (int v : g.adj(u)) {
				if (!marked[v]) {
					marked[v] = true;
					stack.push(v);
				}
			}
		}

		// not connected!
		for (int i = 0; i < g.V(); i++) {
			if (!marked[i]) { return false; }
		}
		return true;
	}

	static int findSafeVertex (Graph g) {
		if (!isConnected(g)) { 
			return -1;
		}
		int N = g.V();
		boolean [] marked = new boolean[N];

		// positions in the Queue are Gray and under investigation.
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(0);
		marked[0] = true;
		while (!stack.isEmpty()) {
			Integer u = stack.pop();

			boolean isSafe = true;
			for (int v : g.adj(u)) {
				if (!marked[v]) {
					isSafe = false;
					marked[v] = true;
					stack.push(v);
				}
			}
			
			// if neighbors are all marked, then this is safe to be deleted.
			if (isSafe) {
				return u;
			}
		}
		
		return -1;
	}
	
	static int countNumberSafeVertices (Graph g) {
		if (!isConnected(g)) { 
			return -1;
		}
		int count = 0;
		
		int N = g.V();
		boolean [] marked = new boolean[N];

		// positions in the Queue are Gray and under investigation.
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(0);
		marked[0] = true;
		while (!stack.isEmpty()) {
			Integer u = stack.pop();

			boolean isSafe = true;
			for (int v : g.adj(u)) {
				if (!marked[v]) {
					isSafe = false;
					marked[v] = true;
					stack.push(v);
				}
			}
			
			// if neighbors are all marked, then this is safe to be deleted.
			if (isSafe) {
				count ++;
			}
		}
		
		return count;
	}
	
	public static void main(String[] args) {
		int T = 16384;
		System.out.println ("N\tnumSafe");
		
		for (int N = 64; N <= 128; N++) {

			int numSafe = -1;
			for (int t = 0; t < T; t++) {
				Graph g = RandomGraph.random(N);
				numSafe = countNumberSafeVertices(g);
				if (numSafe != -1) { break; }
			}
			System.out.println(N + "\t" + numSafe);
		}
	}
}
