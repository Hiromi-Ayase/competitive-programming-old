
class UnionFind {
	private int[] table;
	private int[] rank;
	
	public UnionFind(int size) {
		this.table = new int[size];
		this.rank = new int[size];
		for (int i = 0; i < size; i ++) {
			this.table[i] = i;
			this.rank[i] = 1;
		}
	}

	public boolean isSame(int node1, int node2) {
		return find(node1) == find(node2);
	}

	public int find(int node) {
		if (table[node] == node) {
			return node;
		} else {
			return table[node] = find(table[node]);
		}
	}

	public void union(int node1, int node2) {
		int root1 = find(node1);
		int root2 = find(node2);
		
		if (rank[root1] < rank[root2]) {
			table[root1] = root2;
		} else if (rank[root1] > rank[root2]) {
			table[root2] = root1;
		} else if (root1 != root2) {
			table[root2] = root1;
			rank[root1] ++;
		}
	}
}
