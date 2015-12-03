package algs.days.day20;

import java.text.NumberFormat;

import algs.days.day19.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class ShowAdjacencyMatrix {
	public static void main(String[] args) {
		In in = new In(args[0]);
		Graph g = new Graph(in);
		
		int[][] matrix = new int[g.V()][g.V()];
		
		for (int i = 0; i < g.V(); i++) {
			for (Integer neighbor : g.adj(i)) {
				matrix[i][neighbor] = 1;
				matrix[neighbor][i] = 1;
			}
		}

		NumberFormat nf =  NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(2);
		
		StdOut.print("   ");
		for (int i = 0; i < g.V(); i++) {
			StdOut.print(nf.format(i) + " ");
		}
		StdOut.println();
		for (int i = 0; i < g.V(); i++) {
			StdOut.print(nf.format(i) + " ");
			
			for (int j = 0; j < g.V(); j++) {
				if (matrix[i][j] == 0) {
					StdOut.print("   ");
				} else {
					StdOut.print("1  ");
				}
			}
			StdOut.println();
		}
		
	}
}
