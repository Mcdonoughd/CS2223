package algs;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.StringTokenizer;

import algs.sedgewick.*;

/**
 * Helper class to launch programs on your behalf within Eclipse.
 * 
 * Will eventually be able to support loading data from file and writing out to file. I will use
 * this in class, and perhaps it will be useful for you
 * 
 * @author George Heineman
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
	static String[] getArguments(StringTokenizer st, char end[]) {
		ArrayList<String> args = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String s =st.nextToken();
			if (s.equals ("<")) {
				end[0] = '<';
				break;
			} else if (s.equals (">")) {
				end[1] = '>';
				break;
			} else {
				args.add(s);
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
			
			// build up arguments and deal with '<' and '>' but not |
			char end[] = { '\0' };
			Object[] argList = getArguments(st, end);
			if (end[0] == '\0') {
				// execute!
				try {
					Method m = clazz.getDeclaredMethod("main", String[].class);
					m.invoke(null, new Object[] { argList});
				} catch (Exception e) {
					fail (e.getMessage());
				}
			}
		}
	}
}
