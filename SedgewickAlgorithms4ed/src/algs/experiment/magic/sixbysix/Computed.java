package algs.experiment.magic.sixbysix;

import algs.experiment.magic.Cell;

public class Computed extends Cell {
	
	public Computed (int r, int c) { super(r, c); type = 1;}
	
	public Cell[] bases;
	
	/** These are the four coordinates that would be used to compute. */
	public Cell from (Cell c1, Cell c2, Cell c3, Cell c4, Cell c5) {
		bases = new Cell[] { c1, c2, c3, c4, c5 }; 
		return this;
	}
	
}
