
public class IntegrateAccel {

    public static void main (String[] argv)
    {
        // a(t) = 4.9 (constant acceleration).
        double a = 4.9;

        // Initial velocity.
        double v = 0;

        // Our small "change" interval 
        double s = 0.1;

        // Start and end time.
        double t = 0;
        double endTime = 0.5;

        while (t < endTime) {
            // Print current time and velocity:
            System.out.println (">> t=" + t + " v=" + v);
            // Update velocity:
            v = v + s * a;
            // Update time:
            t = t + s;
        }

        System.out.println ("Final: t=" + t + "  v=" + v);
    }

}
