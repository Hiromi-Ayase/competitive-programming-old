import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

abstract class SegmentTree<T> {
	private final T unit;
	private final List<T> tree;
	private final int size;

	public SegmentTree(int size, T unit) {
		size = 1 << (32 - Integer.numberOfLeadingZeros(size));
		this.unit = unit;
		this.tree = new ArrayList<T>(size * 2);
		for (int i = 0; i < size * 2; i++) {
			this.tree.add(unit);
		}
		this.size = size;
	}

	public void update(int i, T n) {
		int k = i + size - 1;
		tree.set(k, n);
		while (k > 0) {
			k = (k - 1) / 2;
			T a = tree.get(2 * k + 1);
			T b = tree.get(2 * k + 2);
			tree.set(k, calc(a, b));
		}
	}

	public abstract T calc(T a, T b);

	private T query(int a, int b, int k, int l, int r) {
		if (b <= l || r <= a) {
			return unit;
		}

		if (a <= l && r <= b) {
			return tree.get(k);
		} else {
			T ql = query(a, b, 2 * k + 1, l, (l + r) / 2);
			T qr = query(a, b, 2 * k + 2, (l + r) / 2, r);
			return calc(ql, qr);
		}
	}

	public T query(int a, int b) {
		return query(a, b, 0, 0, size);
	}
}

class SegmentTreeRMQ {
	public int M, H, N;
	public long[] st;

	public SegmentTreeRMQ(int n) {
		N = n;
		M = Integer.highestOneBit(Math.max(N - 1, 1)) << 2;
		H = M >>> 1;
		st = new long[M];
		Arrays.fill(st, 0, M, Long.MAX_VALUE);
	}

	public SegmentTreeRMQ(int[] a) {
		N = a.length;
		M = Integer.highestOneBit(Math.max(N - 1, 1)) << 2;
		H = M >>> 1;
		st = new long[M];
		for (int i = 0; i < N; i++) {
			st[H + i] = a[i];
		}
		Arrays.fill(st, H + N, M, Integer.MAX_VALUE);
		for (int i = H - 1; i >= 1; i--)
			propagate(i);
	}

	public void update(int pos, long x) {
		st[H + pos] = x;
		for (int i = (H + pos) >>> 1; i >= 1; i >>>= 1)
			propagate(i);
	}

	private void propagate(int i) {
		st[i] = Math.min(st[2 * i], st[2 * i + 1]);
	}

	public long min(int l, int r) {
		return l >= r ? 0 : min(l, r, 0, H, 1);
	}

	private long min(int l, int r, int cl, int cr, int cur) {
		if (l <= cl && cr <= r) {
			return st[cur];
		} else {
			int mid = cl + cr >>> 1;
			long ret = Long.MAX_VALUE;
			if (cl < r && l < mid) {
				ret = Math.min(ret, min(l, r, cl, mid, 2 * cur));
			}
			if (mid < r && l < cr) {
				ret = Math.min(ret, min(l, r, mid, cr, 2 * cur + 1));
			}
			return ret;
		}
	}

	public int firstle(int l, int v) {
		int cur = H + l;
		while (true) {
			if (st[cur] <= v) {
				if (cur < H) {
					cur = 2 * cur;
				} else {
					return cur - H;
				}
			} else {
				cur++;
				if ((cur & cur - 1) == 0)
					return -1;
				if ((cur & 1) == 0)
					cur >>>= 1;
			}
		}
	}

	public int lastle(int l, int v) {
		int cur = H + l;
		while (true) {
			if (st[cur] <= v) {
				if (cur < H) {
					cur = 2 * cur + 1;
				} else {
					return cur - H;
				}
			} else {
				if ((cur & cur - 1) == 0)
					return -1;
				cur--;
				if ((cur & 1) == 1)
					cur >>>= 1;
			}
		}
	}
}
