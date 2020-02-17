import java.util.Arrays;

public class Permutation {
	public static void permDFS(long table, int[] v, int N, int depth) {
		if (depth == v.length) {
			System.out.println(Arrays.toString(v));
			return;
		}
		for (int i = 0; i < N; i ++) {
			if ((table & (1L << i)) == 0) {
				v[depth] = i;
				permDFS(table | (1L << i), v, N, depth + 1);
			}
		}
	}

	public static void perm(int N, int len) {
		permDFS(0, new int[len], N, 0);
	}

	public static void permDFSL(long table, long v, int N, int depth, int maxDepth) {
		if (depth == maxDepth) {
			System.out.println(v);
			return;
		}
		for (int i = 0; i < N; i ++) {
			if ((depth != 0 || i != 0) && (table & (1L << i)) == 0) {
				permDFSL(table | (1L << i), v * 10 + i, N, depth + 1, maxDepth);
			}
		}
	}

	public static void permL(int N, int len) {
		permDFSL(0, 0, N, 0, len);
	}
}
