import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private final Stack<LineSegment> allSegments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Input cannot be null");

        Point[] ps = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
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
        Arrays.sort(ps);

        for (Point p : ps) {
            Arrays.sort(ps, p.slopeOrder());
            for (int j = 1; j < ps.length; j++) {
                double slopeToMatch = p.slopeTo(ps[j]);
                int lineLength = 2;
                int k = j + 1;
                while(k < ps.length && Double.compare(p.slopeTo(ps[k]), slopeToMatch) == 0) {
                    lineLength++;
                    k++;
                }
                if (lineLength > 3 && p.compareTo(ps[j]) < 0) {
                    allSegments.push(new LineSegment(p, ps[k-1]));
                }
                j = k - 1;
            }
        Arrays.sort(ps);
        }
    }

    private static Comparator<Point> byPosition() {
        return Point::compareTo;
    }

    public int numberOfSegments() {
        return allSegments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] output = new LineSegment[numberOfSegments()];
        int i = 0;
        for (LineSegment lineSeg : allSegments) {
//            System.out.printf("Adding %s to output\n", lineSeg.toString());
            output[i++] = lineSeg;
        }
        return output;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        StdOut.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}