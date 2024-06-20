import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trialResults;
    private double confidence95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials cannot be negative or equal to 0");
        }
        trialResults = new double[trials];
        for (int i = 0; i < trials; i++) {
            // System.out.printf("Running trial %d\n", i);
            trialResults[i] = runTrial(n);
        }
    }

    private double runTrial(int n) {
        Percolation perc = new Percolation(n);
        while (!perc.percolates()) {
            // double closedCount = 0.0;
            int sampleId = StdRandom.uniformInt(0, n * n);
            int row = (sampleId / n) + 1;
            int column = (sampleId % n + 1);
            if (!perc.isOpen(row, column)) perc.open(row, column);
        }
        return perc.numberOfOpenSites() / (double) (n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.trialResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = this.mean();
        double stddev = this.stddev();
        return (mean - (this.confidence95 * stddev / Math.sqrt(trialResults.length)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = this.mean();
        double stddev = this.stddev();
        return (mean + (this.confidence95 * stddev / Math.sqrt(trialResults.length)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats pstats = new PercolationStats(n, trials);
        System.out.printf("mean = %f\n", pstats.mean());
        System.out.printf("stddev = %f\n", pstats.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n", pstats.confidenceLo(),
                          pstats.confidenceHi());

    }

}