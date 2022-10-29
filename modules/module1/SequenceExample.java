
public class SequenceExample {

    public static void main (String[] argv)
    {
//        int n = 10;
//        System.out.println ("An, n=" + n + ": " + computeA(n));
//        n = 10000;
//        System.out.println ("An, n=" + n + ": " + computeA(n));

        for (int i = 1; i <= 1200001; i += 50000) {
            System.out.println (i + " " + computeA(i));
        }

    }

    static double computeA (int n)
    {
//        double sum = 0;
//        for (int i = 1; i <= n; i++) {
//          sum += Math.pow(1 + (1/(double)n), n);
//        }
//        return sum;
        return Math.pow(1 + (1/(double)n), n);
    }

}
