package algs.days.day03;

import java.util.ArrayList;
import java.util.Optional;

import edu.princeton.cs.algs4.StdOut;

/**
 * Given an arrangement of tracks with a single spur that can hold up to N cars,
 * what is the percentage of impossible arrangements in the final track, and
 * which ones are these?
 * 
 * Check out: https://oeis.org/A000108
 */
public class SingleSpurProblem {

	static boolean print = false;

	static int[] odometer;

	// check if already in odometer, up to but not including max position
	static boolean contains (int t, int max) {
		for (int i = 0; i < max; i++) {
			if (odometer[i] == t) { return true; }
		}
		return false;
	}

	// order of cars
	public static void trial (int [] order) {

	}

	// each test has between N and 2N operations. Each time a car could be 
	// placed on the spur (which requires an additional move later) 
	public static int test(int n, int total) {
		int[] order = new int[n];
		for (int i = 0; i < n; i++) {
			order[i] = i+1;
		}
		
		// possible states
		FixedCapacityStackOfState process = new FixedCapacityStackOfState(n*n); // more than enough
		ArrayList<String> possibleOutputs = new ArrayList<String>();

		// From start state, uncover new potential states made possible by making available moves
		// and then push them back onto the process stack. When a state is popped and it has no
		// more moves, then add its outgoing state to an ArrayList as long as that state doesn't
		// already exist (note: see how useful it would be to have a 'contains' method?.
		State start = new State(order);
		process.push(start);
		while (!process.isEmpty()) {
			State here = process.pop();
			Optional<State> next = here.canMove();

			if (!next.isPresent()) {
				// we have an answer! Make sure no duplicates. Note that I have to 'cheat' a bit here
				// since we have not yet covered a data type that allows for contains.
				if (!possibleOutputs.contains(here.outgoing)) {
					possibleOutputs.add(here.outgoing);
				}
			} else {
				// try each of the possible moves
				// Perhaps we can move a car to the spur
				State ns = next.get();
				if (ns.moveToSpur()) {
					process.push(ns);
				}

				// Perhaps we can move a car right to the outgoing
				State no = here.canMove().get();
				if (no.moveToOutgoing()) {
					process.push(no);
				}

				// Perhaps we can move a car from the spur to the outgoing
				State so = here.canMove().get();
				if (so.moveSpurToOutgoing()) {
					process.push(so);
				}
			}
		}

		if (print) {
			System.out.println("Possible arrangements");
			for (String s : possibleOutputs) {
				System.out.println(s);
			}
		}

		return possibleOutputs.size();
	}

	public static void main(String[] args) {
		int fact = 1;

		StdOut.println("N\tN!\tNumPossible from 123...");
		for (int n = 2; n < 10; n++) {
			fact *= n;
			int numImpossible = test(n, fact);

			StdOut.println(n + "\t" + fact + "\t" + numImpossible);
		}
	}
}
