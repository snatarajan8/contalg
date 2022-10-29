
public class RandomSequence2 {

    public static void main (String[] argv)
    {
        for (int n=1; n<=1000; n++) {
            System.out.println ("Vn, n=" + n + ": " + computeV(n));
        }
    }

    static double computeV (int n)
    {
      double sum = 0;
      for (int i = 1; i <= n; i++) {
        sum += computeU();
      }
      return sum / (double) n;
    }

    static double computeU ()
    {
        return RandTool.uniform ();
    }

}
