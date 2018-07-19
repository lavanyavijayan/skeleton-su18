public class UnionFind {

    /* This array contains the parent of each node that is not a root.
    If the node is a root, the array contains the negative size of the tree the node is the root for. */
    private int[] id;

    /* Creates a UnionFind data structure holding N vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int N) {
      id = new int[N];
      for (int i = 0; i < N; i++) {
        id[i] = -1;
      }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
      validate(v);
      int idOfV = id[v];
      if (idOfV < 0) {
        return -1 * idOfV;
      } else {
        return -1 * id[idOfV];
      }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
      validate(v);
      return id[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
      validate(v1);
      validate(v2);
      return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid vertices are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
      validate(v);
      int curr = v;
      while (id[curr] >= 0) {
        id[curr] = id[id[curr]];
        curr = id[curr];
      }
      return curr;
    }

    /* Connects two elements V1 and V2 together. V1 and V2 can be any element,
       and a union-by-size heuristic is used. If the sizes of the sets are
       equal, tie break by connecting V1's root to V2's root. Union-ing a vertex
       with itself or vertices that are already connected should not change the
       structure. */
    public void union(int v1, int v2) {
      validate(v1);
      validate(v2);
      if (v1 == v2 || connected(v1, v2)) {
        return;
      }
      int root1 = find(v1);
      int root2 = find(v2);
      if (sizeOf(root1) < sizeOf(root2)) {
        id[root2] += sizeOf(root1);
        id[root1] = root2;
      } else if (sizeOf(root2) < sizeOf(root1)) {
        id[root1] += sizeOf(root2);
        id[root2] = root1;
      } else {  // sizes of the sets are equal --> break tie
        id[root2] += id[root1];
        id[root1] = root2;
      }
    }

    private void validate(int v) {
      validate(v);
      int len = id.length;
      if (v < 0 || v >= len) {
        throw new IllegalArgumentException("index" + v + " is invalid. index must be between 0 and " + (len - 1));
      }
    }
}
