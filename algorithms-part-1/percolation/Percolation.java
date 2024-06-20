import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF uf;
    private byte[] statusArray;
    // private boolean[] openId;
    // private boolean[] fullIds;
    // private int find0;
    // private int bwFind0;
    private int numOpen;
    private boolean perculates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be smaller than 1");
        }
        uf = new WeightedQuickUnionUF(n * n); // NxN grid + top and bottom
        this.n = n;

        this.statusArray = new byte[n * n];
        for (int i = 0; i < n * n; i++) {
            statusArray[i] = 0;
        }
        this.perculates = false;
        this.numOpen = 0;
    }

    private void validate(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException(
                    "Row or Column cannot be negative or equal to 0, or larger than n");
        }
    }

    private int getArrayId(int row, int col) {
        return ((row - 1) * n + col) - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int arrayId = getArrayId(row, col);
        if (statusArray[arrayId] == 0) this.numOpen++;

        byte prevRight = 0;
        byte prevLeft = 0;
        byte prevDown = 0;
        byte prevUp = 0;
        byte prevRoot = 4;
        byte newStatus;

        boolean isRightEdge = col == n;
        boolean isLeftEdge = col == 1;
        boolean isTopRow = row == 1;
        boolean isBottomRow = row == n;

        if (this.n == 1 && isTopRow && isBottomRow) {
            prevRoot = 7;
        }
        else if (isTopRow) {
            prevRoot = 6;
        }
        else if (isBottomRow) {
            prevRoot = 5;
        }

        if (!isRightEdge && isOpen(row, col + 1)) {
            prevRight = statusArray[uf.find(arrayId + 1)];
            uf.union(arrayId, arrayId + 1);
        }
        if (!isLeftEdge && isOpen(row, col - 1)) {
            prevLeft = statusArray[uf.find(arrayId - 1)];
            uf.union(arrayId, arrayId - 1);
        }

        if (!isTopRow && isOpen(row - 1, col)) {
            prevUp = statusArray[uf.find(arrayId - n)];
            uf.union(arrayId, arrayId - n);
        }
        if (!isBottomRow && isOpen(row + 1, col)) {
            prevDown = statusArray[uf.find(arrayId + n)];
            uf.union(arrayId, arrayId + n);
        }

        byte newRoot = statusArray[uf.find(arrayId)];
        newStatus = (byte) (prevRoot | prevRight | prevLeft | prevDown | prevUp | newRoot);
        // System.out.println(prevRoot);
        // System.out.println(prevRight);
        // System.out.println(prevLeft);
        // System.out.println(prevDown);
        // System.out.println(prevUp);
        // System.out.println(newRoot);

        statusArray[uf.find(arrayId)] = newStatus;
        statusArray[arrayId] = newStatus;

        if (!isRightEdge && isOpen(row, col + 1)) {
            statusArray[arrayId + 1] = newStatus;
        }
        if (!isLeftEdge && isOpen(row, col - 1)) {
            statusArray[arrayId - 1] = newStatus;
        }

        if (!isBottomRow && isOpen(row + 1, col)) {
            statusArray[arrayId + n] = newStatus;
        }
        if (!isTopRow && isOpen(row - 1, col)) {
            statusArray[arrayId - n] = newStatus;
        }

        if (newStatus == 7) {
            this.perculates = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        int arrayId = getArrayId(row, col);
        return statusArray[arrayId] != 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int arrayId = getArrayId(row, col);
        return statusArray[uf.find(arrayId)] >= 6;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        // byte res = 0;
        // for (int i = statusArray.length - n; i < n * n; i++) {
        //     res = (byte) (res | statusArray[i]);
        // }
        // return res >= 6;
        return this.perculates;
    }

    // test client (optional)
    public static void main(String[] args) {
        if (args.length > 0) {
            // Percolation perc = new Percolation(Integer.parseInt(args[0]));
            Percolation perc = new Percolation(Integer.parseInt("3"));
            // System.out.printf("Is open (1,1): %b\n", perc.isOpen(1, 1));
            // System.out.printf("Is full (1,1): %b\n", perc.isFull(1, 1));
            // System.out.printf("Is connected (0, 1): %b\n", perc.uf.find(0) == perc.uf.find(1));
            // System.out.println(perc.percolates());
            perc.open(1, 1);
            // System.out.printf("Percs: %b\n", perc.percolates());
            perc.open(2, 1);
            // System.out.printf("Percs: %b\n", perc.percolates());
            perc.open(2, 2);
            perc.open(2, 2);
            // System.out.printf("Percs: %b\n", perc.percolates());
            // perc.open(3, 2);
            System.out.printf("Is full (2, 2): %b\n", perc.isFull(2, 2));

            System.out.println(perc.numberOfOpenSites());
        }
        else {
            int n = 4;
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int sampleId = StdRandom.uniformInt(0, n * n);
                int row = (sampleId / n) + 1;
                int column = (sampleId % n + 1);
                System.out.printf("Sampled id %d: row %d col %d\n", sampleId, row, column);
                if (!perc.isOpen(row, column)) {
                    // System.out.printf("Sampled id %d: row %d col %d\n", sampleId, row, column);
                    perc.open(row, column);
                }
            }
        }
    }
}