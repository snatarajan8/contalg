
public class ProjectileSimulatorExample {

    public static void main (String[] argv)
    {
        // Make a new simulator object.
        ProjectileSimulator sim = new ProjectileSimulator ();

        // We want to plot d vs. t
        Function dist = new Function ("distance");
        Function vel = new Function ("velocity");
        Function acc = new Function ("acceleration");

        double delta = 0.01;

        for (double t=0.1; t<=2.3; t+=0.1) {
            // mass=1, angle=37, initVel=20, s=0.01
            sim.run (1, 70, 20, t, 0.0001);
            dist.add (t, sim.getD());
            double d = sim.getD();
            sim.run (1, 70, 20, t+delta, 0.0001);
            double dd = sim.getD();
            sim.run (1, 70, 20, t+delta*2, 0.0001);
            double ddd = sim.getD();
            double v = (dd-d)/delta;
            double vv = (ddd-dd)/delta;
            double accel = (vv-v)/delta;
            vel.add(t, v);

            acc.add(t,accel);
        }

        // Display.
       Function.show(vel, acc, dist);
    }

}
