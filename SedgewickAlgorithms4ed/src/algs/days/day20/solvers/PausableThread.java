package algs.days.day20.solvers;

public class PausableThread extends Thread {
	
	/** Is processing temporarily suspended. */
	boolean paused;
	
	public void pause(boolean b) {
		paused = b;
	}

	/** Delay if paused. Only external action unpauses, once in while loop. */
	void checkPaused() {
		while (paused) {
			try {
				this.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isPaused() {
		return paused;
	}
}
