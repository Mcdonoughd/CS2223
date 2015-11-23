package algs.solutions.hw3;

/**
 * This is a specially designed class to use with HW3.
 * 
 * It stores a String with an Integer. It sort in reverse order by the integer value, and then in 
 * ascending order by the string. USE THIS CLASS AS IS.
 * 
 */
public class WordPair implements Comparable<WordPair> {
	
	String    word;
	Integer   count;

	public WordPair(String  v1, Integer  v2) {
		this.word = v1;
		this.count = v2;
	}
	
	/** 
	 * This method first compares in reverse order by count, and then in ascending order by word.
	 *  
	 * @param d
	 * @return
	 */
	public int compareTo(WordPair other) {
		// first determine comparison by descending count frequency
		int rc = count.compareTo(other.count);
		if (rc > 0) { return +1; }
		if (rc < 0) { return -1; }
		
		// then determine comparison by ascending word
		return other.word.compareTo(word);
	}
}