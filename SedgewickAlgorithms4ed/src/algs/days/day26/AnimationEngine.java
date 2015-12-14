package algs.days.day26;

import java.awt.Color;

import edu.princeton.cs.algs4.*;

public abstract class AnimationEngine {

	static int delay = 10;
	static SeparateChainingHashST<EightPuzzleNode, Boolean> check;

	GoodEvaluator eval = new GoodEvaluator();
	SeparateChainingHashST<EightPuzzleNode,Point> locations = new SeparateChainingHashST<EightPuzzleNode,Point>();

	// keep track of left-most board for given evaluation.
	int leftMostPositions[];
	
	public AnimationEngine () {
		leftMostPositions = new int[100];
		for (int i = 0; i < leftMostPositions.length; i++) {
			leftMostPositions[i] = 5;
		}
	}
	
	static EightPuzzleNode goal = new EightPuzzleNode(new int[][]{
			{1,2,3},{8,0,4},{7,6,5}
	});

	static double Radius = 0.5;
	static int White = 0;
	static int Gray = 1;
	static int Black = 2;
	static Color colors[] = { Color.gray, Color.blue, Color.green };

	/** Filled in by subclasses. */
	abstract boolean explore(EightPuzzleNode n);

	void drawLine(Color c, EightPuzzleNode u, EightPuzzleNode v) {
		Point p = locations.get(u);
		Point q = locations.get(v);
		StdDraw.setPenColor(c);
		StdDraw.line(p.x, p.y, q.x, q.y);
	}

	// get previous or create new one
	Point getLocation(EightPuzzleNode n) {
		if (!locations.contains(n)) {
			int row = eval.eval(n);
			Point p = new Point(leftMostPositions[row], row);
			leftMostPositions[row] += 3;
			locations.put(n, p);
			return p;
		} else {
			return locations.get(n);
		}
	}
	
	void runTrial(EightPuzzleNode n) {

		// Create starting point based on evaluated distance from goal using GoodEvaluator
		int row = eval.eval(n);

		if (!locations.contains(n)) {
			Point p = new Point(leftMostPositions[row], row);
			leftMostPositions[row] += 8;
			locations.put(n, p);
		}

		// set the scale of the coordinate system
		StdDraw.setXscale(0, 100);
		StdDraw.setYscale(0, 100);

		// 
		StdDraw.show(100); // wait 100 milliseconds.

		explore(n);  // BFS
	}

	static EightPuzzleNode bfs (EightPuzzleNode n, int maxDepth) {
		Queue<EightPuzzleNode> queue = new Queue<EightPuzzleNode>();
		n = n.copy();
		n.transition = new Transition(null, n, 0);
		queue.enqueue(n);
		int depth = 0;

		while (!queue.isEmpty()) {
			n = queue.dequeue();
			depth = n.transition.depth;
			if (depth == maxDepth) {
				return n;
			}

			Bag<SlideMove> moves = n.validMoves();
			for (SlideMove m : moves) {
				EightPuzzleNode next = n.copy();
				m.execute(next);
				if (!check.contains(next)) {
					next.transition = new Transition(m, n, depth+1);
					check.put(next, true);
					queue.enqueue(next);
				}
			}
		}

		// return as far as we were able to get.
		System.out.println("stopping at:" + depth);
		return n;
	}

	/** Must be called by subclass main. */
	public void launch() {
		

		// numMoves to make away from goal without duplicating a state.
		for (int numMoves = 4; numMoves < 32; numMoves++) {

			// make T random moves and never revisit a prior state
			check = new SeparateChainingHashST<EightPuzzleNode, Boolean>();
			EightPuzzleNode n = bfs(goal, numMoves);

			// at this point we can explore...
			runTrial(n);

			while (!StdDraw.mousePressed()) {
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					// not much to do.
				}
			}

			StdDraw.clear();
		}
	}
} 
