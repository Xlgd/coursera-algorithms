import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private final double[] trialsFraction;
    private final int t;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        trialsFraction = new double[trials];
        t = trials;
        int randomRowIndex, randomColIndex;
        double percolateCounter;
        for (int i = 0; i < t; i++) {
            Percolation testPercolation = new Percolation(n);
            percolateCounter = 0;
            while (!testPercolation.percolates()) {
                do {
                    randomRowIndex = StdRandom.uniform(n) + 1;
                    randomColIndex = StdRandom.uniform(n) + 1;
                } while (testPercolation.isOpen(randomRowIndex, randomColIndex));
                testPercolation.open(randomRowIndex, randomColIndex);
                percolateCounter++;
            }
            trialsFraction[i] = percolateCounter / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (t <= 0) {
            return Double.NaN;
        }
        else {
            return StdStats.mean(trialsFraction);
        }
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (t <= 1) {
            return Double.NaN;
        }
        else {
            return StdStats.stddev(trialsFraction);
        }
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - (1.96 * stddev() / Math.sqrt(t)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (1.96 * stddev() / Math.sqrt(t)));
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch watch = new Stopwatch();
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats testPercolationStats = new PercolationStats(n, trials);
        System.out.println("mean= " + testPercolationStats.mean());
        System.out.println("stddev= " + testPercolationStats.stddev());
        System.out.println(
                ("95% confidence interval= " + "[" + testPercolationStats.confidenceLo() + ", "
                        + testPercolationStats.confidenceHi() + "]"));
        System.out.println("CPU time= " + watch.elapsedTime());

    }
}
