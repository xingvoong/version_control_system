import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Arrays;

public class MinHeapTesting {

    @Test
    public void insertTesting() {
        MinHeap mHeap = new MinHeap();
        mHeap.insert(1);
        System.out.println(Arrays.toString(mHeap.contents.toArray()));
        assertEquals(1, mHeap.peek());
        mHeap.insert(6);
        System.out.println(Arrays.toString(mHeap.contents.toArray()));
        assertEquals(1, mHeap.peek());
        mHeap.insert(5);
        System.out.println(Arrays.toString(mHeap.contents.toArray()));
        mHeap.insert(7);
        System.out.println(Arrays.toString(mHeap.contents.toArray()));
        mHeap.insert(8);
        System.out.println(Arrays.toString(mHeap.contents.toArray()));
        mHeap.insert(2);
        System.out.println(Arrays.toString(mHeap.contents.toArray()));
        assertEquals(1, mHeap.peek());
        assertEquals(2, mHeap.getElement(3));


        //assertEquals(2, mHeap.getElement())
    }
    @Test
    public void sizeTesting() {
        MinHeap mHeap = new MinHeap();
        assertEquals(0, mHeap.size());
        mHeap.insert(1);
        mHeap.insert(6);
        mHeap.insert(5);
        mHeap.insert(7);
        mHeap.insert(8);
        mHeap.insert(2);
        assertEquals(6, mHeap.size());
    }

    @Test
    public void removeMinTesting() {
        MinHeap h = new MinHeap();
        h.insert(3);
        h.insert(4);
        h.insert(7);
        h.insert(9);
        h.insert(10);
        h.insert(8);
        h.insert(11);
        h.removeMin();
        System.out.println(Arrays.toString(h.contents.toArray()));
        assertEquals(4, h.peek());
        assertEquals(9, h.getElement(2));
        assertEquals(7, h.getElement(3));
        assertEquals(11, h.getElement(4));
        assertEquals(10, h.getElement(5));
        //assertEquals(8, h.geElement(6));


    }

    public void getMinTesting() {

    }


}
