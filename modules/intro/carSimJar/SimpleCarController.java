import java.util.*;
import java.awt.geom.*;
import java.awt.*;


public class SimpleCarController implements CarController {

    // The two controls: either (vel,phi) or (acc,phi)
    double acc; // Acceleration.
    double vel; // Velocity.
    double phi; // Steering angle.

    ArrayList < Rectangle2D.Double > obstacles;
    SensorPack sensors;

    // Is the first control an accelerator?
    boolean isAccelModel = false;


    public void init(double initX, double initY, double initTheta, double endX, double endY, double endTheta, ArrayList < Rectangle2D.Double > obstacles, SensorPack sensors) {
        this.obstacles = obstacles;
        this.sensors = sensors;
    }


    public double getControl(int i) {
        if (i == 1) {
            if (isAccelModel) {
                return acc;
            } else {
                return vel;
            }
        } else if (i == 2) {
            return phi;
        }
        return 0;
    }


    public void move() {
        // This is where you adjust the control values.
        // if (isAccelModel) {
        //   this.acc = this.getControl(1)
        //   this.vel += this.acc
        // } else {
        //   this.vel = this.getControl(1)
        // }
        //
        // this.phi = this.getControl(2)

        // this.acc = 0
        this.vel = 10;
        System.out.println(this.obstacles.toString());
        this.phi = 0;
    }

    public void draw(Graphics2D g2, Dimension D) {
        // If you want do draw something on the screen (e.g., a path)
        // this is where you do it. Remember to convert to Java coordinates:
        //    yJava = D.height - y;
    }

}
