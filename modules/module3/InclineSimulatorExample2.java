
public class InclineSimulatorExample2 {

    public static void main (String[] argv)
    {
        // Make a new instance of the class.
        InclineSimulator sim = new InclineSimulator ();

        // Set mass and angle.
        sim.mass = 1;
        sim.angle = 30;
        
        // Measure x(t) = distance moved along x-axis.
        Function dist = new Function ("dist");
        Function xvel = new Function("X velocity");
        Function ydist = new Function("ydist");
        Function yvel = new Function("Y velocity");

        double delta = 0.01;
        for (double t=1; t<=10; t+=1) {
            sim.run (t);
            double x = sim.getX ();
            dist.add (t, x);
            double y = sim.getY();
            ydist.add(t, y);
            sim.run(t+delta);
            double v = (sim.getX()-x)/delta;
            xvel.add(t,v);
            double yv = (sim.getY()-y)/delta;
            yvel.add(t,yv);
        }
        
        // Display result.
//        dist.show ();
        Function.show(dist,xvel,ydist,yvel);
    }

}
