package algs.hw1;

import edu.princeton.cs.algs4.*;

public class FixedCapacityStackOfStrings  {
    private String[] a;  // holds the items
    private int N;       // number of items in stack

    // create an empty stack with given capacity
    public FixedCapacityStackOfStrings(int capacity) {
        a = new String[capacity];
        N = 0;
    }

    public boolean isEmpty()            {  return N == 0;                    }
    public boolean isFull()             {  return N == a.length;             }
    public void push(String item)       {  a[N++] = item;                    }
    public String pop()                 {  return a[--N];                    }

    public static void main(String[] args) {
        FixedCapacityStackOfStrings stack = new FixedCapacityStackOfStrings(100);
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) stack.push(item); 
            else if (stack.isEmpty())  StdOut.println("BAD INPUT"); 
            else                       StdOut.print(stack.pop() + " ");
        }
        StdOut.println();

        // print what's left on the stack
        StdOut.println("(" + stack.N + " left on the stack)");
    } 
} 