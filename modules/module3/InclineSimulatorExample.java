
public class InclineSimulatorExample {

    public static void main (String[] argv)
    {
        // Make a new instance of the class.
        InclineSimulator sim = new InclineSimulator ();

        // Set mass and angle.
        sim.mass = 1;
        sim.angle = 30;
        
        // We'll measure distance travelled at t=1, t=2, ... and put
        // these values into a Function object.
        Function dist = new Function ("dist");
        Function accel = new Function("accel");

        for (double t=1; t<=10; t+=1) {
            double delta = 0.01;
            double d = sim.run (t);
            double dd = sim.run(t+delta);
            double acc = (dd - d)/delta;
            System.out.println(acc + " is the acceleration.");
            dist.add (t, d);
            accel.add(t,acc);
            System.out.println ("t=" + t + "  d=" + d);
        }
        
        // Display result.
        Function.show(dist,accel);
    }

}
