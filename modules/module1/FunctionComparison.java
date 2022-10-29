
public class FunctionComparison {

    public static void main (String[] argv)
    {
        // Make two objects, one for each function.
        Function F = new Function ("3x+5");
        Function G = new Function ("3x+10");

        // Generate 100 values in the range [0,10]
        for (double x=0; x<=10; x+=0.2) {
            F.add (x, 3*x+5);
            G.add (x, 4*x+5);
        }

        // Display both together.
        Function.show (F, G);
    }

}
