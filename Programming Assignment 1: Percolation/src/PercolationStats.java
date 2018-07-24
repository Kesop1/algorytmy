import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by Krzysztof Piotrak on 2/11/18.
 */

/**
 * PercolationStats class for https://www.coursera.org/learn/algorithms-part1/home/week/1
 */
public class PercolationStats {

    /**
     * constant double value of confidence interval for value 95%
     */
    private static double Z = 1.96;

    /**
     * confidence interval
     */
    private double interval;

    /**
     * array for storing the opened sites number for each trial
     */
    private double[] resultsArray;

    /**
     * calculated mean from the {@code resultsArray}
     */
    private double mean;

    /**
     * calculated standard deviation from the {@code resultsArray}
     */
    private double stddev;

    /**
     * calculated confidence Low from the {@code resultsArray} and {@code interval}
     */
    private double confidenceLo;

    /**
     *  calculated confidence High from the {@code resultsArray} and {@code interval}
     */
    private double confidenceHi;

    /**
     * class used for testing the Percolation class
     * it creates Percolation class instance with a grid of {@code n} size
     * using StdRandom class it opens the sites util it percolates
     * the schema repeats {@code trials} times
     * after that it calculates mean, standard deviation and confidence values
     * @param n grid site
     * @param trials number of tests
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        resultsArray = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            do {
                int x = StdRandom.uniform(n + 1);
                int y = StdRandom.uniform(n + 1);
                if (x == 0 || y == 0)
                    continue;
                percolation.open(y, x);
            } while (!percolation.percolates());
            double result = percolation.numberOfOpenSites();
            resultsArray[i] = result / (n * n);
        }
        mean();
        stddev();
        interval = Z * stddev / Math.sqrt(trials);
        confidenceHi();
        confidenceLo();
    }

    /**
     * main class for initiating the class
     * it prints the values of calculates mean, standard deviation and confidence
     * @param args
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        System.out.println("mean\t\t\t\t\t = " + ps.mean);
        System.out.println("stddev\t\t\t\t\t = " + ps.stddev);
        System.out.println("95% confidence interval\t = [" + ps.confidenceLo + ", " + ps.confidenceHi + "]");
    }

    /**
     * calculates the mean value from the {@code resultsArray}
     * @return
     */
    public double mean() {
        mean = StdStats.mean(resultsArray);
        return mean;
    }

    /**
     * calculates the standard deviation value from the {@code resultsArray}
     * @return
     */
    public double stddev() {
        if(resultsArray.length==1)
            return Double.NaN;
        stddev = StdStats.stddev(resultsArray);
        return stddev;
    }

    /**
     * calculates the confidence Low value from the {@code mean} and {@code interval}
     * @return
     */
    public double confidenceLo() {
        confidenceLo = mean - interval;
        return confidenceLo;
    }

    /**
     * calculates the confidence High value from the {@code mean} and {@code interval}
     * @return
     */
    public double confidenceHi() {
        confidenceHi = mean + interval;
        return confidenceHi;
    }
}
