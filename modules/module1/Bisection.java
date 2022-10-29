// Bisection.java
//
// Rahul Simha
// Spring 2008

public class Bisection {

    public static void main (String[] argv)
    {
        System.out.println ("sqrt(3)=" + bisection (0, 2));
    }

    static double f (double x)
    {
        return x*x - 3;
    }

    static double bisection (double a, double b)
    {
        // Compute mid-point and function value at mid-point.
        double m = (a + b) / 2.0;

        // As long as f(m) <> 0, repeat.
        while (Math.abs (f(m)) > 0.01) {
            if (f(m) * f(a) > 0) {
                a = m;      // Same side as fa => move "a" closer to m.
            }
            else {
                b = m;      // Other side => move "b" closer to m.
            }
            m = (a + b) / 2.0;
        }

        return m;
    }


}
