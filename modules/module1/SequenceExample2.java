
public class SequenceExample2 {

    public static void main (String[] argv)
    {
        int n = 10;
	for (int i=1; i<=n; i++) {
	    System.out.println ("Cn, n=" + i + ": " + computeC(i));
	}

    }

    static double computeC (int n)
    {
      double sum = 0;
      for (int i = 1; i <= n; i++) {
        sum += Math.sin(n) / (double) n;
      }
      return sum;
    }

}
