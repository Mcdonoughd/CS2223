package algs.solutions.hw1;

import algs.hw1.FixedCapacityStackOfInts;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Standard Tower of Hanoi disk problem (https://en.wikipedia.org/wiki/Tower_of_Hanoi).
 * 
 * Note that with n=4 disks, a solution takes 2^n-1 or 15 steps.
 */
public class Solution_TowerOfHanoi {

	/** Three separate stacks, each to hold up to four disks. */
	static FixedCapacityStackOfInts stacks[] = new FixedCapacityStackOfInts[] {
		new FixedCapacityStackOfInts(4), new FixedCapacityStackOfInts(4), new FixedCapacityStackOfInts(4)
	};

	/** Output the state in visible form. You do not need to modify this method. */
	public static void outputState() {
		StdOut.println();
		for (int stack = 1; stack <= 3; stack++) {
			StdOut.print("Stack" + stack + ": ");

			/* Note how this properly orders the state from left to right? */
			String state = "";
			for (Integer d : stacks[stack-1]) {
				state = d + state;
			}

			StdOut.println(state);
		}
		StdOut.println();
	}

	/**
	 * You must write this method. A move is allowed if the disk from the top of
	 * stack 'fromStack' is smaller in size than the topmost disk on the top of stack 'toStack'.
	 * 
	 * @param fromStack
	 * @param toStack
	 */
	public static boolean move(int fromStack, int toStack) {
		if (stacks[fromStack-1].isEmpty()) { return false; }
		
		// no self-moves
		if (fromStack == toStack) { return false; }
		
		// this is the disk to move
		int toMove = stacks[fromStack-1].pop();
		
		// if moving to a non-empty stack, must check that we can make the move
		if (!stacks[toStack-1].isEmpty()) {
			// make sure toMove is smaller than exist
			int exist = stacks[toStack-1].pop();
			stacks[toStack-1].push(exist);

			// invalid move; must put back.
			if (toMove > exist) {
				stacks[fromStack-1].push(toMove);
				return false;
			}
		}
		
		// move checks out!
		stacks[toStack-1].push(toMove);
		return true;
	}
	
	public static void main(String[] args) {
		/* Load up four disks on disk 1. */
		stacks[0].push(4);
		stacks[0].push(3);
		stacks[0].push(2);
		stacks[0].push(1);

		int numMoves = 0;
		while (!stacks[0].isEmpty() || !stacks[1].isEmpty()) {
			outputState();
			StdOut.println ("Enter two disk numbers A B to move top disk on A to B. You win when all disks are on Stack 3.");
			int from = StdIn.readInt();
			int to = StdIn.readInt();
			
			System.out.println("Moving top disk from stack " + from + " to stack " + to);
			if (!move(from, to)) {
				StdOut.println ("Error: That move is not allowed!");
			} else {
				numMoves++;
			}
		}
		StdOut.println ("Congratulations! You completed the puzzle in " + numMoves + " moves.");
	}
}
