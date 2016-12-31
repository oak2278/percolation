import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trialCount;
    private double[] trialResults;


    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("N and T must be <= 0");
        }
        int gridSize = n;
        trialCount = trials;
        trialResults = new double[trialCount];

        for (int trial = 0; trial < trialCount; trial++) {
            Percolation percolation = new Percolation(gridSize);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, gridSize + 1);
                int col = StdRandom.uniform(1, gridSize + 1);
                percolation.open(row, col);
            }
            int openSites = percolation.numberOfOpenSites();
            double result = (double) openSites / (gridSize * gridSize);
            trialResults[trial] = result;
        }
    }


    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trialCount));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trialCount));
    }

    // test client (described below)
    public static void main(String[] args) {
        int gridSize = 10;
        int trialCount = 10;
        if (args.length >= 2) {
            gridSize = Integer.parseInt(args[0]);
            trialCount = Integer.parseInt(args[1]);
        }
        PercolationStats ps = new PercolationStats(gridSize, trialCount);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}