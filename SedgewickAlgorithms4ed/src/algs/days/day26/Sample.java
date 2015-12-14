package algs.days.day26;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Sample {
	public static void main(String[] args) {

		EightPuzzleNode start = new EightPuzzleNode(new int[][]{
				{1,4,8},{7,3,0},{6,5,2}
		});

		EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
				{1,2,3},{8,0,4},{7,6,5}
		});

		AStarSearch as = new AStarSearch(new GoodEvaluator());
		Solution sol = as.search(start, goal);

		for (SlideMove s : sol) {
			StdOut.println(s);
		}
		
		
	}
}
