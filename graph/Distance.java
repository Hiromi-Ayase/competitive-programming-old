import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Distance {
	// O(E) {distance, index1, index2}
	public static long[] treeDiameter(Graph g, int start) {
		ArrayDeque<Graph.Path> queue = new ArrayDeque<>();

		long[] max = new long[3];
		int[] maxIdx = new int[]{start, 0, 0};
		for (int i = 1; i <= 2; i ++) {
			queue.add(new Graph.Path(-1, maxIdx[i - 1], 0));
			
			while (queue.size() > 0) {
				Graph.Path e = queue.poll();
				if (e.cost > max[i]) {
					max[i] = e.cost;
					maxIdx[i] = e.to;
				}
				for (Graph.Path p : g.get(e.to)) {
					if (p.to != e.from) {
						queue.add(new Graph.Path(p.from, p.to, e.cost + 1));
					}
				}
			}
		}
		return new long[]{max[2], maxIdx[1], maxIdx[2]};
	}

	public static long[] bellmanFord(Graph graph, int start) throws Exception {
		int n = graph.size();
		long[] dist = new long[n];
		for (int i = 0; i < n; i++) {
			dist[i] = Integer.MAX_VALUE;
		}
		dist[start] = 0;

		for (int i = 0; i < n; i++) {
			for (int v = 0; v < n; v++) {
				for (Graph.Path edge : graph.get(v)) {
					if (dist[v] != Integer.MAX_VALUE && dist[edge.to] > dist[v] + edge.cost) {
						dist[edge.to] = dist[v] + edge.cost;
						if (i == n - 1) {
							throw new Exception();
						}
					}
				}
			}
		}
		return dist;
	}

	public static long[][] warshallFloyd(Graph graph) {
		int n = graph.size();
		long[][] distance = new long[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				distance[i][j] = Integer.MAX_VALUE;
			}
			distance[i][i] = 0;
			for (Graph.Path edge : graph.get(i)) {
				distance[i][edge.to] = edge.cost;
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					if (distance[j][i] < Integer.MAX_VALUE && distance[i][k] < Integer.MAX_VALUE) {
						distance[j][k] = Math.min(distance[j][k], distance[j][i] + distance[i][k]);
					}
				}
			}
		}
		return distance;
	}

	public static long[] dijkstra(Graph graph, int start) {
		int n = graph.size();
		long[] distance = new long[n];
		boolean[] visited = new boolean[n];

		for (int i = 0; i < n; i++) {
			distance[i] = Integer.MAX_VALUE;
		}

		PriorityQueue<Graph.Path> queue = new PriorityQueue<>();
		distance[start] = 0;
		queue.add(new Graph.Path(start, start, 0));

		while (queue.size() > 0) {
			Graph.Path minPath = queue.poll();
			visited[minPath.to] = true;

			for (Graph.Path edge : graph.get(minPath.to)) {
				if (!visited[edge.to]) {
					long newCost = edge.cost + minPath.cost;
					if (newCost < distance[edge.to]) {
						queue.add(new Graph.Path(start, edge.to, newCost));
						distance[edge.to] = newCost;
					}
				}
			}
		}
		return distance;
	}

	public static long kShortestPath(Graph graph, int start, int goal, int k) {
		int n = graph.size();
		List<List<Long>> distance = new ArrayList<List<Long>>();
		for (int i = 0; i < n; i ++) {
			distance.add(new ArrayList<Long>());
		}

		PriorityQueue<Graph.Path> queue = new PriorityQueue<>();
		queue.add(new Graph.Path(start, start, 0));
		while (!queue.isEmpty()) {
			Graph.Path minPath = queue.poll();
			if (distance.get(minPath.to).size() > k) {
				continue;
			}
			distance.get(minPath.to).add(minPath.cost);
			for (Graph.Path f : graph.get(minPath.to)) {
				queue.add(new Graph.Path(start, f.to, f.cost + +minPath.cost));
			}
		}
		return distance.get(goal).get(k);
	}
}
