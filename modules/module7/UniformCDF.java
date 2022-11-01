
public class UniformCDF {


    public static void main (String[] argv)
    {
        Function F = makeUniformCDF ();
        Function G = makeUniformDerivative(F);
		System.out.println(F.get(0.75));
		System.out.println(F.get(1));
		System.out.println(F.get(1)-F.get(0.75));
//        F.show ();
        Function.show(F,G);
    }

    static Function makeUniformDerivative(Function F) {
    	Function G = new Function("derivative");
		double a = 0, b = 1;
		int M = 50;                   // Number of intervals.
		double delta = (b-a) / M;

		for (int k=0; k<M; k++) {
			double midPoint = a + k * delta + delta/2;
			G.add (midPoint, (F.get(midPoint + delta) - F.get(midPoint)) / delta);
		}

    	return G;
	}
    static Function makeUniformCDF ()
    {
	double a = 0, b = 1;
	int M = 50;                   // Number of intervals.
	double delta = (b-a) / M;     // Interval size.

	double[] intervalCounts = new double [M];
	double numTrials = 1000000;

	for (int t=0; t<numTrials; t++) {
            // Random sample:
	    double y = RandTool.uniform ();
            // Find the right interval:
            int k = (int) Math.floor ((y-a) / delta);
            // Increment the count for every interval above and including k.
            for (int i=k; i<M; i++) {
		if (i > 0) {
                	intervalCounts[i] ++;
		}
            }
	}

	// Now compute probabilities for each interval.
	double[] cdf = new double [M];
	for (int k=0; k<M; k++) {
	    cdf[k] = intervalCounts[k] / numTrials;
	}

        // Build the CDF. Use mid-point of each interval.
        Function F = new Function ("Uniform cdf");
	for (int k=0; k<M; k++) {
	    double midPoint = a + k * delta + delta/2;
            F.add (midPoint, cdf[k]);
	}

        return F;
    }

}

