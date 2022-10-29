import java.util.*;
import java.awt.geom.*;
import java.awt.*;

public class Snatarajan8CarController implements CarController {

    double acc; // Acceleration.
    double vel; // Velocity.
    double phi; // Steering angle.
    double mu1=0, mu2=1;
    ArrayList<Rectangle2D.Double> obstacles;
    SensorPack sensors;

    @Override
    public void init(double initX, double initY, double initTheta, double endX, double endY, double endTheta, ArrayList<Rectangle2D.Double> obstacles, SensorPack sensors) {
        this.obstacles = obstacles;
        this.sensors = sensors;
    }

    @Override
    public void draw(Graphics2D g2, Dimension D) {

    }

    @Override
    public void move() {
        double x = sensors.getX();
        double y = sensors.getY();
        double theta = sensors.getTheta();
        double time = sensors.getTime();

//        this.vel = 10;
//        this.phi = 0;
        this.mu1 = 0;
        this.mu2 = 1;

        if (time < 0.3) {
            // turn left if facing straight
            this.mu1 = 0;
            this.mu2 = 10;
        } else if (time >= 0.9 && time < 2) {
            // turn right
            this.mu1 = 10;
            this.mu2 = 10;
        } else if (time > 2 && time < 4.7) {
            //turn right
            this.mu1 = 10;
            this.mu2 = 8.5;
//        } else if (time >= 8 && time < 11) {
//            this.mu1 = 9;
//            this.mu2 = 10
        } else {
            //check if straight, otherwise straighten
            this.mu1 = 10;
            this.mu2 = 10;
        }
    }

    @Override
    public double getControl(int i) {
        if (i == 1) {
            return mu1;
        }
        else if (i == 2) {
            return mu2;
        }
        else {
            System.out.println ("ERROR: DubinCarController.getControl(): incorrect input");
            return 0;
        }
    }
}
