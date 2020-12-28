import org.junit.Test;
import static org.junit.Assert.*;

public class PercolationTest {

    @Test
    public void testConstructor() {
        Percolation test1 = new Percolation(4);
        int numRow = 0;
        int numCol = 0;
        int total = 0;
        for(int i = 0; i < test1.size; i++) {
            numRow++;
            for(int j = 0; j < test1.size; j++) {
                if( !test1.grid[i][j] ) {
                    total++;
                }
            }
        }
        assertEquals( 4, numRow);
        assertEquals(16,total);
    }

    @Test
    public void testOpen() {
        Percolation test2 = new Percolation(4);
        test2.open(0, 0);
        int node1 =  test2.xyTo1D(0,0);
        assertEquals(true, test2.grid[0][0]);
        test2.open(0,1);
        int node2 =  test2.xyTo1D(0,1);
        //test2.wqf.connected(node1, node2);
        assertEquals(true, test2.wqf.connected(node1, node2));

        test2.open(0, 3);
        int node3 = test2.xyTo1D(0,3);
        assertEquals(true, test2.grid[0][3]);
        test2.open(1, 3);
        int node4 = test2.xyTo1D(1,3);
        assertEquals(true, test2.wqf.connected(node3, node4));

        test2.open(3, 3);
        int node5 = test2.xyTo1D(3,3);
        assertEquals(true, test2.grid[3][3]);
        test2.open(3, 2);
        int node6 = test2.xyTo1D(3,2);
        assertEquals(true, test2.wqf.connected(node5, node6));

        test2.open(3, 0);
        int node7 = test2.xyTo1D(3,0);
        assertEquals(true, test2.grid[3][0]);
        test2.open(2, 0);
        int node8 = test2.xyTo1D(2,0);
        assertEquals(true, test2.wqf.connected(node5, node6));

        test2.open(2,1);
        int node9 = test2.xyTo1D(2,1);
        assertEquals(true, test2.wqf.connected(node9, node7));

    }

    @Test
    public void testIsOpen() {
        Percolation test2 = new Percolation(4);
        test2.open(0, 0);
        test2.open(0,1);
        assertEquals(true, test2.isOpen(0, 0));
        assertEquals(true, test2.isOpen(0,1));

        assertEquals(false, test2.isOpen(2,2));

    }

    @Test
    public void testIsFull() {
        Percolation test2 = new Percolation(4);

        test2.open(0, 0);
        int node1 =  test2.xyTo1D(0,0);
        assertEquals(true, test2.isFull(0,0));

        test2.open(0,1);
        int node2 = test2.xyTo1D(0,1);
        assertEquals(true, test2.isFull(0,1));

        test2.open(3, 0);
        int node7 = test2.xyTo1D(3,0);
        assertEquals(false, test2.isFull(3,0));

    }

    @Test
    public void testNumberOfOpenSites() {
        Percolation test2 = new Percolation(4);
        test2.open(0, 0);
        test2.open(1, 0);
        test2.open(2, 0);
        test2.open(3, 0);
        assertEquals(4, test2.numberOfOpenSites());
    }

    @Test
    public void testPercolates() {
        Percolation test2 = new Percolation(4);
        test2.open(0, 0);
        test2.open(1, 0);
        test2.open(2, 0);
        test2.open(3, 0);
        assertEquals(true, test2.percolates());
    }


}
