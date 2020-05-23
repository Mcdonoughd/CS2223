package algs.days.day05;

/**
 * 
 *  @param <Item>    the type of elements in the Bag
 */
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
}
