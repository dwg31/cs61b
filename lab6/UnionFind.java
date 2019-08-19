/**
 * A class that uses Weighted Quick Union
 * data structure to implement Union Find.
 *
 * @author Dawei Gu
 */

public class UnionFind {
    /** Instance variable. Stores the parent of every element. */
    private int[] parent;

    /** Creates a UnionFind data structure holding n vertices.
     * Initially, all vertices are in disjoint sets.
     *
     * @param n The length of the Union Find structure.
     */
    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i += 1) {
            parent[i] = -1;
        }
    }

    /** Throws an exception if v1 is not a valid index.
     *
     * @param vertex Vertex to be validated.
     */
    private void validate(int vertex) {
        if (vertex < 0 || vertex >= parent.length) {
            throw new IllegalArgumentException("");
        }
    }

    /**
     * @param v1 Set V1.
     * @return the size of the set v1 belongs to.
     */
    public int sizeOf(int v1) {
        validate(v1);
        return -parent(find(v1));
    }

    /**
     * @param v1 Set v1.
     * @return the parent of v1. If v1 is the root of a tree, returns
     * the negative size of the tree for which v1 is the root.
     */
    public int parent(int v1) {
        validate(v1);
        return parent[v1];
    }

    /** Returns true if nodes v1 and v2 are connected.
     *
     * @param v1 Set v1.
     * @param v2 Set v2.
     * @return true if they are connected.
     */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /** Connects two elements v1 and v2 together. v1 and v2 can be any valid
     * elements, and a union-by-size heuristic is used. If the sizes of the sets
     * are equal, tie break by connecting v1's root to v2's root. Unioning a
     * vertex with itself or vertices that are already connected should not
     * change the sets but may alter the internal structure of the data.
     *
     * @param v1 Set v1.
     * @param v2 Set v2.
     */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);

        int r1 = find(v1);
        int r2 = find(v2);
        int size1 = sizeOf(v1);
        int size2 = sizeOf(v2);

        if (connected(v1, v2)) {
            parent[v1] = r1;
            parent[v2] = r2;
        } else if (size1 > size2) {
            parent[r2] = r1;
            parent[r1] = -(size1 + size2);
        } else {
            parent[r1] = r2;
            parent[r2] = -(size1 + size2);
        }
    }

    /**
     * Returns the root of the set V belongs to.
     * Path-compression is employed allowing for fast search-time.
     *
     * @param vertex Set V.
     * @return the root of Set V.
     */

    public int find(int vertex) {
        int ptr = vertex;
        int ptr1 = ptr;
        while (parent(vertex) >= 0) {
            vertex = parent(vertex);
        }

        while (parent(ptr) > 0) {
            ptr1 = ptr;
            parent[ptr] = vertex;
            ptr = parent(ptr1);
        }
        return vertex;
    }
}
