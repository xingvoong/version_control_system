// Source: Arthur helped me setting up this file.


import static org.junit.Assert.*;
import org.junit.Test;
//import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

public class GraphTesting {

    @Test
    public void basicTest() {
        Graph g1 = new Graph(5);
        g1.generateG1();
        assertTrue(g1.isAdjacent(0, 1));
        assertFalse(g1.isAdjacent(1, 0));
        assertTrue(g1.isAdjacent(0, 2));
        assertTrue(g1.isAdjacent(2, 0));

        List<Integer> g10Neighbors = new LinkedList<>(Arrays.asList(1, 2, 4));
        for (Integer neighbor: g10Neighbors) {
            assertTrue(g1.neighbors(0).contains(neighbor));
        }

        assertEquals(1, g1.inDegree(0));
        assertEquals(1, g1.inDegree(1));
        assertEquals(2, g1.inDegree(2));
        assertEquals(2, g1.inDegree(3));
        assertEquals(1, g1.inDegree(4));
    }

    @Test
    public void pathExistsTest() {
        Graph g1 = new Graph(5);
        g1.generateG1();
        assertTrue(g1.pathExists(0, 1));
        assertTrue(g1.pathExists(1, 0));
        assertTrue(g1.pathExists(2, 3));
        assertFalse(g1.pathExists(3, 2));
        assertFalse(g1.pathExists(3, 0));
    }

    @Test
    public void pathTesting() {
        Graph g1 = new Graph(7);
        g1.addEdge(0, 1);
        g1.addEdge(0, 2);
        g1.addEdge(0, 6);
        List<Integer> path1 = g1.path(0, 1);
        assertEquals(0, (int) path1.get(0));
        assertEquals(1, (int) path1.get(1));




    }
}
