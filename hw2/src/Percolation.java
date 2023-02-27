import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF square;
    private WeightedQuickUnionUF square2;
    private boolean[][] id;
    private int rowLength;
    private int size;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        id = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                id[i][j] = false;
            }
        }
        square = new WeightedQuickUnionUF(N * N + 2);
        square2 = new WeightedQuickUnionUF(N * N + 1);
        rowLength = N;
        size = 0;
    }

    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= id.length || col >= id.length) {
            throw new IndexOutOfBoundsException();
        }
        if (!id[row][col]) {
            id[row][col] = true;
            size += 1;
            if (row - 1 >= 0 && id[row - 1][col]) {
                square.union(xyTo1D(row - 1, col), xyTo1D(row, col));
                square2.union(xyTo1D(row - 1, col), xyTo1D(row, col));
            }
            if (col - 1 >= 0 && id[row][col - 1]) {
                square.union(xyTo1D(row, col - 1), xyTo1D(row, col));
                square2.union(xyTo1D(row, col - 1), xyTo1D(row, col));
            }
            if (col + 1 < rowLength && id[row][col + 1]) {
                square.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                square2.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
            if (row + 1 < rowLength && id[row + 1][col]) {
                square.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                square2.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }

            if (row == 0) {
                square.union(xyTo1D(row, col), rowLength * rowLength);
                square2.union(xyTo1D(row, col), rowLength * rowLength);
            }
            if (row == rowLength - 1) {
                if (!this.percolates()) {
                    square.union(xyTo1D(row, col), rowLength * rowLength + 1);
                }
            }
        }
    }

    public int xyTo1D(int r, int c) {
        return r * rowLength + c;
    }

    public boolean isOpen(int row, int col) {
        if (id[row][col]) {
            return true;
        }
        return false;
    }

    public boolean isFull(int row, int col) {
        if (square2.connected(xyTo1D(row, col), rowLength * rowLength)) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return size;
    }

    public boolean percolates() {
        if (square.connected(rowLength * rowLength, rowLength * rowLength + 1)) {
            return true;
        }
        return false;
    }

}
