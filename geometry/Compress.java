import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Compress {
	public final Map<Long, Integer> zip = new HashMap<>();;
	public final long[] unzip;
	public final int[] list;

	public Compress(long[] src) {
		int n = src.length;
		long[] tmp = Arrays.copyOf(src, n);
		Arrays.sort(tmp);

		unzip = new long[n];
		int index = 0;
		for (int i = 0; i < n; i ++) {
			long v = tmp[i];
			if (!zip.containsKey(v)) {
				zip.put(v, index ++);
				unzip[i] = v;
			}
		}

		list = new int[n];
		for (int i = 0; i < n; i ++) {
			list[i] = zip.get(src[i]);
		}
	}
}