package algs.days.day20.maze;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import algs.days.day20.solvers.AStarSolver;
import algs.days.day20.solvers.BFSSolver;
import algs.days.day20.solvers.DFSSolver;
import algs.days.day20.solvers.PausableThread;

/**
 * GUI Window constructed from within WindowBuilder in Eclipse.
 */
public class MazeGUI extends JFrame {
	/** Where the maze is drawn. */
	final MazePanel  panel;
	
	/** Current solver in play. */
	PausableThread    solver;  

	/** Construct the GUI for a random maze. */
	public MazeGUI() {
		setTitle("Maze Viewer: Right=BFS, LEFT=DFS, LEFT+CTRL=AStar. Any key pauses.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 377);
		JPanel contentPane = new JPanel();
		setContentPane(contentPane);
		
		Maze maze = new Maze(getHeight(), getWidth(), MazePanel.boxSize);
		panel = new MazePanel(maze);
		
		// This is layout code that ensures the maze panel will properly be resized if window is resized.
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
		
		this.addKeyListener( new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				solver.pause(!solver.isPaused());
			}
			
		});
		
		// this sets up the reactive code upon a resize to construct a new maze with the given height.
		// offsets of -50 and -30 occur because of the window title bar.
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent e) {
		    	if (solver != null) { 
		    		solver.stop(); 
		    		solver = null;
		    		panel.setProgress(null);
		    	}
		    	
		    	// whenever resize occurs, create a new maze and cause a repaint.
		        panel.maze = new Maze(getHeight()-50, getWidth()-30, MazePanel.boxSize);
		        panel.clearImage();
		        panel.redraw();
		        MazeGUI.this.repaint();
		    }
		});
		
		// react to mouse clicks by launching a solver. If one already exists, stop it and set the progress
		// to null to prevent errors.
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				if (solver != null) {
					solver.stop();
					solver = null;
					panel.setProgress(null);
				}
				
				if (SwingUtilities.isRightMouseButton(me)) {
					solver = new BFSSolver(panel);
				} else {
					if (me.isControlDown()) {
						solver = new AStarSolver(panel);
					} else {
						solver = new DFSSolver(panel);
					}
				}

				solver.start();
			}
		});
	}
}
