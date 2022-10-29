
public class InclineSimulatorExample3 {

    public static void main (String[] argv)
    {
        // Make a new instance of the class.
        InclineSimulator sim = new InclineSimulator ();
        InclineSimulatorXY simXY = new InclineSimulatorXY ();

        // Set mass and angle.
        sim.mass = 1;
        sim.angle = 30;
        simXY.mass = 1;
        simXY.angle = 30;
        
        // Measure x(t) = distance moved along x-axis.
        Function dist = new Function ("dist");
        Function distXY = new Function ("distXY");
        Function accXY = new Function ("accXY");
        double delta = 0.01;
        for (double t=1; t<=10; t+=1) {
            double d = sim.run (t);
            double dXY = simXY.run (t);
            dist.add (t, d);
            distXY.add (t, dXY);
            double ddxy = simXY.run(t+delta);
            double vel = (ddxy - dXY)/delta;
            double ddxy2 = simXY.run(t+delta+delta);
            double vel2 = (ddxy2-ddxy)/delta;
            double acc = (vel2-vel)/delta;
            accXY.add(t,acc);

        }
        
        // Display result.
        dist.show ();
        distXY.show ();
        accXY.show();
    }

}
