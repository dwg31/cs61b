package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Percolation data type
 *
 * @author Dawei Gu
 */
public class Percolation {
    private int size;
    private int numOfOpened;
    private WeightedQuickUnionUF fields;
    private boolean[][] openStatus;
    private int top;
    private int bottom;

    /** create N by N grid, with all sites initially blocked.
     *
     * @param N block size
     */
    public Percolation(int N) {
        if (N < 0) {
            throw new IllegalArgumentException();
        } else {
            size = N;
            numOfOpened = 0;
            fields = new WeightedQuickUnionUF(N * N + 2);
            openStatus = new boolean[N][N];
            top = 0;
            bottom = N * N + 1;
        }
    }

    /** validate the entered row and col are inside the fields.
     *
     * @param row row number
     * @param col column number
     */
    private void validate(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new IllegalArgumentException();
        }
    }

    /** convert 2D coordinate to 1D number
     *
     */
    private int xyTo1D(int row, int col) {
        if (row < 0 || row > size - 1 || col < 0 || col > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return row * size + col + 1;
    }


    /** open the site (row, col) if it is not open already.
     *
     * @param row row position
     * @param col column position
     */
    public void open(int row, int col) {
        validate(row, col);
        if (openStatus[row][col]) {
            return;
        } else {
            openStatus[row][col] = true;
            numOfOpened += 1;
            int num = xyTo1D(row, col);

            if (row == 0) {
                fields.union(num, top);
            }
            if (row == size - 1) {
                fields.union(num, bottom);
            }
            if (row > 0 && isOpen(row - 1, col)) {
                fields.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
            if (row + 1 < size && isOpen(row + 1, col)) {
                fields.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
            if (col > 0 && isOpen(row, col - 1)) {
                fields.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
            if (col + 1 < size && isOpen(row, col + 1)) {
                fields.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
    }

    /** is the site (row, col) open?
     *
     * @param row position
     * @param col column position
     * @return true or false
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return openStatus[row][col];
    }

    /** is the site (row, col) full?
     *
     * @param row position
     * @param col column position
     * @return true or false
     */
    public boolean isFull(int row, int col) {
        validate(row, col);

        int num = xyTo1D(row, col);
        return fields.connected(top, num);
    }

    /** number of open sites.
     *
     * @return number of open sites
     */
    public int numberOfOpenSites() {
        return numOfOpened;
    }

    /** the status of percolation.
     *
     * @return true or false
     */
    public boolean percolates() {
        return fields.connected(top, bottom);
    }

    /** use for unit testing.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Percolation sample = new Percolation(10);
        for (int i = 0; i < 5; i += 1) {
            sample.open(i, 0);
        }
        for (int i = 0; i < 10; i += 1) {
            sample.open(4, i);
        }
        for (int i = 5; i < 10; i += 1) {
            sample.open(i, 9);
        }
        System.out.println(sample.isFull(4,4));
        System.out.println(sample.percolates());
        System.out.println(sample.numberOfOpenSites());
    }
}
