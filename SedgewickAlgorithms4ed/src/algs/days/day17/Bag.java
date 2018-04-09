package algs.days.day17;

import java.util.Iterator;

import edu.princeton.cs.algs4.StdOut;


public class Bag<Item> implements Iterable<Item> {

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


    /** Returns iterator over items in bag. */
    public Iterator<Item> iterator()  { return new ListIterator(first); }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current;

        public ListIterator(Node first)  { current = first;        }
        public boolean hasNext()         { return current != null; }
        public void remove()             {                         }

        public Item next() {
            Item item = current.item;
            current = current.next; 
            return item;
        }
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
		for (String s : bag) {
			StdOut.println(s);
		}
		
	}
}
