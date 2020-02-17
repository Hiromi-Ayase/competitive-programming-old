import java.util.Comparator;
import java.util.PriorityQueue;

class Grid {
	private static int[][] DIR = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
	public static long[][] dijkstra(char[][] map, int[] start, int k) {
		int w = map[0].length;
		int h = map.length;
		long[][] len = new long[h][w];
		for (int i = 0; i < h; i ++) {
			for (int j = 0; j < w; j ++) {
				len[i][j] = Long.MAX_VALUE;
			}
		}

		PriorityQueue<long[]> queue = new PriorityQueue<long[]>(new Comparator<long[]>() {
			@Override
			public int compare(long[] o1, long[] o2) {
				return Long.compare(o1[2], o2[2]);
			}
		});

		queue.add(new long[]{start[0], start[1], 0});
		
		boolean[][] visited = new boolean[h][w];

		while (queue.size() > 0) {
			long[] pos = queue.poll();
			int x = (int)pos[0];
			int y = (int)pos[1];
			len[y][x] = pos[2];
			if (visited[y][x]) {
				continue;
			}
			visited[y][x] = true;

			for (int[] d : DIR) {
				int nextX = x + d[0];
				int nextY = y + d[1];
				if (map[nextY][nextX] != 0) {
					if (!visited[nextY][nextX]) {
						long newCost = pos[2] + (map[nextY][nextX] == '#' ? k : 1);
						queue.add(new long[]{nextX, nextY, newCost});
					}
				}
			}
		}
		return len;
	}

	public static long[][] dijkstra(char[][] map, int[] start) {
		int w = map[0].length;
		int h = map.length;
		long[][] len = new long[h][w];
		for (int i = 0; i < h; i ++) {
			for (int j = 0; j < w; j ++) {
				len[i][j] = Long.MAX_VALUE;
			}
		}

		PriorityQueue<long[]> queue = new PriorityQueue<long[]>(new Comparator<long[]>() {
			@Override
			public int compare(long[] o1, long[] o2) {
				return Long.compare(o1[2], o2[2]);
			}
		});

		queue.add(new long[]{start[0], start[1], 0});
		
		boolean[][] visited = new boolean[h][w];

		while (queue.size() > 0) {
			long[] pos = queue.poll();
			int x = (int)pos[0];
			int y = (int)pos[1];
			len[y][x] = pos[2];
			if (visited[y][x]) {
				continue;
			}
			visited[y][x] = true;

			for (int[] d : DIR) {
				int nextX = x + d[0];
				int nextY = y + d[1];
				if (map[nextY][nextX] != '#') {
					if (!visited[nextY][nextX]) {
						long newCost = pos[2] + 1;
						queue.add(new long[]{nextX, nextY, newCost});
					}
				}
			}
		}
		return len;
	}
}
