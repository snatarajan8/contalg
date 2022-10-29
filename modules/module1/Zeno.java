import java.io.File;
import java.io.IOException;

public class Zeno {

    public static void main(String[] argv) {
//      try {
//          File myObj = new File("exercise7.txt");
//          if (myObj.createNewFile()) {
//              System.out.println("File created: " + myObj.getName());
//          } else {
//              System.out.println("File already exists.");
//          }
//      } catch (IOException e) {
//          System.out.println("An error occurred.");
//          e.printStackTrace();
//      }
      for (int i = 1; i <= 25; i += 1) {
          System.out.println(i + " " + sum(i));
//          System.out.println (sum(i));
//          System.out.println(sum2(i*n));
      }

    }

    static double sum (int n)
    {
        float sum = 0;
        for (int i = 1; i <= n; i++) {
          sum += (1/Math.pow(2,i));
        }
        return sum;
    }

    static double sum2(int n)
    {
        return (((Math.pow(0.5, (double)(n+1))) - 1)/(-0.5)) - 1;
    }

}
