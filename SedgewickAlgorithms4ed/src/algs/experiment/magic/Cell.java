package algs.experiment.magic;

public class Cell {
	public final int row;
	public final int col;
	public Cell (int r, int c) { row = r; col = c; }
	
	public int type = 0;  // NORMAL
	
	public String toString () { return "(" + row + "," + col + ")"; }
}
