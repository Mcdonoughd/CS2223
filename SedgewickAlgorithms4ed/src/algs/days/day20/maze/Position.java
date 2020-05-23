package algs.days.day20.maze;

/**
 * Represents a position in a maze by (row, col).
 * 
 * Must override the equals method so we can compare positions using .equals
 */
public class Position {
	public final int row;
	public final int col;
	
	public Position (int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	@Override
	public boolean equals (Object o) {
		if (o == null) { return false; }
		if (o instanceof Position) {
			Position p = (Position) o;
			return row == p.row && col == p.col;
		}
		
		return false;  // not even a Position
	}
	
	public String toString() { return "[" + row + "," + col + "]"; }
}
