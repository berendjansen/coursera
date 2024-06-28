import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.Stack;

public class BruteCollinearPoints {
    private final Stack<LineSegment> allSegments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Input cannot be null");

        Point[] ps = new Point[points.length];
        for (int i = 0; i < points.length; i ++) {
            if (points[i] == null) throw new IllegalArgumentException("Point in input cannot be null");
            ps[i] = points[i];
        }

        Arrays.sort(ps, byPosition());
        for (int i = 0; i < ps.length - 1; i++) {
            if (ps[i].compareTo(ps[i+1]) == 0) {
                throw new IllegalArgumentException("No duplicate points are allowed");
            }
        }

        this.allSegments = new Stack<>();

        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    for (int m = k + 1; m < points.length; m++) {
                        Point s = points[m];
                        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s)) {
                            Point[] sortedPoints = new Point[] {p, q, r, s};
                            Arrays.sort(sortedPoints, byPosition());
                            allSegments.push(new LineSegment(sortedPoints[0], sortedPoints[3]));
                        }
                    }
                }
            }
        }
    }

    private Comparator<Point> byPosition() {
        return Point::compareTo;
    }
    // finds all line segments containing 4 points
    public int numberOfSegments() {
        return allSegments.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        LineSegment[] output = new LineSegment[numberOfSegments()];
        int i = 0;
        for (LineSegment lineSeg : allSegments) {
//            System.out.printf("Adding %s to output\n", lineSeg.toString());
            output[i++] = lineSeg;
        }
        return output;
    }


}