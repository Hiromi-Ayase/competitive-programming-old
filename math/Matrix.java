

public class Matrix {
	// x[k] = factor * x[k - 1] + a, a[0] = first
	public static long recurrence(long first, long factor, long a, long k, long mod) {
		int n = 63 - Long.numberOfLeadingZeros(k);
		long[] answer = {1L, 0, 0, 1L};
		long[] x = {factor, a, 0, 1};

		for (int i = n; i >= 0; i--) {
			answer = mul(answer, answer, mod);

			if (((k >> i) & 1L) == 1L) {
				answer = mul(answer, x, mod);
			}
		}
		return (answer[0] * first + answer[1]) % mod;
	}

	private static long[] mul(long[] a, long[] b, long mod) {
		int n = (int)Math.sqrt(a.length);
		
		long[] ret = new long[a.length];
		for (int i = 0; i < n ; i ++) {
			for (int j = 0; j < n; j ++) {
				for (int k = 0; k < n; k ++) {
					ret[i * n + j] += a[i * n + k] * b[k * n + j];
					ret[i * n + j] %= mod;
				}
			}
		}
		return ret;
	}
}


