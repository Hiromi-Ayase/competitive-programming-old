import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NumberTheory {
	public static boolean isPrime(long x) {
		if (x == 0 || x == 1 || x % 2 == 0) {
			return false;
		}

		long sqrtX = (long) Math.sqrt(x);
		for (long i = 3; i <= sqrtX; i += 2) {
			if (x % i == 0) {
				return false;
			}
		}
		return true;
	}


	public static int[] sieveAtkin(int size) {
		boolean[] isPrime = new boolean[size + 1];
		int sqrtN = (int) Math.sqrt(size);
		int n;
		for (int x = 1; x <= sqrtN; ++x) {
			for (int y = 1; y <= sqrtN; ++y) {
				n = 4 * x * x + y * y;
				if (n <= size && (n % 12 == 1 || n % 12 == 5)) {
					isPrime[n] = !isPrime[n];
				}
				n = 3 * x * x + y * y;
				if (n <= size && n % 12 == 7) {
					isPrime[n] = !isPrime[n];
				}
				n = 3 * x * x - y * y;
				if (x > y && n <= size && n % 12 == 11) {
					isPrime[n] = !isPrime[n];
				}
			}
		}

		for (n = 5; n <= sqrtN; ++n) {
			if (isPrime[n]) {
				for (int k = n * n; k <= size; k += n * n) {
					isPrime[k] = false;
				}
			}
		}
		isPrime[2] = isPrime[3] = true;
		
		int[] ret = new int[size];
		int count = 0;
		for (int i = 1; i < size; i ++) {
			if (isPrime[i]) {
				ret[count ++] = i;
			}
		}
		return Arrays.copyOf(ret, count);
	}

	public static long combMod(long n, long r, long mod) {
		long comb = 1;
		for (int i = 0; i < r; i++) {
			comb = (comb * (n - i)) % mod;
			comb = (comb * inverse(r - i, mod)) % mod;
		}
		return comb;
	}

	public static long powMod(long x, long k, long mod) {
		int n = 63 - Long.numberOfLeadingZeros(k);
		long answer = 1;
		for (int i = n; i >= 0; i--) {
			answer = (answer * answer) % mod;
			if (((k >> i) & 1L) == 1L) {
				answer = (answer * x) % mod;
			}
		}
		return answer;
	}

	public static long powModBI(long x, long k, long mod) {
		int n = 63 - Long.numberOfLeadingZeros(k);
		BigInteger answer = BigInteger.ONE;
		BigInteger bmod = BigInteger.valueOf(mod);
		BigInteger bx = BigInteger.valueOf(x);

		for (int i = n; i >= 0; i--) {
			answer = answer.multiply(answer).mod(bmod);
			if (((k >> i) & 1L) == 1L) {
				answer = answer.multiply(bx).mod(bmod);
			}
		}
		return answer.longValue();
	}

	public static BigInteger powBI(long x, long k) {
		int n = 63 - Long.numberOfLeadingZeros(k);
		BigInteger answer = BigInteger.ONE;
		BigInteger bx = BigInteger.valueOf(x);

		for (int i = n; i >= 0; i--) {
			answer = answer.multiply(answer);
			if (((k >> i) & 1L) == 1L) {
				answer = answer.multiply(bx);
			}
		}
		return answer;
	}

	public static long gcd(long a, long b) {
		if (a > b) {
			long tmp = a;
			a = b;
			b = tmp;
		}
		while (a != 0) {
			long c = a;
			a = b % c;
			b = c;
		}
		return b;
	}

	public static long[] extgcd(long a, long b) {
		long u = 1;
		long v = 0;
		long x = 0;
		long y = 1;

		while (a > 0) {
			long q = b / a;
			x -= q * u;
			y -= q * v;
			b -= q * a;

			long tmp;
			tmp = x;
			x = u;
			u = tmp;
			tmp = y;
			y = v;
			v = tmp;
			tmp = b;
			b = a;
			a = tmp;
		}

		return new long[] { b, x, y };
	}

	public static long inverse(long n, long mod) {
		long[] gcd = extgcd(n, mod);
		if (gcd[0] == 1) {
			return (gcd[1] + mod) % mod;
		} else {
			return 0;
		}
	}


	public static long[][] factorize(long n, int[] primes) {
		int rp = (int)Math.sqrt(n);
		long[][] factors = new long[100][2];
		int idx = 0;
		for (int i = 0; primes[i] <= rp; i ++) {
			int p = primes[i];
			int count = 0;
			while (n % p == 0) {
				count ++;
				n /= p;
			}
			if (count > 0) {
				factors[idx][0] = p;
				factors[idx][1] = count;
				idx ++;
			}
			if (n == 1) {
				break;
			}
		}
		if (n != 1) {
			factors[idx][0] = n;
			factors[idx][1] = 1;
			idx ++;
		}

		return Arrays.copyOf(factors, idx);
	}

	public static List<Long> allFactors(long n) {
		if (n == 1) {
			return new ArrayList<Long>(Arrays.asList(1L));
		}
		long logN = (long) Math.sqrt(n) + 1;
		List<long[]> factors = new ArrayList<>();

		for (int i = 2; i < logN; i++) {
			if (n % i == 0) {
				long[] elem = new long[] { i, 0 };
				while (n % i == 0) {
					elem[1]++;
					n /= i;
				}
				factors.add(elem);
			}
		}
		if (n != 1) {
			factors.add(new long[] { n, 1 });
		}

		int[] count = new int[factors.size()];
		int factorsSize = factors.size();

		List<Long> list = new ArrayList<>();
		loop: while (true) {
			long x = 1;
			for (int i = 0; i < factorsSize; i++) {
				long[] v = factors.get(i);
				for (int j = 0; j < count[i]; j++) {
					x *= v[0];
				}
			}
			list.add(x);

			int idx = 0;
			while (count[idx] == factors.get(idx)[1]) {
				count[idx] = 0;
				idx++;
				if (idx == factorsSize) {
					break loop;
				}
			}
			count[idx]++;
		}
		return list;
	}
	
	public static BigInteger sqrt(BigInteger N) {
		BigInteger left = BigInteger.ONE;
		BigInteger right = N;
		BigInteger two = BigInteger.valueOf(2);

		while (left.compareTo(right) <= 0) {
			BigInteger mid = left.add(right).divide(two);

			int cmp = N.compareTo(mid.multiply(mid));
			if (cmp == 0) {
				return mid;
			} else if (cmp > 0) {
				left = mid.add(BigInteger.ONE);
			} else {
				right = mid.subtract(BigInteger.ONE);
			}
		}
		return null;
	}

	public static long sqrtL(long N) {
		long left = (long)(Math.sqrt(N) * 0.99);
		long right = (long)(Math.sqrt(N) * 1.01);

		while (left <= right) {
			long mid = (left + right) / 2;
			long cmp = N - mid * mid;
			if (cmp == 0) {
				return mid;
			} else if (cmp > 0) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return -1;
	}

	public static boolean isPalindromic(long n) {
		String s = Long.toString(n);
		int len = s.length();
		int hlen = len / 2;
		for (int i = 0; i < hlen; i ++) {
			if (s.charAt(i) != s.charAt(len - i - 1)) {
				return false;
			}
		}
		return true;
	}

	public static void listupCompositeNumber(int[] primes, long max) {
		long[][] table = new long[primes.length][2];
		for (int i = 0; i < primes.length; i ++) {
			table[i][0] = primes[i];
			table[i][1] = 1;
		}
		
		int x = 1;
		loop: while (true) {
			int digit = 0;
			while (x * table[digit][0] > max) {
				x /= table[digit][1];
				table[digit][1] = 1;
				digit ++;
				if (digit == primes.length || table[digit][0] > max) {
					break loop;
				}
			}
			long p = table[digit][0];
			table[digit][1] *= p;
			x *= p;
			
			System.out.println(x);
		}
	}

	public static long[][] getPythagoreanTriple(long max) {
		List<long[]> list = new ArrayList<>();
		for (long n = 1; n * n < max; n ++) {
			for (long m = 1; m < n; m ++) {
				if ((n + m) % 2 == 1 && gcd(n, m) == 1) {
					long x = n * n - m * m;
					long y = 2 * n * m;
					long z = n * n + m * m;
					int k = 1;

					while (z * k < max) {
						list.add(new long[] {x * k, y * k, z * k});
						k ++;
					}
				}
			}
		}
		return list.toArray(new long[0][]);
	}
	
	public static void listupDiv(int len, int n, int k, int[] current, List<int[]> list) {
		if (n == 0 && len == 0) {
			int[] cp = Arrays.copyOf(current, current.length);
			list.add(cp);
		}
		if (n == 0 || len < 0) {
			return;
		}

		for (int i = 1; i <= k && i <= len; i ++) {
			current[n - 1] = i;
			listupDiv(len - i, n - 1, k, current, list);
		}
	}
}
