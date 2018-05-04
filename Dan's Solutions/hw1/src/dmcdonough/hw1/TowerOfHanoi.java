package dmcdonough.hw1;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Standard Tower of Hanoi disk problem (https://en.wikipedia.org/wiki/Tower_of_Hanoi).
 */
public class TowerOfHanoi {

	/** Three separate stacks, each to hold up to four disks. Leave this alone. */
	static FixedCapacityStackOfInts stacks[] = new FixedCapacityStackOfInts[] {
		new FixedCapacityStackOfInts(4), new FixedCapacityStackOfInts(4), new FixedCapacityStackOfInts(4)
	};

	/** 
	 * Output the state in visible form. Modify this method.
	 * 
	 * You need to modify this method so it outputs state as follows:
	 * 
	 * Stack1: 4321
	 * Stack2:
	 * Stack3:
	 * 
	 * If, given this above state, you make a move from stack1 to stack3, then the output would be:
	 * 
	 * Stack1: 432
	 * Stack2:
	 * Stack3: 1
	 */
	public static void outputState() {
		//for each stack...
		for (int i = 0; i <= 2; i++) {
			//make a cache to fit vars in
			FixedCapacityStackOfInts cache = new FixedCapacityStackOfInts(4);
			StdOut.print("Stack" + (i+1) + ": ");	
			//copy stack values into cache by popping
			for (int j : stacks[i]) {
				cache.push(j); 
				}
			String stack_content = "";
			//reverse the content of the string
			for (int k : cache) {
				stack_content += k;
				}
			StdOut.println(stack_content);
		}
	}

	/**
	 * You must write this method. A move is allowed if the disk from the top of
	 * stack 'from' is smaller in size than the topmost disk on the top of stack 'to'.
	 * 
	 * @param from   stack containing the topmost disk to move
	 * @param to     stack representing the destination disk
	 */
	public static boolean move(int from, int to) {
		if (from == to) {
			return false;
		}
		else if (from < 1 || from > 3) {
			return false;
		}
		else if (to < 1 || to > 3) {
			return false;
		}
		else if(stacks[from-1].isEmpty()) {
			return false; 
		}
		else if(stacks[to-1].isFull()) {
			return false;
		}
		
		else {
		//get the int the move
		int from_int = stacks[from-1].pop(); 
		
		//if the to stack is not empty check if top in is less than the top of the to stack 
		if (!stacks[to-1].isEmpty()) {
			int to_int = stacks[to-1].pop();
			stacks[to-1].push(to_int);
			if (to_int > from_int) {
				stacks[to-1].push(from_int); //push in to pile
				return true;	
			}
			else {
				stacks[from-1].push(from_int); 
				return false;
			}
		}else {
			//if to stack is empty just push
			stacks[to-1].push(from_int); //push in to pile
			return true;
		}
		}
	}
	
	/** You do not need to modify this method. */
	public static void main(String[] args) {
		/* Load up four disks on stack 1, so disk-4 is at the bottom and disk-1 is at the top. */
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
				StdOut.println ("That move is not allowed!");
			} else {
				numMoves++;
			}
		}
		
		StdOut.println ("Congratulations! You completed the puzzle in " + numMoves + " moves.");
	}
}