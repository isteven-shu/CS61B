package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /** N in N * N grids */
    int N;

    /** Number of open boxes */
    int numOfOpen;

    /** true if open, false if not */
    boolean[][] isOpen;

    /** Union find */
    WeightedQuickUnionUF grid;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N < 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        numOfOpen = 0;
        isOpen = new boolean[N][N];

        /** totally N^2 + 2 length for UF, 1--N^2 represents normal boxes
         *  0 represents the top virtual node, N * N + 1 represents the bottom virtual node
         *  top virtual node is connected tp every open node in the top row
         *  bottom virtual node is connected to every full node in the bottom row
         *  */
        grid = new WeightedQuickUnionUF(N * N + 2);
    }

    private int convertTo1D(int row, int col) {
        return row * N + col + 1;
    }

    private void validate(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        numOfOpen++;
        int idx = convertTo1D(row, col);
        isOpen[row][col] = true;

        if (row == 0) {
            grid.union(idx, 0);
        }

        if (row - 1 >= 0) {
            if (isOpen(row - 1, col)) {
                grid.union(idx, convertTo1D(row - 1, col));
            }
        }
        if (row + 1 < N) {
            if (isOpen(row + 1, col)) {
                grid.union(idx, convertTo1D(row + 1, col));
            }
        }
        if (col - 1 >= 0) {
            if (isOpen(row, col - 1)) {
                grid.union(idx, convertTo1D(row, col - 1));
            }
        }
        if (col + 1 < N) {
            if (isOpen(row, col + 1)) {
                grid.union(idx, convertTo1D(row, col + 1));
            }
        }

        if (row == N - 1 && isFull(row, col)) {
            grid.union(idx, N * N + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return isOpen[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int idx = convertTo1D(row, col);
        return grid.connected(idx, 0);
    }


    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    // does the system percolate?
    // We say the system percolates if there is a full site in the bottom row.
    public boolean percolates() {
        return grid.connected(0, N * N + 1);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {

    }
}
