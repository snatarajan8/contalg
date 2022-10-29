import java.util.Random;

public class Histogram2 {

    static int numBins = 10;

    public static void main (String[] argv)
    {
        int numSamples = 10000;
        int n = 5;
//        histogramForUprime (n, numSamples);
//        histogramForVprime (n, numSamples);
//        histogramForWprime (n, numSamples);
        n = 493;
//        histogramForUprime (n, numSamples);
//        histogramForVprime (n, numSamples);
        histogramForWprime (n, numSamples);
//        n = 10000;
//        histogramForUprime (n, numSamples);
//        histogramForVprime (n, numSamples);
//        histogramForWprime (n, numSamples);
    }
    
    static double computeUprime (int n)
    {
	// INSERT YOUR CODE HERE do generate a random
	// number DIFFERENT from RandTool.uniform().
	// Call RandTool.uniform() and do something to it.
        return Math.pow(RandTool.uniform(),2);
    }
    
    static double computeVprime (int n)
    {
        double sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += computeUprime(i);
        }
        return sum / (double) n;
    }

    static double computeWprime (int n)
    {
        // INSERT YOUR CODE HERE. Remember to subtract
	// off whatever Vn appears to be converging to (not 0.5).
        return Math.pow(n, 0.5) * (computeVprime(n) - 0.333333);
    }


    // You don't need to modify any code below this. It's more or less
    // the same as in Histogram.java


    static void histogramForUprime (int n, int numSamples)
    {
        // Generate the data set
	double[] data = new double [numSamples];
        for (int k=0; k<numSamples; k++) {
            double u = computeUprime (n);
	    data[k] = u;
        }

        // Do histogram.
        System.out.println ("Histogram for U'");
	makeHistogram (data);
    }

    static void histogramForVprime (int n, int numSamples)
    {
        // INSERT YOUR CODE HERE.
        // Generate the data set
	double[] data = new double [numSamples];
        for (int k=0; k<numSamples; k++) {
            double v = computeVprime (n);
	    data[k] = v;
        }

        // Do histogram.
        System.out.println ("Histogram for V'");
	makeHistogram (data);
    }


    static void histogramForWprime (int n, int numSamples)
    {
        // INSERT YOUR CODE HERE.
        // Generate the data set
	double[] data = new double [numSamples];
        for (int k=0; k<numSamples; k++) {
            double w = computeWprime (n);
            System.out.println(w);
	    data[k] = w;
        }

        // Do histogram.
        System.out.println ("Histogram for W'");
	makeHistogram (data);
    }
    
    static void makeHistogram (double[] data)
    {
        // Create space: one counter per interval.
        int[] bins = new int [numBins];

	// Find min/max of data.
	double min = Double.MAX_VALUE,  max = Double.MIN_VALUE;
	for (int i=0; i<data.length; i++) {
	    if (data[i] < min) min = data[i];
	    if (data[i] > max) max = data[i];
	}

	// Interval size.
	double interval = (max-min) / numBins;

	// Now count.
	for (int i=0; i<data.length; i++) {
	    double distFromMin = (data[i] - min);
	    int whichBin = (int) Math.floor (distFromMin / interval);
	    if ( (whichBin >= 0) && (whichBin < numBins) ) {
		bins[whichBin] ++;
	    }
	}

        // Print histogram.
        for (int b=0; b<numBins; b++) {
	    // Interval:
	    double leftEnd = min + b*interval;
	    double rightEnd = leftEnd + interval;
            System.out.printf ("  b=%4d: [%5.3f, %5.3f] %6d\n", b, leftEnd, rightEnd, bins[b]);
        }

    }
    
}
