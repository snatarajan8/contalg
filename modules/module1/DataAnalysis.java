
public class DataAnalysis {

    public static void main (String[] argv)
    {
        // Make a Function object.
        Function F = new Function ("40");

        Function G = new Function("41");

        // Put the data in.
        F.add (8.33, 1666.67);
        F.add (22.22, 3666.67);
        F.add (23.61, 4833.33);
        F.add (30.55, 5000);
        F.add (36.81, 5166.67);
        F.add (47.22, 8000);
        F.add (69.44, 11333.33);
        F.add (105.56, 19666.67);

        G.add (1.0, 1);
        G.add (2.0, 13);
        G.add (3.0, 33);
        G.add (4.0, 61);
        G.add (5.0, 97);

        // Display it.
        F.show ();
        G.show();
    }

}
