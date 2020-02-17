import java.util.Arrays;

class Convex {
	public static long outerProduct(Point p1, Point p2, Point o) {
		return (p1.x - o.x) * (p2.y - o.y) - (p1.y - o.y) * (p2.x - o.x);
	}

	public static Point[] getConvex(Point[] input) {

		int n = input.length;
		Point[] ps = Arrays.copyOf(input, input.length);
		Arrays.sort(ps);
		Point[] ch = new Point[n * 2];
		int k = 0;
		for (int i = 0; i < n; ch[k++] = ps[i++]) {
			while (k >= 2 && outerProduct(ch[k - 2], ch[k - 1], ps[i]) <= 0) {
				--k;
			}
		}
		for (int i = n - 2, t = k + 1; i >= 0; ch[k++] = ps[i--]) {
			while (k >= t && outerProduct(ch[k - 2], ch[k - 1], ps[i]) <= 0) {
				--k;
			}
		}
		ch = Arrays.copyOf(ch, k - 1);
		return ch;
	}
}