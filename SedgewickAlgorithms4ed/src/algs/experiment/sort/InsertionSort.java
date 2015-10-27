package algs.experiment.sort;

public class InsertionSort {
	public static void main(String[] args) throws Exception {
		
		int n = 1000;
		Sorter collection = new Sorter(n);
		
		for (int j = 1; j < n; j++) {
			int i = j-1;
			Comparable<Sorter.Item> value = collection.get(j);
			while (i >= 0 && collection.compare(i, value) > 0) {
				collection.set(i+1, collection.get(i));
				i--;
			}
			
			collection.set(i+1, value);
		}
		
		collection.assertSorted();
		String ticket = collection.getValidation();
		System.out.println(ticket);
	}
}
