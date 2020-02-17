class ConcreteTree extends StarrySkyTree {
	public ConcreteTree(int size) {
		super(size, 0);
	}

	@Override
	public long calcAdd(long a, long b) {
		return a + b;
	}

	@Override
	public long calcGet(long a, long b) {
		return Math.max(a, b);
	}
}

abstract class StarrySkyTree {
	private final long UNIT;
	private final long[] tree;
	private final long[] add;
	private final int size;

	public StarrySkyTree(int size, int unit) {
		this.UNIT = unit;
		size = 1 << (32 - Integer.numberOfLeadingZeros(size));

		this.tree = new long[size * 2];
		this.add = new long[size * 2];
		for (int i = 0; i < size * 2; i ++) {
			this.tree[i] = UNIT;
			this.add[i] = UNIT;
		}
		this.size = size;
	}

	public abstract long calcAdd(long a, long b);
	public abstract long calcGet(long a, long b);

	private void queryUpdate(int a, int b, int k, int l, int r, long n) {
		if (b <= l || r <= a) {
			return;
		}

		if (a <= l && r <= b) {
			add[k] = calcAdd(add[k], n);
			while (k > 0) {
				k = (k - 1) / 2;
				long x1 = tree[2 * k + 1];
				long x2 = add[2 * k + 1];
				long y1 = tree[2 * k + 2];
				long y2 = add[2 * k + 2];
				long x = calcAdd(x1, x2);
				long y = calcAdd(y1, y2);
				tree[k] = calcGet(x, y);
			}
		} else {
			queryUpdate(a, b, 2 * k + 1, l, (l + r) / 2, n);
			queryUpdate(a, b, 2 * k + 2, (l + r) / 2, r, n);
		}
	}


	private long query(int a, int b, int k, int l, int r) {
		if (b <= l || r <= a) {
			return UNIT;
		}

		if (a <= l && r <= b) {
			long x1 = tree[k];
			long x2 = add[k];
			return calcAdd(x1, x2);
		} else {
			long ql = query(a, b, 2 * k + 1, l, (l + r) / 2);
			long qr = query(a, b, 2 * k + 2, (l + r) / 2, r);
			long x = calcGet(ql, qr);
			return calcAdd(x, add[k]);
		}
	}
	
	public long query(int a, int b) {
		return query(a, b, 0, 0, size);
	}
	
	public void queryUpdate(int a, int b, long n) {
		queryUpdate(a, b, 0, 0, size, n);
	}
}
