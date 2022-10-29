
public class BallTossSimulatorExample {

    public static void main (String[] argv)
    {
        BallTossSimulator sim = new BallTossSimulator ();

        double delta = 0.01;
        
        Function dist = new Function ("distance");
        Function accel = new Function ("accel");
        Function vel = new Function ("velocity");

        for (double t=1; t<=10; t+=1) {
            double d = sim.run (t, 50);
            double dd = sim.run (t+delta, 50);
            double ddd = sim.run (t+2*delta, 50);
            double v = (dd-d)/delta;
            double vv = (ddd-dd)/delta;
            double acc = (vv-v)/delta;
            vel.add(t, v);
            dist.add (t, d);
            accel.add(t,acc);
        }

        Function.show(dist,vel,accel);
    }
    
}
