package algs.days.day26.search;

/** Linked list of EightPuzzleNodes. Must support insert and remove. */
public class LinkedList {
	class Node {
		EightPuzzleNode state;
		Node            next;
		
		public Node (EightPuzzleNode s) {
			this.state = s;
		}
	}
	
	Node first = null;
	int size = 0;
	
	public void add(EightPuzzleNode state) {
		if (first == null) {
			first = new Node(state);
		} else {
			Node f = new Node(state);
			f.next = first;
			first = f;
		}
		
		size++;
	}
	
	public void remove (EightPuzzleNode state) {
		Node prev = null;
		Node t = first;
		while (t != null) {
			if (t.state.equals(state)) {
				if (prev == null) {
					first = first.next;
				} else {
					prev.next = t.next;
				}
				size--;
				if (size == 0) {
					return;
				}
				return;
			}
			
			prev = t;
			t = t.next;
		}
	}
	
	public EightPuzzleNode pop() {
		if (first == null) { return null; }
		
		EightPuzzleNode state = first.state;
		first = first.next;
		size--;
		if (size == 0) {
			return state;
		}
		return state;
	}

	public String toString() {
		if (first == null) { return "[]"; }
		
		String s = "[";
		
		Node n = first;
		while (n != null) {
			s = s + n.state.toString() + ",";
			n = n.next;
		}
		
		return s + "]";
	}
	
	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return size;
	}
	
}
