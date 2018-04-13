package algs.days.day20.maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class MazePanel extends JPanel {
	Maze maze;
	
	/** This controls the size of each square (in pixels). Smaller is tougher. */
	static int boxSize = 10;
	
	/**
	 * Technique for smooth flicker-free drawing is to use a double-buffering technique.
	 * All drawing statements go to the offscreen Image, and then our paintComponent
	 * method simply copies the image to the screen to improve user experience.
	 */
	Image offscreenImage = null;
	Graphics offscreenGraphics = null;
	
	/** Our color scheme for representing the state of the search. */
	Color[] colors = new Color[] {new Color(238,238,238), Color.blue, Color.green};
	
	/**
	 * State of the current solver. This contains colors (White=0, Gray=1, Black=2) that 
	 * record the progress of the DFS. This state, when available, is drawn using our
	 * own color scheme above.
	 */
	int [][]state;

	/** Need this for WindowBuilder editor. */
	public MazePanel () {
		this.maze = new Maze(getHeight(), getWidth(), boxSize);
	}

	/** every maze panel knows its maze to be drawn. */
	public MazePanel (Maze maze) {
		this.maze = maze;
	}

	/** Helper method. */
	public Maze getMaze() {
		return maze;
	}
	
	/**
	 * Clean up the image properly. Note Java can encounter memory leaks if you don't properly
	 * dispose of images and graphics contexts when you are done with them.
	 */
	void clearImage() {
		if (offscreenImage != null) {
			offscreenGraphics.dispose();
			offscreenImage.flush();
		}
		
		offscreenImage = this.createImage(getWidth(), getHeight());
		offscreenGraphics = offscreenImage.getGraphics();
	}

	/**
	 * Draw a maze to the screen. To make the coding easier, this method also knows about
	 * state which represents the progress of a given search algorithm. 
	 */
	public void redraw() {
		// no point in redrawing if we do not yet have an offscreen image. For technical reasons
		// related to Java GUI initialization routines, this might actually happen.
		if (offscreenImage == null) { return; }

		// nothing to draw in this case.
		if (maze == null) { return; }

		// offset to avoid having maze right up against the walls.
		int offset = 5;
		int size = maze.size;

		// draw outer three lines, but not bottom because those are drawn with south walls.
		offscreenGraphics.drawLine(offset, offset, offset, maze.numrows*size + offset);
		offscreenGraphics.drawLine(offset, offset, ((maze.numcols-1)/2)*size + offset, offset);
		offscreenGraphics.drawLine(offset + size*(1 + (maze.numcols-1)/2), offset, offset + maze.numcols*size, offset);

		for (int r = 0; r < maze.numrows; r++) {
			for (int c = 0; c < maze.numcols; c++) {
				if (maze.hasSouthWall[r][c]) {
					offscreenGraphics.drawLine(offset + c*size, (r+1)*size + offset,
							offset + (c+1)*size, (r+1)*size + offset);
				}
				if (maze.hasEastWall[r][c]) {
					offscreenGraphics.drawLine(offset + (c+1)*size, r*size + offset, 
							offset + (c+1)*size,  (r+1)*size + offset);
				}
				
				// progress is shown here...
				if (state != null) {
					offscreenGraphics.setColor(colors[state[r][c]]);
					offscreenGraphics.fillRect(offset + c*size + 1, r*size + offset + 1, size-1, size-1);
					offscreenGraphics.setColor(Color.black);
				}
			}
		}
	}

	/**
	 * Simple repaint method that just copies the image. 
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(offscreenImage, 0, 0, this);
	}

	/**
	 * Used for GUI solving to show state of solver. When called with color=null, this signals the
	 * solver is done or is prematurely interrupted.
	 * 
	 * @param colors
	 */
	public void setProgress(int[][] colors) {
		state = colors;
	}
}