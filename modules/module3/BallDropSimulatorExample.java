
public class BallDropSimulatorExample {

    public static void main (String[] argv)
    {
        BallDropSimulator sim = new BallDropSimulator ();
        
        Function dist = new Function ("distance");
        Function vel = new Function("velocity");

        for (double t=1; t<=10; t+=1) {
            double delta = 0.01;
            double d = sim.run (t, 1000);
            double ddelta = sim.run(t+delta, 1000);
            dist.add (t, d);
            vel.add(t, (ddelta-d)/delta);
        }
        
//        dist.show ();
        Function.show(dist, vel);
    }
    
}
