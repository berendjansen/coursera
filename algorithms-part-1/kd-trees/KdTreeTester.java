import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class KdTreeTester {
    public KdTreeTester() { }

    public static void main(String[] args) {
        KdTree tree = new KdTree();
        
        String filename = args[0];
        In in = new In(filename);

        while (!in.isEmpty()) {
            in.readString();
            double x = in.readDouble();
            double y = in.readDouble();
            tree.insert(new Point2D(x, y));
        }

        System.out.println(tree.nearest(new Point2D(0.75, 0.94)));
    }
}
