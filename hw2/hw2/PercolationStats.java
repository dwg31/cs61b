package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/** A class that uses Monte Carlo simulation
 * to calculate the threshold of percolation.
 *
 * @author Dawei Gu
 */
public class PercolationStats {
    /** an array that stores the percolation threshold
     * calculated in every simulation.
     */
    private double[] threshold;

    /** perform T independent experiments on an N-by-N grid.
     *
     * @param N size of the grid
     * @param T number of experiments
     * @param pf used to generate Percolation experiments
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N < 0 || T < 0) {
            throw new IllegalArgumentException();
        }

        int row;
        int col;
        int numOfSites = N * N;
        threshold = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation sim = pf.make(N);

            while (!sim.percolates()) {
                row = StdRandom.uniform(N);
                col = StdRandom.uniform(N);
                sim.open(row, col);
            }
            threshold[i] = (double) sim.numberOfOpenSites() / numOfSites;
        }
    }

    /**  sample mean of percolation threshold.
     *
     * @return mean value
     */
    public double mean() {
        return StdStats.mean(threshold);
    }

    /** sample standard deviation of percolation threshold.
     *
     * @return standard deviation
     */
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    /** low endpoint of 95% confidence interval.
     *
     * @return low endpoint
     */
    public double confidenceLow() {
        return (mean() - 1.96 * Math.sqrt(stddev() / threshold.length));
    }

    /** high endpoint of 95% confidence interval.
     *
     * @return high endpoint
     */
    public double confidenceHigh() {
        return (mean() + 1.96 * Math.sqrt(stddev() / threshold.length));
    }
}
