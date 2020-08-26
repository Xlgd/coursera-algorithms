import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] grid;
    private final int gridN;
    private final WeightedQuickUnionUF uf;
    private final WeightedQuickUnionUF ufFull;
    private final int virtualTopRow;
    private final int virtualBottomRow;
    private int openSitesCounter = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n is illegal (" + n + ")");
        }
        grid = new boolean[n + 1][n + 1];
        uf = new WeightedQuickUnionUF(n * n);
        ufFull = new WeightedQuickUnionUF(n * n + 2);
        gridN = n;
        virtualTopRow = n * n;
        virtualBottomRow = n * n + 1;
        for (int i = 1; i <= gridN; i++) {
            for (int j = 1; j <= gridN; j++) {
                grid[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > gridN || col <= 0 || col > gridN) {
            throw new IllegalArgumentException("Index is out of bounds (" + row + ", " + col + ")");
        }

        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSitesCounter++;

            // union the neighbour sites, first calculate the sites index in uf.
            int ufOpenIndex = (row - 1) * gridN + col - 1;
            int ufOpenTop = -1;
            int ufOpenRight = -1;
            int ufOpenBottom = -1;
            int ufOpenLeft = -1;

            // union the top or bottom row sites with the virtual sits.
            if (row == 1) {
                ufFull.union(virtualTopRow, ufOpenIndex);
            }
            if (row == gridN) {
                ufFull.union(virtualBottomRow, ufOpenIndex);
            }


            if (row - 1 > 0) {
                ufOpenTop = (row - 2) * gridN + col - 1;
            }
            if (col + 1 <= gridN) {
                ufOpenRight = (row - 1) * gridN + col;
            }
            if (row + 1 <= gridN) {
                ufOpenBottom = row * gridN + col - 1;
            }
            if (col - 1 > 0) {
                ufOpenLeft = (row - 1) * gridN + col - 2;
            }

            if ((ufOpenTop != -1) && (isOpen((row - 1), col))) {
                uf.union(ufOpenIndex, ufOpenTop);
                ufFull.union(ufOpenIndex, ufOpenTop);
            }
            if ((ufOpenRight != -1) && (isOpen(row, (col + 1)))) {
                uf.union(ufOpenIndex, ufOpenRight);
                ufFull.union(ufOpenIndex, ufOpenRight);
            }
            if ((ufOpenBottom != -1) && (isOpen((row + 1), col))) {
                uf.union(ufOpenIndex, ufOpenBottom);
                ufFull.union(ufOpenIndex, ufOpenBottom);
            }
            if ((ufOpenLeft != -1) && (isOpen(row, (col - 1)))) {
                uf.union(ufOpenIndex, ufOpenLeft);
                ufFull.union(ufOpenIndex, ufOpenLeft);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > gridN || col <= 0 || col > gridN) {
            throw new IllegalArgumentException("Index is out of bounds (" + row + ", " + col + ")");
        }

        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > gridN || col <= 0 || col > gridN) {
            throw new IllegalArgumentException("Index is out of bounds (" + row + ", " + col + ")");
        }

        if (!isOpen(row, col)) {
            return false;
        }

        int index = (row - 1) * gridN + col - 1;
        if (ufFull.find(index) == ufFull.find(virtualTopRow)) {
            return true;
        }
        // for (int i = 0; i < gridN; i++) {
        //     if (uf.find(index) == uf.find(i)) {
        //         return true;
        //     }
        // }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCounter;
    }

    // does the system percolate?
    public boolean percolates() {
        if (ufFull.find(virtualTopRow) == ufFull.find(virtualBottomRow)) {
            return true;
        }
        // for (int i = 1; i <= gridN; i++) {
        //     if (isFull(gridN, i)) {
        //         return true;
        //     }
        // }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation testPercolation = new Percolation(2);
        testPercolation.open(1, 1);
        System.out.println("isOpen(1, 1): " + testPercolation.isOpen(1, 1));
        System.out.println("isFull(1, 1): " + testPercolation.isFull(1, 1));
        System.out.println("percolates? " + testPercolation.percolates());

        testPercolation.open(2, 1);
        System.out.println("isOpen(2, 1): " + testPercolation.isOpen(2, 1));
        System.out.println("isFull(1, 1): " + testPercolation.isFull(1, 1));
        System.out.println("isFull(2, 1): " + testPercolation.isFull(2, 1));
        System.out.println("percolates? " + testPercolation.percolates());
        System.out.println("numberOfOpenSites: " + testPercolation.numberOfOpenSites());

    }
}
