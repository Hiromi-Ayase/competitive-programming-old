import java.util.ArrayList;
import java.util.List;

class Graph {
	public static class Path implements Comparable<Path> {
		public final int from;
		public final int to;
		public final long cost;

		public Path(int from, int to, long cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Path o) {
			return Long.compare(this.cost, o.cost);
		}
	}
	
	private final List<List<Path>> edgeList;
	private final int size;

	public Graph(int n) {
		this.size = n;
		this.edgeList = new ArrayList<List<Path>>();
		for (int i = 0; i < n; i ++) {
			this.edgeList.add(new ArrayList<Path>());
		}
	}

	public void add(int from, int to, long cost) {
		this.edgeList.get(from).add(new Path(from ,to, cost));
	}
	
	public void addBoth(int v1, int v2, long cost) {
		this.add(v1, v2, cost);
		this.add(v2, v1, cost);
	}

	public void add(int from, int to) {
		this.edgeList.get(from).add(new Path(from ,to, 1));
	}
	
	public void addBoth(int v1, int v2) {
		this.add(v1, v2, 1);
		this.add(v2, v1, 1);
	}

	public List<Path> get(int from) {
		return this.edgeList.get(from);
	}
	
	public int size() {
		return this.size;
	}
}

