//import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Stack;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        Edge newEdge = new Edge(v1, v2, weight);
        adjLists[v1].add(newEdge);
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        for (int i = 0; i < adjLists[from].size(); i++) {
            if (adjLists[from].get(i).to == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        List<Integer> toReturn = new ArrayList();
        for (int i = 0; i < adjLists[v].size(); i++) {
            toReturn.add(i, adjLists[v].get(i).to);
        }
        return toReturn;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        int count = 0;
        for (int i = 0; i < adjLists.length; i++) {
            for (int j = 0; j < adjLists[i].size(); j++) {
                if (adjLists[i].get(j).to == v) {
                    count++;
                }
            }
        }
        return count;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /* A class that iterates through the vertices of this graph, starting with
       vertex START. If the iteration from START has no path from START to some
       vertex v, then the iteration will not include v. */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        DFSIterator(int start) {
            fringe = new Stack<Integer>();
            visited = new HashSet<Integer>();
            fringe.push(start);
        }

        public boolean hasNext() {
            return !visited.containsAll(fringe);
        }

        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Graph ran out of elements");
            }
            int vertice = fringe.pop();
            //while (fringe.contains(vertice)) {
                //fringe.remove(vertice);
                //fringe.r
            //}
            //if (!fringe.contains(vertice)) {
            visited.add(vertice);
            //}
            for (Edge e : adjLists[vertice]) {
                if (!visited.contains(e.to)) {
                    fringe.push(e.to);
                }
            }
            return vertice;



        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }


    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        if (start == stop) {
            return true;
        } else {
            //get all the vertices with a start vertice
            List<Integer> vertices = dfs(start);
            return vertices.contains(stop);
        }

    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        ArrayList<Integer> path = new ArrayList();
        if (!pathExists(start, stop)) {
            return path;
        } else if (start == stop) {
            path.add(start);
            return path;
        } else {
            ArrayList<Integer> visited = new ArrayList<>();
            Iterator<Integer> iter = new DFSIterator(start);
            //First, add code to stop calling next when you encounter the finish vertex.
            while (iter.hasNext()) {
                int v = iter.next();
                if (v != stop) {
                    visited.add(v);
                } else {
                    //visited.add(v);
                    path.add(stop);
                    break;
                }

            }
            /*
            Then, trace back from the finish vertex to the start,
            by first finding a visited vertex u for which (u, finish) is an edge,
            then a vertex v visited earlier than u for which (v, u) is an edge,
            and so on, finally finding a vertex w for which (start, w) is an edge
            (isAdjacent may be useful here!)
             */
            //trace back
            int vEarlier = stop;
            for (int i = visited.size() - 1; i >= 0; i--) {
                if (isAdjacent(visited.get(i), vEarlier)) {
                    path.add(0, visited.get(i));
                    vEarlier = visited.get(i);
                }
                if (isAdjacent(start, vEarlier)) {
                //if (visited.get(i) == start && isAdjacent(start, vEarlier)) {
                //if (visited.get(i) == start) {
                    path.add(0, start);
                    ///path.add(0, start);
                    return path;
                }
            }
        }
        return path;
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;

        // To do

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            // To do
        }

        public boolean hasNext() {
            // To do
            return false;
        }

        public Integer next() {
            // To do
            return 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

    }

    public void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(5);
        g1.generateG1();
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();


    }
}
