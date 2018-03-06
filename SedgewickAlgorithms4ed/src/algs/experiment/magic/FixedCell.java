package algs.experiment.magic;

public class FixedCell extends Cell {
	
	public final int value;
	public FixedCell (int r, int c, int fix) { super(r, c); value = fix; type = 2;}
	
}
