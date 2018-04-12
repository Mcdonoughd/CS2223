package algs.days.day19;

public class Sample {
	public static void main(String[] args) {

		// Produce a sample graph. A bit like the one
		// in lecture day19 but without weights or directions on edges.
		
		Graph g = new Graph(6);
		g.addEdge(0, 3);
		g.addEdge(1, 4);
		g.addEdge(2, 4);
		g.addEdge(4, 5);
		g.addEdge(2, 5);
		
		System.out.println(g);
		
		
	}
}
