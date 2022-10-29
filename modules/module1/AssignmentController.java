public class AssignmentController {

    public static void main (String[] argv)
    {
        // Create an instance of Function and add points.
        Function simpleControl = new Function ("Test controller");

        // Add function values.
        simpleControl.add (20, 10);
        simpleControl.add (200, 10);
        simpleControl.add (201, -5);
        simpleControl.add (250, -0);

        // Function does linear interpolation.
        simpleControl.show ();

        // Use this function as the acceleration function.
        AccelCar.animateControl (simpleControl);
    }

}

