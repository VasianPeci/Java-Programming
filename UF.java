public class UF {
    private int[] id; // access to component id (site-indexed)
    private int count; // number of components

    public UF (int N) {
        // Initialize component id array
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public int count () {
        return count;
    }

    private boolean connected (int p, int q) {
        return find(p) == find(q);
    }

    private int find (int p) {
        // Find component name.
        while (p != id[p]) p = id[p];
        return p;
    }

    public void union (int p, int q) {
        // Give p and q the same root.
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;

        id[pRoot] = qRoot;

        count--;
    }
}
