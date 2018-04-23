package dmcdonough.hw4.question3;

import dmcdonough.hw4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import dmcdonough.hw4.question3.Graph;

public class Question3 {
	/** Each location gets its own unique id, mapped using the symbol table. */
	static SeparateChainingHashST<String, Integer> table = new SeparateChainingHashST<>();
	
	/** Each unique ID gets mapped to a location. This is the inverse map of table. */
	static SeparateChainingHashST<Integer,String> reverse = new SeparateChainingHashST<>();

	
	
	public static void main(String[] args) {
		//enter home into the hashmaps
		table.put("home", 0);
		reverse.put(0, "home");
		//prtin out the first statement
		StdOut.println ("Enter locations on a single line, separated by spaces. Note 'home' is special and does not need to be included in list.");
		//read an input line
		String Nodes = StdIn.readLine();
		//trim the input
		String[] Node_array = Nodes.trim().split("\\s+");
		
		int i = 0;
		//for each input node add it to the hashmaps with the value being it's id
		for(i = 0;i<Node_array.length;i++) {
			//check if it already exists
			if(table.contains(Node_array[i])) {
				StdOut.println(Node_array[i] +" already exists.");
			}else {
				table.put(Node_array[i], i+1);
				reverse.put(i+1, Node_array[i]);
			}
		}
		//Make the graph
		Graph graph = new Graph(i+1);
		
		//now enter connections
		StdOut.println ("Please Enter the connections... Enter 0 when done");
		String input = "";
		//check if input is 0 to stop the program
		while(!input.contains("0")) {
			//read input
			input = StdIn.readLine();
			String[] connections = input.trim().split("\\s+");
			//check valid input
			if(connections.length != 2) {
				StdOut.println("Please input 2 nodes");
			}
			//check for valid node input
			else if(!table.contains(connections[0]) || !table.contains(connections[1])){
				StdOut.println("Please input valid nodes");
			}
			//add edges
			else {
				
				int A = table.get(connections[1]);
				int B = table.get(connections[0]);
				
				graph.addEdge(A, B);
			}
		}
		StdOut.println(graph.toString());
		
		//check if graph complete or not?
		//do a breath first search from home node (0)
		DepthFirstSearch Search = new DepthFirstSearch(graph,0);
		 
		
		int length = Search.getMarked().length;
		boolean[] list_of_nodes = Search.getMarked();
		boolean canvisit_all = true;
		for(int k=0;k<length;k++) {
			//System.out.println(list_of_nodes[k]);
			if(list_of_nodes[k] == false) {
				canvisit_all = false;
				StdOut.println("Can't get from 'home' to " + reverse.get(k));
			}
		}
		if(canvisit_all) {
			StdOut.println("Can get everywhere!");
		}
		
		
		
		
		return;
	}
}
