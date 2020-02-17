import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

class StronglyConnectedComponents {
	private static int visit(Graph g, int v, List<Set<Integer>> scc, Stack<Integer> S, boolean[] inS, int[] low,
			int[] num, int time) {
		low[v] = num[v] = ++time;

		S.push(v);
		inS[v] = true;
		for (Graph.Path p : g.get(v)) {
			int w = p.to;
			if (num[w] == 0) {
				visit(g, w, scc, S, inS, low, num, time);
				low[v] = Math.min(low[v], low[w]);
			} else if (inS[w])
				low[v] = Math.min(low[v], low[w]);
		}
		if (low[v] == num[v]) {
			Set<Integer> list = new HashSet<Integer>();
			int w = 0;
			do {
				w = S.pop();
				inS[w] = false;
				list.add(w);
			} while (v != w);
			scc.add(list);
		}
		return time;
	}

	public static List<Set<Integer>> disasm(Graph g) {
		int n = g.size();
		int[] num = new int[n];
		int[] low = new int[n];
		Stack<Integer> S = new Stack<>();
		boolean[] inS = new boolean[n];

		List<Set<Integer>> scc = new ArrayList<>();
		
		int time = 0;
		for (int u = 0; u < n; u++) {
			if (num[u] == 0) {
				time = visit(g, u, scc, S, inS, low, num, time);
			}
		}
		return scc;
	}
	
	public static Graph sccGraph(Graph org, List<Set<Integer>> scc) {
		int size = scc.size();
		int n = org.size();
		int[] table = new int[n];

		for (int i = 0; i < n ; i ++) {
			int index = 0;
			while (!scc.get(index).contains(i)) {
				index ++;
			}
			table[i] = index;
		}
		
		Graph g = new Graph(size);
		boolean[] connected = new boolean[size];
		for (int i = 0; i < n; i ++) {
			Arrays.fill(connected, false);
			int from = table[i];
			for (Graph.Path p : org.get(i)) {
				int to = table[p.to];
				if (!connected[to]) {
					g.add(from, to);
					connected[to] = true;
				}
			}
		}
		return g;
	}
}
