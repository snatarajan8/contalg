
public class DubinCarControllerTest {
    public static void main(String[] argv) {
        // Build an instance of the simulator. Note: no obstacles in this example.
        // The constructor: isAccel=false
        DubinCarSimulator simulator = new DubinCarSimulator(false);
        simulator.init(50, 50, 0, null);

        // This example moves the car from (50,50) to (550,50).
        double x = 50;

        while (x < 550) {

            // Pass the controls (v1=10, v2=10) to the simulator. DeltaT=0.1.
            simulator.nextStep(10, 10, 0.1);

            // Now extract the new location and other info.
            double t = simulator.getTime();
            x = simulator.getX();
            double y = simulator.getY();

            // Display.
            System.out.println("t=" + t + " x=" + x + " y=" + y);

        } //end-while

    }
}
