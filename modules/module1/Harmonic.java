
public class Harmonic {

    public static void main (String[] argv)
    {
        int n = 99999999;
        System.out.println (sumDouble(n));
//        System.out.println (sumFloat(n));
        for (int i = 1; i <= 1200001; i += 50000) {
            System.out.println (i + " " + sumDouble(i));
        }
    }

    static double sumDouble (int n)
    {
      double sum = 0;
      for (int i = 1; i <= n; i++) {

        sum += (double) 1 / (double) i;
      }
      return sum;
    }

    static float sumFloat (int n)
    {
        float sum = 0;
        for (int i = 1; i <= n; i++) {
          sum += (float)1/(float)i;
        }
        return sum;
    }

}
