package algs.solutions.hw4;

import edu.princeton.cs.algs4.StdOut;

public class FormatTree<Value extends Comparable<Value>> implements IVisitor<Value> {

	@Override
	public void process(Value v, int level) {
		for (int i = 0; i < level; i++) { StdOut.print("  "); }
		StdOut.println(v);
	}

}
