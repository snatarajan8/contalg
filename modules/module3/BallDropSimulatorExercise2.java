
public class BallDropSimulatorExercise2 {

    public static void main (String[] argv)
    {
        BallDropSimulator2 sim = new BallDropSimulator2 ();

        // Drop the ball from a height of 1000.
        double height = 1000;
        sim.run (height);
        
        double finalVelocity = sim.getV ();

        while ( Math.abs (finalVelocity + 200) > 1 ) {
            // Try different heights systematically.
            height += 1;
            sim.run (height);
            finalVelocity = sim.getV ();
        }
        double velocity = 0;
        height = 1000;
        while (Math.min(Math.abs(velocity - finalVelocity), 10) <= 10) {
            height += 1;
            sim.run (height);
            velocity = sim.getV ();
        }

        System.out.println ("Height required: " + height);
        System.out.println ("Midpoint velocity - " + velocity + "\nMidpoint height - " + height);
    }
    
}
