import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> treeSet;
    // construct an empty set of points
    public PointSET() {
        treeSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return treeSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return treeSet.size();
    }

    private void validate(Object p) {
        if (p == null) {
            throw new IllegalArgumentException("Input parameter cannot be null");
        }
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validate(p);
        treeSet.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validate(p);
        return treeSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : treeSet) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validate(rect);
        Stack<Point2D> inside = new Stack<>();
        for (Point2D p : treeSet) {
            if (rect.contains(p)) {
                inside.push(p);
            }
        }
        return inside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validate(p);
        double maxDist = Double.POSITIVE_INFINITY;
        Point2D champion = null;
        for (Point2D point : treeSet) {
            double currentDist = point.distanceSquaredTo(p);
            if (currentDist < maxDist) {
                maxDist = currentDist;
                champion = point;
            }
        }
        return champion;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}