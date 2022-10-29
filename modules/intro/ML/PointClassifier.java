// PointClassifier.java
// 
// Rahul Simha
// Spring 2008
//
// For explaining point classification.


import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;


// A small class we will use to store info about each point.
class PlotPoint {
    double x,y;
    Color color;
}


public class PointClassifier extends JPanel {

    // GUI stuff.
    JComboBox colorBox;
    String[] colors = {"Red", "Blue", "Black", "Green", "Orange"};
    JTextField xField, yField;
    
    // The data.
    ArrayList<PlotPoint> points = new ArrayList<PlotPoint>();


    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);

        Graphics2D g2 = (Graphics2D) g;

        // Clear.
        Dimension D = this.getSize();
        g.setColor (Color.white);
        g.fillRect (0,0, D.width,D.height);
        
        // Plot points.
        for (PlotPoint p: points) {
            int x = (int) p.x;
            int y = D.height - (int) p.y;
            g.setColor (p.color);
            g.fillOval (x-4,y-4,8,8);
        }
        
        // Plot lines.
        g.setColor (Color.gray);
        // int x1,y1,x2,y2;
        // g.drawLine (x1,y1,x2,y2);
    }
    

    ////////////////////////////////////////////////////////////////////////
    // GUI actions

    void add ()
    {
        try {
            PlotPoint p = new PlotPoint ();
            p.x = Integer.parseInt (xField.getText());
            p.y = Integer.parseInt (yField.getText());
            p.color = getColor ();
            points.add (p);
            this.repaint ();
        }
        catch (NumberFormatException e) {
        }
        this.repaint ();
    }


    Color getColor ()
    {
        String colorStr = (String) colorBox.getSelectedItem ();
        if (colorStr.equals ("Red")) {
            return Color.red;
        }
        else if (colorStr.equals ("Blue")) {
            return Color.blue;
        }
        else if (colorStr.equals ("Black")) {
            return Color.black;
        }
        else if (colorStr.equals ("Green")) {
            return Color.green;
        }
        else if (colorStr.equals ("Orange")) {
            return Color.green;
        }
        else {
            return Color.gray;
        }
        
    }
    

    void reset ()
    {
        points = new ArrayList<PlotPoint>();
        this.repaint ();
    }

    

    ////////////////////////////////////////////////////////////////////////
    // GUI code


    JPanel makeBottomPanel ()
    {
        JPanel panel = new JPanel ();
        
	JButton resetB = new JButton ("Clear");
	resetB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       reset ();
		   }
           }
        );
	panel.add (resetB);

        panel.add (new JLabel ("             "));
        colorBox = new JComboBox (colors);
        panel.add (colorBox);
        panel.add (new JLabel ("  X:"));
        xField = new JTextField (2);
        xField.setText ("");
        panel.add (xField);
        panel.add (new JLabel ("  Y:"));
        yField = new JTextField (2);
        yField.setText ("");
        panel.add (yField);
	JButton addB = new JButton ("Add");
	addB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       add ();
		   }
           }
        );
	panel.add (addB);


        panel.add (new JLabel ("             "));
	JButton quitB = new JButton ("Quit");
	quitB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       System.exit(0);
		   }
           }
        );
	panel.add (quitB);

        return panel;
    }
    

    void makeFrame ()
    {
        JFrame frame = new JFrame ();
        frame.setSize (800, 600);
        frame.setTitle ("Point Classifier");
        Container cPane = frame.getContentPane();
        cPane.add (makeBottomPanel(), BorderLayout.SOUTH);
        cPane.add (this, BorderLayout.CENTER);
        frame.setVisible (true);
    }


    public static void main (String[] argv)
    {
        new PointClassifier().makeFrame();
    }

}
