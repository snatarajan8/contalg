
public class UnknownFunctionDerivative {

    public static void main (String[] argv)
    {
        // Make an instance of the object.
//        Simulator S = new Simulator ();

        double d = 0.01;

        for (double x=0; x<=10; x+=1) {
            // Compute derivative at x.
            double f = Math.pow(x,3)/3;
            double fd = Math.pow(x+d,3)/3;
            double g = (fd - f) / d;
            System.out.println ("x=" + x + "  g(x)=" + g);
        }

    }

}
