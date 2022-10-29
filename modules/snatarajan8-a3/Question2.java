
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;


class DrawPanel3 extends JPanel {

    double t = 0;
    double h = 1200;
    double v = 0;
    double k = 0;
    double a = 9.8 - k*v;
    double delT = 0.1;

    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);

        // Clear.
        Dimension D = this.getSize();
        g.setColor (Color.white);
        g.fillRect (0,0, D.width,D.height);

        g.setColor (Color.red);
        int y = (int) h;
        int x = D.width/2;
        g.fillOval (x,y, 10, 10);

    }


    void go ()
    {
        t = 0;
        h = this.h;
        v = 100;
        Thread t = new Thread (() -> animate());
        t.start();
    }


    void animate ()
    {
        Function V = new Function ("Speeder");
        Function D = new Function ("Cop");
        while (d2 < d+1) {
            t = t + delT;
            v2 = v2 + delT * a;
            d2 = d2 + delT * v2;
            d = d + delT * v;
            V.add (t, d);
            D.add (t, d2);
            this.repaint ();
            sleep ();
            if (d2 > d) {
                System.out.println("The cop's distance is - " + d2);
                System.out.println("The cop's velocity is - " + v2);
                System.out.println("The time is - " + t);
            }
        }
        Function.show (V,D);
    }

    void sleep ()
    {
        try {
            Thread.sleep (50);
        }
        catch (InterruptedException e){
            System.out.println (e);
        }
    }

}




public class Question2 extends JFrame {

    DrawPanel drawPanel;

    public Question2 ()
    {
        this.setSize (500,200);
        Container cPane = this.getContentPane();
        drawPanel = new DrawPanel ();

        JPanel panel = new JPanel ();
        JButton resetB = new JButton ("Go");
        resetB.addActionListener (a -> drawPanel.go());
        panel.add (resetB);
        panel.add (new JLabel("    "));
        JButton quitB = new JButton ("Quit");
        quitB.addActionListener (a -> System.exit(0));
        panel.add (quitB);
        cPane.add (panel, BorderLayout.SOUTH);
        cPane.add (drawPanel, BorderLayout.CENTER);
        this.setVisible (true);
    }


    public static void main (String[] argv)
    {
        new Question2 ();
    }


}
