
import java.util.*;
import java.io.*;

import PlotPoint;


public class Classifier {

    // Names of files that contain samples from "category A"
    static String[] fileNamesA = {"A0.data", "A1.data", "A2.data", "A3.data", "A4.data"};

    // Names of files that contain samples from "category B"
    static String[] fileNamesB = {"B0.data", "B1.data", "B2.data", "B3.data", "B4.data"};

    // The data in each of the files are eventually placed in these data structures.
    // dataSetA[2] has all the data in "A2.data"
    // Because dataSetA[2] is a Vector of LineSegmentd's, then
    // dataSetA[2].get(5) is the 5th line segment in that file.
    static Vector<LineSegmentd>[] dataSetA;
    static Vector<LineSegmentd>[] dataSetB;

    // The is the unclassified (uncategorized) sample.
    static Vector<LineSegmentd> sample;


    public static void main (String[] argv)
    {
        readData ();
        printData ();
    }

    static void printData ()
    {
        // INSERT YOUR CODE IN THIS METHOD.

        System.out.println ("Data Set A:");
        int sumX1 = 0;
        int sumY1 = 0;
        int sumX2 = 0;
        int sumY2 = 0;
        int slope = 0;
        for (int i=0; i<dataSetA.length; i++) {
            System.out.println ("  Data point i=" + i + ":");
            sumX1 = 0;
            sumY1 = 0;
            sumX2 = 0;
            sumY2 = 0;
            slope = 0;
            for (LineSegmentd L: dataSetA[i]) {
                // Use L.x1, L.y1, L.x2, L.y2
                System.out.println ("        " + L);
                sumX1 += L.x1;
                sumY1 += L.y1;
                sumX2 += L.x2;
                sumY2 += L.y2;
            }
            int avgX1 = sumX1 / datasetA[i].length;
            int avgY1 = sumY1 / datasetA[i].length;
            int avgX2 = sumX2 / datasetA[i].length;
            int avgY2 = sumY2 / datasetA[i].length;
            int slope = (avgY2 - avgY1) / (avgX2 - avgX1);

            System.out.println(avgX1);
            System.out.println(avgY1);
            System.out.println(avgX2);
            System.out.println(avgY2);
            System.out.println(slope);
        }

        System.out.println ("----------------------------------------");

        System.out.println ("Data Set B:");
        for (int i=0; i<dataSetB.length; i++) {
            System.out.println ("  Data point i=" + i + ":");
            for (LineSegmentd L: dataSetB[i]) {
                // Use L.x1, L.y1, L.x2, L.y2
                System.out.println ("        " + L);
                sumX1 += L.x1;
                sumY1 += L.y1;
                sumX2 += L.x2;
                sumY2 += L.y2;
            }
        }

        System.out.println ("----------------------------------------");

        System.out.println ("  Sample point: ");
        for (LineSegmentd L: sample) {
            // Use L.x1, L.y1, L.x2, L.y2
            System.out.println ("        " + L);
        }
    }



    static void readData ()
    {
        dataSetA = new Vector [fileNamesA.length];
        for (int i=0; i<fileNamesA.length; i++) {
            dataSetA[i] = loadFile (fileNamesA[i]);
        }

        dataSetB = new Vector [fileNamesB.length];
        for (int i=0; i<fileNamesB.length; i++) {
            dataSetB[i] = loadFile (fileNamesB[i]);
        }

        sample = loadFile ("sample.data");
    }


    public static Vector<LineSegmentd> loadFile (String fileName)
    {
        try {
            Scanner scanner = new Scanner (new File(fileName));
            Vector<LineSegmentd> segments = new Vector<LineSegmentd>();
            while (scanner.hasNextDouble()) {
                double x1 = scanner.nextDouble();
                double y1 = scanner.nextDouble();
                double x2 = scanner.nextDouble();
                double y2 = scanner.nextDouble();
                LineSegmentd L = new LineSegmentd (x1,y1, x2,y2);
                segments.add (L);
            }
            return segments;
        }
        catch (IOException e){
            System.out.println (e);
            return null;
        }
    }


}
