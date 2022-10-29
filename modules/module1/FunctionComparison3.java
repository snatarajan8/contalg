
public class FunctionComparison3 {

    public static void main (String[] argv)
    {
        // Initialize sum.
        double sum= 0;

        // Generate 50 values in the range [0,10]
        for (double x=0; x<=5; x+=0.01) {
            double f = 3*x + 5;
            double g = 3*x + 10;
            double h = 20;
            sum += Math.abs (f - h);
        }

        // Compute the average distance:
        double distance = sum / 50;

        System.out.println ("Distance f to g: " + distance);
    }

}
