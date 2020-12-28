import org.junit.Test;
import static org.junit.Assert.*;

public class UnionFindTest {
    @Test
    public void testConstructor() {
        UnionFind testArray = new UnionFind(3);
        assertEquals(3, testArray.array.length);
        assertEquals(-1, testArray.array[0] = -1);
        assertEquals(-1, testArray.array[1] = -1);
    }

    @Test
    public void testSizeOf() {
        UnionFind testArray1 = new UnionFind(6);
        testArray1.array[0] = -1;
        testArray1.array[1] = -2;
        testArray1.array[2] = -3;
        testArray1.array[3] = 1;
        testArray1.array[4] = 2;
        testArray1.array[5] = 4;

        assertEquals(testArray1.sizeOf(0), 1);
        assertEquals(testArray1.sizeOf(1), 2);
        assertEquals(testArray1.sizeOf(2), 3);
        assertEquals(testArray1.sizeOf(3), 2);
        assertEquals(testArray1.sizeOf(4), 3);
        assertEquals(testArray1.sizeOf(5), 3);

    }

    @Test
    public void testParent() {
        UnionFind testArray2 = new UnionFind(6);
        testArray2.array[0] = -1;
        testArray2.array[1] = -2;
        testArray2.array[2] = -3;
        testArray2.array[3] = 1;
        testArray2.array[4] = 2;
        testArray2.array[5] = 4;

        assertEquals(testArray2.parent(0), -1);
        assertEquals(testArray2.parent(3), 1);
        assertEquals(testArray2.parent(5), 4);
        assertEquals(testArray2.parent(4), 2);
        assertEquals(testArray2.parent(2), -3);

    }

    @Test
    public void testConnected() {
        UnionFind testArray3 = new UnionFind(6);
        testArray3.array[0] = -1;
        testArray3.array[1] = -2;
        testArray3.array[2] = -3;
        testArray3.array[3] = 1;
        testArray3.array[4] = 2;
        testArray3.array[5] = 4;

        assertEquals(false, testArray3.connected(0, 1));
        assertEquals(true, testArray3.connected(1, 3));
        assertEquals(true, testArray3.connected(2, 4));
        assertEquals(false, testArray3.connected(3, 4));
    }


    @Test
    public void testFind() {
        UnionFind testArray4 = new UnionFind(6);
        testArray4.array[0] = -1;
        testArray4.array[1] = -2;
        testArray4.array[2] = -3;
        testArray4.array[3] = 1;
        testArray4.array[4] = 2;
        testArray4.array[5] = 4;

        assertEquals(0, testArray4.find(0));
        //assertEquals(1,testArray4.find(3));
        assertEquals(1, testArray4.find(1));
        assertEquals(1, testArray4.find(3));
        assertEquals(2, testArray4.find(2));
        assertEquals(2, testArray4.find(4));
        assertEquals(2, testArray4.find(5));
    }

    @Test
    public void testUnion(){
        UnionFind testArray5 = new UnionFind(10);
        testArray5.union(4,3);
        assertEquals(2,testArray5.sizeOf(4));
        assertEquals(2,testArray5.sizeOf(3));
        assertEquals(4,testArray5.parent(3));
        assertEquals(4, testArray5.find(3));

        testArray5.union(3,8);
        assertEquals(3,testArray5.sizeOf(4));
        assertEquals(3,testArray5.sizeOf(8));
        assertEquals(4,testArray5.parent(8));

        testArray5.union(6,5);
        assertEquals(2,testArray5.sizeOf(6));
        assertEquals(2,testArray5.sizeOf(5));
        assertEquals(6,testArray5.parent(5));


        testArray5.union(9,4);
        assertEquals(4,testArray5.sizeOf(4));
        assertEquals(4,testArray5.sizeOf(9));
        assertEquals(4,testArray5.parent(3));
        assertEquals(4,testArray5.parent(9));

        testArray5.union(2,1);
        assertEquals(2,testArray5.sizeOf(2));
        assertEquals(2,testArray5.sizeOf(1));
        assertEquals(-2,testArray5.parent(2));
        assertEquals(2, testArray5.parent(1));
        assertEquals(2, testArray5.find(1));

        testArray5.union(5,0);
        assertEquals(3,testArray5.sizeOf(6));
        assertEquals(3,testArray5.sizeOf(5));
        assertEquals(3,testArray5.sizeOf(0));
        assertEquals(6,testArray5.parent(0));
        assertEquals(-3, testArray5.parent(6));
        assertEquals(6, testArray5.find(0));

        testArray5.union(7,2);
        assertEquals(3,testArray5.sizeOf(7));
        assertEquals(3,testArray5.sizeOf(1));
        assertEquals(3,testArray5.sizeOf(2));
        assertEquals(2,testArray5.parent(7));
        assertEquals(2, testArray5.parent(1));
        assertEquals(-3, testArray5.parent(2));

        assertEquals(2, testArray5.find(7));
        assertEquals(2,  testArray5.find(1));

        testArray5.union(6,1);
        assertEquals(6,testArray5.sizeOf(6));
        assertEquals(6,testArray5.sizeOf(5));
        assertEquals(6,testArray5.parent(1));
        assertEquals(6,testArray5.find(1));
        assertEquals(6,testArray5.find(7));
        assertEquals(6,testArray5.parent(2));
        assertEquals(6,testArray5.sizeOf(7));

        UnionFind testArray6 = new UnionFind(6);
        testArray6.array[0] = -1;
        testArray6.array[1] = -2;
        testArray6.array[2] = -3;
        testArray6.array[3] = 1;
        testArray6.array[4] = 2;
        testArray6.array[5] = 4;
        //testArray6.union(1,2);




   }
}

