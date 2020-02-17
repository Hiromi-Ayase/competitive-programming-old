import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BIT {
	private long[] array;
	private int n;

	public BIT(int n) {
		this.array = new long[n + 1];
		this.n = n;
	}

	public void add(int a, long w) {
		for (int i = a; i <= n; i += (i & -i)) {
			array[i] += w;
		}
	}
	
	public long sum(int a) {
		long ret = 0;
		for (int i = a; i > 0; i -= (i & -i)) {
			ret += array[i];
		}
		return ret;
	}

	public long sum(int a, int b) {
		return a == 0 ? sum(b) : (sum(b) - sum(a - 1));
	}

}

class BIT_Max {
	private long[] array;
	private int n;

	public BIT_Max(int n) {
		this.array = new long[n + 1];
		this.n = n;
	}

	public void add(int a, long w) {
		for (int i = a; i <= n; i += (i & -i)) {
			array[i] = Math.max(array[i], w);
		}
	}
	
	public long max(int a) {
		long ret = 0;
		for (int i = a; i > 0; i -= (i & -i)) {
			ret = Math.max(array[i], ret);
		}
		return ret;
	}

	public int binarySerach(long a) {
		int left = 1;
		int right = n;
		while (left <= right) {
			int mid = left + right >>> 1;
			long max = max(mid);
			if (max >= a) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}
		}
		long m = max(left);
		if (m == a) {
			return left;
		} else {
			return -left - 1;
		}
	}
}

class BIT_Min {
	private long[] array;
	private int n;

	public BIT_Min(int n) {
		this.array = new long[n + 1];
		Arrays.fill(this.array, Long.MAX_VALUE);
		this.n = n;
	}

	public void update(int a, long w) {
		for (int i = a; i <= n; i += (i & -i)) {
			array[i] = Math.min(array[i], w);
		}
	}
	
	public long min(int a) {
		long ret = 0;
		for (int i = a; i > 0; i -= (i & -i)) {
			ret = Math.min(array[i], ret);
		}
		return ret;
	}
}

abstract class AbstractBIT<T> {
	private T unit;
	private List<T> array;
	private int n;

	public AbstractBIT(int n, T unit) {
		this.unit = unit;
		this.array = new ArrayList<T>();
		for (int i = 0; i <= n; i ++) {
			array.add(unit);
		}
		this.n = n;
	}

	public abstract T product(T a, T b);
	
	public void update(int a, T w) {
		for (int i = a; i <= n; i += (i & -i)) {
			array.set(i, product(array.get(i), w));
		}
	}
	
	public T query(int a) {
		T ret = unit;
		for (int i = a; i > 0; i -= (i & -i)) {
			ret = product(ret, array.get(i));
		}
		return ret;
	}
}
