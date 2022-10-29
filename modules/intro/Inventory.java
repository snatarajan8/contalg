// Inventory.java
//
// Rahul Simha
// Spring 2008
//
// Inventory control demo.
//
// Experiment with the following numbers:
//   (1) c=10, m=3, a=6
//   (2) c=10, m=3, a=2
//   (3) c=10, m=3, a=3


import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;



public class Inventory extends JPanel {

    // A separate thread for animation.
    Thread currentThread;  
    boolean isPaused = false;

    // Animation related variables.
    JComboBox animBox;
    String[] animSpeeds = {"slow-animation", "fast-animation"};
    int slowSleepTime = 300;
    int fastSleepTime = 50;
    int sleepTime = slowSleepTime;
    int currentDemandStartAnim;

    // Time increments in steps of 1 (discrete).
    int time;

    // Simulation related variables.
    // Note: Interarrivals are randomly selected from this list.
    int[] demandInterarrivals = {5, 5, 25};
    int[] supplyInterarrivals = {5, 25};
    int nextDemand, nextSupply;  // Time at which the next demand/supply occurs.

    // Output: number of customers who didn't get anything, 
    // and holding time.
    int numLostCust;
    int holdingTime;

    // Problem parameters: capacity of storage.
    int capacity = 10;
    // When to order: as soon as level goes below min.
    int min = 3;
    // How much to order every time min-level is reached.
    int orderAmount = 5;

    // GUI variables.
    JTextField orderField, capField, minField;
    Image stickImage;
    String  topMessage = "";
    Font msgFont = new Font ("Serif", Font.PLAIN, 15);

    // Data structure for the warehouse.
    ArrayList<Integer> queue = new ArrayList<Integer>();


