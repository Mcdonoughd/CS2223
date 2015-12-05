package algs.solutions.hw4;

import edu.princeton.cs.algs4.StdOut;

public class OutputTree<Value extends Comparable<Value>> implements IVisitor<Value> {

	@Override
	public void process(Value v, int level) {
		StdOut.println(v);
	}

}
