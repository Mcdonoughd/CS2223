package algs.days.day26;

/**
 * This structure must support rapid check to see if a board configuration exists. We use 
 * a hash for this purpose, where the key is the configuration of the board and the value
 * is the board together with its current evaluated score.
 * 
 * This structure must also support returning a board whose evaluation is the lowest (ties
 * are arbitrarily broken). We use an AVL balanced tree, where the key is the Integer score
 * and the Value is a linked list of EightPuzzleNodes that all have the same score.
 *
 */
public class OpenStates {
	/** Store all nodes for quick contains check. */
	SeparateChainingHashST<EightPuzzleNode, EightPuzzleNode> hash;

	/** Each node stores a collection of INodes that evaluate to same score. */
	AVL<Integer,LinkedList> tree;

	/** Construct hash to store INode objects. */
	public OpenStates () {
		hash = new SeparateChainingHashST<EightPuzzleNode, EightPuzzleNode>();
		tree = new AVL<Integer,LinkedList>();
	}

	/**
	 * Insert a node.
	 * 
	 * @param n  node to be inserted.
	 */
	public void insert(EightPuzzleNode n) {
		// put into hash for quick contains check later.
		hash.put(n, n);

		// add to AVL tree with proper priority, based on its evaluated score. Note that 
		// all puzzlenodes with same score are stored in the same NODE for easy retrieval
		int score = n.score();
		//System.out.println("add: <" + score + "," + n.toString().substring(0,3) + ">");
		
		LinkedList nodes = tree.get(score);
		if (nodes == null) {
			nodes = new LinkedList();
			nodes.add(n);
			tree.insert(score, nodes);
		} else {
			nodes.add(n);
		}
	}

	/** 
	 * Remove and return EightPuzzleNode with minimum score value
	 */
	public EightPuzzleNode getMinimum() {
		int minScore = tree.minimum();
		LinkedList states = tree.get(minScore);
		
		EightPuzzleNode best = states.pop();
		hash.delete(best);   // no longer considered contained by our state

		if (states.isEmpty()) {
			tree.fastDelete(minScore);
		} 
		
		//System.out.println("get: <" + minScore + "," + best.toString().substring(0,3) + ">");
		return best;
	}


	/**
	 * Remove actual entry from the set.
	 * <p> 
	 * The INode passed in must be an actual value returned by {@link INodeSet#contains(INode)}.
	 * 
	 * @see INodeSet#remove(INode)
	 * @param n   the node representing the entry to be removed.
	 */
	public boolean remove(EightPuzzleNode n) {
		boolean rc = hash.contains(n);
		hash.delete(n);
		if (!rc) { return false; }

		// remove from BT
		int score = n.score();
		LinkedList states = tree.get(score);
		states.remove(n);
		if (states.isEmpty()) {
			tree.fastDelete(score);
		}

		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#isEmpty()
	 */
	public boolean isEmpty() {
		return hash.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * @see algs.model.searchtree.INodeSet#size()
	 */
	public int size() {
		return hash.size();
	}


	/**
	 * Key step: See if existing state is already in our open set. If so, return the one from open state because
	 * new one might have a lower score. 
	 */
	public EightPuzzleNode contains(EightPuzzleNode n) {
		if (!hash.contains(n)) { return null; }
		return n;
	}
}