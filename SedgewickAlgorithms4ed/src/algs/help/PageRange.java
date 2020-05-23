package algs.help;

/**
 * Receive page numbers IN ASCENCING ORDER
 * @author heineman
 *
 */
public class PageRange {
	
	Node head;
	Node tail;
	
	class Node {
		int low;
		int high;
		Node next;

		public Node (int n) { 
			low = high = n;
		}

		public boolean isNeighbor (int n) {
			return (high+1 == n);
		}
		
		void expand () {
			high++;
		}
		
		public String toString() { return low + "-" + high; }
	}
	
	public PageRange () {
		head = tail = null;
	}
	
	public static void main(String[] args) {
		PageRange pr = new PageRange();
		pr.addPage(1);
		pr.addPage(2);
		pr.addPage(3);
		pr.output();
		pr.addPage(5);
		pr.output();
	}
	
	
	// Must be able to add page independent of the number of pages already in the range....
	public void addPage(int page) {
		if (head == null) {
			head = new Node(page);
			tail = head;
		} else {
			// Truth: tail is not null
			// Truth: pages are coming to be in ascending order..
			if (tail.isNeighbor(page)) {
				tail.expand();  // grab it and add to existing tail
			} else {
				Node newNode = new Node(page);
				tail.next = newNode;
				tail = tail.next;
			}
		}
	}
	
	public void output() {
		Node tmp = head;
		while (tmp !=null) {
			System.out.print(tmp);
			
			tmp = tmp.next;
		}
		System.out.println();
	}
}
