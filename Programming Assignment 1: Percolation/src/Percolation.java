/**
 * Created by Krzysztof Piotrak on 2/24/18.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation class for https://www.coursera.org/learn/algorithms-part1/home/week/1
 */
public class Percolation {

    /**
     * grid of open or closed sites
     */
    private int[][] grid;

    /**
     * instance of the WeightedQuickUnionUF class, whic implements union-find data type methods
     */
    private WeightedQuickUnionUF wqu;

    /**
     * number of open sites
     */
    private int openNumber;

    /**
     * virtual site used for checking the connection between top and bottom row,
     * all open sites of the top row are automatically connected to this site,
     * it is always open
     */
    private int virtualTop;

    /**
     * virtual site used for checking the connection between top and bottom row,
     * all open sites of the bottom row are automatically connected to this site,
     * it is always open
     */
    private int virtualBottom;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        wqu = new WeightedQuickUnionUF(n * n + 2);
        grid = new int[n + 1][n + 1];
        for (int i = 0; i < n + 1; i++) {
            for (int j = 0; j < n + 1; j++) {
                grid[i][j] = 0;
            }
        }
        virtualTop = 0;
        virtualBottom = n * n + 1;
        openNumber = 0;
    }

    /**
     * main method for testing purposes
     * @param args
     */
    public static void main(String[] args) {
        int n = 6000;
        Percolation p = new Percolation(n);
        do {
            int x = StdRandom.uniform(n + 1);
            int y = StdRandom.uniform(n + 1);
            if (x == 0 || y == 0)
                continue;
            p.open(y, x);
        } while (!p.percolates());
        System.out.println(p.numberOfOpenSites());
    }

    /**
     * method used for opening site of the {@code grid} if it is not already opened
     * it also checks if the surrounding sites are opened and if so joins them in the same composite
     * if opening a site of first or last row it will connect it to the respective virtual sites
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (!isOpen(row, col)) {
            grid[row][col] = 1;
            openNumber++;

            //get top
            if (row > 1 && checkTop(row, col) >= 0) {
                wqu.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            //get right
            if (col < this.grid[0].length - 1 && checkRight(row, col) >= 0) {
                wqu.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
            //get left
            if (col > 1 && checkLeft(row, col) >= 0) {
                wqu.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            //get bottom
            if (row < this.grid.length - 1 && checkBottom(row, col) >= 0) {
                wqu.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }

            //connect to virtual sites
            if (row == 1) {
                wqu.union(virtualTop, xyTo1D(row, col));
            } else if (row == this.grid.length - 1) {
                wqu.union(xyTo1D(row, col), virtualBottom);
            }
        }
    }

    /**
     * checks whether the site is open
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        return grid[row][col] == 1;
    }

    /**
     * checks whether the site is already full, meaning connected to the {@code virtualTop} site
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        return wqu.connected(virtualTop, xyTo1D(row, col));
    }

    /**
     * number of sites already opened in the {@code grid}
     * @return
     */
    public int numberOfOpenSites() {
        return openNumber;
    }

    /**
     * checks if the {@code virtualTop} and {@code virtualBottom} sites are connected
     * @return
     */
    public boolean percolates() {
        return wqu.connected(virtualTop, virtualBottom);
    }

    /**
     * checks whether the {@code row} or {@code col} indexes are not out opf bounds
     * @param row
     * @param col
     * @throws IndexOutOfBoundsException if < 0 or >= {@code grid} length
     */
    private void checkIndex(int row, int col) {
        if (row < 1 || row >= this.grid.length || col < 1 || col >= this.grid.length) {
            throw new IndexOutOfBoundsException("row index row out of bounds");
        }
    }

    /**
     * converts the 2D {@code grid} coordinates of the site to 1D coordinates
     * row=1 col=1 ==> index=1 because {@code virtualTop} index is 0
     * @param row
     * @param col
     * @return 1D coordinates
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * (this.grid.length - 1) + col;
    }

    /**
     * check top site of the one in question
     * @param row
     * @param col
     * @return -1 if top site is closed, or its' parent
     */
    private int checkTop(int row, int col) {
        row--;
        int result = -1;
        if (isOpen(row, col)) {
            result = wqu.find(xyTo1D(row, col));
        }
        return result;
    }

    /**
     * check right site of the one in question
     * @param row
     * @param col
     * @return -1 if right site is closed, or its' parent
     */
    private int checkRight(int row, int col) {
        col++;
        int result = -1;
        if (isOpen(row, col)) {
            result = wqu.find(xyTo1D(row, col));
        }
        return result;
    }

    /**
     * check left site of the one in question
     * @param row
     * @param col
     * @return -1 if left site is closed, or its' parent
     */
    private int checkLeft(int row, int col) {
        col--;
        int result = -1;
        if (isOpen(row, col)) {
            result = wqu.find(xyTo1D(row, col));
        }
        return result;
    }

    /**
     * check bottom site of the one in question
     * @param row
     * @param col
     * @return -1 if bottom site is closed, or its' parent
     */
    private int checkBottom(int row, int col) {
        row++;
        int result = -1;
        if (isOpen(row, col)) {
            result = wqu.find(xyTo1D(row, col));
        }
        return result;
    }
}
