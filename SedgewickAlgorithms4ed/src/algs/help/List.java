package algs.help;
import javax.swing.plaf.synth.SynthSeparatorUI;

/**
 * 
 * @param <Item>   Made sure that I can compare items to each other.
 */
public class List<Item extends Comparable<Item>> {
	
	Node head;
	
	class Node {
		public Node (Item it) { this.item = it; }
		Item item;
		Node next;
		public String toString () { return "" + item; }
	}
	
	public List() {
		head = null;
	}
	
	/** 
	 * Given list L=N, return "the tail" of the list (N/2) of them, and truncate the
	 * front (original) so it stores (N/2) of them.
	 * @return
	 */
	public List cutInHalf() {
		Node one = head;
		Node two = head;
		if (head == null) { return new List<Item>(); }
		
		while (two != null) {
			one = one.next;
			
			if (two == null || two.next == null) {
				break;
			}
			
			two = two.next;
			two = two.next;
		}
		
		// head points to the original first node
		// one points to some node ~halfway
		List<Item> backHalf = new List<Item>();
		backHalf.head = one.next;
		one.next = null; // truncate
		return backHalf;
	}
	
	public void output() {
		if (head == null) {
			System.out.println("[]");
		} else {
			Node tmp = head;
			System.out.print("[");
			
			while (tmp != null) {
				System.out.print(tmp.item + " ");
				tmp = tmp.next;
			}
			System.out.println("]");
		
		}
	}
	
	// prepends and result is arbitrarily ordered
	public void add(Item it) {
		Node newNode = new Node(it);
		
		newNode.next = head;
		head = newNode;
	}
	
	public boolean delete(Item it) {
		Node tmp = head;
		
		Node prev = null;
		while (tmp != null) {
			// check if "it" is same item that tmp points to
			if (tmp.item.equals(it)) {
				// FOUND IT! OH and this is the first one in the list
				if (prev == null) {
					head = tmp.next;
				} else {
					// OK, some other node in the list. no worries
					prev.next = tmp.next;
				}
				return true;
			}
			
			prev = tmp;       // don't forget !! I need this
			tmp = tmp.next;   // advance to next one (if it exists)
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		List<Integer> list = new List<Integer>();
		list.output();
		list.add(4);
		list.output();
		list.add(7);
		list.output();
		
		list.delete(3);
		list.output();
		list.add(1);
		list.add(1);
		list.add(1);
		list = new List();
		for (int i = 1; i < 30; i++) {
			list.add(i);
		}
		
		list.output();
		List newOne = list.cutInHalf();
		
		newOne.output();
		list.output();
		
	}
}
