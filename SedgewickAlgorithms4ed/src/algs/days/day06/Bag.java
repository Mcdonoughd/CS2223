package algs.days.day06;


public class Bag<Item> {

	Node first;     // first node in the list (may be null)

	class Node {
		Item    item;
		Node    next;
	}

	public void add (Item item) {
		Node oldfirst = first;

		first = new Node();
		first.item = item;
		first.next = oldfirst;
	}

	/** 
	 * This operation will return a new Bag that contains the
     * remaining N - floor(N/2) elements while modifying the
     * current bag to retain only the first floor(N/2) elements.
     * 
     * The trick is to advance two references/pointers, one two steps at
     * a time, and the other one step at a time. Once the two-step reference
     * is null, we have reached the end of the linked list, and the one-step
     * reference is half the size of the list.
     */
	public Bag cutInHalf() {
		Bag<Item> remainder = new Bag<Item>();
		if (first == null) { return remainder; }      // nothing to cut in half, so empty
		
		if (first.next == null) {
			// we must become empty, while the new Bag must contain the leftover
			remainder.first = first;
			first = null;
			return remainder;
		}
		
		// there are TWO or more items in the list. We can go.
		Node step = first;
		Node twoStep = first.next;
		while (true) {
			// advance twice, leaving loop once falling off edge.
			twoStep = twoStep.next;
			if (twoStep == null) { break; }
			twoStep = twoStep.next; 
			if (twoStep == null) { break; }

			// still more left! we can advance.
			step = step.next;
		}
				
		// when we get here, step points to the floor(N/2) node, so we cut away.
		remainder.first = step.next;
		step.next = null;
		return remainder;
	}
	
	public static void main(String[] args) {
		Bag<String> bag = new Bag<String>();
		bag.add("g");
		bag.add("f");
		bag.add("e");
		bag.add("d");
		bag.add("c");
		bag.add("b");
		bag.add("a");
		
		// quick and dirty explanation
		Bag<String> remainder = bag.cutInHalf();
		System.out.println(bag.first.item);
		System.out.println(bag.first.next.item);
		System.out.println(bag.first.next.next.item);
		System.out.println(bag.first.next.next.next);
		
		System.out.println(remainder.first.item);
		System.out.println(remainder.first.next.item);
		System.out.println(remainder.first.next.next.item);
		System.out.println(remainder.first.next.next.next.item);
		
	}
}
