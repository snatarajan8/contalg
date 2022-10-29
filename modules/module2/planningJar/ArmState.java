
import java.util.*;
import java.awt.*;
import java.awt.geom.*;


// A little helper class to store x,y values.
class Node {
    int ID;    
    double x,y;
}


public class ArmState extends State {

    // Note: for comparisons, we're using something that's larger
    // than the tolerance.
    double epsilon = 1;
    
    // Store node instances.
    ArrayList<Node> nodes;

    ArmState parent;

    // The arm parameters.
    int numNodes;
    double linkSize;
    

    public ArmState (int numNodes, double linkSize)
    {
        this.linkSize = linkSize;
        this.numNodes = numNodes;
        nodes =  new ArrayList<Node> (numNodes);
        for (int i=0; i<numNodes; i++) {
            nodes.add (new Node());
        }
    }
    

    public void setNode (int n, double x, double y)
    {
        Node node = nodes.get(n);
        node.x = x;
        node.y = y;
    }
    

    public double getX (int n)
    {
        Node node = nodes.get(n);
        return node.x;
    }


    public double getY (int n)
    {
        Node node = nodes.get(n);
        return node.y;
    }
    

    public State getParent ()
    {
        return parent;
    }
    

    public boolean equals (Object obj)
    {
        if (! (obj instanceof ArmState) ) {
            return false;
        }
        
        ArmState a = (ArmState) obj;
        for (int i=0; i<numNodes; i++) {
            Node node = nodes.get(i);
            Node aNode = a.nodes.get(i);
            if ( (Math.abs(node.x - aNode.x) > epsilon) || 
                 (Math.abs(node.y - aNode.y) > epsilon) ) {
                return false;
            }
        }
        
        return true;
    }
    

    public String toString ()
    {
        String str = "ArmState: [";
        for (int i=0; i<numNodes; i++) {
            Node node = nodes.get(i);
            str += " (" + node.x + "," + node.y + ")";
        }
        str += "]";
        return str;
    }



    // How far is (x,y) from the tip of the arm?

    public double tipDistance (double x, double y)
    {
        return distance (x,y, getX(numNodes-1), getY(numNodes-1));
    }
    

    double distance (double x1, double y1, double x2, double y2)
    {
	return Math.sqrt ( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) );
    }

}
