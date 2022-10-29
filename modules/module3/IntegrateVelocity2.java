
public class IntegrateVelocity2 {

    public static void main (String[] argv)
    {
        // No need to compute velocity function.

        // Compute distance at t=5:
        double finalDistance = computeFinalDistance (0, 0, 0, 5, 0.01);
        System.out.println ("t=5  d=" + finalDistance);

        // Compute distance at t=10:
        finalDistance = computeFinalDistance (0, 0, 0, 10, 0.01);
        System.out.println ("t=10  d=" + finalDistance);
    }
    

    public static double computeFinalDistance (double  initialVelocity, double initialDistance, double initialTime, double endTime, double s)
    {
        // Constant acceleration.
        double a = 4.9;               

        // Set initial values.
        double v = initialVelocity;    
        double d = initialDistance;
        double t = initialTime;

        while (t < endTime) {
            // First compute velocity:
            v = v + s * a;
            // Then, update distance:
            d = d + s * v;
            // Update time:
            t = t + s;
        }

        return d;
    }

}
