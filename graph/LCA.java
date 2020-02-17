import java.util.ArrayDeque;
import java.util.Arrays;

class LCA {
	private final int logN;
	private final int n;
	private final Graph graph;
	private final int[][] par;
	public final int[] depth;
 
	public LCA(Graph g, int root) {
		this.graph = g;
		this.n = g.size();
		this.logN = Integer.numberOfTrailingZeros(Integer.highestOneBit(n - 1)) + 1;
		this.par = new int[logN][n];
		this.depth = new int[n];
 
		init(root);
	}
 
	private void init(int root) {
		// dfs(root, -1, 0);
		bfs(root);
		for (int k = 0; k < logN - 1; k++) {
			for (int v = 0; v < n; v++) {
				if (par[k][v] < 0)
					par[k + 1][v] = -1;
				else
					par[k + 1][v] = par[k][par[k][v]];
			}
		}
	}
 
	private void bfs(int v) {
		Arrays.fill(depth, Integer.MAX_VALUE);
		ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
		queue.add(v);
		depth[v] = 0;
		par[0][v] = -1;
		while (!queue.isEmpty()) {
			int now = queue.poll();
			for (Graph.Path p : graph.get(now)) {
				if (depth[p.to] > depth[now] + 1) {
					depth[p.to] = depth[now] + 1;
					queue.add(p.to);
					par[0][p.to] = now;
				}
			}
		}
	}
 
	public int lca(int u, int v) {
		if (depth[u] > depth[v]) {
			int tmp = u;
			u = v;
			v = tmp;
		}
		for (int k = 0; k < logN; k++) {
			if (((depth[v] - depth[u]) >> k & 1) == 1)
				v = par[k][v];
		}
		if (u == v)
			return u;
 
		for (int k = logN - 1; k >= 0; k--) {
			if (par[k][u] != par[k][v]) {
				u = par[k][u];
				v = par[k][v];
			}
		}
		return par[0][u];
	}
}