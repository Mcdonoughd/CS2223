package algs.problems.symbolTable;

/**
 * API for generic ordered symbol table.
 * 
 * Named after the concept as presented by Sedgewick.
 *
 * @param <K>    type of key objects
 * @param <V>    type of value objects
 */
public interface ISymbolTable<K extends Comparable<K>, V> {
	
	/** Put key-value pair into the table; if val is null then remove. */
	void put (K key, V val);
	
	/** Return value associated with key or null if key is absent. */
	V get (K key);
	
	/** Remove key-value pair from table. */
	void delete (K key);
	
	/** Determine if there is a value paired with key. */
	boolean contains (K key);
	
	/** Determine if table is empty. */
	boolean isEmpty ();
	
	/** Return number of key-value pairs. */
	int size ();
	
	/** Return smallest key. */
	K min ();
	
	/** Return largest key. */
	K max ();
	
	/** Return largest key less than or equal to key. */
	K floor (K key);
	
	/** Return smallest key greater than or equal to key. */
	K ceiling (K key);
	
	/** Return number of keys less than key. */
	int rank (K key);
	
	/** Delete smallest key. */
	void deleteMin();
	
	/** Delete largest key. */
	void deleteMax();
	
	/** Return number of keys in [low .. high]. */
	int size (K low, K high); 

	/** Return keys in [low .. high] in sorted order. */
	Iterable<K> keys (K low, K high);

	/** Return all keys in table, in sorted order. */
	Iterable<K> keys ();
}
