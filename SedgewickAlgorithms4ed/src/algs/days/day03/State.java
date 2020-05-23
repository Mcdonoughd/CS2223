package algs.days.day03;

import java.util.Optional;

import algs.hw1.FixedCapacityStackOfInts;

/**
 * Represents the state of a railroad-track interchange with an incoming stack of cars, a spur stack for cars to be stashed away,
 * and a final outgoing ordering of the initial cars.
 * 
 */
public class State {
	final int N;
	final FixedCapacityStackOfInts incoming;
	final FixedCapacityStackOfInts spur;
	
	/** Just add to the left. */
	String outgoing;
	
	// order is from left to right, so we have to push in reverse order
	public State (int [] order) {
		N = order.length;
		incoming = new FixedCapacityStackOfInts(N);
		for (int i = order.length-1; i >= 0; i--) {
			incoming.push(order[i]);
		}
		
		outgoing = "";
		
		// empty spur
		spur = new FixedCapacityStackOfInts(N);
	}
	
	/** Private constructor for copying. */
	private State (int n, FixedCapacityStackOfInts in, FixedCapacityStackOfInts sp, String out) {
		N = n;
		this.incoming = new FixedCapacityStackOfInts(N);
		String stash = "";
		for (int iv : in) { stash = iv + stash; }
		for (int i = 0; i < stash.length(); i++) { incoming.push(stash.charAt(i) - '0'); }

		stash = "";
		this.spur = new FixedCapacityStackOfInts(N);
		for (int iv : sp) { stash = iv + stash; }
		for (int i = 0; i < stash.length(); i++) { spur.push(stash.charAt(i) - '0'); }
		
		this.outgoing = out;
	}
	
	public Optional<State> canMove() {
		if (!incoming.isEmpty() || !spur.isEmpty()) {
			return Optional.of(new State(N, incoming, spur, outgoing));
		} else {
			return Optional.empty();
		}
		
	}
	
	public boolean moveToSpur() {
		if (!incoming.isEmpty()) {
			int car = incoming.pop();
			spur.push(car);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean moveToOutgoing() {
		if (!incoming.isEmpty()) {
			int car = incoming.pop();
			outgoing = car + outgoing;
			return true;
		} else {
			return false;
		}
	}	
	
	public boolean moveSpurToOutgoing() {
		if (!spur.isEmpty()) {
			int car = spur.pop();
			outgoing = car + outgoing;
			return true;
		} else {
			return false;
		}
	}

}
