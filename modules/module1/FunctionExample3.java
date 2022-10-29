
import java.util.*;

public class FunctionExample3 {

    public static void main (String[] argv)
    {
        // Make a Function object and give it a name.
        Function F = new Function ("3x^2 + 5");
        Function G = new Function ("x^2 - 2");
        Function H = new Function ("5/x^2");
        Function I = new Function ("e^(-2x)");

        for (double x=0.5; x<=10; x=x+1) {
            // Feed the x,f(x) combinations into the object.
            F.add (x, 3*(Math.pow(x,2))+5);
            G.add (x, Math.pow(x,2) - 2);
            H.add (x, 5/Math.pow(x,2));
            I.add (x, Math.exp(-2*x));
        }

        // Write to screen.
        System.out.println (F);

        // Display.
        Function.show(F,G,H,I);
    }

}
