public class Main {
    public static void testBCP() {
        Point a = new Point(0,0);
        Point b = new Point(1,1);
        Point c = new Point(2,2);
        Point d = new Point(3,3);
        Point e = new Point(3,1);
        Point f = new Point(1,3);
        Point g = new Point(0,4);

        Point[] points = new Point[] {a, b, c, d, e, f, g};

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        System.out.println(bcp.numberOfSegments());
        for (LineSegment ls : bcp.segments()) {
            System.out.println(ls.toString());
        }

        try {
            new BruteCollinearPoints(null);
            assert false;
        } catch (Exception ex) {
            System.out.println("Successfully caught exception");
        }

        try {
            new BruteCollinearPoints(new Point[]{null});
            assert false;
        } catch (Exception ex) {
            System.out.println("Successfully caught exception");
        }

        try {
            new BruteCollinearPoints(new Point[]{a, a, b, c});
            assert false;
        } catch (Exception ex) {
            System.out.println("Successfully caught exception");
        }
    }

    public static void testFCP() {
        Point a = new Point(0,0);
        Point b = new Point(1,1);
        Point c = new Point(2,2);
        Point d = new Point(3,3);
        Point e = new Point(3,1);
        Point f = new Point(1,3);
        Point g = new Point(0,4);

        Point[] points = new Point[] {a, b, c, d, e, f, g};

        FastCollinearPoints fcp = new FastCollinearPoints(points);

        System.out.println(fcp.numberOfSegments());
        for (LineSegment ls : fcp.segments()) {
            System.out.println(ls.toString());
        }

        try {
            new FastCollinearPoints(null);
            assert false;
        } catch (Exception ex) {
            System.out.println("Successfully caught exception");
        }

        try {
            new FastCollinearPoints(new Point[]{null});
            assert false;
        } catch (Exception ex) {
            System.out.println("Successfully caught exception");
        }

        try {
            new FastCollinearPoints(new Point[]{a, a, b, c, d});
            assert false;
        } catch (Exception ex) {
            System.out.println("Successfully caught exception");
        }

        try {
            new FastCollinearPoints(new Point[]{a, b, c, d, d});
            assert false;
        } catch (Exception ex) {
            System.out.println("Successfully caught exception");
        }

        try {
            new FastCollinearPoints(new Point[]{a, b, c, c, d});
            assert false;
        } catch (Exception ex) {
            System.out.println("Successfully caught exception");
        }
    }

    public static void testPoint() {
        Point p = new Point(22485, 91);
        Point q = new Point(22485, 91);
        Point r = new Point(2240, 91);

        assert p.slopeTo(q) == Double.NEGATIVE_INFINITY;
        assert p.slopeTo(r) == 0.0;
    }

    public static void main(String[] args) {
        testPoint();
        testBCP();
        testFCP();
    }
}
