package algs.solutions.hw5;

public class DijkstrasAlgorithm {
	// TODO: The result of your computation is stored in these arrays.
	static double dist[];
	static int pred[];     // this is equivalent to edgeTo as discussed for DFS/BFS
	static boolean visited[];
	
	public static void singleSourceShortestPath(DiGraphMatrix graph, int s) {
		int N = graph.V;
		dist = new double[N];
		pred = new int[N];
		visited = new boolean[N];
		for (int v = 0; v < N; v++) {
			dist[v] = Integer.MAX_VALUE;
			pred[v] = -1;
			visited[v] = false;
		}
		dist[s] = 0;
		
		
		while (true) {
			boolean allVisited = true;
			int u;
			for (u = 0; u < N; u++) {
				if (!visited[u]) { allVisited = false; break; }
			}
			
			if (allVisited) { return; }
			
			// assume u is smallest distance. Try to find smaller unvisited
			for (int i = u+1; i < N; i++) {
				if (!visited[i] && dist[i] < dist[u]) {
					u = i;
				}
			}
			
			visited[u] = true;
			for (DirectedEdge e : graph.adj(u)) {
				int v = e.to();
				double w = e.weight();
				double newLen = dist[u] + w;
				if (newLen < dist[v]) {
					dist[v] = newLen;
					pred[v] = u;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// TODO: Replace
		// Manually construct the DiGraphMatrix sample graph from Question 4 and 
		// demonstrate that it produces the results as shown.
		DiGraphMatrix digraph = new DiGraphMatrix(5);
		digraph.addEdge(0, 1, 2);
		digraph.addEdge(0, 4, 4);
		digraph.addEdge(1, 2, 3);
		digraph.addEdge(2, 1, 4);
		digraph.addEdge(2, 3, 5);
		digraph.addEdge(3, 0, 8);
		digraph.addEdge(4, 3, 7);
		
		singleSourceShortestPath(digraph, 0);
		
		for (int i = 0; i < digraph.V; i++) {
			System.out.print (dist[i] + " ");
		}
		System.out.println();
		
	}
}
