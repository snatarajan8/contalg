
public class SinCos {

    public static void main (String[] argv)
    {
        // First, some sin examples;
        double s = Math.sin (0);
        System.out.println ("sin(0)=" + s);
        s = Math.sin (Math.PI/2);
        System.out.println ("sin(pi/2)=" + s);
        s = Math.sin (Math.PI/4);
        System.out.println ("sin(pi/4)=" + s);

        // Now some cos examples:
        double c = Math.cos (0);
        System.out.println ("cos(0)=" + c);
        c = Math.cos (Math.PI/2);
        System.out.println ("cos(pi/2)=" + c);
        c = Math.cos (Math.PI/4);
        System.out.println ("cos(pi/4)=" + c);
    }

}
