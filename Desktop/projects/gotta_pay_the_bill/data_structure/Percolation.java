// import edu.princeton.cs.algs4.QuickFindUF;
// import edu.princeton.cs.algs4.QuickUnionUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    public boolean[][] grid;
    public int size;
    public int openSite;
    public WeightedQuickUnionUF wqf;
    int vTop = 0;
    int vBottom;

    /* Creates an N-by-N grid with all sites initially blocked. */
    public Percolation(int N) {
        if (N < 0) {
            throw new IllegalArgumentException("size needs to be greater than 0");
        }
        size = N;
        grid = new boolean[N][N];
        wqf = new WeightedQuickUnionUF((N * N));
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
            vBottom = size*(size - 1);
            //vBottom = size*size + 1;

    }

    /* Opens the site (row, col) if it is not open already. */
    public void open(int row, int col) {

        if (!valid(row, col)) throw new ArrayIndexOutOfBoundsException("Out Of Bound");

        if (!grid[row][col]) {
            grid[row][col] = true;
            openSite++;
        }

        int current = xyTo1D(row, col);

        if (row == vTop) {
            wqf.union(current, vTop);
        }

        if (row == vBottom/size) {
            wqf.union(current, vBottom);
        }
        //up
        if (((row - 1) >= 0) && grid[row - 1][col]) {
            int up = xyTo1D(row - 1, col);
            wqf.union(current, up);
        }
        //down
        if (((row + 1) <= size - 1) && grid[row + 1][col]) {
            int down = xyTo1D(row + 1, col);
            wqf.union(current, down);
        }
        //right
        if (((col+1) <= size - 1) && grid[row][col+1]) {
            int right = xyTo1D(row, col +1 );
            wqf.union(current, right);
        }
        //left
        if(((col-1) >= 0) && grid[row][col - 1]) {
            int left = xyTo1D(row, col - 1);
            wqf.union(current, left);
        }

        //System.out.println(wqf.find(xyTo1D(row,col)));
        //System.out.println("vTop" + vTop );
        //System.out.println("vBottom" + vBottom);

    }



    /* Returns true if the site at (row, col) is open. */
    public boolean isOpen(int row, int col) {
        if(!valid(row, col)) {
            throw new ArrayIndexOutOfBoundsException("Out Of Bound");
        }
        return grid[row][col];
    }

    /* Returns true if the site (row, col) is full. */
    public boolean isFull(int row, int col) {
        if(row >= 0 && row < size && col >= 0 && col < size){
            if (isOpen(row,col)) {
                int node = xyTo1D(row, col);
                return wqf.connected(node, vTop);
            } else {
                return false;
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Out Of Bound");
        }

    }

    /* Returns the number of open sites. */
    public int numberOfOpenSites() {
        return openSite;
    }

    /* Returns true if the system percolates. */
    public boolean percolates() {
        if (openSite < 1) {
            return false;
        }
        return wqf.connected(vTop, vBottom);
    }

    /* Converts row and column coordinates into a number. This will be helpful
       when trying to tie in the disjoint sets into our NxN grid of sites. */

    //change this to public for testing but it was private to begin with
    public int xyTo1D(int row, int col) {
        if (!valid(row,col)){
            throw new ArrayIndexOutOfBoundsException("Out Of Bound");
        }
        if(row == 0) {
            return col;
        } else {
            return (row * size + col);
        }
    }
    /* Returns true if (row, col) site exists in the NxN grid of sites.
       Otherwise, return false. */
    private boolean valid(int row, int col) {
        if(row > size || col > size || row < 0 || col < 0) {
            return false;
        } else {
            return true;
        }
    }
    /*
    public static void main (String[] args) {
        Percolation test2 = new Percolation(4);
        test2.xyTo1D(0,0);
        System.out.println(test2.xyTo1D(0,0));
        System.out.println(test2.xyTo1D(3,3));
        System.out.println(test2.xyTo1D(1,2));
    }
    */
}
