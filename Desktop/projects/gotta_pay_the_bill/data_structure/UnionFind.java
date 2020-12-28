import java.util.ArrayList;
public class UnionFind {
    public int[] array; // the init array

    /* Creates a UnionFind data structure holding N vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int N) {
        array = new int[N];
        for (int i = 0; i < N; i++) {
            array[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        if (array[v] < 0) {
            return -array[v];
        } else {
            int findNeg = array[v];
            while (findNeg > 0) {
                findNeg = array[findNeg];
            }
            return -findNeg;
        }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (array[v] > 0) {
            return array[v];
        } else {
            return -sizeOf(v);
        }
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid vertices are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0) {
            throw new IllegalArgumentException("vertices can not be less than 0");
        }
        if (v > array.length) {
            throw new IllegalArgumentException("Out of Bound, "
                    + "vertices can not be longer than array");
        }
        if (array[v] < 0) {
            return v;
        } else {
            int findNeg = v;
            ArrayList<Integer> children = new ArrayList<Integer>();
            while (array[findNeg] > 0) {
                children.add(findNeg);
                findNeg = array[findNeg];
            }
            for (int i = 0; i < children.size(); i++) {
                array[children.get(i)] = findNeg;
            }
            return findNeg;
        }

    }

    /* Connects two elements V1 and V2 together. V1 and V2 can be any element,
       and a union-by-size heuristic is used. If the sizes of the sets are
       equal, tie break by connecting V1's root to V2's root. Union-ing a vertex
       with itself or vertices that are already connected should not change the
       structure. */
    public void union(int v1, int v2) {
        //check connected
        if (connected(v1, v2)) {
            return;
        } else {
            if (sizeOf(v1) == sizeOf(v2)) {
                array[find(v2)] += (-sizeOf(v1));
                array[find(v1)] = find(v2);
                array[v1] = find(v2);
            } else if (sizeOf(v1) > sizeOf(v2)) {
                array[find(v1)] += (-sizeOf(v2));
                array[v2] = find(v1);
            } else {
                array[find(v2)] += (-sizeOf(v1));
                array[v1] = find(v2);
            }
        }


    }
}
