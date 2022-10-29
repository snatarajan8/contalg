
import java.util.*;

public class FunctionExample1 {

    public static void main (String[] argv)
    {
        // This is what reads from the keyboard:
        Scanner scanner = new Scanner (System.in);

        // Put out a prompt:
        System.out.print ("Enter x: ");

        while (1 == 1) {
            // Read in a "double" (real) value:
            double x = scanner.nextDouble ();

            // Compute function:
    //        double f = 3*x*x + 5;
            double f = (double) 1 / (x-2);
            // Print result (output):
            System.out.println ("Function value is = " + f);
        }
    }

}
