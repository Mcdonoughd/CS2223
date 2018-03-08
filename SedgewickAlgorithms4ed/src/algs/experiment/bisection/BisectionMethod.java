package algs.experiment.bisection;

/**
 * Used to determine the root for a continuous function f(x) given a,b where the signs
 * of f(a) and f(b) are different. This will compute a root contained within the domain [a,b]
 */
public class BisectionMethod {
	
	/** Point at which we are satisfied with root value. If set to 1e-20 compute will fail to terminate. */
	static double threshold = 1e-14;
	
	/**
	 * Use bisection method over the range [a,b] to determine root of f(x) assuming f(a) has different 
	 * sign than f(b).
	 */
	public static double compute (double a, double b, F f) {
		int signA = (int) Math.signum(f.compute(a));
		int signB = (int) Math.signum(f.compute(b));

		if (signA == signB) {
			throw new IllegalArgumentException("f(a) and f(b) do not have opposite signs");
		}

		double c = (a+b)/2;
		while (Math.abs(b-a) > threshold) {
			c = (a+b)/2;

			int signC = (int) Math.signum(f.compute(c));
			if (signA == signC) {
				a = c;
			} else {
				b = c;
			}
		}

		return c;
	}
	
	/** Client to demonstrate it works. */
	public static void main(String[] args) {
		
		/** A sample polynomial function x^3 - 5x^2 - 13. */
		F sample = new F() {
			public double compute(double x) {
				return x*x*x - 5*x*x + 7*x - 13;
			}
			
		};
		
		// -1 and 6
		double root = compute(-1,6,sample);
		System.out.println("f(" + root + ")=" + sample.compute(root));
		
		/** A sample polynomial function x^3 - 5x^2 - 13. */
		F log = new F() {
			public double compute(double x) {
				return Math.log(x);
			}
			
		};
		
		root = compute(0.1,6,log);
		System.out.println("f(" + root + ")=" + log.compute(root));
	}
}
