
public class IntegrateAccel3 {

    public static void main (String[] argv)
    {
        // First example of using the method:
        double finalVelocity = computeFinalVelocity (4.9, 0, 0, 0.5, 0.1);
        System.out.println ("t=" + 0.5 + "  finalv=" + finalVelocity);


        // Second example:
        finalVelocity = computeFinalVelocity (4.9, 0, 0, 0.7, 0.1);
        System.out.println ("t=" + 0.7 + "  finalv=" + finalVelocity);
    }
    

    public static double computeFinalVelocity (double a, double initialVelocity, double initialTime, double endTime, double s)
    {
        double v = initialVelocity;
        double t = initialTime;

        while (t < endTime) {
            // Update velocity:
            v = v + s * a;
            // Update time:
            t = t + s;
        }

        return v;
    }

}