    public Inventory ()
    {
        // Load the image (of a stick figure) and keep it ready.
        Toolkit tk = Toolkit.getDefaultToolkit ();
        stickImage = tk.getImage ("stick.png");
    }
    

    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);

        Graphics2D g2 = (Graphics2D) g;

        // Clear.
        Dimension D = this.getSize();
        g.setColor (Color.white);
        g.fillRect (0,0, D.width,D.height);

        // Draw boundary of storage.
        g2.setColor (Color.black);
        g2.setStroke (new BasicStroke (2.0f));
        int L = 2*D.width/5;
        int R = 3*D.width/5;
        int H = D.height - 10;
        int h = H/capacity - 4;
        Rectangle box = new Rectangle (L,4, (R-L), H);
        g2.draw (box);

        // Draw current elements inside.
        g.setColor (Color.blue);
        for (int i=0; i<queue.size(); i++) {
            int x = L;
            int y = i * (4 + h);
            g.fillRect (x, D.height-y, (R-L), h);
        }

        // Draw demand/supply animation.
        if (currentDemandStartAnim > 0) {
            int incr = (R-L) / 5;
            int x = -10 + currentDemandStartAnim*incr;
            g.fillRect (x,D.height-4-h, (R-L), h);
            g.drawImage (stickImage, x-30, D.height-4-h, 50, 50, this);
            currentDemandStartAnim --;
        }

        if ( (nextSupply >= time) && (nextSupply-time <= 5) ) {
            int incr = (R-L) / 5;
            int x = 10 + 3*D.width/5 + (nextSupply-time)*incr;
            for (int i=0; i<orderAmount; i++) {
                int y = i*(h+4);
                g.fillRect (x,y, (R-L), h);
            }
        }
        
        // Top msg.
        g.drawString (topMessage, 20, 30);
    }
    

    void reset ()
    {
        // All the initialization prior to a simulation run.
        try {
            queue = new ArrayList<Integer>();
            time = 0;
            numLostCust = 0;
            holdingTime = 0;
            orderAmount = Integer.parseInt (orderField.getText());
            capacity = Integer.parseInt (capField.getText());
            min = Integer.parseInt (minField.getText());
            String speedStr = (String) animBox.getSelectedItem ();
            scheduleNextDemand ();
            scheduleNextSupply ();
            if (speedStr.equals ("slow-animation")) {
                sleepTime = slowSleepTime;
            }
            else {
                sleepTime = fastSleepTime;
            }
        }
        catch (NumberFormatException e) {
            capacity = 10;
            orderAmount = 5;
            min = 3;
        }
    }
    

    void go ()
    {
        // Fire off a thread so that Swing's thread isn't used.
        if (isPaused) {
            isPaused = false;
            return;
        }
        
        currentThread = new Thread () {
                public void run () 
                {
                    animate ();
                }
                
        };
        currentThread.start();
    }
    

    void pause () 
    {
        isPaused = true;
    }
    

    void animate ()
    {
        while (true) {

            if (! isPaused) {
                nextStep ();
            }
            
            topMessage = "Time=" + time + "  #Lost-customers=" + numLostCust + " holdingCost=" + holdingTime;
            this.repaint ();
            
            try {
                Thread.sleep (sleepTime);
            }
            catch (InterruptedException e){
                break;
            }
        } 

        topMessage = "Time=" + time + "  #Lost-customers=" + numLostCust + " holdingCost=" + holdingTime;
        this.repaint ();
    }


    void nextStep ()
    {
        time ++;

        if (time == nextDemand) {
            // A demand event has occured.
            // Remove current demand and start count.
            if (queue.size() == 0) {
                // This customer couldn't get anything.
                numLostCust ++;
            }
            else {
                // Recall: we store the current time in the queue.
                int k = queue.remove (0);
                holdingTime += (time-k);
                currentDemandStartAnim = 4;
            }
            scheduleNextDemand ();
            if ( (queue.size() < min) && (nextSupply < time) ){
                // Order supplies.
                scheduleNextSupply ();
            }
        }

        if (time == nextSupply) {
            // Schedule a supply event.
            for (int i=0; i<orderAmount; i++) {
                queue.add (time);
            }
        }
    }
    


    void scheduleNextDemand ()
    {
        // Pick a random interarrival time.
        int k = UniformRandom.uniform (0, demandInterarrivals.length-1);
        nextDemand = time + demandInterarrivals[k];
    }
    

    void scheduleNextSupply ()
    {
        // Pick a random interarrival time.
        int k = UniformRandom.uniform (0, supplyInterarrivals.length-1);
        nextSupply = time + supplyInterarrivals[k];
    }


    JPanel makeBottomPanel ()
    {
        JPanel panel = new JPanel ();
        
        panel.setLayout (new GridLayout (2,1));
        panel.add (makeFirstLayer());
        panel.add (makeSecondLayer());

        return panel;
    }
    

    JPanel makeFirstLayer ()
    {
        JPanel panel = new JPanel ();

        panel.add (new JLabel ("Capacity:"));
        capField = new JTextField (2);
        capField.setText (""+capacity);
        panel.add (capField);
        panel.add (new JLabel ("  Min:"));
        minField = new JTextField (2);
        minField.setText (""+min);
        panel.add (minField);
        panel.add (new JLabel ("  Order-size:"));
        orderField = new JTextField (2);
        orderField.setText (""+orderAmount);
        panel.add (orderField);

        return panel;
    }


    JPanel makeSecondLayer ()
    {
        JPanel panel = new JPanel ();
        
	JButton resetB = new JButton ("Reset");
	resetB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       reset ();
		   }
           }
        );
	panel.add (resetB);

        panel.add (new JLabel ("  "));
	JButton goB = new JButton ("Go");
	goB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       go ();
		   }
           }
        );
	panel.add (goB);

        panel.add (new JLabel ("  "));
	JButton pauseB = new JButton ("Pause");
	pauseB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       pause ();
		   }
           }
        );
	panel.add (pauseB);


        panel.add (new JLabel ("  "));
        animBox = new JComboBox (animSpeeds);
        panel.add (animBox);

        panel.add (new JLabel ("  "));
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
        frame.setSize (1000, 600);
        frame.setTitle ("Inventory Control");
        Container cPane = frame.getContentPane();
        cPane.add (makeBottomPanel(), BorderLayout.SOUTH);
        cPane.add (this, BorderLayout.CENTER);
        frame.setVisible (true);
    }


    public static void main (String[] argv)
    {
        new Inventory().makeFrame();
    }


}
