import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class SpannigTree {
	public static long prim(Graph graph, int start) {
		return prim(graph, start, new ArrayList<Graph.Path>());
	}

	public static long prim(Graph graph, int start, List<Graph.Path> output) {
		int n = graph.size();
		boolean[] visited = new boolean[n];

		PriorityQueue<Graph.Path> queue = new PriorityQueue<>();
		queue.add(new Graph.Path(start, start, 0));
		long totalCost = 0;
		while (!queue.isEmpty()) {
			Graph.Path edge = queue.poll();
			if (visited[edge.to]) {
				continue;
			}
			output.add(edge);
			visited[edge.to]= true;
			totalCost += edge.cost;
			
			for (Graph.Path p : graph.get(edge.to)) {
				if (!visited[p.to]) {
					queue.add(p);
				}
			}
		}
		return totalCost;
	}

	public static long kruskal(Graph graph) {
		return kruskal(graph, new HashSet<Graph.Path>());
	}

	public static long kruskal(Graph graph, Set<Graph.Path> output) {
		int n = graph.size();
		UnionFind uf = new UnionFind(n);
		
		List<Graph.Path> list = new ArrayList<>();
		for (int i = 0; i < n; i ++) {
			for (Graph.Path edge : graph.get(i)) {
				if (i < edge.to) {
					list.add(edge);
				}
			}
		}
		Collections.sort(list);

		long ret = 0;
		for (Graph.Path e : list) {
			if (!uf.isSame(e.from, e.to)) {
				uf.union(e.from, e.to);
				ret += e.cost;
				output.add(e);
			}
		}

		return ret;
	}
}
