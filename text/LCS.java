import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class LCS {
	public static List<Integer> lcs(int[] a, int[] b) {
		int n = a.length;
		int m = b.length;
		int[][] Y = new int[n + 1][m + 1];
		int[][] X = new int[n + 1][m + 1];
		
		for (int i = 0; i < n; i ++) {
			for (int j = 0; j < m; j ++) {
				if (a[i] == b[j]) {
					X[i + 1][j + 1] = X[i][j] + 1;
					Y[i + 1][j + 1] = 0;
				} else  if (X[i + 1][j] < X[i][j + 1]) {
					X[i + 1][j + 1] = X[i][j + 1];
					Y[i + 1][j + 1] = +1;
				} else {
					X[i + 1][j + 1] = X[i + 1][j];
					Y[i + 1][j + 1] = -1;
				}
			}
		}
		
		List<Integer> ret = new ArrayList<>();
		for (int i = n, j = m; i > 0 && j > 0;) {
			if (Y[i][j] > 0) {
				i --;
			} else if (Y[i][j] < 0) {
				j --;
			} else {
				ret.add(a[i - 1]);
				i --;
				j --;
			}
		}

		Collections.reverse(ret);
		return ret;
	}

	public static int lcsCount(int[] a, int[] b) {
		int n = a.length;
		int m = b.length;
		int[][] X = new int[n + 1][m + 1];
		
		for (int i = 0; i < n; i ++) {
			for (int j = 0; j < m; j ++) {
				if (a[i] == b[j]) {
					X[i + 1][j + 1] = X[i][j] + 1;
				} else  if (X[i + 1][j] < X[i][j + 1]) {
					X[i + 1][j + 1] = X[i][j + 1];
				} else {
					X[i + 1][j + 1] = X[i + 1][j];
				}
			}
		}
		return X[n][m];
	}

}
