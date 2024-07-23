import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    private KdTree.Node root;

    public KdTree() { }

    private class Node {
        private final Point2D key;
        private final boolean isHorizontal;
        private final RectHV rect;
        private KdTree.Node left;
        private KdTree.Node right;
        private int size;

        public Node(Point2D key, int size, boolean isHorizontal, RectHV rect) {
            this.key = key;
            this.size = size;
            this.isHorizontal = isHorizontal;
            this.rect = rect;
        }

        public String toString() {
            return String.format("Key: %s", this.key);
        }
    }

    private int size(KdTree.Node x) {
        return x == null ? 0 : x.size;
    }

    private void put(Point2D key) {
        this.root = put(root, key, true, null);
    }

    private RectHV constructChildRect(Node x, boolean left) {
        double xmin, ymin, xmax, ymax;
        if (x.isHorizontal) {
            xmin = left ? x.rect.xmin() : x.key.x();
            xmax = left ? x.key.x() : x.rect.xmax();
            ymin = x.rect.ymin();
            ymax = x.rect.ymax();
        } else {
            xmin = x.rect.xmin();
            xmax = x.rect.xmax();
            ymin = left ? x.rect.ymin() : x.key.y();
            ymax = left ? x.key.y() : x.rect.ymax();
        }
        return new RectHV(xmin, ymin, xmax, ymax);
    }

    private Node put(KdTree.Node x, Point2D key, boolean isHorizontal, RectHV newRect) {
        if (x == null) {
            RectHV rect = newRect == null ? new RectHV(0.0, 0.0, 1.0, 1.0) : newRect;
            return new Node(key, 1, isHorizontal, rect);
        }
        int cmp = comparePoints(key, x.key, x.isHorizontal);

        if (cmp < 0)
            x.left = put(x.left, key, !x.isHorizontal, x.left == null ? constructChildRect(x, true) : null);
        else if (cmp > 0)
            x.right = put(x.right, key, !x.isHorizontal, x.right == null ? constructChildRect(x, false) : null);

        x.size = 1 + this.size(x.left) + this.size(x.right);
        return x;
    }

    private int comparePoints(Point2D a, Point2D b, boolean byX) {
        if (byX) {
            int cmp = Double.compare(a.x(), b.x());
            return cmp == 0 ? Double.compare(a.y(), b.y()) : cmp;
        } else {
            return a.compareTo(b);
        }
    }

    private Point2D get(KdTree.Node x, Point2D key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        } else if (x == null) {
            return null;
        }

        int cmp = comparePoints(key, x.key, x.isHorizontal);

        if (cmp < 0) {
            return this.get(x.left, key);
        } else {
            return cmp > 0 ? this.get(x.right, key) : x.key;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return this.size(this.root);
    }

    private void validate(Object p) {
        if (p == null) {
            throw new IllegalArgumentException("Input parameter cannot be null");
        }
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        validate(p);
        this.put(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        validate(p);
//        System.out.println(get(this.root, p));
        return get(this.root, p) != null;
    }

    private void draw(Node x) {
        if (x == null) return;
        x.key.draw();

        if (x.isHorizontal) {
            StdDraw.line(
                    x.key.x(),
                    Math.max(0.0, x.rect.ymin()),
                    x.key.x(),
                    Math.min(1.0, x.rect.ymax())
            );
        } else {
            StdDraw.line(
                    Math.max(0.0, x.rect.xmin()),
                    x.key.y(),
                    Math.min(1.0, x.rect.xmax()),
                    x.key.y()
            );
        }

        draw(x.left);
        draw(x.right);
    }

    // draw all points to standard draw
    public void draw() {
        RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
        rect.draw();
        draw(this.root);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        validate(rect);
        Queue<Point2D> inside = new Queue<>();
        range(rect, root, inside);
        return inside;
    }

    private void range(RectHV rect, Node x, Queue<Point2D> queue) {
        if (x == null) return;
        if (rect.contains(x.key)) queue.enqueue(x.key);

        if (x.left != null && rect.intersects(x.left.rect)) {
            range(rect, x.left, queue);
        }
        if (x.right != null && rect.intersects(x.right.rect)) {
            range(rect, x.right, queue);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        validate(p);
        if (root == null) return null;
        return nearest(p, root, root).key;
    }

    private boolean isSearchRequired(Point2D p, Node x, Node champion, boolean left) {
        if (left) {
            return (champion.key.distanceSquaredTo(p) > x.rect.distanceSquaredTo(p));
        } else {
            return (champion.key.distanceSquaredTo(p) > x.rect.distanceSquaredTo(p));
        }
    }

    private boolean isLeftOf(Point2D p, Node x) {
        return ((x.isHorizontal && (p.x() < x.key.x())) || (!x.isHorizontal && (p.y() < x.key.y())));
    }

    private Node nearest(Point2D p, Node x, Node champion) {
        if (x.key.distanceSquaredTo(p) < champion.key.distanceSquaredTo(p)) {
            champion = x;
        }

        boolean leftFirst = isLeftOf(p, x);
        Node firstSearchNode = leftFirst ? x.left : x.right;
        boolean firstSearchRequired = firstSearchNode != null && isSearchRequired(p, firstSearchNode, champion, leftFirst);

        if (firstSearchRequired) {
            champion = nearest(p, firstSearchNode, champion);
        }

        Node secondSearchNode = leftFirst ? x.right : x.left;
        boolean secondSearchRequired = secondSearchNode != null && isSearchRequired(p, secondSearchNode, champion, !leftFirst);

        if (secondSearchRequired) {
            champion = nearest(p, secondSearchNode, champion);
        }

        return champion;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree tree = new KdTree();

        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));

        System.out.printf("Nearest to (0.37, 0.2) is %s\n", tree.nearest(new Point2D(0.37, 0.02)));

        for (Point2D p : tree.range(new RectHV(0.0, 0.4, 0.8, 0.7))) {
            System.out.println(p);
        }
    }
}