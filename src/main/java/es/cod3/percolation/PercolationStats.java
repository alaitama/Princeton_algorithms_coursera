package es.cod3.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by ramborg on 11/08/17.
 */
public class PercolationStats {

    private final int n;
    private final int trials;
    private final int[] numOpenSites;
    private double mean;
    private double stdDev;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        numOpenSites = new int[trials];
        this.trials = trials;
        this.n = n;

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);

                percolation.open(row, col);
            }

            numOpenSites[i] = percolation.numberOfOpenSites();
        }

    }

    // sample mean of percolation threshold
    public double mean() {

        if (trials == 1) {
            return Double.NaN;
        }
        else {
            mean = StdStats.mean(numOpenSites) / ((double) (n*n));
            return mean;
        }
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        if (trials == 1) {
            return Double.NaN;
        }
        else {
            stdDev = StdStats.stddev(numOpenSites) / ((double) (n*n));
            return stdDev;
        }

    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {

        if (trials == 1) {
            return Double.NaN;
        }
        else {

            return getMean() - ((1.96 * getStdDev()) / Math.sqrt(trials));

        }
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        if (trials == 1) {
            return Double.NaN;
        }
        else {
            return getMean() + ((1.96 * getStdDev()) / Math.sqrt(trials));
        }

    }

    private double getMean() {
        if (mean != 0) {
            return mean;
        }
        else {
            return  mean();
        }
    }

    private double getStdDev() {
        if (stdDev != 0) {
            return stdDev;
        }
        else {
            return  stddev();
        }
    }

    // test client (described below)
    public static void main(String[] args)  {

        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        StdOut.print("mean                    = ");
        StdOut.println(percolationStats.mean());
        StdOut.print("stddev                  = ");
        StdOut.println(percolationStats.stddev());
        StdOut.print("95% confidence interval = [");
        StdOut.print(percolationStats.confidenceLo());
        StdOut.print(", ");
        StdOut.print(percolationStats.confidenceHi());
        StdOut.println("]");


    }
}