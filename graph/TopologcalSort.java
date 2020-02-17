
import java.util.ArrayList;
import java.util.List;

public class TopologcalSort {
	private static boolean visit(Graph g, int v, List<Integer> order, int[] color) {
		color[v] = 1;
		for (Graph.Path e : g.get(v)) {
			if (color[e.to] == 2)
				continue;
			if (color[e.to] == 1)
				return false;
			if (!visit(g, e.to, order, color))
				return false;
		}
		order.add(v);
		color[v] = 2;
		return true;
	}

	public static int[] sort(Graph g) {
		int n = g.size();
		int[] color = new int[n];
		List<Integer> order = new ArrayList<>();
		for (int u = 0; u < n; u++)
			if (color[u] == 0 && !visit(g, u, order, color))
				return null;

		int[] ret = new int[n];
		for (int i = 0; i < n; i++) {
			ret[n - i - 1] = order.get(i);
		}
		return ret;
	}

	// matrinx dictional order O(n^3)
	private static int[] topological(boolean[][] E) {
		int n = E.length;
		boolean[] V = new boolean[n];
		int[] R = new int[n];
		int idx = 0;
		for (int i = 0; i < n; i++) {
			int c = -1;
			for (int j = 0; j < n && c == -1; j++)
				if (!V[j]) {
					boolean f = true;
					for (int k = 0; k < n && f; k++)
						if (!V[k] && E[k][j])
							f = false;
					if (f)
						c = j;
				}
			if (c == -1)
				return null;
			V[c] = true;
			R[idx++] = c;
		}
		return R;
	}
}
