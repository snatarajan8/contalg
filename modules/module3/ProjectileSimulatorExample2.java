public class ProjectileSimulatorExample2 {

    public static void main (String[] argv)
    {
        // Make a new simulator object.
        ProjectileSimulator proSim = new ProjectileSimulator ();

        // We want to plot d vs. t along x-axis.
        Function dist = new Function ("distance");
        Function xvel = new Function("X velocity");
        double delta = 0.01;

        for (double t=0.1; t<=2.3; t+=0.1) {
            // mass=1, angle=37, initVel=20, s=0.01
            proSim.run (1, 37, 20, t, 0.0001);
            // After the simulation is run, get the final x-value.
            double x = proSim.getX ();
            proSim.run (1, 37, 20, t+delta, 0.0001);
            double deltaX = proSim.getX();
            dist.add (t, x);
            double v = (deltaX - x)/delta;
            xvel.add(t,v);
        }

        // Display.
//        dist.show ();
        Function.show(dist,xvel);
    }

}
    