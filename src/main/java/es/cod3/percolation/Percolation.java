package es.cod3.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by ramborg on 11/08/17.
 */
public class Percolation {

    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF weightedQuickUnionUFisFull;
    private final int gridSize;
    private final int pointsSize;
    private int numOpens;

    private boolean[][] openedSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        pointsSize = n*n+2;
        weightedQuickUnionUF = new WeightedQuickUnionUF(pointsSize);
        weightedQuickUnionUFisFull = new WeightedQuickUnionUF(pointsSize-1);
        gridSize = n;

        openedSites = new boolean[n][n];

        // Connect virtual top bottom sites
        for (int i = 1; i <= gridSize; i++) {
            weightedQuickUnionUF.union(0, i);
            weightedQuickUnionUF.union(pointsSize-1, pointsSize-i-1);
            weightedQuickUnionUFisFull.union(0, i);
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkParams(row, col);

        if (!isOpen(row, col)) {
            int pointPosition = getPointPosition(row, col);
            // North
            if (row > 1 && isOpen(row -1, col)) {
                weightedQuickUnionUF.union(pointPosition, pointPosition - gridSize);
                weightedQuickUnionUFisFull.union(pointPosition, pointPosition - gridSize);
            }
            // South
            if (row < gridSize && isOpen(row + 1, col)) {
                weightedQuickUnionUF.union(pointPosition, pointPosition + gridSize);
                weightedQuickUnionUFisFull.union(pointPosition, pointPosition + gridSize);

            }
            // East
            if (col > 1 && isOpen(row, col - 1)) {
                weightedQuickUnionUF.union(pointPosition, pointPosition - 1);
                weightedQuickUnionUFisFull.union(pointPosition, pointPosition - 1);

            }
            // West
            if (col < gridSize && isOpen(row, col + 1)) {
                weightedQuickUnionUF.union(pointPosition, pointPosition + 1);
                weightedQuickUnionUFisFull.union(pointPosition, pointPosition + 1);

            }

            numOpens++;
            openedSites[row-1][col-1] = true;
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkParams(row, col);

        return openedSites[row-1][col-1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col)  {
        checkParams(row, col);

        return isOpen(row, col) && weightedQuickUnionUFisFull.connected(0, getPointPosition(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpens;
    }

    // does the system percolate?
    public boolean percolates() {
        return numOpens > 0 && weightedQuickUnionUF.connected(0, pointsSize-1);
    }

    private void checkParams(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException("Not accesed for row " + row + " and col " + col);
        }
    }

    private int getPointPosition(int row, int col) {
        return ((row-1)*gridSize)+col;
    }

    public static void main(String[] args)  {
        StdOut.println("Nothing implemented");
    }
}
