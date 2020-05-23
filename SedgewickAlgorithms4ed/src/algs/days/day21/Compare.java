package algs.days.day21;

public class Compare {
	int distTo[];
	boolean marked[];
	int edgeTo[];
	
	void bfs(Graph G, int s) {
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		Queue<Integer> q = new Queue<Integer>();
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Integer.MAX_VALUE;
		
		distTo[s] = 0;
		marked[s] = true;
		q.enqueue(s);

		while (!q.isEmpty()) {
			int v = q.dequeue();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					marked[w] = true;
					q.enqueue(w);
				}
			}
		}
	}
	
	void dfs(Graph G, int s) {
		marked = new boolean[G.V()];
		edgeTo = new int[G.V()];
		Stack<Integer> stack = new Stack<Integer>();
		marked[s] = true;
		stack.push(s);

		while (!stack.isEmpty()) {
			int v = stack.pop();
			for (int w : G.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					marked[w] = true;
					stack.push(w);
				}
			}
		}
	}
	
}
