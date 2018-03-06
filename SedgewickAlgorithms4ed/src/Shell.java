import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import edu.princeton.cs.algs4.*;

/**
 * Helper class to launch programs on your behalf within Eclipse.
 * 
 * Will eventually be able to support loading data from file and writing out to file. I will use
 * this in class, and perhaps it will be useful for you.
 * 
 * You should copy this class into your own workspace into the default package where it can be 
 * run.
 * 
 * Note: The book makes free use of < and > redirects from input and output files. This is 
 * simply not easy to recreate here though I may take a stab at it.
 * 
 * @author George Heineman
 * @version 10/27/2015
 */
public class Shell {
	
	static void fail(String error) {
		StdOut.println();
		StdOut.println(">> Error");
		StdOut.println(">> " + error);
		System.exit(0);
		
	}
	
	/**
	 * Get all arguments. Stop if '<' or '>' observed
	 * @return
	 */
	static String[] getArguments(StringTokenizer st, boolean redirect[]) throws Exception {
		ArrayList<String> args = new ArrayList<String>();
		boolean doneWithArguments = false;
		boolean redirectInput = false;
		boolean redirectOutput = false;
		
		while (st.hasMoreTokens()) {
			String s =st.nextToken();
			if (s.equals ("<")) {
				doneWithArguments = true;
				redirectInput = true;
				redirect[0] = true;
			} else if (s.equals (">")) {
				doneWithArguments = true;
				redirectOutput = true;
				redirect[0] = true;
			} else {
				if (redirectInput) {
					System.setIn(new FileInputStream(new File (s)));
					StdIn.resync();
					redirectInput = false;
				} else if (redirectOutput) {
					System.setOut(new PrintStream(new File (s)));
					StdOut.resync();
					redirectInput = false;
				} else if (!doneWithArguments) {
					args.add(s);
				}
			}
		}
		
		return args.toArray(new String[]{});
	}
	
	
	public static void main(String[] args) {
		
		while (true) {
			StdOut.print ('%'); 
			StdOut.print (' ');
			
			// user can quit with Contrl-Z
			if (!StdIn.hasNextLine()) {
				break;
			}
			
			String cmd = StdIn.readString();
			if (!cmd.equalsIgnoreCase ("java")) {
				fail("");
			}
			
			String name = StdIn.readString();
			Class<?> clazz = null;
			try {
				clazz = Class.forName(name);
			} catch (Exception e) {
				fail ("No such class as '" + name + "'");
			}
			
			String rest = StdIn.readLine();
			StringTokenizer st = new StringTokenizer(rest, " ");
			
			// execute!
			try {
				// build up arguments and deal with '<' and '>' but not |
				boolean redirect[] = { false };
				Object[] argList = getArguments(st, redirect);
				
				// hold onto existing stdin/stdout
				InputStream existInput  = System.in;
				PrintStream existOutput = System.out;
				
				Method m = clazz.getDeclaredMethod("main", String[].class);
				m.invoke(null, new Object[] { argList});

				// if we were involved in any redirection, leave now to avoid 
				// problems with buffering
				if (redirect[0]) {
					System.exit(0);
				}
				
				// get back into proper alignment so we can close down cleanly...
				System.setIn(existInput);
				StdIn.resync();
				System.setOut(existOutput);
				StdOut.resync();
			} catch (InvocationTargetException ite) {
				fail (ite.getTargetException().toString());
			} catch (Exception e) {
				fail (e.getLocalizedMessage());
			}
		}
	}
}
