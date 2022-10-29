import java.util.*;

public class RickerModel {

    public static void main (String[] argv)
    {
	DrawTool.display ();
	DrawTool.setXYRange (0, 10, 0, 20);
	DrawTool.startAnimationMode ();

	int n = 300;               // number of bins along x, and along r
	int m = 500;               // number of iterations
	double r_lim = 20.0;       // range of r
	double x_lim  = 10.0;      // range of x
	double del_r = r_lim / n;  // To change r in a loop

//	ArrayList<Circle> points = new ArrayList<>();

	double r = 1;
	while (r < r_lim) {
	    // Generate m iterates with x = 1, and bin into x_lim/n bins
	    double[] x_counts = new double [n+1];
	    double x_width = x_lim / n;
	    double x = 1;
	    for (int i=0; i<m; i++) {
		double next_x = r * x * Math.exp(-x);
		if (i > 50) {
		    // Ignore transient
		    int bin_num = (int) (Math.floor(x / x_width)) ;
		    x_counts[bin_num] += 1;
		    x = next_x;
		}
	    }
	    // Plot points for this r
	    for (int b=0; b<n; b++) {
		if (x_counts[b] > 1) {
		    double x_val = b * x_width;
		    DrawTool.drawCircle (x_val, r, 0.01);
		}
	    }

	    // Draw
	    DrawTool.animationPauseNoClear (50);

	    // Next r
	    r += del_r;
	}

    }

}
