
public class FunctionComparison2 {

    public static void main (String[] argv)
    {
        // Initialize sum.
        double distance = 0;
        
        // Generate 50 values in the range [0,10]
        for (double x=0; x<=10; x+=0.1) {
//            double f = 3*x + 5;
//            double g = 3*x + 10;
//            double h = 20;
//            double diff = g - h;
//            distance = distance + diff;
            distance += 3*x-10;
        }

        System.out.println ("Distance: " + distance);
    }

}
