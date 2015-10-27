package algs.experiment.rank;

public class MaxFinder {
	/** Demonstrate that it works. */
	public static void main(String[] args) throws Exception {
		
		for (int n = 2; n <= 2048; n*= 2) {
			Selection collection = new Selection(n);
			Object largest = collection.get(0);
			for (int i = 1; i < n; i++) {
				if (collection.compare(i, largest) > 0) {
					largest = collection.get(i);
				}
			}
			
			collection.assertKthRank(n-1, largest);
			String ticket = collection.getValidation();
			System.out.println(n + "\t" + ticket);
		}
	}
}
