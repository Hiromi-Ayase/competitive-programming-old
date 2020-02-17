import java.util.Arrays;
import java.util.Comparator;

class SuffixArray {
	private final String str;
	public final Integer[] array;

	public SuffixArray(final String str) {
		final int n = str.length();
		this.array = new Integer[n];
		this.str = str;

		for (int i = 0; i < n; i++) {
			array[i] = i;
		}

		Arrays.sort(array, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				int i1 = o1;
				int i2 = o2;
				int N = Math.min(n - i1, n - i2);
				for (int i = 0; i < N; i++) {
					if (str.charAt(i1 + i) < str.charAt(i2 + i))
						return -1;
					if (str.charAt(i1 + i) > str.charAt(i2 + i))
						return +1;
				}
				return i2 - i1;
			}
		});
	}

	public int[] lcp() {
		int n = str.length();
		int[] inv = new int[n];
		int[] lcp = new int[n];
		for (int i = 0; i < n; i++) {
			inv[array[i]] = i;
		}
		int h = 0;
		for (int i = 0; i < n; i++) {
			if (inv[i] == n - 1) {
				continue;
			}
			h = Math.max(h - 1, 0);
			int j = array[inv[i] + 1];
			while (i + h < n && j + h < n && str.charAt(i + h) == str.charAt(j + h)) {
				h++;
			}
			lcp[inv[i]] = h;
		}
		return lcp;
	}

	public String search(String word) {
		int left = 0;
		int right = array.length;
		while (left <= right) {
			int mid = (left + right) / 2;
			String suffix = str.substring(array[mid]);
			int cmp = word.compareTo(suffix);
			if (cmp == 0) {
				break;
			} else if (cmp > 0) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return str.substring(array[right]);
	}
}